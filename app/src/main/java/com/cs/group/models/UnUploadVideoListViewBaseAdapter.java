package com.cs.group.models;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.group.com.cs.group.entity.Video;
import com.cs.group.tool.DataBaseTool;
import com.cs.group.videoapp.R;
import com.cs.group.videoapp.VideoCaptureActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by chenyuting on 12/11/16.
 */

public class UnUploadVideoListViewBaseAdapter extends BaseAdapter {
    private Context con;
    private ArrayList<Video> uploadingVideos;
    public UnUploadVideoListViewBaseAdapter(Context con){
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
        if(convertView == null) convertView= LayoutInflater.from(con).inflate(R.layout.un_upload_videos_list_cell, null);
        ImageView iv = (ImageView) convertView.findViewById(R.id.un_upload_videos_list_cell_iv);

        TextView idtv = (TextView) convertView.findViewById(R.id.un_upload_videos_list_cell_tv_vidt);
        Button btn = (Button) convertView.findViewById(R.id.un_upload_videos_list_cell_btn_upload);

        final Video video = (Video) this.getItem(position);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseTool dataBaseTool = new DataBaseTool();
                dataBaseTool.updateVideoToUploadById(con,video.getId());
                new AlertDialog.Builder(con)
                        .setMessage("This video is added to upload list!\nVideo Id: "+video.getId())
                        .setNeutralButton("OK", null)
                        .show();
            }
        });
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
