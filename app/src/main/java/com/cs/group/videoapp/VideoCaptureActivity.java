package com.cs.group.videoapp;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.cs.group.tool.DataBaseTool;
import com.cs.group.tool.Parameter;
import com.cs.group.tool.SharedPreferenceTool;

public class VideoCaptureActivity extends AppCompatActivity {

    private static final String TAG = VideoCaptureActivity.class.getSimpleName();

    private static final int VIDEO_CAPTURE_REQUEST = 1111;
    private static final int VIDEO_CAPTURE_PERMISSION = 2222;

    private String videoUri;
    private String imgUri;
    private String mediaID;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_capture);

        Log.d(TAG, "************************************** enter create...");

        ArrayList<String> permissions = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(VideoCaptureActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CAMERA);
        }
        if (ContextCompat.checkSelfPermission(VideoCaptureActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(VideoCaptureActivity.this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.RECORD_AUDIO);
        }
        if (ContextCompat.checkSelfPermission(VideoCaptureActivity.this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.INTERNET);
        }

        if(permissions.size() > 0) {
            String[] permiss = permissions.toArray(new String[0]);

            ActivityCompat.requestPermissions(VideoCaptureActivity.this, permiss,
                    VIDEO_CAPTURE_PERMISSION);
        } else {
            StartVideoCapture();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VIDEO_CAPTURE_REQUEST && resultCode == RESULT_OK) {

            Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(videoUri, MediaStore.Video.Thumbnails.MINI_KIND);

            File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "myVideoApp" + File.separator + "Thumbnails");
            // ("/mnt/sdcard" + File.separator + "myVideoApp" + File.separator + "Thumbnails");
            //(Environment.getExternalStorageDirectory().getPath() + File.separator + "myVideoApp" + File.separator + "Thumbnails");

            if (! mediaStorageDir.exists()) {
                if(! mediaStorageDir.mkdirs()){
                    Log.e(TAG, "Failed to create directory.");
                    return;
                }
            }

            mediaStorageDir.mkdirs();

            FileOutputStream fOutputStream;

            imgUri = mediaStorageDir.getPath() + File.separator + mediaID + ".png";

            File file = new File(imgUri);

            if (file.exists()) {
                file.delete();
            }

            try {
                fOutputStream = new FileOutputStream(file);

                bmThumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOutputStream);

                fOutputStream.flush();
                fOutputStream.close();

                //MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
                return;
            }
            DataBaseTool dataBaseTool = new DataBaseTool();

            dataBaseTool.insertVideo(VideoCaptureActivity.this, mediaID, imgUri, videoUri, username, SharedPreferenceTool.read(Parameter.K_IS_AUTO_UPLOAD, VideoCaptureActivity.this));
            if(SharedPreferenceTool.read(Parameter.K_IS_AUTO_UPLOAD, VideoCaptureActivity.this).equals(Parameter.V_AUTO_UPLOAD)){
                new AlertDialog.Builder(VideoCaptureActivity.this)
                        .setMessage("This video is added to upload list!\nVideo Id: "+mediaID)
                        .setNeutralButton("OK", null)
                        .show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == VIDEO_CAPTURE_PERMISSION) {
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                StartVideoCapture();
            }
            else {
                // Your app will not have this permission. Turn off all functions
                // that require this permission or it will force close like your
                // original question
            }
        }
    }

    private void StartVideoCapture() {
        Uri viduri = getOutputMediaFileUri();

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, viduri);
        //intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        //intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
        //intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, (long) (4 * 1024 * 1024));

        startActivityForResult(intent, VIDEO_CAPTURE_REQUEST);
    }

    private Uri getOutputMediaFileUri() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        if (isExternalStorageAvailable()) {
            // get the Uri

            //1. Get the external storage directory
            File mediaStorageDir =new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "myVideoApp" + File.separator + "Videos");
            //new File("/mnt/sdcard" + File.separator + "myVideoApp" + File.separator + "Videos");
            // (Environment.getExternalStorageDirectory().getPath() + File.separator + "myVideoApp" + File.separator + "Videos");

            //2. Create our subdirectory
            if (! mediaStorageDir.exists()) {
                if(! mediaStorageDir.mkdirs()){
                    Log.e(TAG, "Failed to create directory.");
                    return null;
                }
            }

            //3. Create a file name
            //4. Create the file
            File mediaFile;
            Date now = new Date();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);

            String path = mediaStorageDir.getPath() + File.separator;

            username = SharedPreferenceTool.read(Parameter.K_USERNAMEM, VideoCaptureActivity.this);

            mediaID = timestamp + "_" + username;
            videoUri = path + mediaID + ".mp4";
            mediaFile = new File(videoUri);

            Log.d(TAG, "File: " + Uri.fromFile(mediaFile));
            //5. Return the file's URI
            return Uri.fromFile(mediaFile);
        } else {
            return null;
        }
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        } else {
            return false;
        }
    }
}
