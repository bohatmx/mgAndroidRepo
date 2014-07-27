package com.boha.proximity.library;

import android.app.Application;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader;
import com.boha.proximity.volley.BohaVolley;
import com.estimote.sdk.BeaconManager;

/**
 * Created by aubreyM on 2014/07/12.
 */
public class ProxiApplication extends Application {

    @Override
    public void onCreate() {
        Log.i(LOG,"***************************** BeaconsApp starting...");
        beaconManager = new BeaconManager(getApplicationContext());
        imageLoader = BohaVolley.getImageLoader(getApplicationContext());

    }
    private BeaconManager beaconManager;

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public BeaconManager getBeaconManager() {
        return beaconManager;
    }
    static final String LOG = ProxiApplication.class.getName();
    ImageLoader imageLoader;
}
