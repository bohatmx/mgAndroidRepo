package com.boha.beacon.beaconsapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.boha.proximity.data.BeaconDTO;
import com.boha.proximity.data.BranchDTO;
import com.boha.proximity.data.PhotoUploadDTO;
import com.boha.proximity.library.Statics;


/**
 * Created by aubreyM on 2014/07/09.
 */
public class PromotionFragment extends Fragment implements PageFragment{
    @Override
    public void onAttach(Activity a) {
//        if (a instanceof BusyListener) {
//            busyListener = (BusyListener) a;
//        } else {
//            throw new UnsupportedOperationException("Host activity "
//                    + a.getLocalClassName()
//                    + " must implement BusyListener");
//        }
//        if (a instanceof CameraRequestListener) {
//            cameraRequestListener = (CameraRequestListener) a;
//        } else {
//            throw new UnsupportedOperationException("Host activity "
//                    + a.getLocalClassName()
//                    + " must implement CameraRequestListener");
//        }
        super.onAttach(a);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saved) {
        ctx = getActivity();
        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_promotion, container, false);
        Bundle b = getArguments();
        if (b != null) {
            fileName = b.getString("fileName");
            beacon = (BeaconDTO)b.getSerializable("beacon");
        }
        setFields();
        return view;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    private void setFields() {
        image = (NetworkImageView)view.findViewById(R.id.image);
        StringBuilder sb = new StringBuilder();
        sb.append(Statics.IMAGE_URL).append(PhotoUploadDTO.COMPANY_PREFIX).append(beacon.getCompanyID());
        sb.append("/").append(PhotoUploadDTO.BRANCH_PREFIX).append(beacon.getBranchID()).append("/");
        sb.append(PhotoUploadDTO.BEACON_PREFIX).append(beacon.getBeaconID()).append("/");
        sb.append(fileName);

        Log.e(LOG, "imageURL: " + sb.toString());
        image.setImageUrl(sb.toString(), imageLoader);

    }
    BeaconDTO beacon;
    Context ctx;
    View view;
    ImageLoader imageLoader;
    NetworkImageView image;
    static final String LOG = PromotionFragment.class.getName();
    String fileName;
    BranchDTO branch;


}
