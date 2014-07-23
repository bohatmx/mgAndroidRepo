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
@Table(name = "tournamentVolunteer")
@NamedQueries({
    @NamedQuery(name = "TournamentVolunteer.findAll", query = "SELECT t FROM TournamentVolunteer t")})
public class TournamentVolunteer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tournamentVolunteerID")
    private Integer tournamentVolunteerID;
    @JoinColumn(name = "tournamentID", referencedColumnName = "tournamentID")
    @ManyToOne(optional = false)
    private Tournament tournament;
    @JoinColumn(name = "volunteerID", referencedColumnName = "volunteerID")
    @ManyToOne
    private Volunteer volunteer;

    public TournamentVolunteer() {
    }

    public TournamentVolunteer(Integer tournamentVolunteerID) {
        this.tournamentVolunteerID = tournamentVolunteerID;
    }

    public Integer getTournamentVolunteerID() {
        return tournamentVolunteerID;
    }

    public void setTournamentVolunteerID(Integer tournamentVolunteerID) {
        this.tournamentVolunteerID = tournamentVolunteerID;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tournamentVolunteerID != null ? tournamentVolunteerID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TournamentVolunteer)) {
            return false;
        }
        TournamentVolunteer other = (TournamentVolunteer) object;
        if ((this.tournamentVolunteerID == null && other.tournamentVolunteerID != null) || (this.tournamentVolunteerID != null && !this.tournamentVolunteerID.equals(other.tournamentVolunteerID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.golfkids.data.TournamentVolunteer[ tournamentVolunteerID=" + tournamentVolunteerID + " ]";
    }
    
}
