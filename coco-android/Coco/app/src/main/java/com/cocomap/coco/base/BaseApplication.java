package com.cocomap.coco.base;

import android.app.Application;
import android.content.Context;

import com.cocomap.coco.dagger.component.DaggerNetComponent;
import com.cocomap.coco.dagger.component.NetComponent;
import com.cocomap.coco.dagger.module.AppModule;
import com.cocomap.coco.dagger.module.NetModule;

/**
 * Created by Emrah on 20/11/2016.
 */
public class BaseApplication extends Application {
    private NetComponent mNetComponent;
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("https://api.ipify.org/"))
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public BaseApplication() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }
}
