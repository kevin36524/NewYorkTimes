package com.example.patelkev.newyorktimes.Network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkHelper {

    OkHttpClient okHttpClient;
    Retrofit retrofit;
    NYTimesServices nyTimesServices;

    public static final String BASE_URL = "https://api.nytimes.com/";

    public NYTimesServices getNyTimesServices() {
        return nyTimesServices;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    private NetworkHelper() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        nyTimesServices = retrofit.create(NYTimesServices.class);
    }

    public static NetworkHelper sharedInstance() {
        return  NetworkHelperInstanceHolder.sharedInstance;
    }

    private static class NetworkHelperInstanceHolder {
        public static final NetworkHelper sharedInstance = new NetworkHelper();
    }
}