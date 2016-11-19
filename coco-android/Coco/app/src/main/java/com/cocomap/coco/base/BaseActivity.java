package com.cocomap.coco.base;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Emrah on 20/11/2016.
 */
public class BaseActivity extends AppCompatActivity {

    public BaseApplication getBaseApplication() {
        return (BaseApplication) BaseApplication.getContext();
    }
}
