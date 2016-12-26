package com.wbertan.awesomephotofeed.model.flickr;

/**
 * Created by william.bertan on 26/12/2016.
 */

public class Photo {
    private String id;
    private String secret;
    private String server;
    private long farm;
    private long dateuploaded;
    private String originalsecret;
    private String originalformat;
    private Content title;
    private Content description;

    public Photo() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public long getFarm() {
        return farm;
    }

    public void setFarm(long farm) {
        this.farm = farm;
    }

    public long getDateuploaded() {
        return dateuploaded;
    }

    public void setDateuploaded(long dateuploaded) {
        this.dateuploaded = dateuploaded;
    }

    public String getOriginalsecret() {
        return originalsecret;
    }

    public void setOriginalsecret(String originalsecret) {
        this.originalsecret = originalsecret;
    }

    public String getOriginalformat() {
        return originalformat;
    }

    public void setOriginalformat(String originalformat) {
        this.originalformat = originalformat;
    }

    public String getTitle() {
        return title != null ? title.get_content() : null;
    }

    public void setTitle(Content title) {
        this.title = title;
    }

    public String getDescription() {
        return description != null ? description.get_content() : null;
    }

    public void setDescription(Content description) {
        this.description = description;
    }
}
