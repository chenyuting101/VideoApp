package com.cs.group.videoapp;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.cs.group.tool.Parameter;

/**
 * Created by linyanhong on 09/12/2016.
 */

public class SetInfoActivity extends AppCompatActivity {

    private String TAG = "============Video::SetInfoActivity===========";
    private Intent mIntent;
    private int Activitycode;

    private String mUserName;
    private String mUseGender;
    private String mUseSlogn;
    private Image mUserIcon;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mIntent = getIntent();
        Activitycode = mIntent.getIntExtra(Parameter.SET, 0);
//        Log.d(TAG,Activitycode + "");
        switch (Activitycode) {
            case Parameter.SET_GENDER:
//                Log.d(TAG,getSupportActionBar().toString());
                getSupportActionBar().setTitle("My new title");
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                setContentView(R.layout.set_gender_setinfoactivity);

                break;
//            case Parameter.SET_NAME:
//                setContentView(R.layout.set_name_setinfoactivity);
//                break;
//            case Parameter.SET_ICON:
//                setContentView(R.layout.set_icon_setinfoactivity);
//                break;
//            case Parameter.SET_SLOGN:
//                setContentView(R.layout.set_slogn_setinfoactivity);
//                break;
        }
    }

}
