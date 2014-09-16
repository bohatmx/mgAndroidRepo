package com.boha.proximity.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aubreyM on 2014/07/27.
 */
public class UploadUrlDTO implements Parcelable{
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
    }

    public UploadUrlDTO() {
    }

    private UploadUrlDTO(Parcel in) {
        this.url = in.readString();
    }

    public static final Creator<UploadUrlDTO> CREATOR = new Creator<UploadUrlDTO>() {
        public UploadUrlDTO createFromParcel(Parcel source) {
            return new UploadUrlDTO(source);
        }

        public UploadUrlDTO[] newArray(int size) {
            return new UploadUrlDTO[size];
        }
    };
}
