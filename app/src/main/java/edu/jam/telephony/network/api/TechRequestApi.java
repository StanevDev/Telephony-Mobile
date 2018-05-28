package edu.jam.telephony.network.api;


import java.util.Date;
import java.util.List;

import edu.jam.telephony.model.TechRequest;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TechRequestApi {

    @GET("tech/get/all")
    Observable<List<TechRequest>> getDebugRequests();

    @GET("tech/get/my")
    Observable<List<TechRequest>> getTechRequests();

    @POST("tech/add")
    Observable<TechRequest> addTechRequest(@Body String description);

}
