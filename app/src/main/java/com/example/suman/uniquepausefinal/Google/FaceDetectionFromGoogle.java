package com.example.suman.uniquepausefinal.Google;

import android.graphics.Bitmap;
import android.util.SparseArray;
import android.widget.Toast;

import com.example.suman.uniquepausefinal.ServiceConnectionForCamerwAndFaceDetection.CameraAndFaceDetectionService;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

/**
 * Created by suman on 5/27/2017.
 */

public  class FaceDetectionFromGoogle {

    CameraAndFaceDetectionService activity;
    Bitmap bitmap;
    String choose;

    //TODO Implementing Face Detector
    FaceDetector faceDetector;

    //TODO play or pause
    static String playOrPause = null;

    public FaceDetectionFromGoogle(CameraAndFaceDetectionService activity, Bitmap bitmap, String choose) {
        this.activity = activity;
        this.bitmap = bitmap;
        this.choose = choose;

       // Toast.makeText(activity,choose,Toast.LENGTH_LONG).show();
    }

    public void detectFaceDetectionFromGoogle() {
        //Bitmap tempBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);

        faceDetector = new FaceDetector.Builder(activity.getApplicationContext())
                                        .setTrackingEnabled(true)
                                        .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                                        .setMode(FaceDetector.FAST_MODE)
                                        .build();

        Frame frame = new Frame.Builder().setBitmap(bitmap).build();

        //TODO getting face points
        SparseArray<Face> array = faceDetector.detect(frame);

        //TODO If user selects face then this below will run
        //TODO not complete yet
        if(choose.equals("google_face")){
            //TODO If user selects face then this below will run
            //TODO not complete yet
            if(array.size()>0){
                playOrPause = "play";
               // Toast.makeText(activity,array.toString(), Toast.LENGTH_LONG).show();
            }else {
                playOrPause = "pause";
               // Toast.makeText(activity,array.toString(),Toast.LENGTH_LONG).show();
            }
        }
    }

    public void destroyFaceDetectionFromGoogle(){
        faceDetector.release();
    }

    public static String getPlayOrPause(){
        return playOrPause;
    }
}
