package com.cs.group.videoapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.cs.group.Utils.Utils;
import com.cs.group.tool.Parameter;
import com.cs.group.tool.SharedPreferenceTool;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class AboutActivity extends BaseActivity {

    private static final String TAG = " Video :: AboutActivity";
    private RelativeLayout mRlUserIcon;
    private RelativeLayout mRlUserName;
    private RelativeLayout mRlUserSlogn;
    private RelativeLayout mRlUserGender;

    private ImageView mIvUserIcon;
    private TextView mTvUserName;
    private TextView mTvUserSlogn;
    private TextView mTvUserGender;
    private TextView mTvUserId;
    private ImageView mImageView;
    private Switch mSwIsAutoUpdate;
    private View.OnClickListener mOnclickListener;

    private Uri mUserIcon;
    private String mUserName;
    private String mUserGender;
    private String mUserSlogn;
    private String mUserId;

    private String isAutoUpdate = "";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getView();
        setListener();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initView() {

        mTvUserId.setText(SharedPreferenceTool.read(Parameter.K_USERNAMEM, this));
        if (SharedPreferenceTool.read(Parameter.USERGENDER, getApplicationContext()) != null) {
            mTvUserGender.setText(SharedPreferenceTool.read(Parameter.USERGENDER, getApplicationContext()));
        } else {
            mTvUserGender.setText("Male / Female");
        }
//        mTvUserGender.setText("Male / Female");
        if (SharedPreferenceTool.read(Parameter.USERNAME, getApplicationContext()) != null) {
            mTvUserName.setText(SharedPreferenceTool.read(Parameter.USERNAME, this));
        } else {
            mTvUserName.setText(SharedPreferenceTool.read(Parameter.K_USERNAMEM, this));
        }
        if (SharedPreferenceTool.read(Parameter.USERSLOGN, getApplicationContext()) != null) {
            mTvUserSlogn.setText(SharedPreferenceTool.read(Parameter.USERSLOGN, this));
        }
        mTvUserSlogn.setText(R.string.user_slogn_text);
        mIvUserIcon.setImageURI(getmUserIcon());

        isAutoUpdate = SharedPreferenceTool.read(Parameter.K_IS_AUTO_UPLOAD, getApplicationContext());
        Log.d(TAG, "isAutoUpdate = " + isAutoUpdate);
        Log.d(TAG, "Parameter.V_AUTO_UPLOAD = " + Parameter.V_AUTO_UPLOAD);
        if (isAutoUpdate.equals(Parameter.V_AUTO_UPLOAD)) {
            mSwIsAutoUpdate.setChecked(true);
        }
    }

    private void setListener() {
        mOnclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
//                    case R.id.layout_icon:
//                        startActivityWith(Parameter.SET_ICON, mTvUserGender.getText().toString());
//                        break;
                    case R.id.layout_name:
                        startActivityWith(Parameter.SET_NAME, mTvUserName.getText().toString());
                        break;
                    case R.id.layout_gender:
                        startActivityWith(Parameter.SET_GENDER, mTvUserGender.getText().toString());
                        break;
                    case R.id.layout_slogn:
                        startActivityWith(Parameter.SET_SLOGN, mTvUserSlogn.getText().toString());
                        break;
                }

            }
        };
        mRlUserIcon.setOnClickListener(mOnclickListener);
        mRlUserName.setOnClickListener(mOnclickListener);
        mRlUserSlogn.setOnClickListener(mOnclickListener);
        mRlUserGender.setOnClickListener(mOnclickListener);

        mSwIsAutoUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("Switch State=", "" + isChecked);

                if (isChecked) {
                    SharedPreferenceTool.write(Parameter.K_IS_AUTO_UPLOAD, Parameter.V_AUTO_UPLOAD, getApplicationContext());

                } else {
                    SharedPreferenceTool.write(Parameter.K_IS_AUTO_UPLOAD, Parameter.V_NOT_AUTO_UPLOAD, getApplicationContext());

                }
            }
        });
    }

    private void startActivityWith(int code, String pre) {
        Intent intent = new Intent(this, SetInfoActivity.class);
        intent.putExtra(Parameter.SET, code);
        intent.putExtra(Parameter.PRE, pre);

        startActivityForResult(intent, code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Intent refresh = new Intent(this, AboutActivity.class);
            startActivity(refresh);
            this.finish();
        }
    }

    private void getView() {

        mImageView = (ImageView) findViewById(R.id.image);
        mBackground = mImageView;
        moveBackground();


        mRlUserIcon = (RelativeLayout) findViewById(R.id.layout_icon);
        mRlUserName = (RelativeLayout) findViewById(R.id.layout_name);
        mRlUserSlogn = (RelativeLayout) findViewById(R.id.layout_slogn);
        mRlUserGender = (RelativeLayout) findViewById(R.id.layout_gender);

        mIvUserIcon = (ImageView) findViewById(R.id.user_icon);
        mTvUserName = (TextView) findViewById(R.id.user_name);
        mTvUserSlogn = (TextView) findViewById(R.id.user_slogn);
        mTvUserGender = (TextView) findViewById(R.id.user_gender);
        mTvUserId = (TextView) findViewById(R.id.user_id);
        mSwIsAutoUpdate = (Switch) findViewById(R.id.user_is_update);

        initView();
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (Utils.hasHoneycomb()) {
            View demoContainerView = findViewById(R.id.image);
            demoContainerView.setAlpha(0);
            ViewPropertyAnimator animator = demoContainerView.animate();
            animator.alpha(1);
            if (Utils.hasICS()) {
                animator.setStartDelay(250);
            }
            animator.setDuration(1000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    public Uri getmUserIcon() {
        return mUserIcon;
    }

    public void setmUserIcon(Uri mUserIcon) {
        this.mUserIcon = mUserIcon;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmUserGender() {
        return mUserGender;
    }

    public void setmUserGender(String mUserGender) {
        this.mUserGender = mUserGender;
    }

    public String getmUserSlogn() {
        return mUserSlogn;
    }

    public void setmUserSlogn(String mUserSlogn) {
        this.mUserSlogn = mUserSlogn;
    }

    public String getmUserId() {
        return mUserId;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("About Page") // TODO: Define a title for the content shown.
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

