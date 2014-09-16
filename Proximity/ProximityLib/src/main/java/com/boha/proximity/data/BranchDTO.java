/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.proximity.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 *
 * @author aubreyM
 */
public class BranchDTO implements Parcelable {
   private int branchID;
    private String branchName;
    private String email;
    private String cellphone;
    private int companyID;
    private List<BeaconDTO> beaconList; 

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public List<BeaconDTO> getBeaconList() {
        return beaconList;
    }

    public void setBeaconList(List<BeaconDTO> beaconList) {
        this.beaconList = beaconList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.branchID);
        dest.writeString(this.branchName);
        dest.writeString(this.email);
        dest.writeString(this.cellphone);
        dest.writeInt(this.companyID);
        dest.writeTypedList(beaconList);
    }

    public BranchDTO() {
    }

    private BranchDTO(Parcel in) {
        this.branchID = in.readInt();
        this.branchName = in.readString();
        this.email = in.readString();
        this.cellphone = in.readString();
        this.companyID = in.readInt();
        in.readTypedList(beaconList, BeaconDTO.CREATOR);
    }

    public static final Creator<BranchDTO> CREATOR = new Creator<BranchDTO>() {
        public BranchDTO createFromParcel(Parcel source) {
            return new BranchDTO(source);
        }

        public BranchDTO[] newArray(int size) {
            return new BranchDTO[size];
        }
    };
}
