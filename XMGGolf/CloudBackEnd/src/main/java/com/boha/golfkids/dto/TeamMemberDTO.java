/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.dto;

import com.boha.golfkids.data.TeamMember;

/**
 *
 * @author aubreyM
 */
public class TeamMemberDTO {

    private int teamMemberID;
    private int teamID;
    private String teamName;
    private PlayerDTO player;

    public TeamMemberDTO(TeamMember a) {
        teamID = a.getTeam().getTeamID();
        teamName = a.getTeam().getTeamName();
        teamMemberID = a.getTeamMemberID();
        player = new PlayerDTO(a.getPlayer());
    }

    public int getTeamMemberID() {
        return teamMemberID;
    }

    public void setTeamMemberID(int teamMemberID) {
        this.teamMemberID = teamMemberID;
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public PlayerDTO getPlayer() {
        return player;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }

}
