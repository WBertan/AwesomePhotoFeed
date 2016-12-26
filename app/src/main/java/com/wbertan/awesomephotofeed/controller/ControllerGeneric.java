package com.wbertan.awesomephotofeed.controller;

import com.wbertan.awesomephotofeed.executor.JobExecutor;
import com.wbertan.awesomephotofeed.executor.PostExecutionThread;
import com.wbertan.awesomephotofeed.executor.ThreadExecutor;
import com.wbertan.awesomephotofeed.executor.UIThread;
import com.wbertan.awesomephotofeed.interactor.Params;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by william.bertan on 18/12/2016.
 */

public abstract class ControllerGeneric {
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;
    private final CompositeDisposable mCompositeDisposable;

    protected ControllerGeneric() {
        this.mThreadExecutor = JobExecutor.getInstance();
        this.mPostExecutionThread = new UIThread();
        this.mCompositeDisposable = new CompositeDisposable();
    }

    protected abstract Observable buildInteractorObservable(Params aParams) throws Exception;

    public void execute(DisposableObserver aDisposableObserver, Params aParams) throws Exception {
        final Observable<?> lObservable = this.buildInteractorObservable(aParams)
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler());
        addDisposable(lObservable.subscribeWith(aDisposableObserver));
    }

    public void dispose() {
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    private void addDisposable(Disposable aDisposable) {
        if (aDisposable == null) {
            throw new NullPointerException();
        }
        mCompositeDisposable.add(aDisposable);
    }

    protected boolean isParamsNull(Params aParams) {
        return aParams == null;
    }
}