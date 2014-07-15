package com.boha.beacon.beaconsapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

public class BeaconMonitorService extends Service {
    public BeaconMonitorService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG, "onCreate ----> ");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(LOG, "onStartCommand ***********************");
        BApp app = (BApp) getApplication();
        beaconManager = app.getBeaconManager();
        checkBluetooth();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LOG, "onBind ------------------");
        return null;
    }

    private void checkBluetooth() {
        Log.d(LOG, "############# onStart - checking for bluetooth support");
        // Check if device supports Bluetooth Low Energy.
        if (!beaconManager.hasBluetooth()) {
            Toast.makeText(this, "Device does not have Bluetooth Low Energy", Toast.LENGTH_LONG).show();
            return;
        }

        // If Bluetooth is not enabled, let user enable it.
        if (!beaconManager.isBluetoothEnabled()) {
            //Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //getApplication().startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            connectToService();
        }
    }
    private void connectToService() {
        Log.e(LOG, "################# connectToService - start ranging...");
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
                    Log.i(LOG, "############# Ranging has started .............");
                } catch (RemoteException e) {
                    Toast.makeText(getApplicationContext(), "Cannot start ranging, something terrible happened",
                            Toast.LENGTH_LONG).show();
                    Log.e(LOG, "-----------------> Cannot start ranging", e);
                }
            }
        });
    }

    private static final int REQUEST_ENABLE_BT = 1234;
    private static final Region ALL_ESTIMOTE_BEACONS_REGION =
            new Region("rid", null, null, null);
    BeaconManager beaconManager;
    static final String LOG = BeaconMonitorService.class.getName();
}
