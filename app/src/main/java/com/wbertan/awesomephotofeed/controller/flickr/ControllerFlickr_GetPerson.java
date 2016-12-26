package com.wbertan.awesomephotofeed.controller.flickr;

import com.wbertan.awesomephotofeed.controller.ControllerGeneric;
import com.wbertan.awesomephotofeed.interactor.Params;
import com.wbertan.awesomephotofeed.repository.RepositoryFlickr;

import io.reactivex.Observable;

/**
 * Created by william.bertan on 25/12/2016.
 */

public class ControllerFlickr_GetPerson extends ControllerGeneric {
    private final RepositoryFlickr mRepositoryFlickr;

    public ControllerFlickr_GetPerson() {
        super();
        this.mRepositoryFlickr = new RepositoryFlickr();
    }

    @Override
    protected Observable buildInteractorObservable(Params aParams) throws Exception {
        if(aParams == null || !aParams.contains("author_id")) {
            throw new Exception("No parameter author_id!");
        }
        return mRepositoryFlickr.getPersonProfile(aParams.get("author_id", null, String.class));
    }
}
