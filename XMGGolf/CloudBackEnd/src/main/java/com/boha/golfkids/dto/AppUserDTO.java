/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.dto;

import com.boha.golfkids.data.AppUser;
import java.util.List;

/**
 *
 * @author aubreyM
 */
public class AppUserDTO {

    private int appUserID;
    private String email;
    private long dateRegistered;
    private List<GolfGroupDTO> golfGroupList;
    
    public AppUserDTO(AppUser a) {
        appUserID = a.getAppUserID();
        email = a.getEmail();
        dateRegistered = a.getDateRegistered().getTime();
    }

    public int getAppUserID() {
        return appUserID;
    }

    public void setAppUserID(int appUserID) {
        this.appUserID = appUserID;
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

    public List<GolfGroupDTO> getGolfGroupList() {
        return golfGroupList;
    }

    public void setGolfGroupList(List<GolfGroupDTO> golfGroupList) {
        this.golfGroupList = golfGroupList;
    }
}
