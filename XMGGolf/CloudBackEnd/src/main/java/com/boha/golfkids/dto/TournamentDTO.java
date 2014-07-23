/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.dto;

import com.boha.golfkids.data.Tournament;
import java.util.List;

/**
 *
 * @author Aubrey Malabie
 */
public class TournamentDTO implements Comparable<TournamentDTO> {

    public TournamentDTO() {
    }

    private int tournamentID, tournamentType;
    private long closingDate;
    private long endDate;
    private int golfRounds, numberOfRegisteredPlayers;
    private int closedForScoringFlag;
    private int closedForRegistrationFlag;
    private long startDate;
    private String tourneyName, clubName;
    private int clubID;
    private int scoringCommencedFlag;
    private int exampleFlag;
    private int useAgeGroups;
    private int clubCourseID, provinceID;
    private int golfGroupID, par = 72, holesPerRound;
    private List<LeaderBoardDTO> scores;
    private List<VolunteerDTO> volunteers;
    private List<TournamentCourseDTO> tournamentCourses;
    
    public TournamentDTO(Tournament a) {
        tournamentID = a.getTournamentID();
        tournamentType = a.getTournamentType();
        closedForScoringFlag = a.getClosedForScoringFlag();
        scoringCommencedFlag = a.getScoringCommencedFlag();
        exampleFlag = a.getExampleFlag();
        useAgeGroups = a.getUseAgeGroups();
        closedForRegistrationFlag = a.getClosedForRegistrationFlag();
        //
        if (a.getClosingDate() != null) {
            closingDate = a.getClosingDate().getTime();
        }
        if (a.getEndDate() != null) {
            endDate = a.getEndDate().getTime();
        }
        golfRounds = a.getGolfRounds();
        holesPerRound = a.getHolesPerRound();
        if (a.getStartDate() != null) {
            startDate = a.getStartDate().getTime();
        }
        tourneyName = a.getTourneyName();
        par = a.getPar();
        if (a.getClub() != null) {
            clubID = a.getClub().getClubID();
            clubName = a.getClub().getClubName();
            provinceID = a.getClub().getProvince().getProvinceID();

        }
        if (a.getGolfGroup() != null) {
            golfGroupID = a.getGolfGroup().getGolfGroupID();
        }

    }

    public int getTournamentType() {
        return tournamentType;
    }

    public void setTournamentType(int tournamentType) {
        this.tournamentType = tournamentType;
    }

    public int getScoringCommencedFlag() {
        return scoringCommencedFlag;
    }

    public void setScoringCommencedFlag(int scoringCommencedFlag) {
        this.scoringCommencedFlag = scoringCommencedFlag;
    }

    
    public int getUseAgeGroups() {
        return useAgeGroups;
    }

    public int getExampleFlag() {
        return exampleFlag;
    }

    public void setExampleFlag(int exampleFlag) {
        this.exampleFlag = exampleFlag;
    }

    public int getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(int provinceID) {
        this.provinceID = provinceID;
    }

    public void setUseAgeGroups(int useAgeGroups) {
        this.useAgeGroups = useAgeGroups;
    }

    
    public int getHolesPerRound() {
        return holesPerRound;
    }

    public List<TournamentCourseDTO> getTournamentCourses() {
        return tournamentCourses;
    }

    public void setTournamentCourses(List<TournamentCourseDTO> tournamentCourses) {
        this.tournamentCourses = tournamentCourses;
    }

    public int getClosedForRegistrationFlag() {
        return closedForRegistrationFlag;
    }

    public void setClosedForRegistrationFlag(int closedForRegistrationFlag) {
        this.closedForRegistrationFlag = closedForRegistrationFlag;
    }

    
   
    public void setHolesPerRound(int holesPerRound) {
        this.holesPerRound = holesPerRound;
    }

    public int getTournamentID() {
        return tournamentID;
    }

    public int getNumberOfRegisteredPlayers() {
        return numberOfRegisteredPlayers;
    }

    public void setNumberOfRegisteredPlayers(int numberOfRegisteredPlayers) {
        this.numberOfRegisteredPlayers = numberOfRegisteredPlayers;
    }

    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public long getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(long closingDate) {
        this.closingDate = closingDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public int getGolfRounds() {
        return golfRounds;
    }

    public void setGolfRounds(int golfRounds) {
        this.golfRounds = golfRounds;
    }

    public int getClosedForScoringFlag() {
        return closedForScoringFlag;
    }

    public void setClosedForScoringFlag(int closedForScoringFlag) {
        this.closedForScoringFlag = closedForScoringFlag;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public String getTourneyName() {
        return tourneyName;
    }

    public int getGolfGroupID() {
        return golfGroupID;
    }

    public void setGolfGroupID(int golfGroupID) {
        this.golfGroupID = golfGroupID;
    }

    public List<LeaderBoardDTO> getScores() {
        return scores;
    }

    public void setScores(List<LeaderBoardDTO> scores) {
        this.scores = scores;
    }

  
    public List<VolunteerDTO> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(List<VolunteerDTO> volunteers) {
        this.volunteers = volunteers;
    }

    public void setTourneyName(String tourneyName) {
        this.tourneyName = tourneyName;
    }

    @Override
    public int compareTo(TournamentDTO t) {

        if (startDate > t.startDate) {
            return 1;
        }
        if (startDate < t.startDate) {
            return -1;
        }

        return 0;
    }

    public int getClubID() {
        return clubID;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
    }

    public int getClubCourseID() {
        return clubCourseID;
    }

    public void setClubCourseID(int clubCourseID) {
        this.clubCourseID = clubCourseID;
    }
}
