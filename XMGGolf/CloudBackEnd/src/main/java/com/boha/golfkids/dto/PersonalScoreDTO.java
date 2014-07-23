/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.dto;

import com.boha.golfkids.data.PersonalScore;

/**
 *
 * @author aubreyM
 */
public class PersonalScoreDTO {

    private int personalScoreID;
    private int score1;
    private int score2;
    private int score3;
    private int score4;
    private int score5;
    private int score6;
    private int score7;
    private int score8;
    private int score9;
    private int score10;
    private int score11;
    private int score12;
    private int score13;
    private int score14;
    private int score15;
    private int score16;
    private int score17;
    private int score18;
    private int totalScore;
    private int personalPlayerID, clubID;
    private String clubName;
    private long datePlayed;
    private int fairwaysHit;
    private int greensHit;
    private int numberOfPutts;
    private int timeOfDay;

    public PersonalScoreDTO() {
    }

    public PersonalScoreDTO(PersonalScore a) {
        personalPlayerID = a.getPersonalPlayer().getPersonalPlayerID();
        personalScoreID = a.getPersonalScoreID();
        datePlayed = a.getDatePlayed().getTime();
        fairwaysHit = a.getFairwaysHit();
        greensHit = a.getGreensHit();
        numberOfPutts = a.getNumberOfPutts();
        timeOfDay = a.getTimeOfDay();
        if (a.getClub() != null) {
            clubID = a.getClub().getClubID();
            clubName = a.getClub().getClubName();
        }
        score1 = a.getScore1();
        score2 = a.getScore2();
        score3 = a.getScore3();
        score4 = a.getScore4();
        score5 = a.getScore5();
        score6 = a.getScore6();
        score7 = a.getScore7();
        score8 = a.getScore8();
        score9 = a.getScore9();
        score10 = a.getScore10();
        score11 = a.getScore11();
        score12 = a.getScore12();
        score13 = a.getScore13();
        score14 = a.getScore14();
        score15 = a.getScore15();
        score16 = a.getScore16();
        score17 = a.getScore17();
        score18 = a.getScore18();
        totalScore = a.getTotalScore();
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getClubID() {
        return clubID;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
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

    public int getPersonalScoreID() {
        return personalScoreID;
    }

    public void setPersonalScoreID(int personalScoreID) {
        this.personalScoreID = personalScoreID;
    }

    public int getPersonalPlayerID() {
        return personalPlayerID;
    }

    public void setPersonalPlayerID(int personalPlayerID) {
        this.personalPlayerID = personalPlayerID;
    }

    public long getDatePlayed() {
        return datePlayed;
    }

    public void setDatePlayed(long datePlayed) {
        this.datePlayed = datePlayed;
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

}
