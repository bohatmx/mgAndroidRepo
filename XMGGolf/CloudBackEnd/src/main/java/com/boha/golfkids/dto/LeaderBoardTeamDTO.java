/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.dto;

import com.boha.golfkids.data.LeaderBoard;
import com.boha.golfkids.data.LeaderBoardTeam;
import com.boha.golfkids.data.Tournament;
import java.util.List;

/**
 *
 * @author Aubrey Malabie
 */
public class LeaderBoardTeamDTO implements Comparable<LeaderBoardTeamDTO> {

    public static final int NO_PAR_STATUS = 9999,
            WINNER_BY_PLAYOFF = 2,
            WINNER_BY_COUNT_OUT = 1, SORT_BY_PAR_STATUS = 1, SORT_BY_TOTAL_POINTS = 2;
    private int leaderBoardTeamID, position, parStatus, tournamentID;
    private TeamDTO team;
    private boolean tied, scoringComplete;
    private int rounds, lastHole, holesPerRound, currentRoundStatus, age;
    private long startDate;
    private String tournamentName, clubName;
    private List<TourneyScoreByRoundTeamDTO> tourneyScoreByRoundTeamList;
    private int winnerFlag, tournamentType;
    private int withDrawn, sortType = SORT_BY_PAR_STATUS;
    private int orderOfMeritPoints;
    private int scoreRound1,
            scoreRound2,
            scoreRound3,
            scoreRound4,
            scoreRound5,
            scoreRound6,
            totalScore;
    private int pointsRound1,
            pointsRound2,
            pointsRound3,
            pointsRound4,
            pointsRound5,
            pointsRound6,
            totalPoints;

    public LeaderBoardTeamDTO() {
    }

    public LeaderBoardTeamDTO(LeaderBoardTeam a) {
        setBasics(a);

    }

    public LeaderBoardTeamDTO(LeaderBoardTeam a, boolean getDetail) {
        setBasics(a);
        if (getDetail) {
            tournamentName = a.getTournament().getTourneyName();
        }
    }

    private void setBasics(LeaderBoardTeam a) {
        leaderBoardTeamID = a.getLeaderBoardTeamID();
        tournamentType = a.getTournament().getTournamentType();

        withDrawn = a.getWithDrawn();
        winnerFlag = a.getWinnerFlag();
        team = new TeamDTO(a.getTeam());
        position = a.getPosition();
        parStatus = a.getParStatus();
        Tournament t = a.getTournament();
        startDate = t.getStartDate().getTime();
        tournamentID = t.getTournamentID();
        holesPerRound = t.getHolesPerRound();
        clubName = t.getClub().getClubName();
        orderOfMeritPoints = a.getOrderOfMeritPoints();

       
        if (a.getTied() > 0) {
            tied = true;
        }
        if (a.getScoringComplete() > 0) {
            scoringComplete = true;
        }

        scoreRound1 = a.getScoreRound1();
        scoreRound2 = a.getScoreRound2();
        scoreRound3 = a.getScoreRound3();
        scoreRound4 = a.getScoreRound4();
        scoreRound5 = a.getScoreRound5();
        scoreRound6 = a.getScoreRound6();
        totalScore = a.getTotalScore();

        pointsRound1 = a.getPointsRound1();
        pointsRound2 = a.getPointsRound2();
        pointsRound3 = a.getPointsRound3();
        pointsRound4 = a.getPointsRound4();
        pointsRound5 = a.getPointsRound5();
        pointsRound6 = a.getPointsRound6();
        totalPoints = a.getTotalPoints();

        rounds = a.getTournament().getGolfRounds();
    }

    public int getPosition() {
        return position;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    
    public int getTournamentType() {
        return tournamentType;
    }

    public void setTournamentType(int tournamentType) {
        this.tournamentType = tournamentType;
    }


    public int getOrderOfMeritPoints() {
        return orderOfMeritPoints;
    }

    public int getAge() {
        return age;
    }

    public void setOrderOfMeritPoints(int orderOfMeritPoints) {
        this.orderOfMeritPoints = orderOfMeritPoints;
    }

    public boolean isScoringComplete() {
        return scoringComplete;
    }

    public void setScoringComplete(boolean scoringComplete) {
        this.scoringComplete = scoringComplete;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWithDrawn() {
        return withDrawn;
    }

    public void setWithDrawn(int withDrawn) {
        this.withDrawn = withDrawn;
    }

    public int getCurrentRoundStatus() {
        return currentRoundStatus;
    }

    public void setCurrentRoundStatus(int currentRoundStatus) {
        this.currentRoundStatus = currentRoundStatus;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public int getWinnerFlag() {
        return winnerFlag;
    }

    public void setWinnerFlag(int winnerFlag) {
        this.winnerFlag = winnerFlag;
    }

    public int getLastHole() {
        return lastHole;
    }

    public void setLastHole(int lastHole) {
        this.lastHole = lastHole;
    }

    public int getHolesPerRound() {
        return holesPerRound;
    }

    public void setHolesPerRound(int holesPerRound) {
        this.holesPerRound = holesPerRound;
    }

  

    public int getRounds() {
        return rounds;
    }

    public long getStartDate() {
        return startDate;
    }

    public int getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

   

    public int getLeaderBoardTeamID() {
        return leaderBoardTeamID;
    }

    public void setLeaderBoardTeamID(int leaderBoardTeamID) {
        this.leaderBoardTeamID = leaderBoardTeamID;
    }

    public List<TourneyScoreByRoundTeamDTO> getTourneyScoreByRoundTeamList() {
        return tourneyScoreByRoundTeamList;
    }

    public void setTourneyScoreByRoundTeamList(List<TourneyScoreByRoundTeamDTO> tourneyScoreByRoundTeamList) {
        this.tourneyScoreByRoundTeamList = tourneyScoreByRoundTeamList;
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

    public boolean isTied() {
        return tied;
    }

    public void setTied(boolean tied) {
        this.tied = tied;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getParStatus() {
        return parStatus;
    }

    public void setParStatus(int parStatus) {
        this.parStatus = parStatus;
    }

    public TeamDTO getTeam() {
        return team;
    }

    public void setTeam(TeamDTO team) {
        this.team = team;
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
        totalScore = scoreRound1 + scoreRound2 + scoreRound4
                + scoreRound5 + scoreRound3 + scoreRound6;
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public int compareTo(LeaderBoardTeamDTO t) {

        switch (sortType) {
            case SORT_BY_PAR_STATUS:
                if (this.getParStatus() < t.getParStatus()) {
                    return 1;
                }
                if (this.getParStatus() > t.getParStatus()) {
                    return -1;
                }
                break;
            case SORT_BY_TOTAL_POINTS:
                if (this.getTotalPoints() < t.getTotalPoints()) {
                    return 1;
                }
                if (this.getTotalPoints() > t.getTotalPoints()) {
                    return -1;
                }
                break;
        }

        return 0;
    }

}
