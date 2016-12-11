package com.cs.group.tool;

import android.content.Context;
import android.util.Log;

import com.cs.group.com.cs.group.entity.Video;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.sql.ParameterMetaData;

import java.util.ArrayList;

/**
 * Created by chenyuting on 12/3/16.
 */

public class VideoTool {
    private String TAG = "VideoTool";
    /**
     * get videos in the server by username
     * @param username
     * @return
     */
    public ArrayList<Video> getVideoListByUsername(String username) {
        ArrayList<Video> videoList = new ArrayList<>();
        NetTool netTool = new NetTool();
        String requsetBody = Parameter.K_FUNCTION+"="+ Parameter.V_FUNCTION_GET_ALL_VIDEO+"&"+Parameter.K_USERNAMEM+"="+username;
        try {
            String jsonStr = netTool.postRequest(Parameter.GET_VIDEO_URL, requsetBody);

            JSONArray ja = new JSONArray(jsonStr);
            for(int i=0;i<ja.length();i++){
                Video video = new Video();
                video.setId(ja.getJSONObject(i).getString(Parameter.K_ID));
                video.setImageUri(ja.getJSONObject(i).getString(Parameter.K_IMAGE_URI));
                video.setVideoUri(ja.getJSONObject(i).getString(Parameter.K_VIDEO_URI));
                videoList.add(video);
            }
            //System.out.println("======="+videoList.get(0).getImageUri());
            return videoList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return videoList;
    }

    public Video getVideoById(String id)
    {
        Video video = null;
        NetTool netTool = new NetTool();
        String requsetBody = Parameter.K_FUNCTION+"="+ Parameter.V_FUNCTION_GET_VIDEO_BY_ID+"&"+Parameter.K_ID+"="+id;
        try {
            String jsonStr = netTool.postRequest(Parameter.GET_VIDEO_URL, requsetBody);
            JSONObject jsonObject = new JSONObject(jsonStr);
            video = new Video();
            video.setId(jsonObject.getString(Parameter.K_ID));
            video.setImageUri(jsonObject.getString(Parameter.K_IMAGE_URI));
            video.setVideoUri(jsonObject.getString(Parameter.K_VIDEO_URI));
            //System.out.println("requestBody"+requsetBody+"\nVideoTool getVideoById"+jsonStr);
            return video;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return video;
    }

    public boolean deleteVideoById(String id)
    {
        NetTool netTool = new NetTool();
        String requsetBody = Parameter.K_FUNCTION+"="+ Parameter.V_FUNCTION_DELETE_VIDEO_BY_ID+"&"+Parameter.K_ID+"="+id;
        try {
            String jsonStr = netTool.postRequest(Parameter.GET_VIDEO_URL, requsetBody);
            //JSONObject jsonObject = new JSONObject(jsonStr);
            System.out.println("requestBody"+requsetBody+"\nVideoTool deleteVideoById"+jsonStr);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean uploadVideo(Video video, Context con){
        NetTool netTool = new NetTool();
        DataBaseTool dataBaseTool = new DataBaseTool();
        try {
            String result = NetTool.uploadFile(Parameter.UPLOAD_VIDEO_URL, video.getVideoUri()).trim();
            Log.d(TAG, result+Parameter.UPLOAD_SUCCESS);
            if(result.equals(Parameter.UPLOAD_SUCCESS)){
                result = NetTool.uploadFile(Parameter.UPLOAD_IMAGE_URL,video.getImageUri()).trim();
                Log.d(TAG, result);
                if(result.equals(Parameter.UPLOAD_SUCCESS)){
                    String requsetBody = Parameter.K_FUNCTION+"="+ Parameter.V_FUNCTION_INSERT_VIDEO+"&"+Parameter.K_ID+"="+video.getId()
                            +"&"+Parameter.K_USERNAMEM+"="+video.getUsername()+"&"+Parameter.K_VIDEO_URI+"="+video.getVideoUri()
                            +"&"+Parameter.K_IMAGE_URI+"="+video.getImageUri();
                    result = netTool.postRequest(Parameter.GET_VIDEO_URL, requsetBody).trim();
                    Log.d(TAG, result);
                    if(result.equals(Parameter.UPLOAD_SUCCESS)){
                        dataBaseTool.deleteVideoById(con, video.getId());
                    }else Log.d(TAG, "insert video failed");
                }else Log.d(TAG, "upload image failed");

            }else Log.d(TAG, "upload video failed");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

}
