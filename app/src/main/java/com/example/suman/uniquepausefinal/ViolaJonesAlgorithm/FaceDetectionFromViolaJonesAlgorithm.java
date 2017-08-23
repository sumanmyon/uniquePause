package com.example.suman.uniquepausefinal.ViolaJonesAlgorithm;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.suman.uniquepausefinal.MainActivity;
import com.example.suman.uniquepausefinal.R;
import com.example.suman.uniquepausefinal.ServiceConnectionForCamerwAndFaceDetection.CameraAndFaceDetectionService;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import static org.opencv.android.CameraBridgeViewBase.CAMERA_ID_FRONT;

public class FaceDetectionFromViolaJonesAlgorithm implements CameraBridgeViewBase.CvCameraViewListener2{

    String choose;
    CameraAndFaceDetectionService activity;
    static JavaCameraView javaCameraView;
    Mat rbga;

    static int playOrPauseinIntForm;
    static String playOrPause = null;

    static {
        System.loadLibrary("MyLibs");
    }

    public FaceDetectionFromViolaJonesAlgorithm(){

    }

    public FaceDetectionFromViolaJonesAlgorithm(CameraAndFaceDetectionService activity, String choose){
        this.choose = choose;
        this.activity = activity;

    }

    BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(activity) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case BaseLoaderCallback.SUCCESS:
                    javaCameraView.enableView();
                    Log.d("voilaDetect", "run 5");
                    break;
                default:
                    super.onManagerConnected(status);
                    Log.d("voilaDetect", "run 6");
                    break;
            }
        }
    };

    public void detectFaceDetectionFromViolaJones() {
        if(choose.equals("opencv_face" )){
            Toast.makeText(activity.getApplicationContext(), choose.toString(), Toast.LENGTH_SHORT).show();
            javaCameraView = new JavaCameraView(activity,CAMERA_ID_FRONT);
            javaCameraView.setMaxFrameSize(300,300);
            javaCameraView.setVisibility(View.VISIBLE);
            javaCameraView.setCvCameraViewListener(this);
            Toast.makeText(activity.getApplicationContext(), javaCameraView.toString(), Toast.LENGTH_SHORT).show();

            Log.d("voilaDetect", "run 1");
            onResume();
        }
    }

    private void onResume() {
        if(OpenCVLoader.initDebug()){
            Log.d("voilaDetect", "run 2");
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }else {
            Log.d("voilaDetect", "run 3");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9,activity,baseLoaderCallback);
        }
    }


    @Override
    public void onCameraViewStarted(int width, int height) {
        rbga = new Mat(height, width, CvType.CV_8UC4);
        Log.d("voilaDetect", "run 4");

    }

    @Override
    public void onCameraViewStopped() {
        rbga.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        rbga = inputFrame.rgba();
        Log.d("voilaDetect", rbga.toString());
        final int i = OpencvClass.faceDetection(rbga.getNativeObjAddr());
        playOrPauseinIntForm = i;
        Log.d("voilaDetect",String.valueOf(i));

        return null;
    }


    public static void releaseJavaCameraView(){
        if(javaCameraView.isEnabled()){
            javaCameraView.disableView();
        }
    }

    public static String getPlayOrPause(){
        if (playOrPauseinIntForm == 0){
            playOrPause = "pause";
        }
        if (playOrPauseinIntForm == 1){
            playOrPause = "play";
        }
        return playOrPause;
    }
}
