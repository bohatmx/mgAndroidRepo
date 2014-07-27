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

import com.boha.proximity.cms.R;
import com.boha.proximity.cms.adapters.BranchAdapter;
import com.boha.proximity.data.BranchDTO;
import com.boha.proximity.data.CompanyDTO;
import com.boha.proximity.util.SharedUtil;

import java.util.List;

/**
 * Created by aubreyM on 2014/06/13.
 */
public class BranchListFragment extends Fragment {
    public interface BranchListFragmentListener {
        public void onBranchPicked(BranchDTO branch);
        public void setBusy();
        public void setNotBusy();
    }

    BranchListFragmentListener listener;

    @Override
    public void onAttach(Activity a) {
        if (a instanceof BranchListFragmentListener) {
            listener = (BranchListFragmentListener) a;
        } else {
            throw new UnsupportedOperationException("Host " + a.getLocalClassName() +
                    " must implement BranchListFragmentListener");
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
                .inflate(R.layout.fragment_branch_list, container, false);
        company = SharedUtil.getCompany(ctx);
        setFields();
        return view;

    }

    private void setFields() {

        txtCount = (TextView) view.findViewById(R.id.FBL_txtCount);
        txtCompany = (TextView) view.findViewById(R.id.FBL_txtCompany);
        listView = (ListView) view.findViewById(R.id.FBL_list);
        txtCompany.setText(company.getCompanyName());
    }


    private void setList() {
        adapter = new BranchAdapter(ctx, R.layout.branch_item, branchList);
        listView.setAdapter(adapter);
        txtCount.setText("" + branchList.size());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                branch = branchList.get(i);
                listener.onBranchPicked(branch);
            }
        });
    }

    public void setBranchList(List<BranchDTO> branchList) {
        this.branchList = branchList;
        setList();
    }

    View view;
    Context ctx;
    CompanyDTO company;
    List<BranchDTO> branchList;
    BranchDTO branch;
    BranchAdapter adapter;

    ListView listView;
    TextView  txtCount, txtCompany;

    static final String LOG = "BranchListFragment";
}
