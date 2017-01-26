package com.abhinav.networkingfuzz;

import android.app.Application;

/**
 * Created by abhinav.sharma on 25/01/17.
 */

public class NetworkingFuzz extends Application {

    private static NetworkingFuzz mNetworkingFuzz;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetworkingFuzz = this;

    }

    public static NetworkingFuzz getInstance() {
        return mNetworkingFuzz;
    }
}
