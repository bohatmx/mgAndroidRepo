/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author aubreyM
 */
@Entity
@Table(name = "player")
@NamedQueries({
    @NamedQuery(name = "Player.login",
            query = "SELECT a FROM Player a "
            + " where a.email = :email and a.pin = :pin"),
     @NamedQuery(name = "Player.getSamplePlayers",
            query = "SELECT t FROM Player t, GolfGroupPlayer g "
                    + "where t.exampleFlag > 0 "
                    + "and g.golfGroup.golfGroupID = :id "
                    + "and t.playerID = g.player.playerID"),

    @NamedQuery(name = "Player.findByEmail",
            query = "SELECT a FROM Player a "
            + "where a.email = :email"),

    @NamedQuery(name = "Player.findByTourney",
            query = "SELECT distinct a FROM Player a, LeaderBoard b "
            + "where a.playerID = b.player.playerID "
            + "and b.tournament.tournamentID = :id"),

    @NamedQuery(name = "Player.findByGolfGroup",
            query = "SELECT p FROM Player p, GolfGroupPlayer b "
            + "where p.playerID = b.player.playerID "
            + "and b.golfGroup.golfGroupID = :id "
            + "order by p.lastName, p.firstName")})
public class Player implements Serializable, Comparable<Player> {
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private List<TeamMember> teamMemberList;
    @Column(name = "gender")
    private int gender;
    @Column(name = "yearJoined")
    private int yearJoined;
    @OneToMany(mappedBy = "player")
    private List<GcmDevice> gcmDeviceList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player", fetch = FetchType.LAZY)
    private List<LeaderBoard> leaderBoardList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "playerID")
    private int playerID;
    
    @Column(name = "firstName")
    private String firstName;
    
    @Column(name = "middleName")
    private String middleName;
    
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "dateOfBirth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Column(name = "exampleFlag")
    private int exampleFlag;
    @Column(name = "dateRegistered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;
    @Column(name = "email")
    private String email;
    @Column(name = "cellphone")
    private String cellphone;
    
    @Column(name = "pin")
    private String pin;
    @JoinColumn(name = "parentID", referencedColumnName = "parentID")
    @ManyToOne
    private Parent parent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private List<GolfGroupPlayer> golfGroupPlayerList;

    public Player() {
    }

    public Player(int playerID) {
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public List<GolfGroupPlayer> getGolfGroupPlayerList() {
        return golfGroupPlayerList;
    }

    public void setGolfGroupPlayerList(List<GolfGroupPlayer> golfGroupPlayerList) {
        this.golfGroupPlayerList = golfGroupPlayerList;
    }

    public int getExampleFlag() {
        return exampleFlag;
    }

    public void setExampleFlag(int exampleFlag) {
        this.exampleFlag = exampleFlag;
    }

    @Override
    public String toString() {
        return "com.boha.golfkids.data.Player[ playerID=" + playerID + " ]";
    }

    public List<LeaderBoard> getLeaderBoardList() {
        return leaderBoardList;
    }

    public void setLeaderBoardList(List<LeaderBoard> leaderBoardList) {
        this.leaderBoardList = leaderBoardList;
    }

    @Override
    public int compareTo(Player o) {
        String n = lastName + firstName;
        String n1 = o.getLastName() + o.getFirstName();
        
        return n.compareTo(n1);
    }


    public List<GcmDevice> getGcmDeviceList() {
        return gcmDeviceList;
    }

    public void setGcmDeviceList(List<GcmDevice> gcmDeviceList) {
        this.gcmDeviceList = gcmDeviceList;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getYearJoined() {
        return yearJoined;
    }

    public void setYearJoined(int yearJoined) {
        this.yearJoined = yearJoined;
    }


    public List<TeamMember> getTeamMemberList() {
        return teamMemberList;
    }

    public void setTeamMemberList(List<TeamMember> teamMemberList) {
        this.teamMemberList = teamMemberList;
    }

}
