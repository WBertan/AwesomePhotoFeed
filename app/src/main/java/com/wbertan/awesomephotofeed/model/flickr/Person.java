package com.wbertan.awesomephotofeed.model.flickr;

/**
 * Created by william.bertan on 26/12/2016.
 */

public class Person {
    private String id;
    private String nsid;
    private String iconserver;
    private long iconfarm;
    private Content username;
    private Content realname;
    private Content location;
    private Content description;
    private Content photosurl;
    private Content profileurl;

    public Person() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNsid() {
        return nsid;
    }

    public void setNsid(String nsid) {
        this.nsid = nsid;
    }

    public String getIconserver() {
        return iconserver;
    }

    public void setIconserver(String iconserver) {
        this.iconserver = iconserver;
    }

    public long getIconfarm() {
        return iconfarm;
    }

    public void setIconfarm(long iconfarm) {
        this.iconfarm = iconfarm;
    }

    public String getUsername() {
        return username != null ? username.get_content() : null;
    }

    public void setUsername(Content username) {
        this.username = username;
    }

    public String getRealname() {
        return realname != null ? realname.get_content() : null;
    }

    public void setRealname(Content realname) {
        this.realname = realname;
    }

    public String getLocation() {
        return location != null ? realname.get_content() : null;
    }

    public void setLocation(Content location) {
        this.location = location;
    }

    public String getDescription() {
        return description != null ? description.get_content() : null;
    }

    public void setDescription(Content description) {
        this.description = description;
    }

    public String getPhotosurl() {
        return photosurl != null ? photosurl.get_content() : null;
    }

    public void setPhotosurl(Content photosurl) {
        this.photosurl = photosurl;
    }

    public String getProfileurl() {
        return profileurl != null ? profileurl.get_content() : null;
    }

    public void setProfileurl(Content profileurl) {
        this.profileurl = profileurl;
    }
}
