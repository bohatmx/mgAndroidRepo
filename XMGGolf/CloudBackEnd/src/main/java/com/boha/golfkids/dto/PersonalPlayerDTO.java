/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.dto;

import com.boha.golfkids.data.PersonalPlayer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aubreyM
 */
public class PersonalPlayerDTO {

    private String firstName, lastName, email, cell, pin;
    private int personalPlayerID;
    private List<PersonalScoreDTO> personalScoreList;
    private int countryID, clubID;
    private String countryName, countryCode, clubName;
    private GcmDeviceDTO gcmDevice;
    public PersonalPlayerDTO(PersonalPlayer a) {
        firstName = a.getFirstName();
        lastName = a.getLastName();
        email = a.getEmail();
        cell = a.getCellphone();
        personalPlayerID = a.getPersonalPlayerID();
        personalScoreList = new ArrayList<>();
        
        if (a.getCountry() != null) {
            countryCode = a.getCountry().getCountryCode();
            countryID = a.getCountry().getCountryID();
        }
        if (a.getClub() != null) {
            clubID = a.getClub().getClubID();
            clubName = a.getClub().getClubName();
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public GcmDeviceDTO getGcmDevice() {
        return gcmDevice;
    }

    public void setGcmDevice(GcmDeviceDTO gcmDevice) {
        this.gcmDevice = gcmDevice;
    }

    
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
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

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public int getPersonalPlayerID() {
        return personalPlayerID;
    }

    public void setPersonalPlayerID(int personalPlayerID) {
        this.personalPlayerID = personalPlayerID;
    }

    public List<PersonalScoreDTO> getPersonalScoreList() {
        return personalScoreList;
    }

    public void setPersonalScoreList(List<PersonalScoreDTO> personalScoreList) {
        this.personalScoreList = personalScoreList;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public int getClubID() {
        return clubID;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
}
