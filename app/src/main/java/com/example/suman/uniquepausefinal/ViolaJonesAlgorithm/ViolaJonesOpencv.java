package com.example.suman.uniquepausefinal.ViolaJonesAlgorithm;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.example.suman.uniquepausefinal.ServiceConnectionForCamerwAndFaceDetection.CameraAndFaceDetectionService;

/**
 * Created by suman on 5/31/2017.
 */

public class ViolaJonesOpencv {
    
    CameraAndFaceDetectionService activity;
    String choose;
    Activity chactivity;

    FaceDetectionFromViolaJonesAlgorithm faceDetectionFromViolaJonesAlgorithm;
    
    public ViolaJonesOpencv(CameraAndFaceDetectionService cameraAndFaceDetectionService, String choose){//, Activity chActivity) {
        activity = cameraAndFaceDetectionService;
        this.choose = choose;
        //this.chactivity = chActivity;
    }

    public void startOpencvDetection() {
        if(choose.equals("opencv_face")) {

            faceDetectionFromViolaJonesAlgorithm = new FaceDetectionFromViolaJonesAlgorithm(activity, choose);//, chactivity);
            faceDetectionFromViolaJonesAlgorithm.detectFaceDetectionFromViolaJones();
//            Intent faceDetectionIntent = new Intent(activity.getApplicationContext(), FaceDetectionFromViolaJonesAlgorithm.class);
//            faceDetectionIntent.putExtra("choose",choose);
//            activity.startActivity(faceDetectionIntent);

        }else if(choose.equals("opencv_eye")){

            Toast.makeText(activity.getApplicationContext(), choose.toString(), Toast.LENGTH_SHORT).show();

        }

    }

    public void destroyOpencvDetection(){
        faceDetectionFromViolaJonesAlgorithm.releaseJavaCameraView();
    }

}
