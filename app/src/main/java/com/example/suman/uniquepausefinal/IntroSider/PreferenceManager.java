package com.example.suman.uniquepausefinal.IntroSider;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by suman on 7/27/2017.
 */

public class PreferenceManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    //shared pref mode
    int PRIVATE_MODE = 0;

    //shared preference file name
    private static final String PREF_NAME = "intro_slider_welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstLaunch";

    public PreferenceManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstLaunch(boolean isFirstTime){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
