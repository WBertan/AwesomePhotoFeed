package com.wbertan.awesomephotofeed.model.flickr;

/**
 * Created by william.bertan on 26/12/2016.
 */

public class PhotoInfo {
    private String attribute;
    private String value;

    public PhotoInfo() {}

    public PhotoInfo(String a, String b) {
        attribute = a;
        value = b;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
