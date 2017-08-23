package com.example.suman.uniquepausefinal.ServiceConnectionForCamerwAndFaceDetection;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.widget.Toast;

import com.example.suman.uniquepausefinal.Google.EyeDetectionFromGoogle;
import com.example.suman.uniquepausefinal.Google.FaceDetectionFromGoogle;
import com.example.suman.uniquepausefinal.Google.UniquePauseCamera;
import com.example.suman.uniquepausefinal.ViolaJonesAlgorithm.FaceDetectionFromViolaJonesAlgorithm;
import com.example.suman.uniquepausefinal.ViolaJonesAlgorithm.ViolaAct;
import com.example.suman.uniquepausefinal.ViolaJonesAlgorithm.ViolaJonesOpencv;

import org.opencv.android.JavaCameraView;

public class CameraAndFaceDetectionService extends Service {
    //TODO creating Bundle for to get values from intent
    Bundle bundle;
    static String choose;
    static Activity chActivity;

    UniquePauseCamera camera;
    ViolaJonesOpencv violaJonesOpencv;

//
//public CameraAndFaceDetectionService(Activity a){
//    chActivity = a;
//}

    @Override
    public void onCreate() {
        super.onCreate();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(),"Service Started",Toast.LENGTH_SHORT).show();
        bundle = intent.getExtras();
        choose = bundle.getString("choose");
        //chActivity = bundle.getParcelable("activity");
        //Toast.makeText(getApplicationContext(),choose.toString(),Toast.LENGTH_SHORT).show();

        //TODO which you have selected google type or opencv type
        if(choose.equals("google_face") || choose.equals("google_eye")){

            //TODO staring Camera view
            camera = new UniquePauseCamera(CameraAndFaceDetectionService.this, choose);
            //camera.isChecking();
            camera.startCameraCaptureAndSurfaceView();

        }else if(choose.equals("opencv_face")){// || choose.equals("opencv_eye")){
            violaJonesOpencv = new ViolaJonesOpencv(CameraAndFaceDetectionService.this, choose);//, chActivity);
            violaJonesOpencv.startOpencvDetection();
        }
        else if(choose.endsWith("opencv_eye")){

//            Service myService;
//            myService = this;
//            new SubService(myService);
//TODO use above for sub class
            //Intent dialogIntent = new Intent(this, ViolaAct.class);
           // dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           // startActivity(dialogIntent);
        }
        return Service.START_NOT_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        for (ActivityManager.RunningAppProcessInfo pid : am.getRunningAppProcesses()) {
//            am.killBackgroundProcesses(pid.processName);
//        }
        //Process.killProcess(Process.myPid());
        if(choose.equals("google_face") || choose.equals("google_eye")){
            if(camera.isChecking()) {
                camera.destroyCameraCaptureAndSurfaceView();
            }
        }else if(choose.equals("opencv_face") || choose.equals("opencv_eye")){
            violaJonesOpencv.destroyOpencvDetection();
        }
        Toast.makeText(getApplicationContext(),"Service Destroyed",Toast.LENGTH_SHORT).show();

    }

    public static String getPlayOrPause(){
        String playOrPause = null;
        if(choose.equals("google_face") || choose.equals("google_eye")){
            if(choose.equals("google_face")){
                playOrPause = FaceDetectionFromGoogle.getPlayOrPause();
            }
            if(choose.equals("google_eye")){
                playOrPause = EyeDetectionFromGoogle.getPlayOrPause();
            }

        }
        else if(choose.equals("opencv_face") || choose.equals("opencv_eye")){
            if(choose.equals("opencv_face")){
                playOrPause = FaceDetectionFromViolaJonesAlgorithm.getPlayOrPause();
            }
            if(choose.equals("opencv_eye")){
                playOrPause = FaceDetectionFromViolaJonesAlgorithm.getPlayOrPause();
            }
        }
        return playOrPause;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    //TODO MyCameraService interface
    private IBinder mBinder = new MyRequestService();

    public class MyRequestService extends Binder {
        public CameraAndFaceDetectionService getService(){
            return CameraAndFaceDetectionService.this;
        }
    }
}
