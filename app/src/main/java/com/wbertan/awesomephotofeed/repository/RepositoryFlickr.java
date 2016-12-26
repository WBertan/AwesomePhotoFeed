package com.wbertan.awesomephotofeed.repository;

import com.wbertan.awesomephotofeed.model.flickr.Feed;
import com.wbertan.awesomephotofeed.model.flickr.PersonResponse;
import com.wbertan.awesomephotofeed.model.flickr.PhotoResponse;
import com.wbertan.awesomephotofeed.props.PropsRequestUrl;
import com.wbertan.awesomephotofeed.rest.services.ServiceFlickr;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by william.bertan on 26/12/2016.
 */

public class RepositoryFlickr extends RepositoryGeneric {
    public Observable<Feed> getFeed(final String aTags) {
        return Observable.create(new ObservableOnSubscribe<Feed>() {
            @Override
            public void subscribe(ObservableEmitter<Feed> observableEmitter) throws Exception {
                Retrofit retrofit = getRetrofit(PropsRequestUrl.FLICKR);
                ServiceFlickr serviceFlickr = retrofit.create(ServiceFlickr.class);
                Call<Feed> callFlickr = serviceFlickr.getFeed(aTags);
                try {
                    observableEmitter.onNext(callFlickr.execute().body());
                    observableEmitter.onComplete();
                } catch (IOException e) {
                    observableEmitter.onError(e);
                }
            }
        });
    }

    public Observable<PersonResponse> getPersonProfile(final String aAuthorId) {
        return Observable.create(new ObservableOnSubscribe<PersonResponse>() {
            @Override
            public void subscribe(ObservableEmitter<PersonResponse> observableEmitter) throws Exception {
                Retrofit retrofit = getRetrofit(PropsRequestUrl.FLICKR);
                ServiceFlickr serviceFlickr = retrofit.create(ServiceFlickr.class);
                Call<PersonResponse> callFlickr = serviceFlickr.getPerson(aAuthorId);
                try {
                    observableEmitter.onNext(callFlickr.execute().body());
                    observableEmitter.onComplete();
                } catch (IOException e) {
                    observableEmitter.onError(e);
                }
            }
        });
    }

    public Observable<PhotoResponse> getPhotoData(final String aPhotoId) {
        return Observable.create(new ObservableOnSubscribe<PhotoResponse>() {
            @Override
            public void subscribe(ObservableEmitter<PhotoResponse> observableEmitter) throws Exception {
                Retrofit retrofit = getRetrofit(PropsRequestUrl.FLICKR);
                ServiceFlickr serviceFlickr = retrofit.create(ServiceFlickr.class);
                Call<PhotoResponse> callFlickr = serviceFlickr.getPhotoData(aPhotoId);
                try {
                    observableEmitter.onNext(callFlickr.execute().body());
                    observableEmitter.onComplete();
                } catch (IOException e) {
                    observableEmitter.onError(e);
                }
            }
        });
    }
}
