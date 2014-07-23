/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.golfkids.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author aubreyM
 */
@Entity
@Table(name = "tourneyScoreByRound")
@NamedQueries({
    @NamedQuery(name = "TourneyScoreByRound.getScoreTotals", 
            query = "SELECT t FROM TourneyScoreByRound t "
                    + "where t.leaderBoard = :t"),
    
    @NamedQuery(name = "TourneyScoreByRound.getByTourneyAgeGroup", 
            query = "SELECT t FROM TourneyScoreByRound t where t.leaderBoard.ageGroup.ageGroupID = :aID "
                    + "and t.leaderBoard.tournament.tournamentID = :tID "
                    + " order by t.leaderBoard.player.playerID, t.golfRound"),
    
    @NamedQuery(name = "TourneyScoreByRound.getByTourney", 
            query = "SELECT t FROM TourneyScoreByRound t "
                    + "where t.leaderBoard.tournament.tournamentID = :id "
                    + " order by t.leaderBoard.player.playerID, t.golfRound"),

    @NamedQuery(name = "TourneyScoreByRound.getByPlayer",
            query = "select a from TourneyScoreByRound a "
                    + "where a.leaderBoard.player.playerID = :id "
                    + "order by a.leaderBoard.tournament.tournamentID, a.golfRound"),
    
    @NamedQuery(name = "TourneyScoreByRound.getByTourneyPlayer",
            query = "select a from TourneyScoreByRound a "
                    + "where a.leaderBoard.tournament.tournamentID = :tID "
                    + "and a.leaderBoard.player.playerID = :pID "
                    + "order by a.golfRound"),
    
    @NamedQuery(name = "TourneyScoreByRound.removeTourneyPlayer",
            query = "delete from TourneyScoreByRound a "
                    + "where a.tournamentIDx = :tID "
                    + "and a.leaderBoard.player.playerID = :pID")
})


