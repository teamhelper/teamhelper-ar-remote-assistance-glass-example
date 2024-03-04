package com.teamhelper.glass.application;

import android.app.Activity;
import android.app.Application;

import com.teamhelper.glass.BuildConfig;
import com.teamhelper.glass.Config;
import com.teamhelper.glass.manager.IntentManager;
import com.teamhelper.glass.view.activity.LoginActivity;
import com.teamhelper.im.IMConfig;
import com.teamhelper.meeting.MeetingConfig;
import com.teamhelper.meeting.manager.MeetingManager;
import com.teamhelper.tools.ActivityStackManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MeetingManager.init(this);
        MeetingManager.addTokenWillExpire(unused -> {
            //token即将失效的回调
            long timestamp = System.currentTimeMillis() + (5 * 60 * 60 * 1000);
            MeetingManager.exampleRefreshToken(Config.APP_KEY, Config.ACCESS_KEY, Config.ACCESS_SECRET, timestamp, null);
        });
        MeetingManager.addKickOutCallback(kickOut -> {
            //被踢下线回调
            Activity currentActivity = ActivityStackManager.getInstance().getCurrentActivity();
            ActivityStackManager.getInstance().finishActivityList();
            IntentManager.build(LoginActivity.class).startActivity(currentActivity);
        });
    }
}
