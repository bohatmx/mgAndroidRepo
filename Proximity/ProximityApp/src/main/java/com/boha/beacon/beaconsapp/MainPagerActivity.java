package com.boha.beacon.beaconsapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.boha.proximity.data.BeaconDTO;
import com.boha.proximity.data.RequestDTO;
import com.boha.proximity.data.ResponseDTO;
import com.boha.proximity.library.ProxiApplication;
import com.boha.proximity.library.Statics;
import com.boha.proximity.volley.BaseVolley;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.estimote.sdk.Utils.computeAccuracy;
import static com.estimote.sdk.Utils.computeProximity;


public class MainPagerActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getApplicationContext();
        setContentView(R.layout.activity_main);
        mPager = (ViewPager) findViewById(R.id.pager);
        ProxiApplication app = (ProxiApplication) getApplication();
        beaconManager = app.getBeaconManager();
        imageLoader = app.getImageLoader();

        if (savedInstanceState != null) {
            getCachedBeaconList();
        }

        setTitle("");
    }

    @Override
    public void onResume() {
        Log.i(TAG, "%%%%%%%%%%%%% onResume");
        if (response == null) {
            Log.e(TAG, "getCachedBeaconList =============== ...");
            //getCachedBeaconList();
        }
        super.onResume();
    }

    private void buildPages(BeaconDTO beacon) {
        if (beaconDTO != null) {
            if (beacon.getMacAddress().equalsIgnoreCase(beaconDTO.getMacAddress())) {
                return;
            }
        }
        beaconDTO = beacon;
        getActionBar().setSubtitle(beacon.getBeaconName());
        pageList = new ArrayList<PageFragment>();
        Log.e(TAG, "#############---> building pages for beacon, "
                + "company: " + beacon.getCompanyName() + " branch: " + beacon.getBranchName());
        if (beacon.getImageFileNameList() != null) {
            for (String item : beacon.getImageFileNameList()) {
                PromotionFragment pf = new PromotionFragment();
                pf.setImageLoader(imageLoader);
                Bundle b = new Bundle();
                b.putString("fileName", item);
                b.putSerializable("beacon", beacon);
                pf.setArguments(b);
                pageList.add(pf);
            }
        }
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);


    }

    ImageLoader imageLoader;
    boolean serverBeaconsLoaded;
    private void findStoreBeacons() {

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> beacons) {
                beaconList = beacons;
                Log.e(TAG, "***** Beacons discovered .... Yeah! " + beacons.size()
                        + "  region: " + region.getIdentifier());
                for (Beacon b : beacons) {
                    log(b);
                }
                if (!beacons.isEmpty()) {
                    if (!serverBeaconsLoaded) {
                        try {
                            beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
                            getBeaconsFromServer(beacons.get(0).getMacAddress());
                            Log.e(TAG, "***** RANGING STOPPED...maybe..at least I asked!");
                            return;
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }else {
                        for (BeaconDTO b: response.getBeaconList()) {
                            if (b.getMacAddress().equalsIgnoreCase(beacons.get(0).getMacAddress())) {
                                buildPages(b);
                                break;
                            }
                        }

                    }
                }

            }
        });
        try {
            beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void getCachedBeaconList() {
        CacheUtil.getCachedData(ctx, CacheUtil.SERVER_BEACON_LIST, new CacheUtil.CacheUtilListener() {
            @Override
            public void onFileDataDeserialized(ResponseDTO r) {
                if (r != null) {
                    response = r;
                    findStoreBeacons();
                }

            }

            @Override
            public void onDataCached() {

            }
        });
    }

    private void getBeaconsFromServer(String macAddress) {
        RequestDTO w = new RequestDTO();
        w.setRequestType(RequestDTO.GET_BEACONS_BY_MAC_ADDRESS);
        w.setMacAddress(macAddress);

        if (!BaseVolley.checkNetworkOnDevice(ctx)) return;
        BaseVolley.getRemoteData(Statics.SERVLET_ADMIN, w, ctx, new BaseVolley.BohaVolleyListener() {
            @Override
            public void onResponseReceived(ResponseDTO r) {
                if (r.getStatusCode() > 0) {
                    Toast.makeText(ctx, r.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                BeaconDTO b = r.getBeaconList().get(0);
                setTitle(b.getCompanyName());

                serverBeaconsLoaded = true;
                response = r;
                findStoreBeacons();
//                beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
//                    @Override
//                    public void onEnteredRegion(Region region, List<Beacon> beacons) {
//                        Log.w(TAG, "&&&&&&&&&&&&&&&&&& onEnteredRegion, beacons: " + beacons.size()
//                                + " \nregion: " + region.getIdentifier() + " proxID:" + region.getProximityUUID()
//                                + "\nMajor: " + region.getMajor() + " Minor: " + region.getMinor());
//
//                        if (!beacons.isEmpty()) {
//                            for (BeaconDTO d : response.getBeaconList()) {
//                                if (d.getMacAddress().equalsIgnoreCase(beacons.get(0).getMacAddress())) {
//                                    buildPages(d);
//                                }
//                            }
//                        }
//                        //start ranging to find other beacons in the store
//                        findStoreBeacons();
//                    }
//
//                    @Override
//                    public void onExitedRegion(Region region) {
//
//                    }
//                });
//                if (!r.getBeaconList().isEmpty()) {
//                    BeaconDTO b = r.getBeaconList().get(0);
//                    region = new Region(REGION, CHAIN_STORE_IDENTIFIER, null, null);
//                    try {
//                        Log.i(TAG, "&&&&&&&&&&&&&&&&&& startMonitoring region .....");
//                        beaconManager.startMonitoring(region);
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                }
                CacheUtil.cacheData(ctx, response, CacheUtil.SERVER_BEACON_LIST, new CacheUtil.CacheUtilListener() {
                    @Override
                    public void onFileDataDeserialized(ResponseDTO response) {

                    }

                    @Override
                    public void onDataCached() {

                    }
                });

            }

            @Override
            public void onVolleyError(VolleyError error) {
                //TODO - use cached beacons?
                Toast.makeText(ctx, "Communications Error, please try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    private static final String CHAIN_STORE_IDENTIFIER = "b9407f30-f5f8-466e-aff9-25556b57fe6d",
            REGION = "apparelStore";

    private void log(Beacon b) {
        double accuracy = computeAccuracy(b);
        Utils.Proximity prox = computeProximity(b);

        double distance = Math.min(accuracy, 10.0);
        Log.w(TAG, "Beacon name: " + b.getName() +
                " \nmacAddress: " + b.getMacAddress()
                + "\n getProximityUUID: " + b.getProximityUUID()
                + "\nmajor: " + b.getMajor() + " minor: " + b.getMinor()
                + "\nmeasuredPower: " + b.getMeasuredPower()
                + "\nDistance: " + distance
                + "\nRSSI: " + b.getRssi());


    }

    ResponseDTO response;
    int rangingCount;
    static final int RANGING_LIMIT = 3;
    private Region region;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e(TAG, "-------------------- onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main, menu);
        if (response == null)
            getCachedBeaconList();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            return (Fragment) pageList.get(i);
        }

        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = PROMOTION + " " + (position + 1);

            return title;
        }
    }

    @Override
    protected void onDestroy() {
        beaconManager.disconnect();

        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "############# onStart - checking for bluetooth support");
        // Check if device supports Bluetooth Low Energy.
        if (!beaconManager.hasBluetooth()) {
            Toast.makeText(this, "Device does not have Bluetooth Low Energy", Toast.LENGTH_LONG).show();
            return;
        }

        // If Bluetooth is not enabled, let user enable it.
        if (!beaconManager.isBluetoothEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            connectToService();
            //Intent u = new Intent(getApplicationContext(),BeaconMonitorService.class);
            //startService(u);
        }
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "############# onStop - stop ranging");
        try {
            beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
        } catch (RemoteException e) {
            Log.d(TAG, "Error while stopping ranging", e);
        }

        super.onStop();
    }

    private void connectToService() {
        Log.e(TAG, "################# connectToService - start ranging...");
        getActionBar().setSubtitle("Scanning for beacons...");
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
               findStoreBeacons();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.w(TAG, "############# onActivityResult ...........");
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                connectToService();
            } else {
                Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_LONG).show();
                getActionBar().setSubtitle("Bluetooth not enabled");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    Context ctx;
    List<PageFragment> pageList;
    static final String PROMOTION = "Content Page", TAG = MainPagerActivity.class.getName();
    PagerAdapter mPagerAdapter;
    BeaconManager beaconManager;
    List<Beacon> beaconList;
    Beacon selectedBeacon;
    BeaconDTO beaconDTO;
    MainFragment mainFragment;
    ViewPager mPager;
    private static final int REQUEST_ENABLE_BT = 1234;
    private static final Region ALL_ESTIMOTE_BEACONS_REGION =
            new Region("rid", null, null, null);
    @Override
    public void onPause() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onPause();
    }
}
