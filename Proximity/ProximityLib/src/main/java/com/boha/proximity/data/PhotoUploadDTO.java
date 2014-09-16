package com.boha.proximity.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aubreyM on 2014/07/27.
 */
public class PhotoUploadDTO implements Parcelable{
    public static final String BRANCH_PREFIX = "branch";
    public static final String COMPANY_PREFIX = "company";
    public static final String BEACON_PREFIX = "beacon";


    public static final int PICTURES_FULL_SIZE = 1;
    public static final int PICTURES_THUMBNAILS = 2;

    private int companyID, branchID, beaconID;

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public int getBeaconID() {
        return beaconID;
    }

    public void setBeaconID(int beaconID) {
        this.beaconID = beaconID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.companyID);
        dest.writeInt(this.branchID);
        dest.writeInt(this.beaconID);
    }

    public PhotoUploadDTO() {
    }

    private PhotoUploadDTO(Parcel in) {
        this.companyID = in.readInt();
        this.branchID = in.readInt();
        this.beaconID = in.readInt();
    }

    public static final Creator<PhotoUploadDTO> CREATOR = new Creator<PhotoUploadDTO>() {
        public PhotoUploadDTO createFromParcel(Parcel source) {
            return new PhotoUploadDTO(source);
        }

        public PhotoUploadDTO[] newArray(int size) {
            return new PhotoUploadDTO[size];
        }
    };
}
