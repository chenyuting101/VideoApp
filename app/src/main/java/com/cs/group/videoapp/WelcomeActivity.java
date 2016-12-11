package com.cs.group.videoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.group.tool.Parameter;
import com.cs.group.tool.SharedPreferenceTool;

/**
 * Created by linyanhong on 10/12/2016.
 */

public class WelcomeActivity extends AppCompatActivity {

    TextView mUserId;
    TextView mVideo;
    TextView mGallery;
    View.OnClickListener mListener;
    private String TAG = "Video :: WelcomeActivity ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Log.d(TAG, "HELLO");


        initView();
        setListener();

    }

    private void initView() {

        mUserId = (TextView) findViewById(R.id.tv_userid);
        mGallery = (TextView) findViewById(R.id.tv_gallery);
        mVideo = (TextView) findViewById(R.id.tv_video);

        mUserId.setText(SharedPreferenceTool.read(Parameter.K_USERNAMEM, getApplicationContext()));

    }

    private void setListener() {

        mListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_gallery:
                        startActivityWith(MainActivity.class);
                        break;
                    case R.id.tv_video:
                        Toast.makeText(getApplicationContext(), TAG, Toast.LENGTH_SHORT).show();

                        break;
                }
            }
        };

        mVideo.setOnClickListener(mListener);
        mGallery.setOnClickListener(mListener);

    }

    private void startActivityWith(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
