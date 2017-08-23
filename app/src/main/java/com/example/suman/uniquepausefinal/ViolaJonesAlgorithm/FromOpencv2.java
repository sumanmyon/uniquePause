package com.example.suman.uniquepausefinal.ViolaJonesAlgorithm;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.suman.uniquepausefinal.MainActivity;
import com.example.suman.uniquepausefinal.R;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Created by suman on 7/27/2017.
 */

public class FromOpencv2 implements CameraBridgeViewBase.CvCameraViewListener2 {

    Activity activity;
    String choose;

    static JavaCameraView javaCameraView;
    Mat mRbga;

    static int seektime;
    static VideoView videoView;

    static {
        System.loadLibrary("MyLibs");
    }

    public FromOpencv2(MainActivity mainActivity, String chooseFaceOrEyeOrNone, VideoView videoView, int seektime) {
        this.activity = mainActivity;
        this.choose = chooseFaceOrEyeOrNone;
        this.videoView = videoView;
        this.seektime = seektime;
        onCreate();
    }


    private void onCreate() {
        if(choose == "opencv_face" || choose == "opencv_eye") {
            javaCameraView = (JavaCameraView) activity.findViewById(R.id.javaCameraView);
            javaCameraView.setVisibility(View.VISIBLE);
            javaCameraView.setCvCameraViewListener(this);

            Toast.makeText(activity,javaCameraView.toString(),Toast.LENGTH_LONG).show();

            onResume();
        }else{
            Toast.makeText(activity,"",Toast.LENGTH_SHORT).show();
        }
    }

    private void onResume() {
        if(OpenCVLoader.initDebug()){
            mbaseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }else {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9,activity,mbaseLoaderCallback);
        }
    }

    BaseLoaderCallback mbaseLoaderCallback = new BaseLoaderCallback(activity) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case BaseLoaderCallback.SUCCESS:
                    javaCameraView.enableView();
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };


    @Override
    public void onCameraViewStarted(int width, int height) {
        mRbga = new Mat(height,width, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        mRbga.release();
    }

    static String playOrPause;
    long begin, end, calculated;

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRbga = inputFrame.rgba();
        begin = System.nanoTime();
        final int i = OpencvClass.faceDetection(mRbga.getNativeObjAddr());

        end = System.nanoTime();
        calculated = end - begin;

        Log.v("OpencvDetection",choose+" "+begin+" "+end+" "+calculated);

        Log.d("Working....",String.valueOf(i));
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (i == 0){
                    playOrPause = "pause";
                    videoView.pause();
                    seektime = videoView.getCurrentPosition();
                }else{
                    playOrPause = "play";
                    videoView.start();
                    //TODO play from the video that where it is paused
                    if(seektime>0) {
                        videoView.seekTo(seektime);
                    }
                    seektime = 0;
                }
            }
        });
        return null;
    }

    public static void releaseJavaCameraView(){
        if(javaCameraView.isEnabled()){
            javaCameraView.disableView();
        }
    }

    public static int getSeekTime(){
        videoView.pause();
        //TODO get current position of time you pause
        int seekTtime = videoView.getCurrentPosition();
        return seekTtime;
    }


    public static String getPlayOrPause(){
        return playOrPause;
    }

    public void resume(int seekTime) {
        //TODO play from the video thae is paused
        videoView.start();
        if (seekTime > 0) {
            videoView.seekTo(seekTime);
        }
        seektime = 0;
    }
}

