/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.proximity.data;


import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author aubreyM
 */
public class BeaconDataItemDTO implements Parcelable {
  private int beaconDataItemID;
    private String imageUrl;
    private String html;
    private String text;
    private int beaconID;  

    public int getBeaconDataItemID() {
        return beaconDataItemID;
    }

    public void setBeaconDataItemID(int beaconDataItemID) {
        this.beaconDataItemID = beaconDataItemID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBeaconID() {
        return beaconID;
    }

    public void setBeaconID(int beaconID) {
        this.beaconID = beaconID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.beaconDataItemID);
        dest.writeString(this.imageUrl);
        dest.writeString(this.html);
        dest.writeString(this.text);
        dest.writeInt(this.beaconID);
    }

    public BeaconDataItemDTO() {
    }

    private BeaconDataItemDTO(Parcel in) {
        this.beaconDataItemID = in.readInt();
        this.imageUrl = in.readString();
        this.html = in.readString();
        this.text = in.readString();
        this.beaconID = in.readInt();
    }

    public static final Creator<BeaconDataItemDTO> CREATOR = new Creator<BeaconDataItemDTO>() {
        public BeaconDataItemDTO createFromParcel(Parcel source) {
            return new BeaconDataItemDTO(source);
        }

        public BeaconDataItemDTO[] newArray(int size) {
            return new BeaconDataItemDTO[size];
        }
    };
}
