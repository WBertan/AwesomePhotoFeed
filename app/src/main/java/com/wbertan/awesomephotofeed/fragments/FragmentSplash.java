package com.wbertan.awesomephotofeed.fragments;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wbertan.awesomephotofeed.R;
import com.wbertan.awesomephotofeed.props.PropsBroadcastReceiver;

/**
 * Created by william.bertan on 25/12/2016.
 */

public class FragmentSplash extends FragmentGeneric {
    @Override
    public String getFragmentTitle() {
        return getString(R.string.fragment_splash_title);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater aInflater, @Nullable ViewGroup aContainer, @Nullable Bundle aSavedInstanceState) {
        return aInflater.inflate(R.layout.fragment_splash, aContainer, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getView() == null) {
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.flip);
                final ImageView imageViewAnim = (ImageView) getView().findViewById(R.id.imageViewAnim);

                animatorSet.setTarget(imageViewAnim);
                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) { /* do nothing */ }
                    @Override
                    public void onAnimationEnd(Animator animator)  {
                        imageViewAnim.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getActivity().sendBroadcast(prepareAction(PropsBroadcastReceiver.LOGIN));
                            }
                        });
                        if(getActivity() != null) {
                            getActivity().sendBroadcast(prepareAction(PropsBroadcastReceiver.LOGIN));
                        }
                    }
                    @Override
                    public void onAnimationCancel(Animator animator) { /* do nothing */ }
                    @Override
                    public void onAnimationRepeat(Animator animator) { /* do nothing */ }
                });
                animatorSet.start();
            }
        }, 1500);
    }
}
