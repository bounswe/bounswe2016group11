package com.cocomap.coco.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cocomap.coco.R;
import com.cocomap.coco.activity.CreateTopicActivity;

public class TopicDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);
    }

    public void onClickPost(View v) {
        Intent intent = new Intent(this, CreateTopicActivity.class);
        startActivity(intent);
    }
}