public class TourneyScoreByRound implements Serializable {
    @Column(name = "score1")
    private int score1;
    @Column(name = "score2")
    private int score2;
    @Column(name = "score3")
    private int score3;
    @Column(name = "score4")
    private int score4;
    @Column(name = "score5")
    private int score5;
    @Column(name = "score6")
    private int score6;
    @Column(name = "score7")
    private int score7;
    @Column(name = "score8")
    private int score8;
    @Column(name = "score9")
    private int score9;
    @Column(name = "score10")
    private int score10;
    @Column(name = "score11")
    private int score11;
    @Column(name = "score12")
    private int score12;
    @Column(name = "score13")
    private int score13;
    @Column(name = "score14")
    private int score14;
    @Column(name = "score15")
    private int score15;
    @Column(name = "score16")
    private int score16;
    @Column(name = "score17")
    private int score17;
    @Column(name = "score18")
    private int score18;
    @Column(name = "golfRound")
    private int golfRound;
    @Column(name = "totalScore")
    private int totalScore;
     @Column(name = "points1")
    private int points1;
    @Column(name = "points2")
    private int points2;
    @Column(name = "points3")
    private int points3;
    @Column(name = "points4")
    private int points4;
    @Column(name = "points5")
    private int points5;
    @Column(name = "points6")
    private int points6;
    @Column(name = "points7")
    private int points7;
    @Column(name = "points8")
    private int points8;
    @Column(name = "points9")
    private int points9;
    @Column(name = "points10")
    private int points10;
    @Column(name = "points11")
    private int points11;
    @Column(name = "points12")
    private int points12;
    @Column(name = "points13")
    private int points13;
    @Column(name = "points14")
    private int points14;
    @Column(name = "points15")
    private int points15;
    @Column(name = "points16")
    private int points16;
    @Column(name = "points17")
    private int points17;
    @Column(name = "points18")
    private int points18;
    @Column(name = "totalPoints")
    private int totalPoints;
    @Column(name = "teeTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date teeTime;
    @Column(name = "scoringComplete")
    private int scoringComplete;
    @Basic(optional = false)
    
    @Column(name = "tee")
    private int tee;
    @JoinColumn(name = "leaderBoardID", referencedColumnName = "leaderBoardID")
    @ManyToOne(optional = false)
    private LeaderBoard leaderBoard;
    @JoinColumn(name = "clubCourseID", referencedColumnName = "clubCourseID")
    @ManyToOne(optional = false)
    private ClubCourse clubCourse;
    @Basic(optional = false)
    
    @Column(name = "holesPerRound")
    private int holesPerRound;
    @Basic(optional = false)
    
    @Column(name = "par")
    private int par;
    @Basic(optional = false)
    
    @Column(name = "tournamentIDx")
    private int tournamentIDx;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tourneyScoreByRoundID")
    private int tourneyScoreByRoundID;
    

    public TourneyScoreByRound() {
    }

    public TourneyScoreByRound(int tourneyScoreByRoundID) {
        this.tourneyScoreByRoundID = tourneyScoreByRoundID;
    }

    public int getTourneyScoreByRoundID() {
        return tourneyScoreByRoundID;
    }

    public void setTourneyScoreByRoundID(int tourneyScoreByRoundID) {
        this.tourneyScoreByRoundID = tourneyScoreByRoundID;
    }
   
    @Override
    public String toString() {
        return "com.boha.golfkids.data.TourneyScoreByRound[ tourneyScoreByRoundID=" + tourneyScoreByRoundID + " ]";
    }
    public int getTournamentIDx() {
        return tournamentIDx;
    }
    public void setTournamentIDx(int tournamentIDx) {
        this.tournamentIDx = tournamentIDx;
    }

    public int getHolesPerRound() {
        return holesPerRound;
    }

    public void setHolesPerRound(int holesPerRound) {
        this.holesPerRound = holesPerRound;
    }

    public int getScoringComplete() {
        return scoringComplete;
    }

    public void setScoringComplete(int scoringComplete) {
        this.scoringComplete = scoringComplete;
    }

    public int getPoints1() {
        return points1;
    }

    public void setPoints1(int points1) {
        this.points1 = points1;
    }

    public int getPoints2() {
        return points2;
    }

    public void setPoints2(int points2) {
        this.points2 = points2;
    }

    public int getPoints3() {
        return points3;
    }

    public void setPoints3(int points3) {
        this.points3 = points3;
    }

    public int getPoints4() {
        return points4;
    }

    public void setPoints4(int points4) {
        this.points4 = points4;
    }

    public int getPoints5() {
        return points5;
    }

    public void setPoints5(int points5) {
        this.points5 = points5;
    }

    public int getPoints6() {
        return points6;
    }

    public void setPoints6(int points6) {
        this.points6 = points6;
    }

    public int getPoints7() {
        return points7;
    }

    public void setPoints7(int points7) {
        this.points7 = points7;
    }

    public int getPoints8() {
        return points8;
    }

    public void setPoints8(int points8) {
        this.points8 = points8;
    }

    public int getPoints9() {
        return points9;
    }

    public void setPoints9(int points9) {
        this.points9 = points9;
    }

    public int getPoints10() {
        return points10;
    }

    public void setPoints10(int points10) {
        this.points10 = points10;
    }

    public int getPoints11() {
        return points11;
    }

    public void setPoints11(int points11) {
        this.points11 = points11;
    }

    public int getPoints12() {
        return points12;
    }

    public void setPoints12(int points12) {
        this.points12 = points12;
    }

    public int getPoints13() {
        return points13;
    }

    public void setPoints13(int points13) {
        this.points13 = points13;
    }

    public int getPoints14() {
        return points14;
    }

    public void setPoints14(int points14) {
        this.points14 = points14;
    }

    public int getPoints15() {
        return points15;
    }

    public void setPoints15(int points15) {
        this.points15 = points15;
    }

    public int getPoints16() {
        return points16;
    }

    public void setPoints16(int points16) {
        this.points16 = points16;
    }

    public int getPoints17() {
        return points17;
    }

    public void setPoints17(int points17) {
        this.points17 = points17;
    }

    public int getPoints18() {
        return points18;
    }

    public void setPoints18(int points18) {
        this.points18 = points18;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }
    public ClubCourse getClubCourse() {
        return clubCourse;
    }
    public void setClubCourse(ClubCourse clubCourse) {
        this.clubCourse = clubCourse;
    }
    public LeaderBoard getLeaderBoard() {
        return leaderBoard;
    }
    public void setLeaderBoard(LeaderBoard leaderBoard) {
        this.leaderBoard = leaderBoard;
    }

    public Date getTeeTime() {
        return teeTime;
    }

    public void setTeeTime(Date teeTime) {
        this.teeTime = teeTime;
    }

    public int getTee() {
        return tee;
    }

    public void setTee(int tee) {
        this.tee = tee;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public int getScore3() {
        return score3;
    }

    public void setScore3(int score3) {
        this.score3 = score3;
    }

    public int getScore4() {
        return score4;
    }

    public void setScore4(int score4) {
        this.score4 = score4;
    }

    public int getScore5() {
        return score5;
    }

    public void setScore5(int score5) {
        this.score5 = score5;
    }

    public int getScore6() {
        return score6;
    }

    public void setScore6(int score6) {
        this.score6 = score6;
    }

    public int getScore7() {
        return score7;
    }

    public void setScore7(int score7) {
        this.score7 = score7;
    }

    public int getScore8() {
        return score8;
    }

    public void setScore8(int score8) {
        this.score8 = score8;
    }

    public int getScore9() {
        return score9;
    }

    public void setScore9(int score9) {
        this.score9 = score9;
    }

    public int getScore10() {
        return score10;
    }

    public void setScore10(int score10) {
        this.score10 = score10;
    }

    public int getScore11() {
        return score11;
    }

    public void setScore11(int score11) {
        this.score11 = score11;
    }

    public int getScore12() {
        return score12;
    }


    public void setScore12(int score12) {
        this.score12 = score12;
    }

    public int getScore13() {
        return score13;
    }

    public void setScore13(int score13) {
        this.score13 = score13;
    }

    public int getScore14() {
        return score14;
    }

    public void setScore14(int score14) {
        this.score14 = score14;
    }

    public int getScore15() {
        return score15;
    }

    public void setScore15(int score15) {
        this.score15 = score15;
    }

    public int getScore16() {
        return score16;
    }

    public void setScore16(int score16) {
        this.score16 = score16;
    }

    public int getScore17() {
        return score17;
    }

    public void setScore17(int score17) {
        this.score17 = score17;
    }

    public int getScore18() {
        return score18;
    }

    public void setScore18(int score18) {
        this.score18 = score18;
    }

    public int getGolfRound() {
        return golfRound;
    }

    public void setGolfRound(int golfRound) {
        this.golfRound = golfRound;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

   

    
}
