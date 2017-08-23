package com.example.suman.uniquepausefinal.Google;

import android.graphics.Bitmap;
import android.util.SparseArray;
import android.widget.Toast;

import com.example.suman.uniquepausefinal.ServiceConnectionForCamerwAndFaceDetection.CameraAndFaceDetectionService;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

/**
 * Created by suman on 5/29/2017.
 */

public class EyeDetectionFromGoogle {
    CameraAndFaceDetectionService activity;
    Bitmap bitmap;
    String choose;

    //TODO Implementing Face Detector
    FaceDetector faceDetector;

    //TODO play or pause
    static String playOrPause = null;

    public EyeDetectionFromGoogle(CameraAndFaceDetectionService activity, Bitmap bitmap, String choose) {
        this.activity = activity;
        this.bitmap = bitmap;
        this.choose = choose;
    }

    public void detectEyeDetectionFromGoogle() {

        faceDetector = new FaceDetector.Builder(activity.getApplicationContext())
                .setTrackingEnabled(true)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE)
                .build();

        Frame frame = new Frame.Builder().setBitmap(bitmap).build();

        //TODO getting face points
        SparseArray<Face> array = faceDetector.detect(frame);

        //TODO If user selects eye then this below will run
        for(int i=0;i<array.size();i++){
            Face face =array.valueAt(i);
            detectLandmark(face);
        }

        if(isLeftEye==false|| isRightEye==false){
            playOrPause="pause";
            //Toast.makeText(activity,playOrPause.toString(), Toast.LENGTH_LONG).show();
            isLeftEye=false;
            isRightEye=false;
        }else{
            playOrPause="play";
            //Toast.makeText(activity,playOrPause.toString(), Toast.LENGTH_LONG).show();
            isLeftEye=false;
            isRightEye=false;
        }
    }

    private void detectLandmark(Face face) {
        for(Landmark landmark:face.getLandmarks()){
            drawonImageView(landmark.getType());
        }
    }

    boolean isRightEye,isLeftEye;
    private void drawonImageView(int type) {
        boolean left=false;
        boolean right=false;
        if(type==Landmark.LEFT_EYE){
            left=true;
            isLeftEye=left;

        }
        if(type== Landmark.RIGHT_EYE){
            right=true;
            isRightEye=right;
        }

    }

    public void destroyEyeDetectionFromGoogle(){
        faceDetector.release();
    }

    public static  String getPlayOrPause(){
        return playOrPause;
    }
}
