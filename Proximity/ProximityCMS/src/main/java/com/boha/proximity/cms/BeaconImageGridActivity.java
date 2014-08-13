package com.boha.proximity.cms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.boha.proximity.cms.fragments.BeaconDeleteDialog;
import com.boha.proximity.cms.fragments.BeaconImageGridFragment;
import com.boha.proximity.data.BeaconDTO;
import com.boha.proximity.data.RequestDTO;
import com.boha.proximity.data.ResponseDTO;
import com.boha.proximity.library.Statics;
import com.boha.proximity.volley.BaseVolley;

import java.util.ArrayList;
import java.util.List;

public class BeaconImageGridActivity extends ActionBarActivity implements BeaconImageGridFragment.BeaconImageGridListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid);
        ctx = getApplicationContext();
        beacon = (BeaconDTO) getIntent().getSerializableExtra("beacon");
        fragment = (BeaconImageGridFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        fragment.setBeacon(beacon);
        setTitle(beacon.getBeaconName());
    }


    BeaconDTO beacon;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.beacon_image_grid, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public void onActivityResult(int req, int res, Intent data) {
        switch (req) {
            case BEACON_PIC_REQ:
                if (res == RESULT_OK) {
                    FileNames f = (FileNames)data.getSerializableExtra("fileNames");
                    beacon.getImageFileNameList().addAll(f.getFileNames());
                    fragment.setBeacon(beacon);
                }
                break;
        }
    }
    static final int BEACON_PIC_REQ = 1155;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent t = new Intent(this, PictureActivity.class);
            t.putExtra("beacon", beacon);
            startActivityForResult(t,BEACON_PIC_REQ);
            return true;
        }
        if (id == R.id.action_delete) {
            BeaconDeleteDialog diag = new BeaconDeleteDialog();
            diag.setContext(ctx);
            diag.setBeacon(beacon);
            diag.setListener(new BeaconDeleteDialog.BeaconDeleteListener() {
                @Override
                public void onImagesDeleted() {

                }

                @Override
                public void onBeaconDeleted() {

                }
            });
            diag.show(getSupportFragmentManager(), "BEACON_DELETE");


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void removeAllImages() {
        RequestDTO d = new RequestDTO();
        d.setRequestType(RequestDTO.DELETE_ALL_BEACON_IMAGES);
        d.setBeaconID(beacon.getBeaconID());
        d.setBranchID(beacon.getBranchID());
        d.setCompanyID(beacon.getCompanyID());

        if (!BaseVolley.checkNetworkOnDevice(ctx)) return;
        setRefreshActionButtonState(true);
        BaseVolley.getRemoteData(Statics.SERVLET_ADMIN,d,ctx,new BaseVolley.BohaVolleyListener() {
            @Override
            public void onResponseReceived(ResponseDTO response) {
                setRefreshActionButtonState(false);
                if (response.getStatusCode() == 0) {
                    Log.d("BeaconImageGridActivity", response.getMessage());
                    Toast.makeText(ctx,"Beacon images removed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onVolleyError(VolleyError error) {
                setRefreshActionButtonState(false);
                Toast.makeText(ctx,"Communications with server have failed", Toast.LENGTH_LONG).show();
            }
        });
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
    BeaconImageGridFragment fragment;
    Context ctx;

    @Override
    public void onPause() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onPause();
    }

    @Override
    public void onImageRemoved(String fileName) {
        removedFileNames.add(fileName);
    }
    private List<String> removedFileNames = new ArrayList<String>();
    @Override
    public void onBackPressed() {
        if (removedFileNames.size() > 0) {
            Intent w = new Intent();
            FileNames fn = new FileNames(removedFileNames);
            w.putExtra("fileNames", fn);
            setResult(RESULT_OK, w);

        }
        finish();
    }
}
