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
import com.boha.beacon.data.BeaconDTO;
import com.boha.beacon.data.RequestDTO;
import com.boha.beacon.data.ResponseDTO;
import com.boha.beacon.util.BaseVolley;
import com.boha.beacon.util.Statics;
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
        BApp app = (BApp) getApplication();
        beaconManager = app.getBeaconManager();

        if (savedInstanceState != null) {
            getCachedBeaconList();
        }


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

        pageList = new ArrayList<>();
        //pageList.add(new MainFragment());
        Log.e(TAG, "#############---> building pages for beacon, "
                + "major: " + beacon.getMajor() + " minor: " + beacon.getMinor());
        getActionBar().setSubtitle(" Beacon major: " + beacon.getMajor() + " minor: " + beacon.getMinor());

        if (beacon.getImageFiles() != null) {
            for (String name : beacon.getImageFiles()) {
                PromotionFragment pf = new PromotionFragment();
                Bundle b = new Bundle();
                b.putString("fileName", name);
                b.putSerializable("beacon", beacon);
                pf.setArguments(b);
                pageList.add(pf);
            }
        }
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

    }

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
                //take top beacon and see if it's already displayed
                if (selectedBeacon != null) {
                    if (beacons.get(0).getMacAddress()
                            .equalsIgnoreCase(selectedBeacon.getMacAddress())) {
                        return;
                    }
                }
                selectedBeacon = beacons.get(0);
                rangingCount++;
                if (rangingCount < RANGING_LIMIT) {
                    //return;
                }
                for (BeaconDTO b : response.getBeaconList()) {
                    if (b.getMacAddress().equalsIgnoreCase(
                            selectedBeacon.getMacAddress())) {
                        buildPages(b);
                        rangingCount = 0;
                        break;
                    }
                }

            }
        });
    }

    private void getCachedBeaconList() {
        CacheUtil.getCachedData(ctx, CacheUtil.SERVER_BEACON_LIST, new CacheUtil.CacheUtilListener() {
            @Override
            public void onFileDataDeserialized(ResponseDTO r) {
                if (r != null) {
                    response = r;
                    findStoreBeacons();
                }
                getBeaconsFromServerx();
            }

            @Override
            public void onDataCached() {

            }
        });
    }

    private void getBeaconsFromServerx() {
        RequestDTO w = new RequestDTO();
        w.setRequestType(RequestDTO.GET_BRANCH_BEACONS);
        w.setCompanyID(1);
        w.setBranchID(1);
        //TODO - get all of company's beacons, could be thousands...dont matter?

        if (!BaseVolley.checkNetworkOnDevice(ctx)) return;
        BaseVolley.getRemoteData(Statics.SERVLET_ADMIN, w, ctx, new BaseVolley.BohaVolleyListener() {
            @Override
            public void onResponseReceived(ResponseDTO r) {
                if (r.getStatusCode() > 0) {
                    Toast.makeText(ctx, r.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

                response = r;

                beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
                    @Override
                    public void onEnteredRegion(Region region, List<Beacon> beacons) {
                        Log.w(TAG, "&&&&&&&&&&&&&&&&&& onEnteredRegion, beacons: " + beacons.size()
                                + " \nregion: " + region.getIdentifier() + " proxID:" + region.getProximityUUID()
                                + "\nMajor: " + region.getMajor() + " Minor: " + region.getMinor());

                        if (!beacons.isEmpty()) {
                            for (BeaconDTO d : response.getBeaconList()) {
                                if (d.getMacAddress().equalsIgnoreCase(beacons.get(0).getMacAddress())) {
                                    buildPages(d);
                                }
                            }
                        }
                        //start ranging to find other beacons in the store
                        findStoreBeacons();
                    }

                    @Override
                    public void onExitedRegion(Region region) {

                    }
                });

                if (!r.getBeaconList().isEmpty()) {
                    BeaconDTO b = r.getBeaconList().get(0);
                    region = new Region(REGION, CHAIN_STORE_IDENTIFIER, null, null);
                    try {
                        Log.i(TAG, "&&&&&&&&&&&&&&&&&& startMonitoring region .....");
                        beaconManager.startMonitoring(region);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    //TODO - cache beaconList from server
                }
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

    static final String CHAIN_STORE_IDENTIFIER = "b9407f30-f5f8-466e-aff9-25556b57fe6d",
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
            String title = "Title";
            switch (position) {
//                case 0:
//                    title = "Cover Page";
//                    break;
                case 0:
                    title = PROMOTION + " " + 1;
                    break;
                case 1:
                    title = PROMOTION + " " + 2;
                    break;
                case 2:
                    title = PROMOTION + " " + 3;
                    break;
                case 3:
                    title = PROMOTION + " " + 4;
                    break;
                case 4:
                    title = PROMOTION + " " + 5;
                    break;

                default:
                    break;
            }
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
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
                    Log.i(TAG, "############# Ranging has started .............");
                } catch (RemoteException e) {
                    Toast.makeText(ctx, "Cannot start ranging, something terrible happened",
                            Toast.LENGTH_LONG).show();
                    Log.e(TAG, "-----------------> Cannot start ranging", e);
                }
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
    static final String PROMOTION = "PROMOTION", TAG = MainPagerActivity.class.getName();
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

}
