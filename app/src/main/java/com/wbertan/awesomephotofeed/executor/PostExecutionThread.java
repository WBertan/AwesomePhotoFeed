package com.wbertan.awesomephotofeed.executor;

import io.reactivex.Scheduler;

/**
 * Created by william.bertan on 26/12/2016.
 */

public interface PostExecutionThread {
    Scheduler getScheduler();
}
