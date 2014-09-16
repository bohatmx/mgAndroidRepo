package com.boha.proximity.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aubreyM on 2014/09/09.
 */
public class VisitorTrackDTO implements Parcelable{
    private int visitorTrackID, visitorID, beaconID;
    private String beaconName;
    private long dateTracked;
    private String firstName, lastName, email;

    public int getVisitorTrackID() {
        return visitorTrackID;
    }

    public void setVisitorTrackID(int visitorTrackID) {
        this.visitorTrackID = visitorTrackID;
    }

    public int getVisitorID() {
        return visitorID;
    }

    public void setVisitorID(int visitorID) {
        this.visitorID = visitorID;
    }

    public int getBeaconID() {
        return beaconID;
    }

    public void setBeaconID(int beaconID) {
        this.beaconID = beaconID;
    }

    public String getBeaconName() {
        return beaconName;
    }

    public void setBeaconName(String beaconName) {
        this.beaconName = beaconName;
    }

    public long getDateTracked() {
        return dateTracked;
    }

    public void setDateTracked(long dateTracked) {
        this.dateTracked = dateTracked;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.visitorTrackID);
        dest.writeInt(this.visitorID);
        dest.writeInt(this.beaconID);
        dest.writeString(this.beaconName);
        dest.writeLong(this.dateTracked);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.email);
    }

    public VisitorTrackDTO() {
    }

    private VisitorTrackDTO(Parcel in) {
        this.visitorTrackID = in.readInt();
        this.visitorID = in.readInt();
        this.beaconID = in.readInt();
        this.beaconName = in.readString();
        this.dateTracked = in.readLong();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
    }

    public static final Creator<VisitorTrackDTO> CREATOR = new Creator<VisitorTrackDTO>() {
        public VisitorTrackDTO createFromParcel(Parcel source) {
            return new VisitorTrackDTO(source);
        }

        public VisitorTrackDTO[] newArray(int size) {
            return new VisitorTrackDTO[size];
        }
    };
}
