/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.proximity.data;

import java.io.Serializable;

/**
 *
 * @author aubreyM
 */
public class RequestDTO implements Serializable {

    private int beaconID, branchID, companyID, requestType;
    private CompanyDTO company;
    private BranchDTO branch;
    private BeaconDTO beacon;
    private BeaconDataItemDTO beaconDataItem;
    private String macAddress;

    public static final int REGISTER_COMPANY = 1;
    public static final int REGISTER_BRANCH = 2;
    public static final int REGISTER_BEACON = 3;
    public static final int REGISTER_DATA_ITEM = 4;
    public static final int UPDATE_DATA_ITEM = 5;
    public static final int UPDATE_BEACON = 6;

    public static final int GET_BRANCH_BEACONS = 11;
    public static final int GET_BEACONS_BY_MAC_ADDRESS = 12;
    public static final int GET_COMPANY_BEACONS = 33;
    public static final int GET_BEACON_IMAGE_FILES = 14;
    public static final int GET_COMPANIES = 15;


    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    
    public int getBeaconID() {
        return beaconID;
    }

    public void setBeaconID(int beaconID) {
        this.beaconID = beaconID;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public BranchDTO getBranch() {
        return branch;
    }

    public void setBranch(BranchDTO branch) {
        this.branch = branch;
    }

    public BeaconDTO getBeacon() {
        return beacon;
    }

    public void setBeacon(BeaconDTO beacon) {
        this.beacon = beacon;
    }

    public BeaconDataItemDTO getBeaconDataItem() {
        return beaconDataItem;
    }

    public void setBeaconDataItem(BeaconDataItemDTO beaconDataItem) {
        this.beaconDataItem = beaconDataItem;
    }

}
