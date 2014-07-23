/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.golfkids.data;

import java.io.Serializable;

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

/**
 *
 * @author aubreyM
 */
@Entity
@Table(name = "tournamentCourse")
@NamedQueries({
    @NamedQuery(name = "TournamentCourse.findByGolfGroup", 
            query = "SELECT t FROM TournamentCourse t "
                    + "where t.tournament.golfGroup.golfGroupID = :id "
                    + "order by t.tournament.tournamentID"),
    @NamedQuery(name = "TournamentCourse.findByTourney", 
            query = "SELECT t FROM TournamentCourse t "
                    + "where t.tournament.tournamentID = :id")
    })
public class TournamentCourse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tournamentCourseID")
    private Integer tournamentCourseID;
    @Basic(optional = false)
    @Column(name = "round")
    private int round;
    @JoinColumn(name = "tournamentID", referencedColumnName = "tournamentID")
    @ManyToOne(optional = false)
    private Tournament tournament;
    @JoinColumn(name = "clubCourseID", referencedColumnName = "clubCourseID")
    @ManyToOne(optional = false)
    private ClubCourse clubCourse;

    public TournamentCourse() {
    }

    public TournamentCourse(Integer tournamentCourseID) {
        this.tournamentCourseID = tournamentCourseID;
    }

    public TournamentCourse(Integer tournamentCourseID, int round) {
        this.tournamentCourseID = tournamentCourseID;
        this.round = round;
    }

    public Integer getTournamentCourseID() {
        return tournamentCourseID;
    }

    public void setTournamentCourseID(Integer tournamentCourseID) {
        this.tournamentCourseID = tournamentCourseID;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public ClubCourse getClubCourse() {
        return clubCourse;
    }

    public void setClubCourse(ClubCourse clubCourse) {
        this.clubCourse = clubCourse;
    }

 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tournamentCourseID != null ? tournamentCourseID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TournamentCourse)) {
            return false;
        }
        TournamentCourse other = (TournamentCourse) object;
        if ((this.tournamentCourseID == null && other.tournamentCourseID != null) || (this.tournamentCourseID != null && !this.tournamentCourseID.equals(other.tournamentCourseID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.golfkids.data.TournamentCourse[ tournamentCourseID=" + tournamentCourseID + " ]";
    }
    
}
