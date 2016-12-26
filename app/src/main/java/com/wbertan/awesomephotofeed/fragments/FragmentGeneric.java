package com.wbertan.awesomephotofeed.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.wbertan.awesomephotofeed.R;
import com.wbertan.awesomephotofeed.props.PropsBroadcastReceiver;

/**
 * Created by william.bertan on 25/12/2016.
 */

public abstract class FragmentGeneric extends Fragment {
    public abstract String getFragmentTitle();
    private static ProgressDialog mProgressDialog;

    protected void showProgress() {
        showProgress(getString(R.string.progress_title_preparing), getString(R.string.progress_message_waiting));
    }

    protected void showProgress(@NonNull String aTitle, @NonNull String aMessage) {
        if(mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(getActivity(), aTitle, aMessage);
        }
    }

    protected void dismissProgress() {
        if(mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void onStop() {
        dismissProgress();
        super.onStop();
    }

    protected void setChildFragment(int aFragmentContainer, Fragment aFragment) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        if (childFragmentManager.getFragments() != null) {
            fragmentTransaction.addToBackStack(null);
        }
        Fragment fragmentExistente = childFragmentManager.findFragmentByTag(aFragment.getClass().getSimpleName());
        if(fragmentExistente != null) {
            fragmentTransaction.replace(aFragmentContainer, aFragment, aFragment.getClass().getSimpleName());
        } else {
            fragmentTransaction.add(aFragmentContainer, aFragment, aFragment.getClass().getSimpleName());
        }
        fragmentTransaction.commitAllowingStateLoss();
        childFragmentManager.executePendingTransactions();
    }

    protected Intent prepareAction(@PropsBroadcastReceiver String aAction) {
        Intent intent = new Intent();
        intent.setAction(aAction);
        return intent;
    }
}
