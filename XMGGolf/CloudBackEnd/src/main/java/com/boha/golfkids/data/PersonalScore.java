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
@Table(name = "personalScore")
@NamedQueries({
    @NamedQuery(name = "PersonalScore.findByPlayer", 
            query = "SELECT p FROM PersonalScore p "
                    + "where p.personalPlayer.personalPlayerID = :id "
                    + "order by p.datePlayed desc"),
    
    @NamedQuery(name = "PersonalScore.findByPeriod", 
            query = "SELECT p FROM PersonalScore p "
                    + "WHERE p.personalPlayer.personalPlayerID = :id and "
                    + "p.datePlayed between :from and :to "
                    + "order by p.datePlayed desc"),
    })
public class PersonalScore implements Serializable {
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
    @Column(name = "totalScore")
    private int totalScore;
    @Column(name = "fairwaysHit")
    private int fairwaysHit;
    @Column(name = "greensHit")
    private int greensHit;
    @Column(name = "numberOfPutts")
    private int numberOfPutts;
    @Column(name = "timeOfDay")
    private int timeOfDay;
    @JoinColumn(name = "clubID", referencedColumnName = "clubID")
    @ManyToOne(optional = false)
    private Club club;
    @Basic(optional = false)
    @Column(name = "datePlayed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePlayed;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "personalScoreID")
    private int personalScoreID;
    @JoinColumn(name = "personalPlayerID", referencedColumnName = "personalPlayerID")
    @ManyToOne
    private PersonalPlayer personalPlayer;

    public PersonalScore() {
    }

    public PersonalScore(int personalScoreID) {
        this.personalScoreID = personalScoreID;
    }

    public PersonalScore(int personalScoreID, int fairwaysHit, int greensHit, int numberOfPutts) {
        this.personalScoreID = personalScoreID;
        this.fairwaysHit = fairwaysHit;
        this.greensHit = greensHit;
        this.numberOfPutts = numberOfPutts;
    }

    public int getPersonalScoreID() {
        return personalScoreID;
    }

    public void setPersonalScoreID(int personalScoreID) {
        this.personalScoreID = personalScoreID;
    }
    public PersonalPlayer getPersonalPlayer() {
        return personalPlayer;
    }
    public void setPersonalPlayer(PersonalPlayer personalPlayer) {
        this.personalPlayer = personalPlayer;
    }
    @Override
    public String toString() {
        return "com.boha.golfkids.data.PersonalScore[ personalScoreID=" + personalScoreID + " ]";
    }

    public Date getDatePlayed() {
        return datePlayed;
    }

    public void setDatePlayed(Date datePlayed) {
        this.datePlayed = datePlayed;
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

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getFairwaysHit() {
        return fairwaysHit;
    }

    public void setFairwaysHit(int fairwaysHit) {
        this.fairwaysHit = fairwaysHit;
    }

    public int getGreensHit() {
        return greensHit;
    }

    public void setGreensHit(int greensHit) {
        this.greensHit = greensHit;
    }

    public int getNumberOfPutts() {
        return numberOfPutts;
    }

    public void setNumberOfPutts(int numberOfPutts) {
        this.numberOfPutts = numberOfPutts;
    }

    public int getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(int timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

  
    
}
