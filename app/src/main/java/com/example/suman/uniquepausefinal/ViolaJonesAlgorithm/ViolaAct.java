package com.example.suman.uniquepausefinal.ViolaJonesAlgorithm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.suman.uniquepausefinal.R;

public class ViolaAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viola);
        Toast.makeText(getApplicationContext(),"Opencv Eye",Toast.LENGTH_LONG).show();
    }
}
