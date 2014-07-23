/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.dto;

import com.boha.golfkids.data.Player;
import java.util.List;
import org.joda.time.LocalDateTime;
import org.joda.time.Years;

/**
 *
 * @author Aubrey Malabie
 */
public class PlayerDTO {

    private int playerID;
    private String cellphone;
    private long dateOfBirth;
    private long dateRegistered;
    private String email;
    private String firstName;
    private int gender, age;
    private String lastName;
    private String middleName;
    private String pin;
    private int exampleFlag;
    private int yearJoined;
    private int parentID;
    private int numberOfTournaments;
    private List<LeaderBoardDTO> scores;
    private GcmDeviceDTO gcmDevice;

    public PlayerDTO(Player a) {
        playerID = a.getPlayerID();
        cellphone = a.getCellphone();
        exampleFlag = a.getExampleFlag();
        if (a.getDateOfBirth() != null) {
            dateOfBirth = a.getDateOfBirth().getTime();
        }
        dateRegistered = a.getDateRegistered().getTime();
        email = a.getEmail();
        firstName = a.getFirstName();
        gender = a.getGender();
        lastName = a.getLastName();
        middleName = a.getMiddleName();
        pin = a.getPin();
        yearJoined = a.getYearJoined();
        if (a.getParent() != null) {
            parentID = a.getParent().getParentID();
        }
        setPlayerAge(dateOfBirth);
    }

    private void setPlayerAge(long date) {
        LocalDateTime birthday = new LocalDateTime(date);
        LocalDateTime start = new LocalDateTime();
        Years years = Years.yearsBetween(birthday, start);
        age = years.getYears();
     }
    public int getParentID() {
        return parentID;
    }

    public GcmDeviceDTO getGcmDevice() {
        return gcmDevice;
    }

    public void setGcmDevice(GcmDeviceDTO gcmDevice) {
        this.gcmDevice = gcmDevice;
    }

    public int getNumberOfTournaments() {
        return numberOfTournaments;
    }

    public void setNumberOfTournaments(int numberOfTournaments) {
        this.numberOfTournaments = numberOfTournaments;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
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
        getAge();
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

    public int getAge() {
        LocalDateTime birthday = new LocalDateTime(dateOfBirth);
        LocalDateTime start = new LocalDateTime();
        Years years = Years.yearsBetween(birthday, start);
        age = years.getYears();
        return age;
    }

    public int getExampleFlag() {
        return exampleFlag;
    }

    public void setExampleFlag(int exampleFlag) {
        this.exampleFlag = exampleFlag;
    }
    
}
