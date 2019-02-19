package com.kult.brightcove;

import android.os.Bundle;

import com.brightcove.player.model.DeliveryType;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BrightcovePlayer;
import com.kult.R;

import java.net.URISyntaxException;

public class BrightCove_StaticVideoActivity extends BrightcovePlayer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brightcove_static_video);

        playDemoVideo();
    }

    private void playDemoVideo() {
        brightcoveVideoView = findViewById(R.id.brightcove_video_view);
        Video video = Video.createVideo("https://learning-services-media.brightcove.com/videos/hls/greatblueheron/greatblueheron.m3u8",
                DeliveryType.HLS);
        try {
            java.net.URI myposterImage = new java.net.URI("https://solutions.brightcove.com/bcls/assets/images/Great-Blue-Heron.png");
            video.getProperties().put(Video.Fields.STILL_IMAGE_URI, myposterImage);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        brightcoveVideoView.add(video);
        brightcoveVideoView.start();
    }
}
