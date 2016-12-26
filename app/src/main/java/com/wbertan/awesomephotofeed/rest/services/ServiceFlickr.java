package com.wbertan.awesomephotofeed.rest.services;

import com.wbertan.awesomephotofeed.model.flickr.Author;
import com.wbertan.awesomephotofeed.model.flickr.Feed;
import com.wbertan.awesomephotofeed.props.PropsRequestUrl;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by william.bertan on 26/12/2016.
 */

public interface ServiceFlickr {
    @GET(PropsRequestUrl.FLICKR_FEED)
    Call<Feed> getFeed();

    @GET(PropsRequestUrl.FLICKR_METHOD_URL + "method=flickr.people.getInfo&api_key=42a1aeef47e5d44288acd5c73a3e5b6d&user_id={author_id}")
    Call<Author> getAuthorProfile(@Path("author_id") String aAuthorId);
}
