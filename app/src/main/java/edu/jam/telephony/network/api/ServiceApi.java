package edu.jam.telephony.network.api;

import java.util.List;

import edu.jam.telephony.model.Service;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceApi {

    @GET("service/get")
    Observable<List<Service>> getAll();

    @GET("service/get/my")
    Observable<List<Service>> getMyExtraServices();

    @POST("service/connect/{id}")
    Observable<Service> connectService(@Path("id") Integer serviceId);

    @POST("service/disconnect/{id}")
    Observable<Service> disconnectService(@Path("id") Integer serviceId);

}
