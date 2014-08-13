package com.boha.proximity.cms.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.boha.proximity.cms.FileNames;
import com.boha.proximity.cms.R;
import com.boha.proximity.cms.adapters.BeaconAdapter;
import com.boha.proximity.data.BeaconDTO;
import com.boha.proximity.data.BranchDTO;
import com.boha.proximity.data.CompanyDTO;
import com.boha.proximity.util.SharedUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by aubreyM on 2014/06/13.
 */
public class BeaconListFragment extends Fragment {
    public interface BeaconListFragmentListener {
        public void onBeaconPicked(BeaconDTO beacon);
        public void setBusy();
        public void setNotBusy();
    }

    BeaconListFragmentListener listener;
    BranchDTO branch;
    @Override
    public void onAttach(Activity a) {
        if (a instanceof BeaconListFragmentListener) {
            listener = (BeaconListFragmentListener) a;
        } else {
            throw new UnsupportedOperationException("This Host " + a.getLocalClassName() +
                    " has to implement BeaconListFragmentListener");
        }
        Log.e(LOG, "##### Fragment hosted by " + a.getLocalClassName());
        super.onAttach(a);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saved) {
        ctx = getActivity();
        inflater = getActivity().getLayoutInflater();
        view = inflater
                .inflate(R.layout.fragment_beacon_list, container, false);
        setFields();
        company = SharedUtil.getCompany(ctx);
        setFields();
        return view;

    }

    private void setFields() {

        txtCount = (TextView) view.findViewById(R.id.FBC_txtCount);
        txtBranch = (TextView) view.findViewById(R.id.FBC_txtBranch);
        listView = (ListView) view.findViewById(R.id.FBC_list);

    }


    public BeaconDTO updateImageFiles(FileNames f) {
        HashMap<String, String> map = new HashMap<String, String>();
        for (String name: f.getFileNames()) {
            int index = getIndex(name);
            if (index > -1) {
                beacon.getImageFileNameList().remove(index);
            }
        }
        adapter.notifyDataSetChanged();
        return beacon;
    }
    private int getIndex(String deleted) {
        int index = 0;
        for (String s: beacon.getImageFileNameList()) {
            if (s.equalsIgnoreCase(deleted)) {
                return index;
            }
            index++;

        }

        return -1;
    }
    private void setList() {
        adapter = new BeaconAdapter(ctx, R.layout.beacon_item, beaconList);
        listView.setAdapter(adapter);
        txtCount.setText("" + beaconList.size());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                beacon = beaconList.get(i);
                listener.onBeaconPicked(beacon);
            }
        });
    }

    public void setBranch(BranchDTO branch) {
        this.branch = branch;
        if (branch.getBeaconList() == null) return;
        beaconList = branch.getBeaconList();
        txtBranch.setText(branch.getBranchName());
        setList();
    }

    View view;
    Context ctx;
    CompanyDTO company;
    List<BeaconDTO> beaconList;
    BeaconDTO beacon;
    BeaconAdapter adapter;

    ListView listView;
    TextView  txtCount, txtBranch;

    static final String LOG = "BeaconListFragment";
}
