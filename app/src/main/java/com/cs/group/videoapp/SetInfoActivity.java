package com.cs.group.videoapp;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cs.group.tool.Parameter;
import com.cs.group.tool.SharedPreferenceTool;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by linyanhong on 09/12/2016.
 */

public class SetInfoActivity extends AppCompatActivity {

    private String TAG = "Video::SetInfoActivity";
    private Intent mIntent;
    private int Activitycode;

    private String mEdit;
    private String mPre;

    private TextView mTvPre;
    private EditText mTvEdit;

    private TextView mTvCancel;
    private TextView mTvSave;

    private View.OnClickListener mOnclickListener;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntent = getIntent();
        Activitycode = mIntent.getIntExtra(Parameter.SET, 0);
        mPre = mIntent.getStringExtra(Parameter.PRE);
        switch (Activitycode) {
            case Parameter.SET_GENDER:
                setActionBar(Parameter.USERGENDER);
                setContentView(R.layout.set_gender_setinfoactivity);
                mTvPre = (TextView) findViewById(R.id.gender_pre);
                mTvEdit = (EditText) findViewById(R.id.gender_edit);
                mTvEdit.setClickable(true);
                mTvPre.setText(mPre);
                break;
            case Parameter.SET_NAME:
                setActionBar(Parameter.USERNAME);
                setContentView(R.layout.set_name_setinfoactivity);
                mTvPre = (TextView) findViewById(R.id.name_pre);
                mTvEdit = (EditText) findViewById(R.id.name_edit);
                mTvPre.setText(mPre);
                mTvEdit.setClickable(true);
                break;
            case Parameter.SET_SLOGN:
                setActionBar(Parameter.USERSLOGN);
                setContentView(R.layout.set_slogn_setinfoactivity);
                mTvPre = (TextView) findViewById(R.id.slogn_pre);
                mTvEdit = (EditText) findViewById(R.id.slogn_edit);
                mTvPre.setText(mPre);
                mTvEdit.setClickable(true);
                break;
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void setActionBar(String title) {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        TextView tvTitle = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.ab_title);
        tvTitle.setText(title);
        mTvCancel = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.ab_cancel);
        mTvSave = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.ab_save);

        mOnclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ab_cancel:
                        setResult(RESULT_OK, null);
                        finish();
                        break;
                    case R.id.ab_save:
                        mEdit = mTvEdit.getText().toString();
                        switch (Activitycode) {
                            case Parameter.SET_GENDER:
                                SharedPreferenceTool.write(Parameter.USERGENDER, mEdit, getApplicationContext());
                                setResult(RESULT_OK, null);
                                finish();
                                break;
                            case Parameter.SET_NAME:
                                SharedPreferenceTool.write(Parameter.USERNAME, mEdit, getApplicationContext());
                                setResult(RESULT_OK, null);
                                finish();
                                break;
                            case Parameter.SET_SLOGN:
                                SharedPreferenceTool.write(Parameter.USERSLOGN, mEdit, getApplicationContext());
                                setResult(RESULT_OK, null);
                                finish();
                                break;
                        }
                        break;
                }
            }
        };
        mTvCancel.setOnClickListener(mOnclickListener);
        mTvSave.setOnClickListener(mOnclickListener);
    }


    private void saveInfo() {
        mEdit = mTvEdit.getText().toString();
        SharedPreferenceTool.write(Parameter.USERGENDER, mEdit, getApplicationContext());

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SetInfo Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
