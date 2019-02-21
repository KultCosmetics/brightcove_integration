package com.kult.brightcove;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.brightcove.player.edge.Catalog;
import com.brightcove.player.edge.VideoListener;
import com.brightcove.player.event.EventEmitter;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BrightcoveExoPlayerVideoView;
import com.kult.R;
import com.kult.models.FeedDataItem;

import java.util.List;

public class Adapter_Feeds extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private Activity mActivity;
    private List<FeedDataItem> feedDataItemList;

    public Adapter_Feeds(Context context, List<FeedDataItem> list) {
        this.mContext = context;
        this.mActivity = (Activity) context;
        this.feedDataItemList = list;
    }

    public void setFeedDataItemList(List<FeedDataItem> list) {
        this.feedDataItemList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_brightcove_feed_video_content_item, viewGroup, false);
        return new ViewHolder_FeedsVideoContent(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        final FeedDataItem feedDataItem = feedDataItemList.get(position);
        if (feedDataItem != null) {

            final ViewHolder_FeedsVideoContent videoViewHolder = (ViewHolder_FeedsVideoContent) viewHolder;
            videoViewHolder.view.setTag(position);

            if (feedDataItem.getVideoId() > 0) {

                EventEmitter eventEmitter = videoViewHolder.brightcoveExoPlayerVideoView.getEventEmitter();
                Catalog catalog = new Catalog(eventEmitter, mContext.getResources().getString(R.string.brightcove_account_id), mContext.getResources().getString(R.string.brightcove_policy_key));

                catalog.findVideoByID(String.valueOf(feedDataItem.getVideoId()), new VideoListener() {
                    // Add the video found to the queue with add().
                    // Start playback of the video with start().
                    @Override
                    public void onVideo(Video video) {
                        videoViewHolder.brightcoveExoPlayerVideoView.add(video);
                        videoViewHolder.brightcoveExoPlayerVideoView.start();
                    }
                });

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.height = feedDataItem.getHeight();
                lp.bottomMargin = 10;
                lp.leftMargin = 10;
                lp.rightMargin = 10;
                lp.topMargin = 10;

                videoViewHolder.brightcoveExoPlayerVideoView.setLayoutParams(lp);

                videoViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(mContext, BrightCove_VideoActivity.class);
                        intent.putExtra("VIDEO_ID", String.valueOf(feedDataItem.getVideoId()));

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

    public class ViewHolder_FeedsVideoContent extends RecyclerView.ViewHolder {

        public View view;
        public BrightcoveExoPlayerVideoView brightcoveExoPlayerVideoView;

        public ViewHolder_FeedsVideoContent(View itemView) {
            super(itemView);
            view = itemView;
            brightcoveExoPlayerVideoView = itemView.findViewById(R.id.brightcove_video_view_feed_video_content);
        }
    }
}
