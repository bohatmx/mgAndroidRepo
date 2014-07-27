package com.boha.proximity.cms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.boha.proximity.cms.fragments.BeaconListFragment;
import com.boha.proximity.data.BeaconDTO;
import com.boha.proximity.data.BranchDTO;

public class BeaconListActivity extends ActionBarActivity
        implements BeaconListFragment.BeaconListFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_list);
        ctx = getApplicationContext();
        branch = (BranchDTO)getIntent().getSerializableExtra("branch");
        beaconListFragment = (BeaconListFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        beaconListFragment.setBranch(branch);
        setTitle("Registered Beacons");
    }


    BranchDTO branch;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.beacon_list, menu);
        mMenu = menu;
        menu.getItem(1).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_refresh) {

            return true;
        }
        if (id == R.id.action_range) {
            Intent i = new Intent(this, BeaconScanActivity.class);
            i.putExtra("branch", branch);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setRefreshActionButtonState(final boolean refreshing) {
        if (mMenu != null) {
            final MenuItem refreshItem = mMenu.findItem(R.id.action_range);
            if (refreshItem != null) {
                if (refreshing) {
                    refreshItem.setActionView(R.layout.action_bar_progess);
                } else {
                    refreshItem.setActionView(null);
                }
            }
        }
    }

    Menu mMenu;
    BeaconListFragment beaconListFragment;
    Context ctx;

    @Override
    public void onBeaconPicked(BeaconDTO beacon) {
        Intent w = new Intent(this, PictureActivity.class);
        w.putExtra("beacon", beacon);
        startActivity(w);
    }


    @Override
    public void setBusy() {
        setRefreshActionButtonState(true);
    }

    @Override
    public void setNotBusy() {
        setRefreshActionButtonState(false);
    }
    @Override
    public void onPause() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onPause();
    }
}
