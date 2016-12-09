package com.cs.group.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.cs.group.adapters.CircularAdapter;
import com.cs.group.provider.ExtraArgumentKeys;
import com.cs.group.provider.ImagesUrls;
import com.cs.group.videoapp.R;
import com.jpardogo.listbuddies.lib.views.ListBuddiesLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ListBuddiesFragment extends Fragment implements ListBuddiesLayout.OnBuddyItemClickListener {
    private static final String TAG = ListBuddiesFragment.class.getSimpleName();
    int mMarginDefault;
    ListBuddiesLayout mListBuddies;
    private CircularAdapter mAdapterLeft;
    private CircularAdapter mAdapterRight;
    private FloatingActionButton mFAB;
    private List<String> mImagesLeft = new ArrayList<String>();
    private List<String> mImagesRight = new ArrayList<String>();

    public static ListBuddiesFragment newInstance(boolean isOpenActivitiesActivated) {
        ListBuddiesFragment fragment = new ListBuddiesFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ExtraArgumentKeys.OPEN_ACTIVITES.toString(), isOpenActivitiesActivated);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMarginDefault = getResources().getDimensionPixelSize(com.jpardogo.listbuddies.lib.R.dimen.default_margin_between_lists);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mListBuddies = (ListBuddiesLayout) rootView.findViewById(R.id.listbuddies);
        mFAB = (FloatingActionButton) rootView.findViewById(R.id.myFAB_upload);

        mImagesLeft.addAll(Arrays.asList(ImagesUrls.imageUrls_left));
        mImagesRight.addAll(Arrays.asList(ImagesUrls.imageUrls_right));
        mAdapterLeft = new CircularAdapter(getActivity(), getResources().getDimensionPixelSize(R.dimen.item_height_small), mImagesLeft);
        mAdapterRight = new CircularAdapter(getActivity(), getResources().getDimensionPixelSize(R.dimen.item_height_tall), mImagesRight);
        mListBuddies.setAdapters(mAdapterLeft, mAdapterRight);
        mListBuddies.setSpeed(ListBuddiesLayout.DEFAULT_SPEED);
        mListBuddies.setOnItemClickListener(this);

        //upload
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resources resources = getResources();
                Toast.makeText(getActivity(), resources.getString(R.string.list) + ": action floating button", Toast.LENGTH_SHORT).show();

            }
        });


        return rootView;
    }

    @Override
    public void onBuddyItemClicked(AdapterView<?> parent, View view, int buddy, int position, long id) {

        Resources resources = getResources();
        Toast.makeText(getActivity(), resources.getString(R.string.list) + ": " + buddy + " " + resources.getString(R.string.position) + ": " + position, Toast.LENGTH_SHORT).show();

    }

    private String getImage(int buddy, int position) {
        return buddy == 0 ? ImagesUrls.imageUrls_left[position] : ImagesUrls.imageUrls_right[position];
    }

    public void setGap(int value) {
        mListBuddies.setGap(value);
    }

    public void setSpeed(int value) {
        mListBuddies.setSpeed(value);
    }

    public void setDividerHeight(int value) {
        mListBuddies.setDividerHeight(value);
    }


    public void resetLayout() {
        mListBuddies.setGap(mMarginDefault)
                .setSpeed(ListBuddiesLayout.DEFAULT_SPEED)
                .setDividerHeight(mMarginDefault)
                .setDivider(getResources().getDrawable(R.drawable.divider));
    }
}