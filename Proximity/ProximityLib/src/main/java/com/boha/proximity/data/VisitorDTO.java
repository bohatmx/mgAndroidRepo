package com.boha.proximity.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aubreyM on 2014/09/09.
 */
public class VisitorDTO implements Parcelable{
    private int visitorID;
    private String firstName, lastName, email;
    private long dateRegistered;
    private List<VisitorTrackDTO> visitorTrackList;

    public int getVisitorID() {
        return visitorID;
    }

    public void setVisitorID(int visitorID) {
        this.visitorID = visitorID;
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

    public long getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(long dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public List<VisitorTrackDTO> getVisitorTrackList() {
        return visitorTrackList;
    }

    public void setVisitorTrackList(List<VisitorTrackDTO> visitorTrackList) {
        this.visitorTrackList = visitorTrackList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.visitorID);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.email);
        dest.writeLong(this.dateRegistered);
        dest.writeList(this.visitorTrackList);
    }

    public VisitorDTO() {
    }

    private VisitorDTO(Parcel in) {
        this.visitorID = in.readInt();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.dateRegistered = in.readLong();
        this.visitorTrackList = new ArrayList<VisitorTrackDTO>();
        in.readList(this.visitorTrackList, VisitorTrackDTO.class.getClassLoader());
    }

    public static final Creator<VisitorDTO> CREATOR = new Creator<VisitorDTO>() {
        public VisitorDTO createFromParcel(Parcel source) {
            return new VisitorDTO(source);
        }

        public VisitorDTO[] newArray(int size) {
            return new VisitorDTO[size];
        }
    };
}
