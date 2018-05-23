package edu.jam.telephony.network.api;

import java.util.List;

import edu.jam.telephony.model.TariffPlan;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TariffApi {

    @GET("tariff/get/all")
    Observable<List<TariffPlan>> getTariffs();

    @GET("tariff/get/{id}")
    Observable<TariffPlan> getTariff(@Path("id") String id);

}
