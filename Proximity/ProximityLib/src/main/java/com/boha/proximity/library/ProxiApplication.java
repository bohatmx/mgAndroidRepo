package com.boha.proximity.library;

import android.app.Application;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader;
import com.boha.proximity.volley.BohaVolley;
import com.estimote.sdk.BeaconManager;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by aubreyM on 2014/07/12.
 */
@ReportsCrashes(
        formKey = "",
        formUri = Statics.CRASH_REPORTS_URL,
        customReportContent = {ReportField.APP_VERSION_NAME, ReportField.APP_VERSION_CODE,
                ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.BRAND, ReportField.STACK_TRACE,
                ReportField.PACKAGE_NAME,
                ReportField.CUSTOM_DATA,
                ReportField.LOGCAT},
        socketTimeout = 3000
)
public class ProxiApplication extends Application {

    @Override
    public void onCreate() {
        Log.i(LOG,"***************************** BeaconsApp starting...");
        beaconManager = new BeaconManager(getApplicationContext());
        imageLoader = BohaVolley.getImageLoader(getApplicationContext());

        ACRA.init(this);
        Log.e(LOG, "###### ACRA Crash Reporting has been initiated");

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
