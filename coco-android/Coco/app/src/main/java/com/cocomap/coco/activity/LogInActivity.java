package com.cocomap.coco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cocomap.coco.R;
import com.cocomap.coco.activity.GlobalViewActivity;

/**
 * Created by Sinan on 15.11.2016.
 */
public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void onClickLogin(View v) {
        Intent intent = new Intent(this, GlobalViewActivity.class);
        startActivity(intent);
    }
}
