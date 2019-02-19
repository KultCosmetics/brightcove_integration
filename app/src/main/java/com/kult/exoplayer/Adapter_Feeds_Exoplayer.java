package com.kult.exoplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.kult.R;
import com.kult.brightcove.BrightCove_VideoActivity;
import com.kult.models.FeedDataItem;

import java.util.List;

public class Adapter_Feeds_Exoplayer extends RecyclerView.Adapter<Adapter_Feeds_Exoplayer.ViewHolder_FeedsVideoContent_ExoPlayer> {

    private Context mContext;
    private Activity mActivity;
    private List<FeedDataItem> feedDataItemList;

    public Adapter_Feeds_Exoplayer(Context context, List<FeedDataItem> list) {
        this.mContext = context;
        this.mActivity = (Activity) context;
        this.feedDataItemList = list;
    }

    @Override
    public ViewHolder_FeedsVideoContent_ExoPlayer onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_feed_video_content_item_exoplayer, viewGroup, false);
        return new ViewHolder_FeedsVideoContent_ExoPlayer(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder_FeedsVideoContent_ExoPlayer videoViewHolder, final int position) {
        final FeedDataItem feedDataItem = feedDataItemList.get(position);
        if (feedDataItem != null) {

            videoViewHolder.view.setTag(position);

            if (!TextUtils.isEmpty(feedDataItem.getVideoUrl())) {
                SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(mContext);
                videoViewHolder.playerView.setPlayer(player);

                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, mContext.getResources().getString(R.string.app_name)));
                Uri mp4VideoUri = Uri.parse(feedDataItem.getVideoUrl());
                MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(mp4VideoUri);
                player.prepare(videoSource);
                player.setPlayWhenReady(false);

                videoViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(mContext, BrightCove_VideoActivity.class);
                        intent.putExtra("VIDEO_URL", String.valueOf(feedDataItem.getVideoUrl()));

                        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                mActivity,
                                new Pair<View, String>(v, "video_transition"));

                        mActivity.startActivity(intent, activityOptions.toBundle());

                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (feedDataItemList != null) {
            return feedDataItemList.size();
        }
        return 0;
    }

    public class ViewHolder_FeedsVideoContent_ExoPlayer extends RecyclerView.ViewHolder {

        public View view;
        public PlayerView playerView;

        public ViewHolder_FeedsVideoContent_ExoPlayer(View itemView) {
            super(itemView);
            view = itemView;
            playerView = itemView.findViewById(R.id.player_view);
        }
    }

}
