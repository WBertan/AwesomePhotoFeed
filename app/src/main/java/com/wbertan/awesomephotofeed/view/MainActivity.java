package com.wbertan.awesomephotofeed.view;

import android.content.Intent;
import android.os.Bundle;

import com.wbertan.awesomephotofeed.fragments.FragmentGeneric;
import com.wbertan.awesomephotofeed.fragments.FragmentSplash;
import com.wbertan.awesomephotofeed.fragments.FragmentMain;
import com.wbertan.awesomephotofeed.fragments.FragmentPhotoInfo;
import com.wbertan.awesomephotofeed.props.PropsBroadcastReceiver;
/**
 * Created by william.bertan on 25/12/2016.
 */
public class MainActivity extends ActivityGeneric {
    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setFragment(new FragmentSplash());
    }

    @Override
    protected FragmentGeneric processBroadcastReceiver(Intent aIntent) {
        if(aIntent.getAction().equals(PropsBroadcastReceiver.LOGIN)) {
            removeAllFragments();
            return new FragmentMain();
        } else if(aIntent.getAction().equals(PropsBroadcastReceiver.SHOW_PHOTO_INFO)) {
            return new FragmentPhotoInfo();
        }
        return null;
    }
}
