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
public class ResponseDTO implements Parcelable {
    private int statusCode;
    private String message;
    
    private List<BeaconDTO> beaconList;
    private List<BranchDTO> branchList;
    private CompanyDTO company;
    private BranchDTO branch;
    private BeaconDTO beacon;
    private BeaconDataItemDTO beaconDataItem;
    private UploadBlobDTO uploadBlob;
    private UploadUrlDTO uploadUrl;
    private List<String> imageFileNames;
    private List<CompanyDTO> companyList;
    private List<ErrorStoreAndroidDTO> errorStoreAndroidList;
    private List<ErrorStoreDTO> errorStoreList;
    private String log;
    private List<VisitorTrackDTO> visitorTrackListSortedByBeacon;

    private List<VisitorDTO> visitorList;
    private List<VisitorTrackDTO> visitorTrackList;
    private VisitorDTO visitor;

    public List<VisitorTrackDTO> getVisitorTrackListSortedByBeacon() {
        return visitorTrackListSortedByBeacon;
    }

    public void setVisitorTrackListSortedByBeacon(List<VisitorTrackDTO> visitorTrackListSortedByBeacon) {
        this.visitorTrackListSortedByBeacon = visitorTrackListSortedByBeacon;
    }

    public List<VisitorDTO> getVisitorList() {
        return visitorList;
    }

    public void setVisitorList(List<VisitorDTO> visitorList) {
        this.visitorList = visitorList;
    }

    public List<VisitorTrackDTO> getVisitorTrackList() {
        return visitorTrackList;
    }

    public void setVisitorTrackList(List<VisitorTrackDTO> visitorTrackList) {
        this.visitorTrackList = visitorTrackList;
    }

    public VisitorDTO getVisitor() {
        return visitor;
    }

    public void setVisitor(VisitorDTO visitor) {
        this.visitor = visitor;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public List<ErrorStoreAndroidDTO> getErrorStoreAndroidList() {
        return errorStoreAndroidList;
    }

    public void setErrorStoreAndroidList(List<ErrorStoreAndroidDTO> errorStoreAndroidList) {
        this.errorStoreAndroidList = errorStoreAndroidList;
    }

    public List<ErrorStoreDTO> getErrorStoreList() {
        return errorStoreList;
    }

    public void setErrorStoreList(List<ErrorStoreDTO> errorStoreList) {
        this.errorStoreList = errorStoreList;
    }

    public List<CompanyDTO> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<CompanyDTO> companyList) {
        this.companyList = companyList;
    }

    public List<String> getImageFileNames() {
        return imageFileNames;
    }

    public void setImageFileNames(List<String> imageFileNames) {
        this.imageFileNames = imageFileNames;
    }

    public UploadUrlDTO getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(UploadUrlDTO uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public UploadBlobDTO getUploadBlob() {
        return uploadBlob;
    }

    public void setUploadBlob(UploadBlobDTO uploadBlob) {
        this.uploadBlob = uploadBlob;
    }

    public static final int DATABASE_ERROR = 100, SERVER_ERROR = 101;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public BeaconDataItemDTO getBeaconDataItem() {
        return beaconDataItem;
    }

    public void setBeaconDataItem(BeaconDataItemDTO beaconDataItem) {
        this.beaconDataItem = beaconDataItem;
    }

    public List<BranchDTO> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<BranchDTO> branchList) {
        this.branchList = branchList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BeaconDTO> getBeaconList() {
        return beaconList;
    }

    public void setBeaconList(List<BeaconDTO> beaconList) {
        this.beaconList = beaconList;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.statusCode);
        dest.writeString(this.message);
        dest.writeTypedList(beaconList);
        dest.writeTypedList(branchList);
        dest.writeParcelable(this.company, 0);
        dest.writeParcelable(this.branch, 0);
        dest.writeParcelable(this.beacon, 0);
        dest.writeParcelable(this.beaconDataItem, 0);
        dest.writeParcelable(this.uploadBlob, 0);
        dest.writeParcelable(this.uploadUrl, 0);
        dest.writeList(this.imageFileNames);
        dest.writeTypedList(companyList);
        dest.writeTypedList(errorStoreAndroidList);
        dest.writeTypedList(errorStoreList);
        dest.writeString(this.log);
        dest.writeTypedList(visitorTrackListSortedByBeacon);
        dest.writeTypedList(visitorList);
        dest.writeTypedList(visitorTrackList);
        dest.writeParcelable(this.visitor, 0);
    }

    public ResponseDTO() {
    }

    private ResponseDTO(Parcel in) {
        this.statusCode = in.readInt();
        this.message = in.readString();
        in.readTypedList(beaconList, BeaconDTO.CREATOR);
        in.readTypedList(branchList, BranchDTO.CREATOR);
        this.company = in.readParcelable(CompanyDTO.class.getClassLoader());
        this.branch = in.readParcelable(BranchDTO.class.getClassLoader());
        this.beacon = in.readParcelable(BeaconDTO.class.getClassLoader());
        this.beaconDataItem = in.readParcelable(BeaconDataItemDTO.class.getClassLoader());
        this.uploadBlob = in.readParcelable(UploadBlobDTO.class.getClassLoader());
        this.uploadUrl = in.readParcelable(UploadUrlDTO.class.getClassLoader());
        this.imageFileNames = new ArrayList<String>();
        in.readList(this.imageFileNames, String.class.getClassLoader());
        in.readTypedList(companyList, CompanyDTO.CREATOR);
        in.readTypedList(errorStoreAndroidList, ErrorStoreAndroidDTO.CREATOR);
        in.readTypedList(errorStoreList, ErrorStoreDTO.CREATOR);
        this.log = in.readString();
        in.readTypedList(visitorTrackListSortedByBeacon, VisitorTrackDTO.CREATOR);
        in.readTypedList(visitorList, VisitorDTO.CREATOR);
        in.readTypedList(visitorTrackList, VisitorTrackDTO.CREATOR);
        this.visitor = in.readParcelable(VisitorDTO.class.getClassLoader());
    }

    public static final Creator<ResponseDTO> CREATOR = new Creator<ResponseDTO>() {
        public ResponseDTO createFromParcel(Parcel source) {
            return new ResponseDTO(source);
        }

        public ResponseDTO[] newArray(int size) {
            return new ResponseDTO[size];
        }
    };
}
