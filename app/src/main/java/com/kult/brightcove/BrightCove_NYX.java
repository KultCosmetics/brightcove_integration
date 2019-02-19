package com.kult.brightcove;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.brightcove.player.edge.Catalog;
import com.brightcove.player.edge.VideoListener;
import com.brightcove.player.event.EventEmitter;
import com.brightcove.player.event.EventType;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BrightcoveExoPlayerVideoView;
import com.brightcove.player.view.BrightcovePlayer;
import com.kult.R;
import com.kult.models.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrightCove_NYX extends BrightcovePlayer implements Adapter_Products.SeekBarListener {
    @BindView(R.id.recycler_view_product_list)
    RecyclerView recyclerViewProducts;
    @BindView(R.id.brightcove_video_view)
    BrightcoveExoPlayerVideoView brightcoveExoPlayerVideoView;

    //NYX
    private String videoId = "6001577828001"; //default: hard coded value

    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nyx);
        ButterKnife.bind(this);

        loadProducts();
        playVideo();
    }

    private void loadProducts() {
        populateProducts();

        Adapter_Products adapter_products = new Adapter_Products(this, productList);
        recyclerViewProducts.setAdapter(adapter_products);
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
                Log.v(TAG, "BrightCove_VideoActivity:: onVideo: video = " + video);
                brightcoveVideoView.add(video);
                brightcoveVideoView.start();
            }
        });
    }

    private void populateProducts() {
        Product p = new Product("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCNEchvef2zV88Y1cz4gOeVXgvWZ3mEeN2JPNTDky6hIRwmDsWjA", "Product 1", 5);
        productList.add(p);
        p = new Product("https://01.avoncdn.com/shop/assets/en/prod/prod_1186651_xl.jpg?w=700", "Product 2", 10);
        productList.add(p);
        p = new Product("https://d2pa5gi5n2e1an.cloudfront.net/global/images/product/beauty/Lakme_9_to_5_Weightless_Mousse/Lakme_9_to_5_Weightless_Mousse_L_1.jpg", "Product 3", 15);
        productList.add(p);
        p = new Product("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRx03-V1EprwEcFy9aamsoFn_taArlSNW3-V0n-Kc-R3YtbS4BW", "Product 4", 20);
        productList.add(p);
        p = new Product("https://01.avoncdn.com/shop/assets/en/prod/prod_1186651_xl.jpg?w=700", "Product 5", 28);
        productList.add(p);
        p = new Product("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCNEchvef2zV88Y1cz4gOeVXgvWZ3mEeN2JPNTDky6hIRwmDsWjA", "Product 6", 35);
        productList.add(p);
    }

    @Override
    public void moveSeekBarTo(float msec) {
        Log.d("poo", "msec:   " + msec);
        brightcoveExoPlayerVideoView.seekTo((int) msec * 1000);
    }
}
