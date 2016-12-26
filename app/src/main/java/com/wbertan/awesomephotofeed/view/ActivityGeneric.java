package com.wbertan.awesomephotofeed.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;

import com.wbertan.awesomephotofeed.R;
import com.wbertan.awesomephotofeed.fragments.FragmentExit;
import com.wbertan.awesomephotofeed.fragments.FragmentGeneric;
import com.wbertan.awesomephotofeed.props.PropsBroadcastReceiver;

import java.util.List;

/**
 * Created by william.bertan on 25/12/2016.
 */

public abstract class ActivityGeneric extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    MainBroadcastReceiver mMainBroadcastReceiver = null;

    class MainBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context aContext, Intent aIntent) {
            FragmentGeneric fragment = processBroadcastReceiver(aIntent);
            if(fragment == null) {
                //No one is supposed to process, or already had been processed (already in the screen)
                return;
            }
            fragment.setArguments(aIntent.getExtras());
            setFragment(fragment);
        }
    }

    protected <T> Fragment recoverFragment(Class<T> aClazz) {
        return getSupportFragmentManager().findFragmentByTag(aClazz.getSimpleName());
    }

    protected abstract FragmentGeneric processBroadcastReceiver(Intent aIntent);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        Toolbar toolbarMain = (Toolbar) findViewById(R.id.toolbarMain);
        if(toolbarMain != null) {
            setSupportActionBar(toolbarMain);
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PropsBroadcastReceiver.SIGN_IN);
        intentFilter.addAction(PropsBroadcastReceiver.SIGN_UP);
        intentFilter.addAction(PropsBroadcastReceiver.LOGIN);
        mMainBroadcastReceiver = new MainBroadcastReceiver();
        registerReceiver(mMainBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        if(mMainBroadcastReceiver != null) {
            unregisterReceiver(mMainBroadcastReceiver);
        }
        super.onPause();
    }

    protected void removeAllFragments() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        for(int counter = 0; counter < backStackCount; counter++) {
            getSupportFragmentManager().popBackStack();
        }
    }

    protected void setFragment(FragmentGeneric aFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment;
        if ((currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout)) != null) {
            fragmentTransaction.hide(currentFragment);
        }
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        if (getSupportFragmentManager().getFragments() != null) {
            fragmentTransaction.addToBackStack(null);
        }
        Fragment fragmentExistente = getSupportFragmentManager().findFragmentByTag(aFragment.getClass().getSimpleName());
        if(fragmentExistente != null) {
            fragmentTransaction.replace(R.id.frameLayout, aFragment, aFragment.getClass().getSimpleName());
        } else {
            fragmentTransaction.add(R.id.frameLayout, aFragment, aFragment.getClass().getSimpleName());
        }
        fragmentTransaction.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();

        //Close keyboard
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), 0);
        }
    }

    @Override
    public void onBackStackChanged() {
        List<Fragment> listFragments = getSupportFragmentManager().getFragments();
        if(listFragments != null && !listFragments.isEmpty()) {
            FragmentGeneric fragmentGeneric = null;
            for(int counter = listFragments.size() - 1; counter > 0; counter--) {
                if(listFragments.get(counter) instanceof FragmentGeneric) {
                    fragmentGeneric = (FragmentGeneric) listFragments.get(counter);
                    if (fragmentGeneric != null) break;
                }
            }
            if(fragmentGeneric != null && getSupportActionBar() != null ) {
                getSupportActionBar().setTitle(fragmentGeneric.getFragmentTitle());
            } else {
                if(getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().findFragmentByTag(FragmentExit.class.getSimpleName()) != null) {
            finish();
            return;
        }
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if(backStackCount > 0) {
            super.onBackPressed();
            return;
        }
        setFragment(new FragmentExit());
    }
}
