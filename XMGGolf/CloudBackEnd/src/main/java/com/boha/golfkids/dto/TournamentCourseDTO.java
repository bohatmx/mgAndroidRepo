/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.dto;

import com.boha.golfkids.data.TournamentCourse;

/**
 *
 * @author aubreyM
 */
public class TournamentCourseDTO {

    private int tournamentCourseID;
    private int round;
    private int tournamentID;
    private ClubCourseDTO clubCourse;
    
    public TournamentCourseDTO(TournamentCourse a) {
        tournamentCourseID = a.getTournamentCourseID();
        round = a.getRound();
        tournamentID = a.getTournament().getTournamentID();
        clubCourse = new ClubCourseDTO(a.getClubCourse());
    }

    public ClubCourseDTO getClubCourse() {
        return clubCourse;
    }

    public void setClubCourse(ClubCourseDTO clubCourse) {
        this.clubCourse = clubCourse;
    }

    public int getTournamentCourseID() {
        return tournamentCourseID;
    }

    public void setTournamentCourseID(int tournamentCourseID) {
        this.tournamentCourseID = tournamentCourseID;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
    }

}
