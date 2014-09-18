/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.malengagolf.library.data;

import android.content.Context;

import com.boha.malengagolf.library.util.PersonInterface;
import com.boha.malengagolf.library.util.SharedUtil;
import com.boha.malengagolf.library.util.Statics;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Aubrey Malabie
 */
public class PlayerDTO implements Serializable, PersonInterface, Comparable<PlayerDTO> {

    private int playerID;
    private String cellphone;
    private long dateOfBirth;
    private long dateRegistered;
    private String email;
    private String firstName;
    private int gender, age, sortType;
    private String lastName;
    private String middleName;
    private String pin, imageURL;
    private int yearJoined, parentID;
    private ParentDTO parent;
    private boolean selected;
    private int numberOfTournaments;
    private List<LeaderBoardDTO> scores;
    private GcmDeviceDTO gcmDevice;
    private boolean forceImageRefresh;

    public String getImageURL(Context ctx) {
        if (imageURL == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(Statics.IMAGE_URL).append("golfgroup")
                    .append(SharedUtil.getGolfGroup(ctx).getGolfGroupID()).append("/player/");
            sb.append("t");
            sb.append(playerID).append(".jpg");
            imageURL = sb.toString();
        }
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isForceImageRefresh() {
        return forceImageRefresh;
    }

    public void setForceImageRefresh(boolean forceImageRefresh) {
        this.forceImageRefresh = forceImageRefresh;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public GcmDeviceDTO getGcmDevice() {
        return gcmDevice;
    }

    public void setGcmDevice(GcmDeviceDTO gcmDevice) {
        this.gcmDevice = gcmDevice;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public int getAge() {

        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNumberOfTournaments() {
        return numberOfTournaments;
    }

    public void setNumberOfTournaments(int numberOfTournaments) {
        this.numberOfTournaments = numberOfTournaments;
    }

    public String getFullName () {
        return firstName + " " + lastName;
    }

    public ParentDTO getParent() {
        return parent;
    }

    public void setParent(ParentDTO parent) {
        this.parent = parent;
    }

    public List<LeaderBoardDTO> getScores() {
        return scores;
    }

    public void setScores(List<LeaderBoardDTO> scores) {
        this.scores = scores;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public long getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(long dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getYearJoined() {
        return yearJoined;
    }

    public void setYearJoined(int yearJoined) {
        this.yearJoined = yearJoined;
    }

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

    @Override
    public int compareTo(PlayerDTO p) {

        switch (sortType) {
            case SORT_BY_AGE:
                if (getAge() == p.getAge()) return 0;
                if (getAge() < p.getAge()) return -1;
                if (getAge() > p.getAge()) return 1;
                break;
            case SORT_BY_NAME:
                String name = lastName + firstName;
                String another = p.getLastName() + p.getFirstName();
                return name.compareTo(another);
        }
        return 0;
    }

    public static final int SORT_BY_AGE = 1, SORT_BY_NAME = 2;


}
