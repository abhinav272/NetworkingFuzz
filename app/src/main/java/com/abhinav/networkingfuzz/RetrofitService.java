package com.abhinav.networkingfuzz;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abhinav.sharma on 11/1/2016.
 */
public class RetrofitService {

    public static ApiInterface getRetrofitService(){
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiInterface.SERVICE_ENDPOINT).build();


        return retrofit.create(ApiInterface.class);

    }
}
