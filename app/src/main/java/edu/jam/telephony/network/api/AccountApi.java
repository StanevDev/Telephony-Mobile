package edu.jam.telephony.network.api;

import edu.jam.telephony.model.Subscriber;
import edu.jam.telephony.model.TariffPlan;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AccountApi {

    @GET("account/get")
    Observable<Subscriber> getAccount();

    @GET("tariff/get/{id}")
    Observable<TariffPlan> getTariff(@Path("id") String planId);

}
