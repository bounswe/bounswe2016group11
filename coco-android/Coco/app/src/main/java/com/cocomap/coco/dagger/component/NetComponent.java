package com.cocomap.coco.dagger.component;

import com.cocomap.coco.activity.CreateTopicActivity;
import com.cocomap.coco.activity.GlobalViewActivity;
import com.cocomap.coco.activity.LogInActivity;
import com.cocomap.coco.activity.SignUpActivity;
import com.cocomap.coco.activity.TopicDetailActivity;
import com.cocomap.coco.base.BaseActivity;
import com.cocomap.coco.dagger.module.AppModule;
import com.cocomap.coco.dagger.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Emrah on 20/11/2016.
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(SignUpActivity activity);

    void inject(LogInActivity activity);

    void inject(TopicDetailActivity activity);

    void inject(CreateTopicActivity activity);

    void inject(GlobalViewActivity activity);

}
