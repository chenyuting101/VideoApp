package com.cs.group.tool;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cs.group.com.cs.group.entity.Video;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by chenyuting on 12/2/16.
 */

public class NetTool {
    public static final String BASE_URL =
            "http://120.24.251.94/3w/jalan/forestore/module/";
    /**
     *
     * @param url 发送请求的URL
     * @return 服务器响应字符串
     * @throws Exception
     */
    public String getRequest(final String url)
            throws Exception
    {
        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>()
                {
                    @Override
                    public String call() throws Exception
                    {
                        return "";
                    }
                });
        new Thread(task).start();
        return task.get();
    }

    /**
     * @param requesturl 发送请求的URL
     * @param  reqBody 请求体
     * @return 服务器响应字符串
     * @throws Exception
     */
    public String postRequest(final String requesturl
            , final String reqBody)throws Exception
    {
        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>()
                {
                    @Override
                    public String call() throws Exception
                    {
                        System.setProperty("http.proxyHost", "gatekeeper.cs.hku.hk");
                        System.setProperty("http.proxyPort", "1080");
                        URL url = null;//请求的URL地址
                        HttpURLConnection conn = null;
                        String requestHeader = null;//请求头
                        byte[] requestBody = null;//请求体
                        String responseHeader = null;//响应头
                        byte[] responseBody = null;//响应体

                        url = new URL(requesturl);
                        conn = (HttpURLConnection) url.openConnection();
                        //通过setRequestMethod将conn设置成POST方法
                        conn.setRequestMethod("POST");
                        //调用conn.setDoOutput()方法以显式开启请求体
                        conn.setDoOutput(true);
                        conn.setUseCaches(false);
                        //用setRequestProperty方法设置一个自定义的请求头:action，由于后端判断
                        //conn.setRequestProperty("action", NETWORK_POST_KEY_VALUE);
                        //获取请求头
                        requestHeader = getReqeustHeader(conn);
                        //获取conn的输出流
                        OutputStream os = conn.getOutputStream();
                        //获取两个键值对name=孙群和age=27的字节数组，将该字节数组作为请求体
                        requestBody = new String(reqBody).getBytes("UTF-8");
                        //将请求体写入到conn的输出流中
                        os.write(requestBody);
                        //记得调用输出流的flush方法
                        os.flush();
                        //关闭输出流
                        os.close();
                        //当调用getInputStream方法时才真正将请求体数据上传至服务器
                        InputStream is = conn.getInputStream();
                        //获得响应体的字节数组
                        responseBody = getBytesByInputStream(is);
                        //获得响应头
                        responseHeader = getResponseHeader(conn);
                        if (conn.getResponseCode() != 200)    //从Internet获取网页,发送请求,将网页以流的形式读回来
                            throw new RuntimeException("请求url失败");


                        return getStringByBytes(responseBody);
                    }
                });
        new Thread(task).start();
        return task.get();
    }

    //读取请求头
    private String getReqeustHeader(HttpURLConnection conn) {
        //https://github.com/square/okhttp/blob/master/okhttp-urlconnection/src/main/java/okhttp3/internal/huc/HttpURLConnectionImpl.java#L236
        Map<String, List<String>> requestHeaderMap = conn.getRequestProperties();
        Iterator<String> requestHeaderIterator = requestHeaderMap.keySet().iterator();
        StringBuilder sbRequestHeader = new StringBuilder();
        while (requestHeaderIterator.hasNext()) {
            String requestHeaderKey = requestHeaderIterator.next();
            String requestHeaderValue = conn.getRequestProperty(requestHeaderKey);
            sbRequestHeader.append(requestHeaderKey);
            sbRequestHeader.append(":");
            sbRequestHeader.append(requestHeaderValue);
            sbRequestHeader.append("\n");
        }
        return sbRequestHeader.toString();
    }

    //读取响应头
    private String getResponseHeader(HttpURLConnection conn) {
        Map<String, List<String>> responseHeaderMap = conn.getHeaderFields();
        int size = responseHeaderMap.size();
        StringBuilder sbResponseHeader = new StringBuilder();
        for(int i = 0; i < size; i++){
            String responseHeaderKey = conn.getHeaderFieldKey(i);
            String responseHeaderValue = conn.getHeaderField(i);
            sbResponseHeader.append(responseHeaderKey);
            sbResponseHeader.append(":");
            sbResponseHeader.append(responseHeaderValue);
            sbResponseHeader.append("\n");
        }
        return sbResponseHeader.toString();
    }

    //根据字节数组构建UTF-8字符串
    private String getStringByBytes(byte[] bytes) {
        String str = "";
        try {
            str = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    //从InputStream中读取数据，转换成byte数组，最后关闭InputStream
    private byte[] getBytesByInputStream(InputStream is) {
        byte[] bytes = null;
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos);
        byte[] buffer = new byte[1024 * 8];
        int length = 0;
        try {
            while ((length = bis.read(buffer)) > 0) {
                bos.write(buffer, 0, length);
            }
            bos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }

    /**
     * if the network is available, return true, else return false
     * @param context
     * @return
     */
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}