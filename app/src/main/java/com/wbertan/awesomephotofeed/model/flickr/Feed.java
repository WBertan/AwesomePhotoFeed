package com.wbertan.awesomephotofeed.model.flickr;

import java.util.Date;
import java.util.List;

/**
 * Created by william.bertan on 26/12/2016.
 */
public class Feed {
    private String title;
    private String link;
    private String description;
    private Date modified;
    private String generator;
    private List<Photo> items;

    public Feed() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public List<Photo> getItems() {
        return items;
    }

    public void setItems(List<Photo> items) {
        this.items = items;
    }
}
