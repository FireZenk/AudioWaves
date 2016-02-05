package org.firezenk.audiowaves.sample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.firezenk.audiowaves.Visualizer;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            setContentView(R.layout.activity_main);
            ((Visualizer) findViewById(R.id.visualizer)).startListening();
        }
        else {
            setContentView(R.layout.no_mic);
        }
    }

}
