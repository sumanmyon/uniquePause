package com.example.suman.uniquepausefinal.SettingAndAboutUs;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.example.suman.uniquepausefinal.R;

public class Setting extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO REMOVE TITLE BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //TODO make activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //TODO set activity for this class
        setContentView(R.layout.activity_setting);

        toolbar = (Toolbar)findViewById(R.id.setting_toolBar);

        //TODO setting and showing ToolBar
        toolbar.setTitle(R.string.aboutUs);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setLogo(R.drawable.ic_video_library_white_24dp);
        setSupportActionBar(toolbar);
    }
}
