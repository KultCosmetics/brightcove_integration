package com.kult.models;

import java.io.Serializable;

public class Product implements Serializable {
    private String imageUrl;
    private String title;
    private float seek_time;

    public Product(String s, String s1, float i) {
        setImageUrl(s);
        setTitle(s1);
        setSeek_time(i);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getSeek_time() {
        return seek_time;
    }

    public void setSeek_time(float seek_time) {
        this.seek_time = seek_time;
    }
}
