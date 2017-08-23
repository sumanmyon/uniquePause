package com.example.suman.uniquepausefinal;

import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.suman.uniquepausefinal.GestureDetection.GestureDetection;
import com.example.suman.uniquepausefinal.ServiceConnectionForCamerwAndFaceDetection.CameraAndFaceDetectionService;
import com.example.suman.uniquepausefinal.ServiceConnectionForCamerwAndFaceDetection.ConnectionEstablished;
import com.example.suman.uniquepausefinal.ViolaJonesAlgorithm.FromOpencv2;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements GestureDetection.SimpleGestureListener {
    //TODO variables for Toolbar and AcionBar
    Toolbar toolbar;
    ActionBar actionBar;

    String name;  //getting absolute video name

    //TODO Buttons and TextView
    Button volumeButton, brightnessButton;
    TextView dataWillDisplay_textView;

    //TODO variables for video player
    VideoView videoView;
    MediaController mediaController;
    MainActivity activity;
    AudioManager audioManager;
    GestureDetection detector;
    int currentPosition;
    int currentVolume;
    Uri videoUri;
    String videoPath;
    static int seekTime;

    //TODO making object for ConnectionEstablished
    ConnectionEstablished connectionEstablished;

    //TODO This Flag is for choosing face or eye or null
    String chooseFaceOrEyeOrNull = null;

    FromOpencv2 fromOpencv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO REMOVE TITLE BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //TODO make activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //TODO set activity for this class
        setContentView(R.layout.activity_main);


        //TODO castings
        toolbar = (Toolbar)findViewById(R.id.mainActivity_toolBar);
        //volumeButton = (Button)findViewById(R.id.volumeButton);
       // brightnessButton = (Button)findViewById(R.id.brightnessButton);
        dataWillDisplay_textView = (TextView) findViewById(R.id.dataWillDisplay_textView);
        videoView = (VideoView)findViewById(R.id.videoView);
        mediaController = new MediaController(MainActivity.this);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        detector = new GestureDetection(this, this);

        //TODO getting video path from internal or external activity
        Bundle bundle = getIntent().getExtras();
        videoPath = bundle.getString("video");

        //TODO getting video name from the intent
        name = new File(videoPath).getName();

        //TODO setting and showing ToolBar

        toolbar.setTitle(name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setLogo(R.drawable.ic_video_library_white_24dp);
        setSupportActionBar(toolbar);
       // getSupportActionBar().hide();
        getSupportActionBar().show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //TODO Loading and start playing
        videoUri = Uri.parse(videoPath);
        getVideo(videoUri);
    }

    //TODO This below one method is for fetching required video
    private void getVideo(Uri videoUri) {
        mediaController.setAnchorView(videoView);
        mediaController.hide();
        //mediaController.show();
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(videoUri);

        if(chooseFaceOrEyeOrNull != null) {
            if (chooseFaceOrEyeOrNull == "google_face" || chooseFaceOrEyeOrNull == "google_eye") {
                connectionEstablished = new ConnectionEstablished(MainActivity.this, chooseFaceOrEyeOrNull);

                connectionEstablished.startConnection();
                connectionEstablished.bindService();

                if (playOrPauseThread != null) {
                    playOrPauseTimer.cancel();
                    playOrPauseThread.interrupt();
                    playOrPauseThread = null;
                }
                getPlayAndPauseState();
            }
            if(chooseFaceOrEyeOrNull == "opencv_face" || chooseFaceOrEyeOrNull == "opencv_eye"){
                fromOpencv2 = new FromOpencv2(this, chooseFaceOrEyeOrNull, videoView, seekTime);
            }
        }
        //TODO play from the video that is paused
        if(seekTime>0){
            videoView.seekTo(seekTime);
            videoView.start();
        }else {
            videoView.start();
        }
        seekTime = 0;
    }


    //TODO This below 4 method is for pause, destroy and restart camera and video
    @Override
    protected void onPause() {
        super.onPause();
        //TODO get current position of time you pause
        if(chooseFaceOrEyeOrNull != null){
            if(chooseFaceOrEyeOrNull == "google_face" || chooseFaceOrEyeOrNull == "google_eye") {
                connectionEstablished.unBindService();
                connectionEstablished.destroyConnection();
            }
            if(chooseFaceOrEyeOrNull == "opencv_face" || chooseFaceOrEyeOrNull == "opencv_eye"){
                fromOpencv2.releaseJavaCameraView();
            }
        }
        if (chooseFaceOrEyeOrNull == null ||chooseFaceOrEyeOrNull == "google_face" || chooseFaceOrEyeOrNull == "google_eye") {
            videoView.pause();
            //TODO get current position of time you pause
            seekTime = videoView.getCurrentPosition();
        } else if (chooseFaceOrEyeOrNull == "opencv_face" || chooseFaceOrEyeOrNull == "opencv_eye"){
            seekTime = fromOpencv2.getSeekTime();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO when service is connected then destroy service and then choose your type
        if(chooseFaceOrEyeOrNull != null){
            if(chooseFaceOrEyeOrNull == "google_face" || chooseFaceOrEyeOrNull == "google_eye") {
                connectionEstablished.unBindService();
                connectionEstablished.destroyConnection();
            }
            if(chooseFaceOrEyeOrNull == "opencv_face" || chooseFaceOrEyeOrNull == "opencv_eye"){
                fromOpencv2.releaseJavaCameraView();
            }
        }
        if(playOrPauseThread != null) {
            playOrPauseTimer.cancel();
            playOrPauseThread.interrupt();
        }
        chooseFaceOrEyeOrNull = null;
        //Process.killProcess(Process.myPid());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //TODO get current position of time you pause
        if(videoView.isPlaying()){
            videoView.pause();
        }
        //TODO when service is connected then destroy service and then choose your type
        if(chooseFaceOrEyeOrNull != null){
            if(chooseFaceOrEyeOrNull == "google_face" || chooseFaceOrEyeOrNull == "google_eye") {
                connectionEstablished.unBindService();
                connectionEstablished.destroyConnection();
            }
            if(chooseFaceOrEyeOrNull == "opencv_face" || chooseFaceOrEyeOrNull == "opencv_eye"){
                fromOpencv2.releaseJavaCameraView();
            }
        }
        if(playOrPauseThread != null) {
            playOrPauseTimer.cancel();
            playOrPauseThread.interrupt();
        }
        chooseFaceOrEyeOrNull = null;
    }

    //TODO this below three method is for ToogleBar, ActionBar and Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.google_and_opencv_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.none:
                //TODO when service is connected then destroy service and then choose your type
                if(chooseFaceOrEyeOrNull != null){
                    if(chooseFaceOrEyeOrNull == "google_face" || chooseFaceOrEyeOrNull == "google_eye") {
                        connectionEstablished.unBindService();
                        connectionEstablished.destroyConnection();
                    }
                    if(chooseFaceOrEyeOrNull == "opencv_face" || chooseFaceOrEyeOrNull == "opencv_eye"){
                        fromOpencv2.releaseJavaCameraView();
                    }
                }
                if(playOrPauseThread != null) {
                    playOrPauseTimer.cancel();
                    playOrPauseThread.interrupt();
                }
                if(chooseFaceOrEyeOrNull != null) {
                    if (chooseFaceOrEyeOrNull == "google_face" || chooseFaceOrEyeOrNull == "google_eye") {
                        videoView.pause();
                        //TODO get current position of time you pause
                        seekTime = videoView.getCurrentPosition();
                    } else if (chooseFaceOrEyeOrNull == "opencv_face" || chooseFaceOrEyeOrNull == "opencv_eye"){
                        seekTime = fromOpencv2.getSeekTime();
                        //fromOpencv2.resume(seekTime);
                        Toast.makeText(getApplicationContext(),String.valueOf(seekTime),Toast.LENGTH_LONG).show();
                    }

                    chooseFaceOrEyeOrNull = null;
                    Toast.makeText(getApplicationContext(),"None",Toast.LENGTH_LONG).show();

                    //TODO play from the video thae is paused
                    //videoView.resume();
                    videoView.start();
                    if (seekTime > 0) {
                        videoView.seekTo(seekTime);
                    }
                    seekTime = 0;
                }
                break;
            case R.id.google_face:
                //TODO when service is connected then destroy service and then choose your type
                if(chooseFaceOrEyeOrNull != null){
                    if(chooseFaceOrEyeOrNull == "google_face" || chooseFaceOrEyeOrNull == "google_eye") {
                        connectionEstablished.unBindService();
                        connectionEstablished.destroyConnection();
                    }
                    if(chooseFaceOrEyeOrNull == "opencv_face" || chooseFaceOrEyeOrNull == "opencv_eye"){
                        fromOpencv2.releaseJavaCameraView();
                    }
                }
                chooseFaceOrEyeOrNull = "google_face";
                connectionEstablished = new ConnectionEstablished(MainActivity.this, chooseFaceOrEyeOrNull);

                connectionEstablished.startConnection();
                connectionEstablished.bindService();

                if(playOrPauseThread != null){
                    playOrPauseTimer.cancel();
                    playOrPauseThread.interrupt();
                    playOrPauseThread = null;
                }
                getPlayAndPauseState();

                break;
            case R.id.google_eye:
                //TODO when service is connected then destroy service and then choose your type
                if(chooseFaceOrEyeOrNull != null){
                    connectionEstablished.unBindService();
                    connectionEstablished.destroyConnection();
                }
                chooseFaceOrEyeOrNull = "google_eye";
                connectionEstablished = new ConnectionEstablished(MainActivity.this, chooseFaceOrEyeOrNull);

                connectionEstablished.startConnection();
                connectionEstablished.bindService();

                if(playOrPauseThread != null){
                    playOrPauseTimer.cancel();
                    playOrPauseThread.interrupt();
                    playOrPauseThread = null;
                }
                getPlayAndPauseState();

                break;
            case R.id.opencv_face:
                //TODO when service is connected then destroy service and then choose your type
                if(chooseFaceOrEyeOrNull != null){
                    if(chooseFaceOrEyeOrNull == "google_face" || chooseFaceOrEyeOrNull == "google_eye") {
                        connectionEstablished.unBindService();
                        connectionEstablished.destroyConnection();
                    }
                    if(chooseFaceOrEyeOrNull == "opencv_face" || chooseFaceOrEyeOrNull == "opencv_eye"){
                        fromOpencv2.releaseJavaCameraView();
                    }
                }
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        chooseFaceOrEyeOrNull = "opencv_face";
                        fromOpencv2 = new FromOpencv2(MainActivity.this, chooseFaceOrEyeOrNull, videoView, seekTime);
                    }
                }, 5000);    //Specific time in milliseconds

                break;

            case R.id.opencv_eye:
                //TODO when service is connected then destroy service and then choose your type
                if(chooseFaceOrEyeOrNull != null){
                    if(chooseFaceOrEyeOrNull == "google_face" || chooseFaceOrEyeOrNull == "google_eye") {
                        connectionEstablished.unBindService();
                        connectionEstablished.destroyConnection();
                    }
                    if(chooseFaceOrEyeOrNull == "opencv_face" || chooseFaceOrEyeOrNull == "opencv_eye"){
                        fromOpencv2.releaseJavaCameraView();
                    }
                }
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        chooseFaceOrEyeOrNull =  "opencv_eye";
                        fromOpencv2 = new FromOpencv2(MainActivity.this, chooseFaceOrEyeOrNull, videoView, seekTime);
                    }
                }, 5000);    //Specific time in milliseconds
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    Timer playOrPauseTimer;
    Thread playOrPauseThread = null;
    //int i;
    private void getPlayAndPauseState() {
        playOrPauseThread = new Thread(new Runnable() {
            @Override
            public void run() {
            playOrPauseTimer = new Timer();
            playOrPauseTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                try {
                    if(connectionEstablished.isServiceBound()){
                        String playOrPause = "";
                        playOrPause = CameraAndFaceDetectionService.getPlayOrPause();
                        //TODO working here play or pause video
                        playOrPauseManager(playOrPause);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                }
            },0,50);        //TODO Scheduless the specified task for repeated fixed-delay executation,
                            //TODO beginging after the fixed delay
            }
        });
        playOrPauseThread.start();
    }

    private void playOrPauseManager(String playOrPause) {
        if(playOrPause != null){
            //Log.d("playOrPause", playOrPause);
        }

        if(playOrPause=="pause"){
            videoView.pause();
            //TODO get current position of time you pause
            seekTime = videoView.getCurrentPosition();
        }else if(playOrPause=="play" || playOrPause== null){
            videoView.start();
            //TODO play from the video that where it is paused
            if(seekTime>0) {
                videoView.seekTo(seekTime);
            }
            seekTime = 0;
        }else{

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!mediaController.isShowing()) {
            mediaController.show();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (!mediaController.isShowing()) {//!getSupportActionBar().isShowing()) {
                        //getSupportActionBar().show();
                        mediaController.hide();
                    }
                }
            }, 5000);    //Specific time in milliseconds
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class

        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    @Override
    public void onSwipe(int direction) {
        String str = "";

        switch (direction) {

            case GestureDetection.SWIPE_LEFT:

                currentPosition = videoView.getCurrentPosition();
                currentPosition = videoView.getCurrentPosition() - 10000;
                videoView.seekTo(currentPosition);
                str = "Swipe to 10 sec back";
                break;

            case GestureDetection.SWIPE_RIGHT:

                currentPosition = videoView.getCurrentPosition();
                currentPosition = videoView.getCurrentPosition() + 10000;
                videoView.seekTo(currentPosition);
                str = "Swipe to 10 sec forward";
                break;

            case GestureDetection.SWIPE_DOWN:

                currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume - 1, 0);
                str = "Volume = "+String.valueOf(currentVolume - 1);
                break;
            case GestureDetection.SWIPE_UP:

                currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume + 1, 0);
                str = "Volume = "+String.valueOf(currentVolume + 1);
                break;

        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
