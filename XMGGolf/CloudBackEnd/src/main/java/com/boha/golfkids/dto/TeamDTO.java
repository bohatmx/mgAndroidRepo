/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.golfkids.dto;

import com.boha.golfkids.data.Team;
import java.util.List;

/**
 *
 * @author aubreyM
 */
public class TeamDTO {
    private int teamID;
    private String teamName;
    private long dateRegistered;
    private List<LeaderBoardTeamDTO> leaderBoardTeamList;
     private int golfGroupID;
    private List<TeamMemberDTO> teamMemberList;
    
    public TeamDTO(Team a) {
        teamID = a.getTeamID();
        teamName = a.getTeamName();
        dateRegistered = a.getDateRegistered().getTime();
        golfGroupID = a.getGolfGroup().getGolfGroupID();
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public long getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(long dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public List<LeaderBoardTeamDTO> getLeaderBoardTeamList() {
        return leaderBoardTeamList;
    }

    public void setLeaderBoardTeamList(List<LeaderBoardTeamDTO> leaderBoardTeamList) {
        this.leaderBoardTeamList = leaderBoardTeamList;
    }

    public int getGolfGroupID() {
        return golfGroupID;
    }

    public void setGolfGroupID(int golfGroupID) {
        this.golfGroupID = golfGroupID;
    }

    public List<TeamMemberDTO> getTeamMemberList() {
        return teamMemberList;
    }

    public void setTeamMemberList(List<TeamMemberDTO> teamMemberList) {
        this.teamMemberList = teamMemberList;
    }
    
}
