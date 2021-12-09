package com.example.registerloginexample;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private static initMyApi initMyApi;

    private final static String baseUrl = "http://http://10.0.2.2:8080/";

    private RetrofitClient() {
        // log를 보기 위한 Interceptor
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                                              .addInterceptor(interceptor)
                                              .build();

        // retrofit 설정
        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(baseUrl)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .client(client)
                                        .build();

        initMyApi = retrofit.create(initMyApi.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public static initMyApi getRetrofitInterface() {
        return initMyApi;
    }
}
