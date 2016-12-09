package com.cs.group.videoapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by linyanhong on 07/12/2016.
 */
public class ListBuddies extends Application {
    private static Context mContext;

    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
}
