package com.cs.group.tool;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by chenyuting on 12/10/16.
 */

public class VideoUploadService extends Service {
    private String TAG = "VideoUploadService";
    private Thread myThread;

    @Override
    public void onCreate() {
        super.onCreate();
//        DataBaseTool dataBaseTool =  new DataBaseTool();
//        dataBaseTool.initiateDataBase(this);
//        dataBaseTool.insertVideo(this,"123ytchen","/mnt/123ytchen.png","/mnt/123ytchen.mp4","ytchen",Parameter.V_UPLOAD);
//        dataBaseTool.getUploadVideosByUsername(this, "ytchen");
//        dataBaseTool.deleteVideoById(this, "123ytchen");


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
