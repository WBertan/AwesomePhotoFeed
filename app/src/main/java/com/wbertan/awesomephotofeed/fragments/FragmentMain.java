package com.wbertan.awesomephotofeed.fragments;

import android.content.Context;
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

import com.wbertan.awesomephotofeed.R;

/**
 * Created by william.bertan on 25/12/2016.
 */

public class FragmentMain extends FragmentGeneric {
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
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewOdds);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getSpanCount()));

//        AdapterGeneric<Bet> adapter = new AdapterGeneric<>(R.layout.adapter_bet_item, BR.bet);
//        recyclerView.setAdapter(adapter);

//        showProgress();
//        ControllerBet.getInstance().getBets(this, 0);
//        ControllerBet.getInstance().getFavoriteBets(this, 1);
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
            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewOdds);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getSpanCount()));
        }
    }
}