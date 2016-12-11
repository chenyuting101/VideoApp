package com.cs.group.videoapp;

import android.app.TabActivity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.cs.group.com.cs.group.entity.Video;
import com.cs.group.models.UnUploadVideoListViewBaseAdapter;
import com.cs.group.models.UploadingVideoListViewBaseAdapter;
import com.cs.group.tool.DataBaseTool;
import com.cs.group.tool.Parameter;
import com.cs.group.tool.SharedPreferenceTool;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class UnUploadVideosActivity extends TabActivity {

    private ListView uplodingLV, unUploadLV;
    private ArrayList<Video> uploadingVideos;
    private ArrayList<Video> unUploadVideos;
    private TextView backTv;
    private Timer myTimer;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_upload_videos);
        TabHost tablHost = getTabHost();
        TabHost.TabSpec tab1 = tablHost.newTabSpec("tab1")
                .setIndicator("Uploading Videos List")
                .setContent(R.id.tab01);
        tablHost.addTab(tab1);
        TabHost.TabSpec tab2 = tablHost.newTabSpec("tab")
                .setIndicator("Unupload Videos List")
                .setContent(R.id.tab02);
        tablHost.addTab(tab2);
        final String username = SharedPreferenceTool.read(Parameter.K_USERNAMEM, this);
        uplodingLV= (ListView) findViewById(R.id.uploading_videos_list_lv);
        unUploadLV = (ListView) findViewById(R.id.un_upload_videos_list_lv);
        backTv = (TextView) findViewById(R.id.activity_un_upload_videos_btn_back);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        final DataBaseTool dataBaseTool = new DataBaseTool();
        //SharedPreferenceTool sharedPreferenceTool = new SharedPreferenceTool();
        uploadingVideos = dataBaseTool.getUploadVideosByUsername(this,username);
        unUploadVideos = dataBaseTool.getUnUploadVideosByUsername(this, username);
        UploadingVideoListViewBaseAdapter uploadingVideoListViewBaseAdapter = new UploadingVideoListViewBaseAdapter(this);
        uploadingVideoListViewBaseAdapter.setUploadingVideos(uploadingVideos);
        uplodingLV.setAdapter(uploadingVideoListViewBaseAdapter);
        UnUploadVideoListViewBaseAdapter unUploadVideoListViewBaseAdapter = new UnUploadVideoListViewBaseAdapter(this);
        unUploadVideoListViewBaseAdapter.setUploadingVideos(unUploadVideos);
        unUploadLV.setAdapter(unUploadVideoListViewBaseAdapter);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 0x1234){
                    uploadingVideos = dataBaseTool.getUploadVideosByUsername(UnUploadVideosActivity.this,username);
                    UploadingVideoListViewBaseAdapter uploadingVideoListViewBaseAdapter = new UploadingVideoListViewBaseAdapter(UnUploadVideosActivity.this);
                    uploadingVideoListViewBaseAdapter.setUploadingVideos(uploadingVideos);
                    uplodingLV.setAdapter(uploadingVideoListViewBaseAdapter);
                    unUploadVideos = dataBaseTool.getUnUploadVideosByUsername(UnUploadVideosActivity.this, username);
                    UnUploadVideoListViewBaseAdapter unUploadVideoListViewBaseAdapter = new UnUploadVideoListViewBaseAdapter(UnUploadVideosActivity.this);
                    unUploadVideoListViewBaseAdapter.setUploadingVideos(unUploadVideos);
                    unUploadLV.setAdapter(unUploadVideoListViewBaseAdapter);
                }

            }
        };
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0x1234);
            }
        },0,2500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTimer.cancel();
    }
}
