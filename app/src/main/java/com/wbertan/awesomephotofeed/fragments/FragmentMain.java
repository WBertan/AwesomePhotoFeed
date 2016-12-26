package com.wbertan.awesomephotofeed.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.wbertan.awesomephotofeed.BR;
import com.wbertan.awesomephotofeed.R;
import com.wbertan.awesomephotofeed.adapter.AdapterGeneric;
import com.wbertan.awesomephotofeed.controller.flickr.ControllerFlickr_GetFeed;
import com.wbertan.awesomephotofeed.interactor.DefaultObserver;
import com.wbertan.awesomephotofeed.interactor.Params;
import com.wbertan.awesomephotofeed.model.flickr.Entry;
import com.wbertan.awesomephotofeed.model.flickr.Feed;
import com.wbertan.awesomephotofeed.props.PropsBroadcastReceiver;

/**
 * Created by william.bertan on 25/12/2016.
 */

public class FragmentMain extends FragmentGeneric implements AdapterGeneric.ViewHolderAction {
    @Override
    public String getFragmentTitle() {
        return getString(R.string.fragment_main_title);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater aInflater, @Nullable ViewGroup aContainer, @Nullable Bundle aSavedInstanceState) {
        return aInflater.inflate(R.layout.fragment_main, aContainer, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getView() == null) {
            return;
        }
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewPhotos);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getSpanCount()));

        AdapterGeneric<Entry> adapter = new AdapterGeneric<>(R.layout.adapter_photo_item, BR.photo, this);
        recyclerView.setAdapter(adapter);
        try {
            showProgress();
            new ControllerFlickr_GetFeed().execute(new FeedDetailsObserver(), Params.EMPTY_PARAM);
        } catch (Exception e) {
            e.printStackTrace();
            dismissProgress();
        }
    }

    private int getSpanCount() {
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        Display display = wm.getDefaultDisplay();
        display.getSize(size);
        int width = size.x;
        float cardViewWidth = getActivity().getResources().getDimension(R.dimen.card_view_width);
        float cardViewPadding = getActivity().getResources().getDimension(R.dimen.card_view_padding);
        int spanCount = (int) (width/(cardViewWidth + cardViewPadding));
        return spanCount < 1 ? 1 : spanCount;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
                || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT
                || newConfig.orientation == Configuration.ORIENTATION_UNDEFINED){
            if(getView() == null) {
                return;
            }
            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewPhotos);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getSpanCount()));
        }
    }

    @Override
    public void executeRecyclerItemClick(View aView, int aPosition) {
        if(getView() == null) {
            return;
        }
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewPhotos);
        AdapterGeneric adapter = (AdapterGeneric) recyclerView.getAdapter();
        Entry entry = (Entry) adapter.getSelectedItem();

        Intent intent = prepareAction(PropsBroadcastReceiver.SHOW_PHOTO_INFO);
        intent.putExtra("photo_id", entry.getPhotoId());
        intent.putExtra("author_id", entry.getAuthor_id());
        intent.putExtra("photo_link", entry.getPhotoUrl());
        intent.putExtra("photo_title", entry.getTitle());
        intent.putExtra("photo_description", entry.getDescription());
        getActivity().sendBroadcast(intent);
    }

    private final class FeedDetailsObserver extends DefaultObserver<Feed> {
        @Override
        public void onComplete() {
            dismissProgress();
        }

        @Override
        public void onError(Throwable e) {
            dismissProgress();
            e.printStackTrace();
        }

        @Override
        public void onNext(Feed feed) {
            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewPhotos);
            ((AdapterGeneric)recyclerView.getAdapter()).addAll(feed.getItems(), true);
        }
    }
}