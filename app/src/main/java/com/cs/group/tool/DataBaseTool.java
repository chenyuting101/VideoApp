package com.cs.group.tool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cs.group.com.cs.group.entity.Video;

import java.sql.ParameterMetaData;
import java.util.ArrayList;

/**
 * Created by chenyuting on 12/9/16.
 */

public class DataBaseTool {
    private String dataBaseName = "BaseData.db";
    private String videoTableName = "Video";
    public void initiateDataBase(Context con){
        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
        sqldb.execSQL("create table if not exists Video (id text,"
                + "username text,image_uri text,video_uri text,isUpload text);");
        sqldb.close();

    }

    public void insertVideo(Context con, String id, String imageUri, String videoUri, String userName, String isUpload){

        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
        ContentValues cv = new ContentValues();
        cv.put(Parameter.K_ID, id);
        cv.put(Parameter.K_USERNAMEM, userName);
        cv.put(Parameter.K_IMAGE_URI, imageUri);
        cv.put(Parameter.K_VIDEO_URI, videoUri);
        cv.put(Parameter.K_IS_UPLOAD, isUpload);
        sqldb.insert(videoTableName, null, cv);
        sqldb.close();
    }

    public ArrayList<Video> getUploadVideosByUsername(Context con, String username){
        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
        ArrayList<Video> videos = new ArrayList<Video>();
        //String[] colums = Parameter.K_ID+","+Parameter.K_IMAGE_URI+","+Parameter.K_VIDEO_URI;
        String[] colums = {Parameter.K_ID, Parameter.K_IMAGE_URI, Parameter.K_VIDEO_URI};
        String whereClause = Parameter.K_USERNAMEM+"=? AND " + Parameter.K_IS_UPLOAD + "=?";
        //String whereArgs = username + ","+ Parameter.V_UPLOAD;
        String[] whereArgs = {username, Parameter.V_UPLOAD};
        Cursor cur = sqldb.query(videoTableName,colums,whereClause,whereArgs,null,null,null);
        //System.out.println("\ngetUploadVideosByUsernamehere"+username);
        while(cur.moveToNext()){
            Video video = new Video();
            video.setId(cur.getString(0));
            video.setImageUri(cur.getString(1));
            video.setVideoUri(cur.getString(2));
            System.out.println("\nGetUploadVideoByUsernameid:"+video.getId()+"image_uri:"+video.getImageUri()+"video_uri:"+video.getVideoUri());
            videos.add(video);
        }
        sqldb.close();
        cur.close();
        return videos;
    }

    public ArrayList<Video> getUnUploadVideosByUsername(Context con, String username){
        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
        ArrayList<Video> videos = new ArrayList<Video>();
        //String[] colums = Parameter.K_ID+","+Parameter.K_IMAGE_URI+","+Parameter.K_VIDEO_URI;
        String[] colums = {Parameter.K_ID, Parameter.K_IMAGE_URI, Parameter.K_VIDEO_URI};
        String whereClause = Parameter.K_USERNAMEM+"=? AND " + Parameter.K_IS_UPLOAD + "=?";
        //String whereArgs = username + ","+ Parameter.V_UPLOAD;
        String[] whereArgs = {username, Parameter.V_NOT_UPLOAD};
        Cursor cur = sqldb.query(videoTableName,colums,whereClause,whereArgs,null,null,null);
        //System.out.println("\ngetUploadVideosByUsernamehere"+username);
        while(cur.moveToNext()){
            Video video = new Video();
            video.setId(cur.getString(0));
            video.setImageUri(cur.getString(1));
            video.setVideoUri(cur.getString(2));
            System.out.println("\nGetUnUploadVideoByUsernameid:"+video.getId()+"image_uri:"+video.getImageUri()+"video_uri:"+video.getVideoUri());
            videos.add(video);
        }
        sqldb.close();
        cur.close();
        return videos;
    }

    public ArrayList<Video> getAllUploadVideos(Context con){
        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
        ArrayList<Video> videos = new ArrayList<Video>();
        //String[] colums = Parameter.K_ID+","+Parameter.K_IMAGE_URI+","+Parameter.K_VIDEO_URI;
        String[] colums = {Parameter.K_ID, Parameter.K_USERNAMEM, Parameter.K_IMAGE_URI, Parameter.K_VIDEO_URI};
        String whereClause = Parameter.K_IS_UPLOAD + "=?";
        //String whereArgs = username + ","+ Parameter.V_UPLOAD;
        String[] whereArgs = { Parameter.V_UPLOAD};
        Cursor cur = sqldb.query(videoTableName,colums,whereClause,whereArgs,null,null,null);
        //System.out.println("\ngetUploadVideosByUsernamehere"+username);
        while(cur.moveToNext()){
            Video video = new Video();
            video.setId(cur.getString(0));
            video.setUsername(cur.getString(1));
            video.setImageUri(cur.getString(2));
            video.setVideoUri(cur.getString(3));
            System.out.println("\nGetUploadVideoByUsernameid:"+video.getId()+"username:"+video.getUsername()+"image_uri:"+video.getImageUri()+"video_uri:"+video.getVideoUri());
            videos.add(video);
        }
        sqldb.close();
        cur.close();
        return videos;
    }

    public void deleteVideoById(Context con, String id){
        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
        int result = sqldb.delete(videoTableName, "id=?", new String[]{id});
        sqldb.close();
    }
    public void updateVideoToUploadById(Context con, String id){
        SQLiteDatabase sqldb = con.openOrCreateDatabase(dataBaseName, con.MODE_PRIVATE, null);
        ContentValues cv =new ContentValues();
        cv.put(Parameter.K_IS_UPLOAD, Parameter.V_UPLOAD);
        int result = sqldb.update(videoTableName, cv, "id=?", new String[]{id});
        sqldb.close();
    }

}
