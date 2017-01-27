package com.abhinav.networkingfuzz;

import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abhinav.sharma on 11/1/2016.
 */
public class RetrofitService {

    /* Taken MAX_AGE for cache as 1 hr */
    private static final int MAX_AGE = 5;
    private static final int CACHE_SIZE = 10 * 1024 * 1024;
    private static final String CACHE_CONTROL = "cache_control";

    public static ApiInterface getRetrofitService(){
        Retrofit retrofit = new Retrofit.Builder()
                .client(getClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiInterface.SERVICE_ENDPOINT).build();


        return retrofit.create(ApiInterface.class);

    }

    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(getOfflineCacheInterceptor())
                .addNetworkInterceptor(getCacheInterceptor())
                .cache(getCache())
                .build();
    }

    private static Cache getCache() {
        Cache cache = null;
        try {
            cache = new Cache(new File(NetworkingFuzz.getInstance().getCacheDir(), "http-cache"), CACHE_SIZE); // 10 MB
            Log.e("getCache: ", "cache created at " + NetworkingFuzz.getInstance().getCacheDir().getAbsolutePath());
        } catch (Exception e) {
            Log.e("Could not create Cache!", e.getMessage());
        }
        return cache;
    }

    private static Interceptor getCacheInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response response = chain.proceed(chain.request());

                // re-write response header to force use of cache
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(MAX_AGE, TimeUnit.MINUTES)
                        .build();

                return response.newBuilder()
                        .header(CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

    private static Interceptor getOfflineCacheInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!Utils.isNetworkAvailable(NetworkingFuzz.getInstance())) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }
                return chain.proceed(request);
            }
        };
    }
}
