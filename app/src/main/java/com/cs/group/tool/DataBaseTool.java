package com.cs.group.tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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

    public void insertVideo(Context con){

    }

}
