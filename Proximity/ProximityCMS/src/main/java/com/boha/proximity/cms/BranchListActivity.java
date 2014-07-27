package com.boha.proximity.cms;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.boha.proximity.cms.fragments.BranchListFragment;
import com.boha.proximity.data.BranchDTO;
import com.boha.proximity.data.RequestDTO;
import com.boha.proximity.data.ResponseDTO;
import com.boha.proximity.library.Statics;
import com.boha.proximity.util.CacheUtil;
import com.boha.proximity.util.SharedUtil;
import com.boha.proximity.volley.BaseVolley;

public class BranchListActivity extends ActionBarActivity
        implements BranchListFragment.BranchListFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_list);
        ctx = getApplicationContext();
        branchListFragment = (BranchListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
    }


    private void getCompanyBeacons() {
        RequestDTO w = new RequestDTO();
        w.setRequestType(RequestDTO.GET_COMPANY_BEACONS);
        w.setCompanyID(SharedUtil.getCompany(ctx).getCompanyID());

        if (!BaseVolley.checkNetworkOnDevice(ctx)) {
            return;
        }
        setRefreshActionButtonState(true);
        BaseVolley.getRemoteData(Statics.SERVLET_ADMIN, w, ctx, new BaseVolley.BohaVolleyListener() {
            @Override
            public void onResponseReceived(ResponseDTO response) {
                setRefreshActionButtonState(false);
                if (response.getStatusCode() > 0) {
                    Toast.makeText(ctx, response.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                branchListFragment.setBranchList(response.getBranchList());
                CacheUtil.cacheData(ctx, response, CacheUtil.CACHE_BRANCHES, new CacheUtil.CacheUtilListener() {
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
                setRefreshActionButtonState(false);
                Toast.makeText(ctx, "Comms Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.branch_list, menu);
        mMenu = menu;
        CacheUtil.getCachedData(ctx, CacheUtil.CACHE_BRANCHES, new CacheUtil.CacheUtilListener() {
            @Override
            public void onFileDataDeserialized(ResponseDTO response) {
                if (response != null) {
                    branchListFragment.setBranchList(response.getBranchList());
                } else {
                    getCompanyBeacons();
                }
            }

            @Override
            public void onDataCached() {

            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            getCompanyBeacons();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setRefreshActionButtonState(final boolean refreshing) {
        if (mMenu != null) {
            final MenuItem refreshItem = mMenu.findItem(R.id.action_refresh);
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
    BranchListFragment branchListFragment;
    Context ctx;

    @Override
    public void onBranchPicked(BranchDTO branch) {

        if (branch.getBeaconList().size() == 0) {
            startDialog(branch);
            return;
        }
        Intent i = new Intent(this, BeaconListActivity.class);
        i.putExtra("branch", branch);
        startActivity(i);
    }

    private void startDialog(final BranchDTO branch) {
        AlertDialog.Builder diag = new AlertDialog.Builder(this);
        diag.setTitle("Beacon Search")
                .setMessage("Do you want to start scanning your beacons?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent p = new Intent(ctx,BeaconScanActivity.class);
                        p.putExtra("branch", branch);
                        startActivity(p);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
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
