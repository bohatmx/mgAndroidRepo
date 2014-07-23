/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.dto;

import java.util.List;

/**
 *
 * @author aubreyM
 */
public class PhotoUploadDTO {

    public static final String PLAYER_PREFIX = "player";
    public static final String ADMINISTRATOR_PREFIX = "admin";
    public static final String PARENT_PREFIX = "parent";
    public static final String SCORER_PREFIX = "scorer";
    public static final String THUMB_PREFIX = "thumbnails";

    public static final int PICTURES_FULL_SIZE = 1;
    public static final int PICTURES_THUMBNAILS = 2;

    private int golfGroupID, tournamentID, type, playerID, parentID, scorerID, administratorID;
    private List<String> tags;

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getAdministratorID() {
        return administratorID;
    }

    public void setAdministratorID(int administratorID) {
        this.administratorID = administratorID;
    }

   

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getScorerID() {
        return scorerID;
    }

    public void setScorerID(int scorerID) {
        this.scorerID = scorerID;
    }

    public int getGolfGroupID() {
        return golfGroupID;
    }

    public void setGolfGroupID(int golfGroupID) {
        this.golfGroupID = golfGroupID;
    }

    public int getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
   

}
