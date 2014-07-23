/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.golfkids.data;


import java.io.Serializable;
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

/**
 *
 * @author aubreyM
 */
@Entity
@Table(name = "clubCourse")
@NamedQueries({
    @NamedQuery(name = "ClubCourse.findByClub", 
            query = "SELECT c FROM ClubCourse c "
                    + "where c.club.clubID = :id order by c.courseName"),
    @NamedQuery(name = "ClubCourse.findByCountry",
            query = "select a from ClubCourse a "
                    + "where a.club.province.country.countryID = :id "
                    + "order by a.club.clubID"),
    @NamedQuery(name = "ClubCourse.findByProvince",
            query = "select a from ClubCourse a "
                    + "where a.club.province.provinceID = :id "
                    + "order by a.club.clubID")
    })
public class ClubCourse implements Serializable {
   
    @Column(name = "parHole1")
    private int parHole1;
    @Column(name = "parHole2")
    private int parHole2;
    @Column(name = "parHole3")
    private int parHole3;
    @Column(name = "parHole4")
    private int parHole4;
    @Column(name = "parHole5")
    private int parHole5;
    @Column(name = "parHole6")
    private int parHole6;
    @Column(name = "parHole7")
    private int parHole7;
    @Column(name = "parHole8")
    private int parHole8;
    @Column(name = "parHole9")
    private int parHole9;
    @Column(name = "parHole10")
    private int parHole10;
    @Column(name = "parHole11")
    private int parHole11;
    @Column(name = "parHole12")
    private int parHole12;
    @Column(name = "parHole13")
    private int parHole13;
    @Column(name = "parHole14")
    private int parHole14;
    @Column(name = "parHole15")
    private int parHole15;
    @Column(name = "parHole16")
    private int parHole16;
    @Column(name = "parHole17")
    private int parHole17;
    @Column(name = "parHole18")
    private int parHole18;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clubCourse")
    private List<TourneyScoreByRoundTeam> tourneyScoreByRoundTeamList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clubCourse")
    private List<TournamentCourse> tournamentCourseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clubCourse")
    private List<TourneyScoreByRound> tourneyScoreByRoundList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clubCourseID")
    private int clubCourseID;
    @Basic(optional = false)
    
    @Column(name = "holes")
    private int holes;
    @Basic(optional = false)
    
    @Column(name = "par")
    private int par;
    @Column(name = "courseName")
    private String courseName;
    @JoinColumn(name = "clubID", referencedColumnName = "clubID")
    @ManyToOne(optional = false)
    private Club club;

    public ClubCourse() {
    }

    public ClubCourse(int clubCourseID) {
        this.clubCourseID = clubCourseID;
    }

    public ClubCourse(int clubCourseID, int holes, int par) {
        this.clubCourseID = clubCourseID;
        this.holes = holes;
        this.par = par;
    }

    public int getClubCourseID() {
        return clubCourseID;
    }

    public void setClubCourseID(int clubCourseID) {
        this.clubCourseID = clubCourseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public int getHoles() {
        return holes;
    }

    public void setHoles(int holes) {
        this.holes = holes;
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
    @Override
    public String toString() {
        return "com.boha.golfkids.data.ClubCourse[ clubCourseID=" + clubCourseID + " ]";
    }
    public List<TourneyScoreByRound> getTourneyScoreByRoundList() {
        return tourneyScoreByRoundList;
    }
    public void setTourneyScoreByRoundList(List<TourneyScoreByRound> tourneyScoreByRoundList) {
        this.tourneyScoreByRoundList = tourneyScoreByRoundList;
    }
    public List<TournamentCourse> getTournamentCourseList() {
        return tournamentCourseList;
    }
    public void setTournamentCourseList(List<TournamentCourse> tournamentCourseList) {
        this.tournamentCourseList = tournamentCourseList;
    }
  

    public int getParHole1() {
        return parHole1;
    }

    public void setParHole1(int parHole1) {
        this.parHole1 = parHole1;
    }

    public int getParHole2() {
        return parHole2;
    }

    public void setParHole2(int parHole2) {
        this.parHole2 = parHole2;
    }

    public int getParHole3() {
        return parHole3;
    }

    public void setParHole3(int parHole3) {
        this.parHole3 = parHole3;
    }

    public int getParHole4() {
        return parHole4;
    }

    public void setParHole4(int parHole4) {
        this.parHole4 = parHole4;
    }

    public int getParHole5() {
        return parHole5;
    }

    public void setParHole5(int parHole5) {
        this.parHole5 = parHole5;
    }

    public int getParHole6() {
        return parHole6;
    }

    public void setParHole6(int parHole6) {
        this.parHole6 = parHole6;
    }

    public int getParHole7() {
        return parHole7;
    }

    public void setParHole7(int parHole7) {
        this.parHole7 = parHole7;
    }

    public int getParHole8() {
        return parHole8;
    }

    public void setParHole8(int parHole8) {
        this.parHole8 = parHole8;
    }

    public int getParHole9() {
        return parHole9;
    }

    public void setParHole9(int parHole9) {
        this.parHole9 = parHole9;
    }

    public int getParHole10() {
        return parHole10;
    }

    public void setParHole10(int parHole10) {
        this.parHole10 = parHole10;
    }

    public int getParHole11() {
        return parHole11;
    }

    public void setParHole11(int parHole11) {
        this.parHole11 = parHole11;
    }

    public int getParHole12() {
        return parHole12;
    }

    public void setParHole12(int parHole12) {
        this.parHole12 = parHole12;
    }

    public int getParHole13() {
        return parHole13;
    }

    public void setParHole13(int parHole13) {
        this.parHole13 = parHole13;
    }

    public int getParHole14() {
        return parHole14;
    }

    public void setParHole14(int parHole14) {
        this.parHole14 = parHole14;
    }

    public int getParHole15() {
        return parHole15;
    }


    public void setParHole15(int parHole15) {
        this.parHole15 = parHole15;
    }

    public int getParHole16() {
        return parHole16;
    }

    public void setParHole16(int parHole16) {
        this.parHole16 = parHole16;
    }

    public int getParHole17() {
        return parHole17;
    }

    public void setParHole17(int parHole17) {
        this.parHole17 = parHole17;
    }

    public int getParHole18() {
        return parHole18;
    }

    public void setParHole18(int parHole18) {
        this.parHole18 = parHole18;
    }

    public List<TourneyScoreByRoundTeam> getTourneyScoreByRoundTeamList() {
        return tourneyScoreByRoundTeamList;
    }

    public void setTourneyScoreByRoundTeamList(List<TourneyScoreByRoundTeam> tourneyScoreByRoundTeamList) {
        this.tourneyScoreByRoundTeamList = tourneyScoreByRoundTeamList;
    }
    
}
