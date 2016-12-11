package com.cs.group.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.group.com.cs.group.entity.Video;
import com.cs.group.videoapp.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by chenyuting on 12/11/16.
 */

public class UploadingVideoListViewBaseAdapter extends BaseAdapter {
    private Context con;
    private ArrayList<Video> uploadingVideos;
    public UploadingVideoListViewBaseAdapter(Context con){
        this.con = con;
    }

    public ArrayList<Video> getUploadingVideos() {
        return uploadingVideos;
    }

    public void setUploadingVideos(ArrayList<Video> uploadingVideos) {
        this.uploadingVideos = uploadingVideos;
    }

    @Override
    public int getCount() {
        return uploadingVideos.size();
    }

    @Override
    public Object getItem(int position) {
        return uploadingVideos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) convertView= LayoutInflater.from(con).inflate(R.layout.uploading_videos_list_cell, null);
        ImageView iv = (ImageView) convertView.findViewById(R.id.uploading_videos_list_cell_iv);

        TextView idtv = (TextView) convertView.findViewById(R.id.uploading_videos_list_cell_tv_vidt);
        Video video = (Video) this.getItem(position);
        Bitmap bitmap = this.getLoacalBitmap(video.getImageUri());
        iv.setImageBitmap(bitmap);
        idtv.setText(video.getId());
        return convertView;
    }
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
