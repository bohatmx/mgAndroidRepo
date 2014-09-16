package com.boha.proximity.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aubreyM on 2014/07/30.
 */
public class ErrorStoreDTO implements Parcelable{
    private Integer errorStoreID;
    private int statusCode;
    private String message, origin;
    private long dateOccured;

    public Integer getErrorStoreID() {
        return errorStoreID;
    }

    public void setErrorStoreID(Integer errorStoreID) {
        this.errorStoreID = errorStoreID;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public long getDateOccured() {
        return dateOccured;
    }

    public void setDateOccured(long dateOccured) {
        this.dateOccured = dateOccured;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.errorStoreID);
        dest.writeInt(this.statusCode);
        dest.writeString(this.message);
        dest.writeString(this.origin);
        dest.writeLong(this.dateOccured);
    }

    public ErrorStoreDTO() {
    }

    private ErrorStoreDTO(Parcel in) {
        this.errorStoreID = (Integer) in.readValue(Integer.class.getClassLoader());
        this.statusCode = in.readInt();
        this.message = in.readString();
        this.origin = in.readString();
        this.dateOccured = in.readLong();
    }

    public static final Creator<ErrorStoreDTO> CREATOR = new Creator<ErrorStoreDTO>() {
        public ErrorStoreDTO createFromParcel(Parcel source) {
            return new ErrorStoreDTO(source);
        }

        public ErrorStoreDTO[] newArray(int size) {
            return new ErrorStoreDTO[size];
        }
    };
}
