package com.cocomap.coco.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.cocomap.coco.adapter.GridViewAdapter;
import com.cocomap.coco.R;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GlobalViewActivity extends AppCompatActivity {

    TextView focus;
    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_view);

        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url("http://ec2-54-186-167-76.us-west-2.compute.amazonaws.com:8000/cocomapapp/topicList")
                .addHeader("Content-Type", "application/json")
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                json = response.body().string();


            }
        });

        final String[] thumbnailUrls = {"Dolar", "Hillary", "Bahçeli"};
        focus = (TextView) findViewById(R.id.focusTopicTextView);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new GridViewAdapter(this, thumbnailUrls));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                focus.setText(thumbnailUrls[position]);
            }
        });
    }

    public void onClickCreateTopic(View view) {

        Intent intent = new Intent(this, CreateTopicActivity.class);

        startActivity(intent);

    }

    public void onFocusClick(View view) {
        Intent intent = new Intent(GlobalViewActivity.this, TopicDetailActivity.class);
        startActivity(intent);

    }
}
