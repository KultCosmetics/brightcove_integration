package com.kult;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.brightcove.player.view.BrightcoveExoPlayerVideoView;

public class ViewHolder_FeedsVideoContent extends RecyclerView.ViewHolder {

    public View view;
    public BrightcoveExoPlayerVideoView brightcoveExoPlayerVideoView;

    public ViewHolder_FeedsVideoContent(View itemView) {
        super(itemView);
        view = itemView;
        brightcoveExoPlayerVideoView = itemView.findViewById(R.id.brightcove_video_view_feed_video_content);
    }
}
