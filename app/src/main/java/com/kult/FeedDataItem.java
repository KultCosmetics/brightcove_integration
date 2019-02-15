package com.kult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FeedDataItem implements Serializable {
    @SerializedName("video_id")
    @Expose
    private long videoId;

    public final long getVideoId() {
        return this.videoId;
    }
}