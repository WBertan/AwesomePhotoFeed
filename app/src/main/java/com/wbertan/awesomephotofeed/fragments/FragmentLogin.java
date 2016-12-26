package com.wbertan.awesomephotofeed.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wbertan.awesomephotofeed.R;
import com.wbertan.awesomephotofeed.props.PropsBroadcastReceiver;

/**
 * Created by william.bertan on 25/12/2016.
 */

public class FragmentLogin extends FragmentGeneric implements View.OnClickListener {
    @Override
    public String getFragmentTitle() {
        return getString(R.string.fragment_login_title);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater aInflater, @Nullable ViewGroup aContainer, @Nullable Bundle aSavedInstanceState) {
        return aInflater.inflate(R.layout.fragment_login, aContainer, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getView() == null) {
            return;
        }
        Button buttonSignIn = (Button) getView().findViewById(R.id.buttonSignIn);
        Button buttonSignUp = (Button) getView().findViewById(R.id.buttonSignUp);

        buttonSignIn.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);

//        if(ControllerUser.getInstance().isSubscripted(getActivity())){
//            buttonSignUp.setVisibility(View.INVISIBLE);
//        } else {
//            buttonSignIn.setVisibility(View.INVISIBLE);
//        }
    }

    @Override
    public void onClick(View aView) {
        if(aView.getId() == R.id.buttonSignIn) {
            getActivity().sendBroadcast(prepareAction(PropsBroadcastReceiver.SIGN_IN));
        } else if(aView.getId() == R.id.buttonSignUp) {
            getActivity().sendBroadcast(prepareAction(PropsBroadcastReceiver.SIGN_UP));
        }
    }
}
