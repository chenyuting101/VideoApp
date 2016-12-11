package com.cs.group.tool;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.cs.group.com.cs.group.entity.Video;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by chenyuting on 12/2/16.
 */

public class NetTool {
    //private static final String path = "/mnt/sdcard/Desert.jpg";
    //private String uploadUrl = "http://192.168.1.102:8080/Android/testupload.php";
    private String TAG = "NetTool";
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

    public String uploadFileByCommonPost(final String uploadUrl, final String path) throws Exception {
        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>()
                {
                    @Override
                    public String call() throws Exception
                    {
                        System.setProperty("http.proxyHost", "gatekeeper.cs.hku.hk");
                        System.setProperty("http.proxyPort", "1080");
                        String end = "\r\n";
                        String twoHyphens = "--";
                        String boundary = "******";
                        URL url = new URL(uploadUrl);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url
                                .openConnection();
                        httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
                        // 允许输入输出流
                        httpURLConnection.setDoInput(true);
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setUseCaches(false);
                        // 使用POST方法
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                        httpURLConnection.setRequestProperty("Charset", "UTF-8");
                        httpURLConnection.setRequestProperty("Content-Type",
                                "multipart/form-data;boundary=" + boundary);

                        DataOutputStream dos = new DataOutputStream(
                                httpURLConnection.getOutputStream());
                        dos.writeBytes(twoHyphens + boundary + end);
                        dos.writeBytes("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
                                + path.substring(path.lastIndexOf("/") + 1) + "\"" + end);
                        dos.writeBytes(end);

                        FileInputStream fis = new FileInputStream(path);
                        byte[] buffer = new byte[8192]; // 8k
                        int count = 0;
                        // 读取文件
                        while ((count = fis.read(buffer)) != -1) {
                            dos.write(buffer, 0, count);
                        }
                        fis.close();
                        dos.writeBytes(end);
                        dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
                        dos.flush();
                        InputStream is = httpURLConnection.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is, "utf-8");
                        BufferedReader br = new BufferedReader(isr);
                        String result = br.readLine();
                        Log.i(TAG, result);
                        dos.close();
                        is.close();
                        return result;
                    }
                });
        new Thread(task).start();
        return task.get();
    }

    public static String uploadFile(String actionUrl, String uploadFilePaths) {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        DataOutputStream ds = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        try {
            // 统一资源
            URL url = new URL(actionUrl);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpURLConnection.setDoInput(true);
            // 设置是否向httpUrlConnection输出
            httpURLConnection.setDoOutput(true);
            // Post 请求不能使用缓存
            httpURLConnection.setUseCaches(false);
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("POST");
            // 设置字符编码连接参数
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 设置请求内容类型
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            // 设置DataOutputStream
            ds = new DataOutputStream(httpURLConnection.getOutputStream());
            //for (int i = 0; i < uploadFilePaths.length; i++) {
                String uploadFile = uploadFilePaths;//uploadFilePaths[i];
                String filename = uploadFile.substring(uploadFile.lastIndexOf("//") + 1);
                ds.writeBytes(twoHyphens + boundary + end);
                ds.writeBytes("Content-Disposition: form-data; " + "name=\"file"  + "\";filename=\"" + filename
                        + "\"" + end);
            Log.d("NetTool uploadFile", filename);
                ds.writeBytes(end);

                FileInputStream fStream = new FileInputStream(uploadFile);
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                int length = -1;
                while ((length = fStream.read(buffer)) != -1) {
                    ds.write(buffer, 0, length);
                }
                ds.writeBytes(end);
                /* close streams */
                fStream.close();

            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            /* close streams */
            ds.flush();
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception(
                        "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                tempLine = null;
                resultBuffer = new StringBuffer();
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (ds != null) {
                try {
                    ds.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return resultBuffer.toString();
        }
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