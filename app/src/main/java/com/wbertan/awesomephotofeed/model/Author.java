package com.wbertan.awesomephotofeed.model;

/**
 * Created by william.bertan on 25/12/2016.
 */

public class Author {
    private final String id;
    private String name;
    private String link;
    private String icon;

    public Author(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}