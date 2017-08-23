package com.example.suman.uniquepausefinal.Google;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;
import com.example.suman.uniquepausefinal.ServiceConnectionForCamerwAndFaceDetection.CameraAndFaceDetectionService;

import static android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK;
import static android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT;

/**
 * Created by suman on 5/27/2017.
 */

public class UniquePauseCamera {

    private Camera camera;  //a variable to control the camera
    private Camera.Parameters parameters;   //the camera parameters
    private SurfaceHolder sHolder;  //a surface holder
    private SurfaceView sv;
    CameraAndFaceDetectionService activity;
    String choose;

    FaceDetectionFromGoogle faceDetectionFromGoogle = null;
    EyeDetectionFromGoogle eyeDetectionFromGoogle = null;



    //TODO take that image in bitmap form
    Bitmap  bitmap, bit;

    SurfaceHolder.Callback b = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

            if (camera != null) {
                camera.stopPreview();
                camera.setPreviewCallback(null);
                camera.release();
                camera = null;
                Toast.makeText(activity,"Camera Destroyed",Toast.LENGTH_SHORT).show();
            }
        }
    };

    public UniquePauseCamera(CameraAndFaceDetectionService cameraAndFaceDetectionService, String choose) {
        activity = cameraAndFaceDetectionService;
        this.choose = choose;


    }

    public boolean isChecking() {
        boolean check;
       if(camera != null){
           check = true;
       }else {
           check = false;
       }
       return check;
    }

    long begin, end, calculated;

    public void startCameraCaptureAndSurfaceView(){
        begin = System.nanoTime();
        //TODO opening front camera
        try {
            camera = Camera.open(CAMERA_FACING_FRONT);
        }catch (Exception e){
            //Toast.makeText(activity,e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }

        //TODO making surface view for camera
        sv = new SurfaceView(activity);
        try {
            //camera.lock();
            camera.setPreviewDisplay(sv.getHolder());
            parameters = camera.getParameters();
            //TODO set camera parameters
            camera.setParameters(parameters);
            camera.startPreview();

            //TODO taking picture
            try {
                camera.takePicture(null, null, call);
//                camera.stopPreview();
//                camera.release();
//                camera = null;
                //Thread.sleep(500);
            }catch (Exception e1){
                //Toast.makeText(activity,"Could not capture",Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        //Toast.makeText(activity,bitmap.toString(),Toast.LENGTH_LONG).show();

        //TODO Get a surface
        sHolder = sv.getHolder();
        //TODO tells Android that this surface will have its data constantly replaced
        sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);



    }


    //TODO getting the picture that is taken by camera
    Camera.PictureCallback call = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            try {
                bitmap = BitmapFactory.decodeByteArray(data,0,data.length);

                if(choose.equals("google_face")){

                    faceDetectionFromGoogle = new FaceDetectionFromGoogle(activity, bitmap, choose);
                    faceDetectionFromGoogle.detectFaceDetectionFromGoogle();

                }else if(choose.equals("google_eye")){

                    eyeDetectionFromGoogle = new EyeDetectionFromGoogle(activity, bitmap, choose);
                    eyeDetectionFromGoogle.detectEyeDetectionFromGoogle();
                    //Toast.makeText(activity,"Construction is going on",Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(activity,bitmap.toString(),Toast.LENGTH_LONG).show();
                end = System.nanoTime();

                calculated = end - begin;

                Log.v("GoogleDetection",choose+" "+begin+" "+end+" "+calculated);
                startCameraCaptureAndSurfaceView();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };


    public void destroyCameraCaptureAndSurfaceView(){
        b.surfaceDestroyed(sHolder);

        //sHolder = null;
    }

}
