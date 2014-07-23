/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.dto;

import com.boha.golfkids.data.GolfGroupPlayer;

/**
 *
 * @author aubreyM
 */
public class GolfGroupPlayerDTO {
    
    private long dateRegistered;
    
    private PlayerDTO player;
   
    private int golfGroupID;
    
    public GolfGroupPlayerDTO(GolfGroupPlayer a) {
        dateRegistered = a.getDateRegistered().getTime();
        player = new PlayerDTO(a.getPlayer());
        golfGroupID = a.getGolfGroup().getGolfGroupID();
    }

    public long getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(long dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public PlayerDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }

    public int getGolfGroupID() {
        return golfGroupID;
    }

    public void setGolfGroupID(int golfGroupID) {
        this.golfGroupID = golfGroupID;
    }
}
