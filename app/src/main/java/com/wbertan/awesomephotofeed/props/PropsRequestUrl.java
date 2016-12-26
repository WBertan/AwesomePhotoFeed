package com.wbertan.awesomephotofeed.props;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.wbertan.awesomephotofeed.props.PropsRequestUrl.FLICKR;
import static com.wbertan.awesomephotofeed.props.PropsRequestUrl.FLICKR_FEED;
import static com.wbertan.awesomephotofeed.props.PropsRequestUrl.FLICKR_METHOD_URL;

/**
 * Created by william.bertan on 25/12/2016.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef(value = {FLICKR, FLICKR_FEED, FLICKR_METHOD_URL})
public @interface PropsRequestUrl {
    String FLICKR = "https://api.flickr.com/services/";
    String FLICKR_FEED = "feeds/photos_public.gne?format=json&nojsoncallback=1";
    String FLICKR_METHOD_URL = "rest/?method=";
}
