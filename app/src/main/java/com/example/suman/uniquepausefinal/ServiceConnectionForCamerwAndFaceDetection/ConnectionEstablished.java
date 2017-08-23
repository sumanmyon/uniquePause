package com.example.suman.uniquepausefinal.ServiceConnectionForCamerwAndFaceDetection;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcelable;
import android.widget.Toast;

import com.example.suman.uniquepausefinal.MainActivity;

import java.io.Serializable;

/**
 * Created by suman on 5/27/2017.
 */

public class ConnectionEstablished{
    //TODO getting main activity to this activity
    Activity activity;
    Context context;
    String chooseFaceOrEyeOrNul;
    Intent serviceIntent;

    boolean isConnected = false;

    ServiceConnection serviceConnection = null;
    CameraAndFaceDetectionService cameraAndFaceDetectionService;
    boolean isServiceBound;


    public ConnectionEstablished(Activity mainActivity, String chooseFaceOrEyeOrNull) {
        activity = mainActivity;
        //context = mainActivity;
        chooseFaceOrEyeOrNul = chooseFaceOrEyeOrNull;
    }

    public void startConnection(){
       // new CameraAndFaceDetectionService(activity);
        serviceIntent = new Intent(activity,CameraAndFaceDetectionService.class);
        serviceIntent.putExtra("choose",chooseFaceOrEyeOrNul);
        //serviceIntent.putExtra("activity", activity);

        activity.startService(serviceIntent);

    }

    public void destroyConnection(){
        serviceIntent = new Intent(activity,CameraAndFaceDetectionService.class);
        activity.stopService(serviceIntent);
    }

    public void bindService(){
        //TODO binding service to get values return by service
        Toast.makeText(activity.getApplicationContext(),"Bind Service",Toast.LENGTH_SHORT).show();
        if(serviceConnection == null){
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    CameraAndFaceDetectionService.MyRequestService binder = (CameraAndFaceDetectionService.MyRequestService) service;
                    cameraAndFaceDetectionService = binder.getService();
                    isServiceBound = true;
                    Toast.makeText(activity.getApplicationContext(),"Service Connected",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    serviceConnection = null;
                    isServiceBound = false;
                    Toast.makeText(activity.getApplicationContext(),"Service DisConnected",Toast.LENGTH_SHORT).show();
                }
            };
        }
        activity.bindService(serviceIntent, serviceConnection,activity.BIND_AUTO_CREATE);
    }

    public void unBindService(){
        //TODO unbinding service
        if(isServiceBound){
            activity.unbindService(serviceConnection);
            isServiceBound = false;
            Toast.makeText(activity.getApplicationContext(),"UnBind Service",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isServiceBound(){
//        if(isServiceBound){
//            isConnected = true;
//        }else {
//            isConnected = false;
//        }
        return isServiceBound;
    }
}
