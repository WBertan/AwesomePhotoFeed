package com.wbertan.awesomephotofeed.controller.flickr;

import com.wbertan.awesomephotofeed.controller.ControllerGeneric;
import com.wbertan.awesomephotofeed.interactor.Params;
import com.wbertan.awesomephotofeed.repository.RepositoryFlickr;

import io.reactivex.Observable;

/**
 * Created by william.bertan on 25/12/2016.
 */

public class ControllerFlickr_GetFeed extends ControllerGeneric {
    private final RepositoryFlickr mRepositoryFlickr;

    public ControllerFlickr_GetFeed() {
        super();
        this.mRepositoryFlickr = new RepositoryFlickr();
    }

    @Override
    protected Observable buildInteractorObservable(Params aParams) throws Exception {
        return mRepositoryFlickr.getFeed();
    }
}
