package com.abhinav.networkingfuzz;

import com.abhinav.networkingfuzz.model.Library;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by abhinav.sharma on 11/1/2016.
 */
public interface ApiInterface {

    String SERVICE_ENDPOINT = " http://www.mocky.io";

    @GET("/v2/581854fb2800000308e80e85")
    Observable<Library> getLibrary();
}
