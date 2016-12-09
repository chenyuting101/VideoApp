package com.cs.group.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cs.group.Utils.SharePreferences;
import com.cs.group.adapters.CustomizeSpinnersAdapter;
import com.cs.group.provider.SharedPrefKeys;
import com.cs.group.videoapp.R;


public class CustomizeFragment extends Fragment {
    private static final String TAG = CustomizeFragment.class.getSimpleName();
    SeekBar mSeekBarGap;
    TextView seekBarGapValue;

    SeekBar mSeekBarSpeed;
    TextView seekBarSpeedValue;

    SeekBar mSeekBarDivHeight;
    TextView seekBarDivHeightValue;


    private OnCustomizeListener mOnCustomizeListener;
    private CustomizeSpinnersAdapter mSpinnerAdapter;
    private int[] mScrollSpinnerValues;

    private int mGapSeekBarProgress;
    private int mSpeedSeekBarProgress;
    private int mDivHeightSeekBarProgress;
    private SeekBar.OnSeekBarChangeListener mSeekBarListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.seekBarGap:
                    mGapSeekBarProgress = progress;
                    seekBarGapValue.setText(String.valueOf(progress));
                    mOnCustomizeListener.setGap(progress);
                    break;
                case R.id.seekBarSpeed:
                    mSpeedSeekBarProgress = progress;
                    seekBarSpeedValue.setText(String.valueOf(progress));
                    mOnCustomizeListener.setSpeed(progress);
                    break;
                case R.id.seekBarDivHeight:
                    mDivHeightSeekBarProgress = progress;
                    seekBarDivHeightValue.setText(String.valueOf(progress));
                    mOnCustomizeListener.setDividerHeight(progress);
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    public static Fragment newInstance() {
        return new CustomizeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mScrollSpinnerValues = getActivity().getResources().getIntArray(R.attr.scroll);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customize, container, false);
        initView(rootView);
        startConfig();
        return rootView;
    }

    private void initView(View v) {
        mSeekBarGap = (SeekBar) v.findViewById(R.id.seekBarGap);
        seekBarGapValue = (TextView) v.findViewById(R.id.seekBarGapValue);
        mSeekBarSpeed = (SeekBar) v.findViewById(R.id.seekBarSpeed);
        seekBarSpeedValue = (TextView) v.findViewById(R.id.seekBarSpeedValue);
        mSeekBarDivHeight = (SeekBar) v.findViewById(R.id.seekBarDivHeight);
        seekBarDivHeightValue = (TextView) v.findViewById(R.id.seekBarDivHeightValue);
    }

    private void startConfig() {
        restartLastConfig();
        setProgressText();
        initSeekBars();
    }

    private void initSeekBars() {
        mSeekBarGap.setProgress(mGapSeekBarProgress);
        mSeekBarGap.setOnSeekBarChangeListener(mSeekBarListener);
        mSeekBarSpeed.setProgress(mSpeedSeekBarProgress);
        mSeekBarSpeed.setOnSeekBarChangeListener(mSeekBarListener);
        mSeekBarDivHeight.setProgress(mDivHeightSeekBarProgress);
        mSeekBarDivHeight.setOnSeekBarChangeListener(mSeekBarListener);
    }

    private void setProgressText() {
        seekBarGapValue.setText(String.valueOf(mGapSeekBarProgress));
        seekBarSpeedValue.setText(String.valueOf(mSpeedSeekBarProgress));
        seekBarDivHeightValue.setText(String.valueOf(mDivHeightSeekBarProgress));
    }

    private void restartLastConfig() {
        mGapSeekBarProgress = SharePreferences.getValue(SharedPrefKeys.GAP_PROGRESS);
        mSpeedSeekBarProgress = SharePreferences.getValue(SharedPrefKeys.SPEED_PROGRESS);
        mDivHeightSeekBarProgress = SharePreferences.getValue(SharedPrefKeys.DIV_HEIGHT_PROGRESS);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mOnCustomizeListener = (OnCustomizeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + OnCustomizeListener.class.getSimpleName());
        }

    }

    public void reset() {
        startConfig();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharePreferences.saveCustomization(SharedPrefKeys.GAP_PROGRESS, mGapSeekBarProgress);
        SharePreferences.saveCustomization(SharedPrefKeys.SPEED_PROGRESS, mSpeedSeekBarProgress);
        SharePreferences.saveCustomization(SharedPrefKeys.DIV_HEIGHT_PROGRESS, mDivHeightSeekBarProgress);
    }

    public interface OnCustomizeListener {
        void setSpeed(int value);

        void setGap(int value);

        void setDividerHeight(int value);

    }
}
