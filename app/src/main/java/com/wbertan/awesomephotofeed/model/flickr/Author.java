package com.wbertan.awesomephotofeed.model.flickr;

/**
 * Created by william.bertan on 25/12/2016.
 */
public class Author {
    private String flickr_id;
    private String name;
    private String uri;
    private String flickr_buddyicon;

    public Author() { }

    public String getFlickr_id() {
        return flickr_id;
    }

    public void setFlickr_id(String flickr_id) {
        this.flickr_id = flickr_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getFlickr_buddyicon() {
        return flickr_buddyicon;
    }

    public void setFlickr_buddyicon(String flickr_buddyicon) {
        this.flickr_buddyicon = flickr_buddyicon;
    }
}