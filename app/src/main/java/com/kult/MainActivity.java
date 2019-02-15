package com.kult;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_brightcove_static_video).setOnClickListener(this);
        findViewById(R.id.tv_brightcove_video_from_id).setOnClickListener(this);
        findViewById(R.id.tv_kult_feed).setOnClickListener(this);
        findViewById(R.id.tv_kult_need).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_brightcove_static_video:
                startActivity(new Intent(this, StaticVideoActivity.class));
                break;
            case R.id.tv_brightcove_video_from_id:
                startActivity(new Intent(this, VideoIdUsingCatalogActivity.class));
                break;
            case R.id.tv_kult_feed:
                startActivity(new Intent(this, KultVideoListActivity.class));
                break;
            case R.id.tv_kult_need:
                startActivity(new Intent(this, KultProductActivity_NYX.class));
                break;
        }

    }
}
