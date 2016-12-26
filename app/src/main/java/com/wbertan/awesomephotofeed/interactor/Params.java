package com.wbertan.awesomephotofeed.interactor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by william.bertan on 26/12/2016.
 */

public class Params {
    public static final Params EMPTY_PARAM = Params.getInstance();

    private final Map<String, Object> mParameters = new HashMap<>();

    private Params() {}

    public static Params getInstance() {
        return new Params();
    }

    public <T> void put(String aKey, T aValue) {
        mParameters.put(aKey, aValue);
    }

    public <T> T get(String aKey, T aDefaultValue, Class<T> clazz) {
        if(mParameters.containsKey(aKey)) {
            return (T) mParameters.get(aKey);
        }
        return aDefaultValue;
    }

    public boolean contains(String aKey) {
        return mParameters.containsKey(aKey);
    }

    public boolean validValueWithKey(String aKey) throws Exception {
        if(!contains(aKey)) {
            throw new Exception("No param named " + aKey + " was given!");
        }
        if(mParameters.get(aKey) == null) {
            throw new Exception("Invalid param named " + aKey + " was given!");
        }
        return true;
    }
}