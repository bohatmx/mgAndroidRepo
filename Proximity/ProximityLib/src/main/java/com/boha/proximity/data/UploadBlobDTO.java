package com.boha.proximity.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aubreyM on 2014/07/27.
 */
public class UploadBlobDTO implements Parcelable{
    private String servingUrl, blobKey;

    public String getServingUrl() {
        return servingUrl;
    }

    public void setServingUrl(String servingUrl) {
        this.servingUrl = servingUrl;
    }

    public String getBlobKey() {
        return blobKey;
    }

    public void setBlobKey(String blobKey) {
        this.blobKey = blobKey;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.servingUrl);
        dest.writeString(this.blobKey);
    }

    public UploadBlobDTO() {
    }

    private UploadBlobDTO(Parcel in) {
        this.servingUrl = in.readString();
        this.blobKey = in.readString();
    }

    public static final Creator<UploadBlobDTO> CREATOR = new Creator<UploadBlobDTO>() {
        public UploadBlobDTO createFromParcel(Parcel source) {
            return new UploadBlobDTO(source);
        }

        public UploadBlobDTO[] newArray(int size) {
            return new UploadBlobDTO[size];
        }
    };
}
