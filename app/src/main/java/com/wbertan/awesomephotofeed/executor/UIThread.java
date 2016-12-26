package com.wbertan.awesomephotofeed.executor;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by william.bertan on 26/12/2016.
 */

public class UIThread implements PostExecutionThread {
    public UIThread() {}

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
