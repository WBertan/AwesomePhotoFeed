package com.wbertan.awesomephotofeed.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wbertan.awesomephotofeed.BuildConfig;
import com.wbertan.awesomephotofeed.json.DateDeserializer;
import com.wbertan.awesomephotofeed.props.PropsRequestUrl;

import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by william.bertan on 26/12/2016.
 */

abstract class RepositoryGeneric {
    Retrofit getRetrofit(@PropsRequestUrl String aBaseUrl) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(aBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson));

        if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);
            retrofitBuilder.client(httpClient.build());
        }

        return retrofitBuilder.build();
    }
}
