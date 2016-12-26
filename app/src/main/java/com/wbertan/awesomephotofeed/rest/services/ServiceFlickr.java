package com.wbertan.awesomephotofeed.rest.services;

import com.wbertan.awesomephotofeed.model.flickr.Feed;
import com.wbertan.awesomephotofeed.model.flickr.PersonResponse;
import com.wbertan.awesomephotofeed.model.flickr.PhotoResponse;
import com.wbertan.awesomephotofeed.props.PropsRequestUrl;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by william.bertan on 26/12/2016.
 */

public interface ServiceFlickr {
    @GET(PropsRequestUrl.FLICKR_FEED)
    Call<Feed> getFeed(@Query("tags") String aTags);

    @GET(PropsRequestUrl.FLICKR_METHOD_URL + "flickr.people.getInfo&format=json&nojsoncallback=1&api_key=42a1aeef47e5d44288acd5c73a3e5b6d")
    Call<PersonResponse> getPerson(@Query("user_id") String aAuthorId);

    @GET(PropsRequestUrl.FLICKR_METHOD_URL + "flickr.photos.getInfo&format=json&nojsoncallback=1&api_key=42a1aeef47e5d44288acd5c73a3e5b6d")
    Call<PhotoResponse> getPhotoData(@Query("photo_id") String aPhotoId);
}
