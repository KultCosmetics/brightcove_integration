package com.kult.brightcove;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kult.R;
import com.kult.SpacesItemDecoration;
import com.kult.models.FeedDataItem;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BrightCove_VideoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brightcove_video_list);

        getFeedData();
    }

    private void getFeedData() {
        List<FeedDataItem> list = loadProfiles();
        displayVideoList(list);
    }

    private void displayVideoList(List<FeedDataItem> feedDataItemList) {
        Adapter_Feeds adapter = new Adapter_Feeds(this, feedDataItemList);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_video_list);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        //TODO TRY THIS FOR BRIGHTCOVE QUERY: recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public List<FeedDataItem> loadProfiles() {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromAsset());
            List<FeedDataItem> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                FeedDataItem feedDataItem = gson.fromJson(array.getString(i), FeedDataItem.class);
                Log.d("poo", "feedDataItem:  " + feedDataItem.getVideoId());
                list.add(feedDataItem);
            }
            return list;
        } catch (Exception e) {
            Log.e("poo", "error", e);
            return null;
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
