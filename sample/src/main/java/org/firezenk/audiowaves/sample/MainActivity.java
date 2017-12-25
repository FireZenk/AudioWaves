package org.firezenk.audiowaves.sample;

import android.Manifest;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.firezenk.audiowaves.Visualizer;

public class MainActivity extends AppCompatActivity {

    private Visualizer visualizer;
    private RelativeLayout rlMain;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        visualizer = (Visualizer) findViewById(R.id.visualizer);
        rlMain = (RelativeLayout) findViewById(R.id.rlMain);

        Dexter.checkPermission(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                visualizer.startListening();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Snackbar.make(rlMain, getString(R.string.permission_not_granted),Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }

        }, Manifest.permission.RECORD_AUDIO);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        visualizer.stopListening();
    }
}
