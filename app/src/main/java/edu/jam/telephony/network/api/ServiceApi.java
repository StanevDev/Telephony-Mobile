package edu.jam.telephony.network.api;

import java.util.List;

import edu.jam.telephony.model.Service;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ServiceApi {

    @GET("service/get")
    Observable<List<Service>> getAll();

    @GET("service/get/my")
    Observable<List<Service>> getMyExtraServices();

}
