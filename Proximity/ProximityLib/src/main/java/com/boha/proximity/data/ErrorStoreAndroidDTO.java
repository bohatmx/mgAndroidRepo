package com.boha.proximity.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aubreyM on 2014/07/30.
 */
public class ErrorStoreAndroidDTO implements Parcelable{
    private int errorStoreAndroidID, golfGroupID;
    private String companyName, logCat, stackTrace, androidVersion,
            brand, appVersionCode, appVersionName, packageName, phoneModel;
    private long errorDate;

    public int getErrorStoreAndroidID() {
        return errorStoreAndroidID;
    }

    public void setErrorStoreAndroidID(int errorStoreAndroidID) {
        this.errorStoreAndroidID = errorStoreAndroidID;
    }

    public int getGolfGroupID() {
        return golfGroupID;
    }

    public void setGolfGroupID(int golfGroupID) {
        this.golfGroupID = golfGroupID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLogCat() {
        return logCat;
    }

    public void setLogCat(String logCat) {
        this.logCat = logCat;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(String appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getAppVersionName() {
        return appVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public long getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(long errorDate) {
        this.errorDate = errorDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.errorStoreAndroidID);
        dest.writeInt(this.golfGroupID);
        dest.writeString(this.companyName);
        dest.writeString(this.logCat);
        dest.writeString(this.stackTrace);
        dest.writeString(this.androidVersion);
        dest.writeString(this.brand);
        dest.writeString(this.appVersionCode);
        dest.writeString(this.appVersionName);
        dest.writeString(this.packageName);
        dest.writeString(this.phoneModel);
        dest.writeLong(this.errorDate);
    }

    public ErrorStoreAndroidDTO() {
    }

    private ErrorStoreAndroidDTO(Parcel in) {
        this.errorStoreAndroidID = in.readInt();
        this.golfGroupID = in.readInt();
        this.companyName = in.readString();
        this.logCat = in.readString();
        this.stackTrace = in.readString();
        this.androidVersion = in.readString();
        this.brand = in.readString();
        this.appVersionCode = in.readString();
        this.appVersionName = in.readString();
        this.packageName = in.readString();
        this.phoneModel = in.readString();
        this.errorDate = in.readLong();
    }

    public static final Creator<ErrorStoreAndroidDTO> CREATOR = new Creator<ErrorStoreAndroidDTO>() {
        public ErrorStoreAndroidDTO createFromParcel(Parcel source) {
            return new ErrorStoreAndroidDTO(source);
        }

        public ErrorStoreAndroidDTO[] newArray(int size) {
            return new ErrorStoreAndroidDTO[size];
        }
    };
}
