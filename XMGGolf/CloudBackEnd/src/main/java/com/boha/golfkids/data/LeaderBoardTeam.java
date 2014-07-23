/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.golfkids.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author aubreyM
 */
@Entity
@Table(name = "leaderBoardTeam")
@NamedQueries({
    @NamedQuery(name = "LeaderBoardTeam.findAll", query = "SELECT l FROM LeaderBoardTeam l")
   })
public class LeaderBoardTeam implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "leaderBoardTeamID")
    private int leaderBoardTeamID;
    @Column(name = "winnerFlag")
    private int winnerFlag;
    @Basic(optional = false)
    
    @Column(name = "position")
    private int position;
    @Column(name = "tied")
    private int tied;
    @Basic(optional = false)
    
    @Column(name = "parStatus")
    private int parStatus;
    @Basic(optional = false)
    
    @Column(name = "scoreRound1")
    private int scoreRound1;
    @Column(name = "scoreRound2")
    private int scoreRound2;
    @Column(name = "scoreRound3")
    private int scoreRound3;
    @Column(name = "scoreRound4")
    private int scoreRound4;
    @Column(name = "scoreRound5")
    private int scoreRound5;
    @Column(name = "scoreRound6")
    private int scoreRound6;
    @Basic(optional = false)
    
    @Column(name = "totalScore")
    private int totalScore;
    @Basic(optional = false)
    
    @Column(name = "pointsRound1")
    private int pointsRound1;
    @Column(name = "pointsRound2")
    private int pointsRound2;
    @Column(name = "pointsRound3")
    private int pointsRound3;
    @Column(name = "pointsRound4")
    private int pointsRound4;
    @Column(name = "pointsRound5")
    private int pointsRound5;
    @Column(name = "pointsRound6")
    private int pointsRound6;
    @Column(name = "totalPoints")
    private int totalPoints;
    @Column(name = "dateRegistered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;
    @Basic(optional = false)
    
    @Column(name = "orderOfMeritPoints")
    private int orderOfMeritPoints;
    @Column(name = "scoringComplete")
    private int scoringComplete;
    @Column(name = "withDrawn")
    private int withDrawn;
    @JoinColumn(name = "teamID", referencedColumnName = "teamID")
    @ManyToOne(optional = false)
    private Team team;
    @JoinColumn(name = "tournamentID", referencedColumnName = "tournamentID")
    @ManyToOne(optional = false)
    private Tournament tournament;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "leaderBoardTeam")
    private List<TourneyScoreByRoundTeam> tourneyScoreByRoundTeamList;

    public LeaderBoardTeam() {
    }

    public LeaderBoardTeam(int leaderBoardTeamID) {
        this.leaderBoardTeamID = leaderBoardTeamID;
    }

    public LeaderBoardTeam(int leaderBoardTeamID, int position, int parStatus, int scoreRound1, int totalScore, int pointsRound1, int orderOfMeritPoints) {
        this.leaderBoardTeamID = leaderBoardTeamID;
        this.position = position;
        this.parStatus = parStatus;
        this.scoreRound1 = scoreRound1;
        this.totalScore = totalScore;
        this.pointsRound1 = pointsRound1;
        this.orderOfMeritPoints = orderOfMeritPoints;
    }

    public int getLeaderBoardTeamID() {
        return leaderBoardTeamID;
    }

    public void setLeaderBoardTeamID(int leaderBoardTeamID) {
        this.leaderBoardTeamID = leaderBoardTeamID;
    }

    public int getWinnerFlag() {
        return winnerFlag;
    }

    public void setWinnerFlag(int winnerFlag) {
        this.winnerFlag = winnerFlag;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getTied() {
        return tied;
    }

    public void setTied(int tied) {
        this.tied = tied;
    }

    public int getParStatus() {
        return parStatus;
    }

    public void setParStatus(int parStatus) {
        this.parStatus = parStatus;
    }

    public int getScoreRound1() {
        return scoreRound1;
    }

    public void setScoreRound1(int scoreRound1) {
        this.scoreRound1 = scoreRound1;
    }

    public int getScoreRound2() {
        return scoreRound2;
    }

    public void setScoreRound2(int scoreRound2) {
        this.scoreRound2 = scoreRound2;
    }

    public int getScoreRound3() {
        return scoreRound3;
    }

    public void setScoreRound3(int scoreRound3) {
        this.scoreRound3 = scoreRound3;
    }

    public int getScoreRound4() {
        return scoreRound4;
    }

    public void setScoreRound4(int scoreRound4) {
        this.scoreRound4 = scoreRound4;
    }

    public int getScoreRound5() {
        return scoreRound5;
    }

    public void setScoreRound5(int scoreRound5) {
        this.scoreRound5 = scoreRound5;
    }

    public int getScoreRound6() {
        return scoreRound6;
    }

    public void setScoreRound6(int scoreRound6) {
        this.scoreRound6 = scoreRound6;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getPointsRound1() {
        return pointsRound1;
    }

    public void setPointsRound1(int pointsRound1) {
        this.pointsRound1 = pointsRound1;
    }

    public int getPointsRound2() {
        return pointsRound2;
    }

    public void setPointsRound2(int pointsRound2) {
        this.pointsRound2 = pointsRound2;
    }

    public int getPointsRound3() {
        return pointsRound3;
    }

    public void setPointsRound3(int pointsRound3) {
        this.pointsRound3 = pointsRound3;
    }

    public int getPointsRound4() {
        return pointsRound4;
    }

    public void setPointsRound4(int pointsRound4) {
        this.pointsRound4 = pointsRound4;
    }

    public int getPointsRound5() {
        return pointsRound5;
    }

    public void setPointsRound5(int pointsRound5) {
        this.pointsRound5 = pointsRound5;
    }

    public int getPointsRound6() {
        return pointsRound6;
    }

    public void setPointsRound6(int pointsRound6) {
        this.pointsRound6 = pointsRound6;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public int getOrderOfMeritPoints() {
        return orderOfMeritPoints;
    }

    public void setOrderOfMeritPoints(int orderOfMeritPoints) {
        this.orderOfMeritPoints = orderOfMeritPoints;
    }

    public int getScoringComplete() {
        return scoringComplete;
    }

    public void setScoringComplete(int scoringComplete) {
        this.scoringComplete = scoringComplete;
    }

    public int getWithDrawn() {
        return withDrawn;
    }

    public void setWithDrawn(int withDrawn) {
        this.withDrawn = withDrawn;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

  

    public List<TourneyScoreByRoundTeam> getTourneyScoreByRoundTeamList() {
        return tourneyScoreByRoundTeamList;
    }

    public void setTourneyScoreByRoundTeamList(List<TourneyScoreByRoundTeam> tourneyScoreByRoundTeamList) {
        this.tourneyScoreByRoundTeamList = tourneyScoreByRoundTeamList;
    }

   

    @Override
    public String toString() {
        return "com.boha.golfkids.data.LeaderBoardTeam[ leaderBoardTeamID=" + leaderBoardTeamID + " ]";
    }
    
}
