package com.arctouch.codechallenge.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance;
    private Retrofit retrofit;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(TmdbApi.URL)
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null){
            instance = new RetrofitClient();
        }
        return instance;
    }

    public TmdbApi getTmdbApi(){
        return retrofit.create(TmdbApi.class);
    }
}
