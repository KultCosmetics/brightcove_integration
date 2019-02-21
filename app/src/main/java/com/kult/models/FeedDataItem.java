package com.kult.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FeedDataItem implements Serializable {
    @SerializedName("video_id")
    @Expose
    private long videoId;
    @SerializedName("video_url")
    @Expose
    private String videoUrl;
    @SerializedName("height")
    @Expose
    private int height;

    public final long getVideoId() {
        return this.videoId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public int getHeight() {
        return height;
    }
}