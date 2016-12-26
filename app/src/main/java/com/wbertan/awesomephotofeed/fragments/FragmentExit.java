package com.wbertan.awesomephotofeed.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wbertan.awesomephotofeed.R;

/**
 * Created by william.bertan on 25/12/2016.
 */

public class FragmentExit extends FragmentGeneric {
    @Override
    public String getFragmentTitle() {
        return getString(R.string.fragment_exit_title);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater aInflater, @Nullable ViewGroup aContainer, @Nullable Bundle aSavedInstanceState) {
        return aInflater.inflate(R.layout.fragment_exit, aContainer, false);
    }
}
