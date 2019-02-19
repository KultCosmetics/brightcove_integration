package com.kult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kult.brightcove.BrightCove_NYX;
import com.kult.brightcove.BrightCove_StaticVideoActivity;
import com.kult.brightcove.BrightCove_VideoActivity;
import com.kult.brightcove.BrightCove_VideoListActivity;
import com.kult.exoplayer.ExoPlayer_VideoActivity;
import com.kult.exoplayer.ExoPlayer_VideoListActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_exoplayer).setOnClickListener(this);
        findViewById(R.id.tv_exoplayer_feed).setOnClickListener(this);
        findViewById(R.id.tv_brightcove_static_video).setOnClickListener(this);
        findViewById(R.id.tv_brightcove_video_from_id).setOnClickListener(this);
        findViewById(R.id.tv_kult_feed).setOnClickListener(this);
        findViewById(R.id.tv_kult_need).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exoplayer:
                startActivity(new Intent(this, ExoPlayer_VideoActivity.class));
                break;
            case R.id.tv_exoplayer_feed:
                startActivity(new Intent(this, ExoPlayer_VideoListActivity.class));
                break;
            case R.id.tv_brightcove_static_video:
                startActivity(new Intent(this, BrightCove_StaticVideoActivity.class));
                break;
            case R.id.tv_brightcove_video_from_id:
                startActivity(new Intent(this, BrightCove_VideoActivity.class));
                break;
            case R.id.tv_kult_feed:
                startActivity(new Intent(this, BrightCove_VideoListActivity.class));
                break;
            case R.id.tv_kult_need:
                startActivity(new Intent(this, BrightCove_NYX.class));
                break;
        }

    }
}
