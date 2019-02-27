package com.kult.exoplayer;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.kult.R;
import com.kult.brightcove.Adapter_Products;
import com.kult.models.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Exoplayer_NYX extends Activity implements Adapter_Products.SeekBarListener {
    @BindView(R.id.recycler_view_product_list)
    RecyclerView recyclerViewProducts;
    @BindView(R.id.player_view)
    PlayerView playerView;

    private SimpleExoPlayer player;

    //NYX
    private String videoUrl = "https://res.cloudinary.com/dqrr4jjhj/video/upload/sp_full_hd/v1550746511/priyanka.m3u8"; //default: hard coded value

    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exoplayer_nyx);
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
        player = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(player);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);

        DataSource.Factory dataSourceFactory = ((DemoApplication) getApplication()).buildDataSourceFactory();

        Uri mp4VideoUri = Uri.parse(videoUrl);
        MediaSource videoSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(mp4VideoUri);
        player.prepare(videoSource);
        player.setPlayWhenReady(true);
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
        player.seekTo((int) msec * 1000);
    }
}
