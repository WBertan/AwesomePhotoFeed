package com.wbertan.awesomephotofeed.executor;

import android.support.annotation.NonNull;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by william.bertan on 26/12/2016.
 */

public class JobExecutor implements ThreadExecutor {
    private final ThreadPoolExecutor mThreadPoolExecutor;

    private static class SingletonHolder {
        private static final JobExecutor INSTANCE = new JobExecutor();
    }

    public static JobExecutor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public JobExecutor() {
        this.mThreadPoolExecutor = new ThreadPoolExecutor(3, 5, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new JobThreadFactory());
    }

    @Override public void execute(@NonNull Runnable runnable) {
        this.mThreadPoolExecutor.execute(runnable);
    }

    private static class JobThreadFactory implements ThreadFactory {
        private int counter = 0;

        @Override public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "android_" + counter++);
        }
    }
}
