package com.cocomap.coco;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
