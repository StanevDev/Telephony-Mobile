package edu.jam.telephony.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitService {

    private static final String API_BASE_URL = "http://192.168.0.168:8080/api/";

    public static synchronized <S> S createApi(Class<S> serviceClass) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS);

        if (credentials != null){
            AuthenticationInterceptor authInterceptor =
                    new AuthenticationInterceptor(credentials);
            clientBuilder.addInterceptor(authInterceptor);
        }

        OkHttpClient client = clientBuilder.build();

                Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .client(client)
                        .addCallAdapterFactory(new ObserveOnMainCallAdapterFactory(AndroidSchedulers.mainThread()))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .addConverterFactory(GsonConverterFactory.create(gson));

        builder.baseUrl(API_BASE_URL);

        Retrofit retrofit = builder.build();
        return retrofit.create(serviceClass);
    }

    public static class AuthenticationInterceptor implements Interceptor {

        private String authToken;

        public AuthenticationInterceptor(String token) {
            this.authToken = token;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            Request.Builder builder = original.newBuilder()
                    .header("Authorization", authToken);

            Request request = builder.build();
            return chain.proceed(request);
        }
    }

    private static String getTestCredentials(){
        return Credentials.basic("metzzo@comcast.net", "password");
    }

    public static String credentials;

}
