package com.cs.group.tool;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.cs.group.com.cs.group.entity.Video;

import java.util.ArrayList;

/**
 * Created by chenyuting on 12/10/16.
 */

public class VideoUploadService extends Service {
    private String TAG = "VideoUploadService";
    private Thread myThread;

    @Override
    public void onCreate() {
        super.onCreate();
//        myThread = new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                DataBaseTool dataBaseTool = new DataBaseTool();
//                NetTool netTool = new NetTool();
//                VideoTool videoTool = new VideoTool();
//                Video video;
//                if(dataBaseTool.getAllUploadVideos(VideoUploadService.this).size()>0){
//                    video = dataBaseTool.getAllUploadVideos(VideoUploadService.this).get(0);
//                    if(netTool.isNetworkAvailable(VideoUploadService.this)) videoTool.uploadVideo(video, VideoUploadService.this);
//                }
//            }
//        };
//        myThread.start();




    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        myThread = new Thread(){
            @Override
            public void run() {
                super.run();
                DataBaseTool dataBaseTool = new DataBaseTool();
                NetTool netTool = new NetTool();
                VideoTool videoTool = new VideoTool();
                Video video;
                while (true) {
                    if (dataBaseTool.getAllUploadVideos(VideoUploadService.this).size() > 0) {
                        video = dataBaseTool.getAllUploadVideos(VideoUploadService.this).get(0);
                        if (netTool.isNetworkAvailable(VideoUploadService.this))
                            videoTool.uploadVideo(video, VideoUploadService.this);
                    }
                    try {
                        this.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        myThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.myThread.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
