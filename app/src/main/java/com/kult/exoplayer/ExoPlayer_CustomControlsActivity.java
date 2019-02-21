package com.kult.exoplayer;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.Util;
import com.kult.R;

//https://medium.com/google-exoplayer/customizing-exoplayers-ui-components-728cf55ee07a

public class ExoPlayer_CustomControlsActivity extends Activity {
    //private String videoUrl = "https://learning-services-media.brightcove.com/videos/hls/greatblueheron/greatblueheron.m3u8";
    private String videoUrl = "https://res.cloudinary.com/dqrr4jjhj/video/upload/v1550484487/samples/test/Priyanka_Chopra_Everyday_Beauty_Tips.mp4";

    private String TAG = "ExoPlayer_boo";

    private SimpleExoPlayer player;
    private PlayerView playerView;
    private DataSource.Factory dataSourceFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exoplayer_custom_control);

        player = ExoPlayerFactory.newSimpleInstance(this);
        playerView = findViewById(R.id.player_view);

        //playerView.setControllerVisibilityListener(this);
        playerView.setPlayer(player);

        dataSourceFactory = buildDataSourceFactory();
        Uri mp4VideoUri = Uri.parse(videoUrl);

        // Prepare the player with the source.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(mp4VideoUri);
        player.prepare(videoSource);
        player.setPlayWhenReady(true);
    }

    private DataSource.Factory buildDataSourceFactory() {
        return ((DemoApplication) getApplication()).buildDataSourceFactory();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            //initializePlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || player == null) {
            //initializePlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null)
            player.release();
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
