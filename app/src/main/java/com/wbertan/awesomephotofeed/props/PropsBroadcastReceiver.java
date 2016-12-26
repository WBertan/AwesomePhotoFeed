package com.wbertan.awesomephotofeed.props;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.wbertan.awesomephotofeed.props.PropsBroadcastReceiver.LOGIN;
import static com.wbertan.awesomephotofeed.props.PropsBroadcastReceiver.SHOW_PHOTO_INFO;
import static com.wbertan.awesomephotofeed.props.PropsBroadcastReceiver.SIGN_IN;
import static com.wbertan.awesomephotofeed.props.PropsBroadcastReceiver.SIGN_UP;

/**
 * Created by william.bertan on 25/12/2016.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef(value = {SIGN_IN, SIGN_UP, LOGIN, SHOW_PHOTO_INFO})
public @interface PropsBroadcastReceiver {
    String SIGN_IN = "com.wbertan.awesomephotofeed.broadcast.SIGN_IN";
    String SIGN_UP = "com.wbertan.awesomephotofeed.broadcast.SIGN_UP";
    String LOGIN = "com.wbertan.awesomephotofeed.broadcast.LOGIN";
    String SHOW_PHOTO_INFO = "com.wbertan.awesomephotofeed.broadcast.SHOW_PHOTO_INFO";
}
