package com.cocomap.coco.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.cocomap.coco.adapter.GridViewAdapter;
import com.cocomap.coco.R;

public class GlobalViewActivity extends AppCompatActivity {

    TextView focus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_view);


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
