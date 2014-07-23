/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.golfkids.data;

import java.io.Serializable;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author aubreyM
 */
@Entity
@Table(name = "agegroup")
@NamedQueries({
    @NamedQuery(name = "AgeGroup.deleteByGolfGroup", 
            query = "delete FROM Agegroup a "
                    + "where a.golfGroup.golfGroupID = :id"),
    @NamedQuery(name = "AgeGroup.findByGolfGroup", 
            query = "SELECT a FROM Agegroup a "
                    + "where a.golfGroup.golfGroupID = :id "
                    + "order by a.groupName"),
    
    @NamedQuery(name = "AgeGroup.findByAge", 
            query = "SELECT a FROM Agegroup a where a.golfGroup.golfGroupID = :gID "
                    + "and (:age between a.ageFrom and a.ageTo) "),
    
    @NamedQuery(name = "AgeGroup.findByAgeBoys", 
            query = "SELECT a FROM Agegroup a where a.golfGroup.golfGroupID = :gID and a.gender = 1 "
                    + "and (:age between a.ageFrom and a.ageTo) "),
    @NamedQuery(name = "AgeGroup.findByAgeGirls", 
            query = "SELECT a FROM Agegroup a where a.golfGroup.golfGroupID = :gID and a.gender = 2 "
                    + "and (:age between a.ageFrom and a.ageTo) "),
    
    @NamedQuery(name = "AgeGroup.findByGender",
            query = "SELECT a FROM Agegroup a "
                    + "where a.golfGroup.golfGroupID = :id "
                    + "and a.gender = :gender order by a.groupName")

})
public class Agegroup implements Serializable {
    @Column(name = "gender")
    private int gender;
    @Column(name = "numberOfHolesPerRound")
    private int numberOfHolesPerRound;
    @Basic(optional = false)
    @Column(name = "ageFrom")
    private int ageFrom;
    @Basic(optional = false)
    @Column(name = "ageTo")
    private int ageTo;
    @OneToMany(mappedBy = "ageGroup")
    private List<LeaderBoard> leaderBoardList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ageGroupID")
    private int ageGroupID;
    @Column(name = "groupName")
    private String groupName;
   
    @JoinColumn(name = "golfGroupID", referencedColumnName = "golfGroupID")
    @ManyToOne(optional = false)
    private GolfGroup golfGroup;

    public Agegroup() {
    }
    public Agegroup(int ageGroupID) {
        this.ageGroupID = ageGroupID;
    }

    public int getAgeGroupID() {
        return ageGroupID;
    }

    public void setAgeGroupID(int ageGroupID) {
        this.ageGroupID = ageGroupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


   

    public GolfGroup getGolfGroup() {
        return golfGroup;
    }

    public void setGolfGroup(GolfGroup golfGroup) {
        this.golfGroup = golfGroup;
    }

    @Override
    public String toString() {
        return "com.boha.golfkids.data.Agegroup[ ageGroupID=" + ageGroupID + " ]";
    }

    public int getAgeFrom() {
        return ageFrom;
    }

    public void setAgeFrom(int ageFrom) {
        this.ageFrom = ageFrom;
    }

    public int getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(int ageTo) {
        this.ageTo = ageTo;
    }

    public List<LeaderBoard> getLeaderBoardList() {
        return leaderBoardList;
    }

    public void setLeaderBoardList(List<LeaderBoard> leaderBoardList) {
        this.leaderBoardList = leaderBoardList;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getNumberOfHolesPerRound() {
        return numberOfHolesPerRound;
    }

    public void setNumberOfHolesPerRound(int numberOfHolesPerRound) {
        this.numberOfHolesPerRound = numberOfHolesPerRound;
    }
    
}
