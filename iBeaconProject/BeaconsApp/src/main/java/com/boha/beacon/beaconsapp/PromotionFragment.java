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
import com.boha.beacon.data.BeaconDTO;
import com.boha.beacon.util.Statics;
import com.boha.volley.toolbox.BohaVolley;

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
        imageLoader = BohaVolley.getImageLoader(ctx);

        Bundle b = getArguments();
        if (b != null) {
            filename = b.getString("fileName");
            beaconDTO = (BeaconDTO) b.getSerializable("beacon");
        }
        setFields();
        return view;
    }

    private void setFields() {
        image = (NetworkImageView)view.findViewById(R.id.image);
        StringBuilder sb = new StringBuilder();
        sb.append(Statics.IMAGE_URL).append("company1/");
        sb.append("branch1/beacon").append(beaconDTO.getBeaconID());
        sb.append("/").append(filename);
        Log.e(LOG,"imageURL: " + sb.toString());

        image.setImageUrl(sb.toString(), imageLoader);

    }
    String filename;
    BeaconDTO beaconDTO;
    Context ctx;
    View view;
    ImageLoader imageLoader;
    NetworkImageView image;
    static final String LOG = PromotionFragment.class.getName();


}
