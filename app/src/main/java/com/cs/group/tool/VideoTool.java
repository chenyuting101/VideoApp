package com.cs.group.tool;

import com.cs.group.com.cs.group.entity.Video;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ParameterMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyuting on 12/3/16.
 */

public class VideoTool {
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

}
