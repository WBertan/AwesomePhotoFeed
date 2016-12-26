package com.wbertan.awesomephotofeed.controller.flickr;

import com.wbertan.awesomephotofeed.controller.ControllerGeneric;
import com.wbertan.awesomephotofeed.interactor.Params;
import com.wbertan.awesomephotofeed.repository.RepositoryFlickr;

import io.reactivex.Observable;

/**
 * Created by william.bertan on 25/12/2016.
 */

public class ControllerFlickr_GetPhotoInfo extends ControllerGeneric {
    private final RepositoryFlickr mRepositoryFlickr;

    public ControllerFlickr_GetPhotoInfo() {
        super();
        this.mRepositoryFlickr = new RepositoryFlickr();
    }

    @Override
    protected Observable buildInteractorObservable(Params aParams) throws Exception {
        if(aParams == null || !aParams.contains("photo_id")) {
            throw new Exception("No parameter photo_id!");
        }
        return mRepositoryFlickr.getPhotoData(aParams.get("photo_id", null, String.class));
    }
}
