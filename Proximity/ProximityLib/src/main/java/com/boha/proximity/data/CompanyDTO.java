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
public class CompanyDTO implements Parcelable {
    private int companyID;
    private String companyName;
    private String email;
    private String cellphone;
    private List<BranchDTO> branchList;

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

    public List<BranchDTO> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<BranchDTO> branchList) {
        this.branchList = branchList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.companyID);
        dest.writeString(this.companyName);
        dest.writeString(this.email);
        dest.writeString(this.cellphone);
        dest.writeList(this.branchList);
    }

    public CompanyDTO() {
    }

    private CompanyDTO(Parcel in) {
        this.companyID = in.readInt();
        this.companyName = in.readString();
        this.email = in.readString();
        this.cellphone = in.readString();
        this.branchList = new ArrayList<BranchDTO>();
        in.readList(this.branchList, BranchDTO.class.getClassLoader());
    }

    public static final Creator<CompanyDTO> CREATOR = new Creator<CompanyDTO>() {
        public CompanyDTO createFromParcel(Parcel source) {
            return new CompanyDTO(source);
        }

        public CompanyDTO[] newArray(int size) {
            return new CompanyDTO[size];
        }
    };
}
