package com.wbertan.awesomephotofeed.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wbertan.awesomephotofeed.json.DateDeserializer;
import com.wbertan.awesomephotofeed.model.flickr.Author;
import com.wbertan.awesomephotofeed.model.flickr.Feed;
import com.wbertan.awesomephotofeed.props.PropsRequestUrl;
import com.wbertan.awesomephotofeed.rest.services.ServiceFlickr;

import java.io.IOException;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by william.bertan on 26/12/2016.
 */

public class RepositoryFlickr extends RepositoryGeneric {
    public Observable<Feed> getFeed() {
        return Observable.create(new ObservableOnSubscribe<Feed>() {
            @Override
            public void subscribe(ObservableEmitter<Feed> observableEmitter) throws Exception {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Date.class, new DateDeserializer())
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(PropsRequestUrl.FLICKR)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                ServiceFlickr serviceFlickr = retrofit.create(ServiceFlickr.class);
                Call<Feed> callFlickr = serviceFlickr.getFeed();
                try {
                    observableEmitter.onNext(callFlickr.execute().body());
                    observableEmitter.onComplete();
                } catch (IOException e) {
                    observableEmitter.onError(e);
                }
            }
        });
    }

    public Observable<Author> getAuthorProfile(String aAuthorId) {
        return null;
    }
}
