package com.wbertan.awesomephotofeed.interactor;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by william.bertan on 26/12/2016.
 */

public class DefaultObserver<T> extends DisposableObserver<T> {
    @Override
    public void onNext(T t) {
        /* do nothing */
    }

    @Override
    public void onComplete() {
        /* do nothing */
    }

    @Override
    public void onError(Throwable exception) {
        /* do nothing */
    }
}
