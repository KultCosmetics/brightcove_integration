package com.kult.exoplayer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Util;
import com.kult.R;

//https://google.github.io/ExoPlayer/guide.html
//https://stackoverflow.com/questions/48545554/right-exoplayer-uri
//https://github.com/google/ExoPlayer/blob/release-v2/demos/main/src/main/java/com/google/android/exoplayer2/demo/PlayerActivity.java
//https://stackoverflow.com/questions/49303165/exoplayer-resume-on-same-position-on-rotate-screen
//https://github.com/GeoffLedak/ExoplayerFullscreen
//https://geoffledak.com/blog/2017/09/11/how-to-add-a-fullscreen-toggle-button-to-exoplayer-in-android/

//Bit rate/ resolution
//https://medium.com/google-exoplayer/exoplayer-2-x-track-selection-2b62ff712cc9

//Bandwidth
//https://medium.com/google-exoplayer/https-medium-com-google-exoplayer-simplified-bandwidth-meter-usage-17d8189f978b
//Error: https://github.com/google/ExoPlayer/issues/3320

//Events
//http://blogs.quovantis.com/exoplayer-events-and-ui-customizations/

//UI
//https://medium.com/google-exoplayer/customizing-exoplayers-ui-components-728cf55ee07a

public class ExoPlayer_VideoActivity extends Activity {
    //private String videoUrl = "https://learning-services-media.brightcove.com/videos/hls/greatblueheron/greatblueheron.m3u8";
    private String videoUrl = "https://res.cloudinary.com/dqrr4jjhj/video/upload/v1550484487/samples/test/Priyanka_Chopra_Everyday_Beauty_Tips.mp4";

    private String TAG = "ExoPlayer_boo";

    private SimpleExoPlayer player;
    private PlayerView playerView;
    private DataSource.Factory dataSourceFactory;
    private LoopingMediaSource loopingSource;
    private int mResumeWindow;
    private long mResumePosition;
    private String STATE_RESUME_WINDOW;
    private String STATE_RESUME_POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exoplayer_video);

        player = ExoPlayerFactory.newSimpleInstance(this);
        playerView = findViewById(R.id.player_view);

        getVideoUrl();

        //playerView.setControllerVisibilityListener(this);
        //playerView.setErrorMessageProvider(new PlayerErrorMessageProvider());
        //playerView.requestFocus();

        playerView.setPlayer(player);
        //initializePlayer();

        dataSourceFactory = buildDataSourceFactory();

        // Produces DataSource instances through which media data is loaded.
        /*DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "yourApplicationName"));*/

        Uri mp4VideoUri = Uri.parse(videoUrl);

        /*if (uriString.contains("m3u8")) {
            MediaSource videoSource =  new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(mp4VideoUri);
            //MediaSource videoSource = new HlsMediaSource(mp4VideoUri, dataSourceFactory, 3, null, null);
            loopingSource = new LoopingMediaSource(videoSource);
        } else {
            // This is the MediaSource representing the media to be played.
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(mp4VideoUri);
        }*/

        // Prepare the player with the source.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(mp4VideoUri);
        player.prepare(videoSource);

        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
                Log.v(TAG, "Listener-onTimelineChanged...  " + reason);
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                Log.v(TAG, "Listener-onTracksChanged...");
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                Log.v(TAG, "Listener-onLoadingChanged...isLoading:" + isLoading);
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.v(TAG, "Listener-onPlayerStateChanged..." + playbackState);
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {
                Log.v(TAG, "Listener-onRepeatModeChanged...");
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.v(TAG, "Listener-onPlayerError...");
                player.stop();
                //player.prepare(loopingSource);
                player.setPlayWhenReady(true);
            }

            @Override
            public void onPositionDiscontinuity(int reason) {
                Log.v(TAG, "Listener-onPositionDiscontinuity... " + reason);
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                Log.v(TAG, "Listener-onPlaybackParametersChanged...");
            }
        });

        player.setPlayWhenReady(true);
        //player.setVideoDebugListener(this);
    }

    /**
     * Returns a new DataSource factory.
     */
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

    private void initializePlayer() {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        //We centralized everything to do with track selection in V2. Assuming you have a `DefaultTrackSelector` somewhere, you can set the maximum bitrate with:
        //
        //DefaultTrackSelector.ParametersBuilder builder = new DefaultTrackSelector.ParametersBuilder().setMaxVideoBitrate(maxBitrate);
        //trackSelector.setParameters(builder.build());
        //You should do this before calling `player.prepare` if you want this to affect the initial track selection.


        LoadControl loadControl = new DefaultLoadControl();
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(this, new DefaultRenderersFactory(this), trackSelector, loadControl);
        playerView.setPlayer(player);
    }

    private void releasePlayer() {
        if (player != null) {
            mResumeWindow = player.getCurrentWindowIndex();
            mResumePosition = Math.max(0, player.getContentPosition());

            player.release();
            player = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        //outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);
        super.onSaveInstanceState(outState);
    }

    private void getVideoUrl() {
        Intent i = getIntent();
        if (i != null && i.hasExtra("VIDEO_URL")) {
            videoUrl = i.getStringExtra("VIDEO_URL");
        }

        Log.v(TAG, "BrightCove_VideoActivity:: getvideoUrl: videoUrl = " + videoUrl);
        if (TextUtils.isEmpty(videoUrl)) {
            return;
        }
    }
}
