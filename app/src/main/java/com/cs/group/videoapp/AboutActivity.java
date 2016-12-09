package com.cs.group.videoapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs.group.Utils.Utils;
import com.cs.group.tool.Parameter;

public class AboutActivity extends BaseActivity {

    private RelativeLayout mRlUserIcon;
    private RelativeLayout mRlUserName;
    private RelativeLayout mRlUserSlogn;
    private RelativeLayout mRlUserGender;

    private ImageView mIvUserIcon;
    private TextView mTvUserName;
    private TextView mTvUserSlogn;
    private TextView mTvUserGender;
    private ImageView mImageView;
    private View.OnClickListener mOnclickListener;

    private Uri mUserIcon;
    private String mUserName;
    private String mUserGender;
    private String mUserSlogn;
    private String mUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();
        setListener();


    }

    private void setListener() {
        mOnclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
//                    case R.id.layout_icon:
//                        startActivityWith(Parameter.SET_ICON);
//                        break;
//                    case R.id.layout_name:
//                        startActivityWith(Parameter.SET_NAME);
//                        break;
                    case R.id.layout_gender:
                        startActivityWith(Parameter.SET_GENDER);
//                        break;
//                    case R.id.layout_slogn:
//                        startActivityWith(Parameter.SET_SLOGN);
//                        break;
                }

            }
        };
        mRlUserIcon.setOnClickListener(mOnclickListener);
        mRlUserName.setOnClickListener(mOnclickListener);
        mRlUserSlogn.setOnClickListener(mOnclickListener);
        mRlUserGender.setOnClickListener(mOnclickListener);
    }

    private void startActivityWith(int code) {
        Intent intent = new Intent(this, SetInfoActivity.class);
        intent.putExtra(Parameter.SET, code);
        startActivity(intent);
    }

    private void initView() {

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


        mTvUserGender.setText("hello");
        mTvUserName.setText("hello");
        mTvUserSlogn.setText("hello");
        mIvUserIcon.setImageURI(getmUserIcon());
//        mTvUserGender.setText(getmUserGender());
//        mTvUserName.setText(getmUserName());
//        mTvUserSlogn.setText(getmUserSlogn());
//        mIvUserIcon.setImageURI(getmUserIcon());


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
}

