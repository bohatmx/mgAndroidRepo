package com.boha.beacon.beaconsapp;

import android.app.Application;
import android.util.Log;

import com.estimote.sdk.BeaconManager;

/**
 * Created by aubreyM on 2014/07/12.
 */
public class BApp extends Application {

    @Override
    public void onCreate() {
        Log.i(LOG,"***************************** BeaconsApp starting...");
        beaconManager = new BeaconManager(getApplicationContext());

    }
    private BeaconManager beaconManager;

    public BeaconManager getBeaconManager() {
        return beaconManager;
    }
    static final String LOG = BApp.class.getName();
}
