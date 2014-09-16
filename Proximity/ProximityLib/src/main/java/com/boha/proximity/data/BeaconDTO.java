/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.proximity.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aubreyM
 */
public class BeaconDTO implements Parcelable {

    private int beaconID;
    private String macAddress, beaconName,companyName, branchName;
    private String proximityUUID;
    private int major;
    private int minor;
    private int branchID, companyID;
    private List<BeaconDataItemDTO> beaconDataItemList;
    private List<String> imageFileNameList;

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public List<String> getImageFileNameList() {
        return imageFileNameList;
    }

    public void setImageFileNameList(List<String> imageFileNameList) {
        this.imageFileNameList = imageFileNameList;
    }

    public String getBeaconName() {
        return beaconName;
    }

    public void setBeaconName(String beaconName) {
        this.beaconName = beaconName;
    }

    public int getBeaconID() {
        return beaconID;
    }

    public void setBeaconID(int beaconID) {
        this.beaconID = beaconID;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getProximityUUID() {
        return proximityUUID;
    }

    public void setProximityUUID(String proximityUUID) {
        this.proximityUUID = proximityUUID;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public List<BeaconDataItemDTO> getBeaconDataItemList() {
        return beaconDataItemList;
    }

    public void setBeaconDataItemList(List<BeaconDataItemDTO> beaconDataItemList) {
        this.beaconDataItemList = beaconDataItemList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.beaconID);
        dest.writeString(this.macAddress);
        dest.writeString(this.beaconName);
        dest.writeString(this.companyName);
        dest.writeString(this.branchName);
        dest.writeString(this.proximityUUID);
        dest.writeInt(this.major);
        dest.writeInt(this.minor);
        dest.writeInt(this.branchID);
        dest.writeInt(this.companyID);
        dest.writeList(this.beaconDataItemList);
        dest.writeList(this.imageFileNameList);
    }

    public BeaconDTO() {
    }

    private BeaconDTO(Parcel in) {
        this.beaconID = in.readInt();
        this.macAddress = in.readString();
        this.beaconName = in.readString();
        this.companyName = in.readString();
        this.branchName = in.readString();
        this.proximityUUID = in.readString();
        this.major = in.readInt();
        this.minor = in.readInt();
        this.branchID = in.readInt();
        this.companyID = in.readInt();
        this.beaconDataItemList = new ArrayList<BeaconDataItemDTO>();
        in.readList(this.beaconDataItemList, BeaconDataItemDTO.class.getClassLoader());
        this.imageFileNameList = new ArrayList<String>();
        in.readList(this.imageFileNameList, String.class.getClassLoader());
    }

    public static final Creator<BeaconDTO> CREATOR = new Creator<BeaconDTO>() {
        public BeaconDTO createFromParcel(Parcel source) {
            return new BeaconDTO(source);
        }

        public BeaconDTO[] newArray(int size) {
            return new BeaconDTO[size];
        }
    };
}
