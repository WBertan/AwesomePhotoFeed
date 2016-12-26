package com.wbertan.awesomephotofeed.props;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.wbertan.awesomephotofeed.props.PropsFeedRequestUrl.FLICKR_FEED;
import static com.wbertan.awesomephotofeed.props.PropsFeedRequestUrl.FLICKR_METHOD_URL;

/**
 * Created by william.bertan on 25/12/2016.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef(value = {FLICKR_FEED, FLICKR_METHOD_URL})
public @interface PropsFeedRequestUrl {
    String FLICKR_FEED = "https://api.flickr.com/services/feeds/photos_public.gne";
    String FLICKR_METHOD_URL = "https://api.flickr.com/services/rest/?";
}
