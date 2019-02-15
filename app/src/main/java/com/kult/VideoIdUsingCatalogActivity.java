package com.kult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.Log;

import com.brightcove.player.edge.Catalog;
import com.brightcove.player.edge.VideoListener;
import com.brightcove.player.event.EventEmitter;
import com.brightcove.player.event.EventType;
import com.brightcove.player.model.DeliveryType;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BrightcovePlayer;

import java.net.URISyntaxException;

public class VideoIdUsingCatalogActivity extends BrightcovePlayer {

    private String videoId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_from_id_using_catalog);

        getVideoId();
        playVideo();
    }

    private void getVideoId() {
        Intent i = getIntent();
        if(i != null && i.hasExtra("VIDEO_ID")) {
            videoId = i.getStringExtra("VIDEO_ID");
        }

        Log.v(TAG, "VideoIdUsingCatalogActivity:: getVideoId: videoId = " + videoId);
        if(TextUtils.isEmpty(videoId)) {
            return;
        }
    }

    private void playVideo() {
        brightcoveVideoView = findViewById(R.id.brightcove_video_view);
        EventEmitter eventEmitter = brightcoveVideoView.getEventEmitter();
        eventEmitter.emit(EventType.ENTER_FULL_SCREEN);

        Catalog catalog = new Catalog(eventEmitter, getString(R.string.brightcove_account_id), getString(R.string.brightcove_policy_key));

        catalog.findVideoByID(videoId, new VideoListener() {
            // Add the video found to the queue with add().
            // Start playback of the video with start().
            @Override
            public void onVideo(Video video) {
                Log.v(TAG, "VideoIdUsingCatalogActivity:: onVideo: video = " + video);
                brightcoveVideoView.add(video);
                brightcoveVideoView.start();
            }
        });
    }
}
