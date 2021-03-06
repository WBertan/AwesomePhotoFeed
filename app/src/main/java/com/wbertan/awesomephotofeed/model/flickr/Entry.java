package com.wbertan.awesomephotofeed.model.flickr;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wbertan.awesomephotofeed.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by william.bertan on 25/12/2016.
 */

public class Entry extends BaseObservable {
    private String title;
    private String link;
    private Map<String, String> media;
    private Date date_taken;
    private String description;
    private Date published;

    private String author;
    private String author_id;
    private String tags;

    public Entry() {}

    @Bindable
    public String getFormattedPublished() {
        return SimpleDateFormat.getDateTimeInstance().format(published);
    }

    public String getPhotoUrl() {
        if(media == null || media.isEmpty()) {
            return null;
        } else {
            String url = (String) media.values().toArray()[0];
            return url.replace("_m.jpg", "_b.jpg");
        }
    }

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, Map<String, String> mapMedia) {
        if(mapMedia == null || mapMedia.isEmpty()) {
            imageView.setImageResource(R.mipmap.logo_awesome_photo_feed_icon);
        } else {
            Glide.with(imageView.getContext())
                 .load(mapMedia.values().toArray()[0])
                 .centerCrop()
                 .placeholder(R.mipmap.logo_awesome_photo_feed_icon)
                 .crossFade()
                 .into(imageView);
        }
    }

    public String getPhotoId() {
        String photoId = link;
        if(photoId.endsWith("/")) {
            photoId = photoId.substring(0, photoId.length()-1);
        }
        int index = photoId.lastIndexOf("/");
        if(index >= 0 && index < photoId.length()) {
            photoId = photoId.substring(index+1, photoId.length());
        }
        return photoId;
    }

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

    public Map<String, String> getMedia() {
        return media;
    }

    public void setMedia(Map<String, String> media) {
        this.media = media;
    }

    public Date getDate_taken() {
        return date_taken;
    }

    public void setDate_taken(Date date_taken) {
        this.date_taken = date_taken;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
