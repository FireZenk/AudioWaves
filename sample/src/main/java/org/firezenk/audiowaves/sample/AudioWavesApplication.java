package org.firezenk.audiowaves.sample;

import android.app.Application;

import com.karumi.dexter.Dexter;

/**
 * Created by alejandrohall on 13/03/16.
 */
public class AudioWavesApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Dexter.initialize(this);
    }
}
