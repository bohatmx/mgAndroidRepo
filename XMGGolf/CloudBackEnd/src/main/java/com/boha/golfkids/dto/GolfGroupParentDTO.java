/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.dto;

import com.boha.golfkids.data.GolfGroupParent;

/**
 *
 * @author aubreyM
 */
public class GolfGroupParentDTO {
    private int golfGroupParentID;
    
    private long dateRegistered;
    
    private int golfGroupID;

    public int getGolfGroupParentID() {
        return golfGroupParentID;
    }

    public void setGolfGroupParentID(int golfGroupParentID) {
        this.golfGroupParentID = golfGroupParentID;
    }

    public long getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(long dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public int getGolfGroupID() {
        return golfGroupID;
    }

    public void setGolfGroupID(int golfGroupID) {
        this.golfGroupID = golfGroupID;
    }

    public ParentDTO getParent() {
        return parent;
    }

    public void setParent(ParentDTO parent) {
        this.parent = parent;
    }
    
    private ParentDTO parent;
    
    public GolfGroupParentDTO(GolfGroupParent a) {
        golfGroupID = a.getGolfGroup().getGolfGroupID();
        golfGroupParentID = a.getGolfGroupParentID();
        dateRegistered = a.getDateRegistered().getTime();
        parent = new ParentDTO(a.getParent());
    }
}
