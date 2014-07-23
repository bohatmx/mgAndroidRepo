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
public class LeaderBoardCarrierDTO {
    
    private AgeGroupDTO ageGroup;
    
    private List<LeaderBoardDTO> leaderBoardList;
    private List<LeaderBoardTeamDTO> leaderBoardTeamList;

    public AgeGroupDTO getAgeGroup() {
        return ageGroup;
    }

    public List<LeaderBoardTeamDTO> getLeaderBoardTeamList() {
        return leaderBoardTeamList;
    }

    public void setLeaderBoardTeamList(List<LeaderBoardTeamDTO> leaderBoardTeamList) {
        this.leaderBoardTeamList = leaderBoardTeamList;
    }

    public void setAgeGroup(AgeGroupDTO ageGroup) {
        this.ageGroup = ageGroup;
    }

    public List<LeaderBoardDTO> getLeaderBoardList() {
        return leaderBoardList;
    }

    public void setLeaderBoardList(List<LeaderBoardDTO> leaderBoardList) {
        this.leaderBoardList = leaderBoardList;
    }

    
    
    
}
