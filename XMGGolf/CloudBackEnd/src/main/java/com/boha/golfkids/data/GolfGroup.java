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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author aubreyM
 */
@Entity
@Table(name = "golfGroup")
@NamedQueries({
    @NamedQuery(name = "GolfGroup.findByEmail", 
            query = "SELECT g FROM GolfGroup g "
                    + "where g.email = :email"),

})
public class GolfGroup implements Serializable {
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "golfGroup")
    private List<Team> teamList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "golfGroup")
    private List<AppUserGroup> appUserGroupList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "golfGroup")
    private List<ErrorStoreAndroid> errorStoreAndroidList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "golfGroup")
    private List<GcmDevice> gcmDeviceList;   
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "golfGroup")
    private OrderOfMeritPoint orderOfMeritPoint;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "golfGroup")
    private List<VideoClip> videoClipList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "golfGroupID")
    private int golfGroupID;
    @Basic(optional = false)

    @Column(name = "golfGroupName")
    private String golfGroupName;
    @Basic(optional = false)

    @Column(name = "email")
    private String email;
    @Column(name = "cellphone")
    private String cellphone;
    @Basic(optional = false)
    @Column(name = "dateRegistered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "golfGroup")
    private List<Tournament> tournamentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "golfGroup")
    private List<GolfGroupParent> golfGroupParentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "golfGroup")
    private List<GolfGroupVolunteer> golfGroupVolunteerList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "golfGroup")
    private List<Agegroup> ageGroupList;
    @JoinColumn(name = "countryID", referencedColumnName = "countryID")
    @ManyToOne
    private Country country;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "golfGroup")
    private List<GolfGroupPlayer> golfGroupPlayerList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "golfGroup")
    private List<Scorer> scorerList;
    @OneToMany(mappedBy = "golfGroup")
    private List<Administrator> administratorList;

    public GolfGroup() {
    }

    public GolfGroup(int golfGroupID) {
        this.golfGroupID = golfGroupID;
    }

    public GolfGroup(int golfGroupID, String golfGroupName, String email, Date dateRegistered) {
        this.golfGroupID = golfGroupID;
        this.golfGroupName = golfGroupName;
        this.email = email;
        this.dateRegistered = dateRegistered;
    }

    public List<ErrorStoreAndroid> getErrorStoreAndroidList() {
        return errorStoreAndroidList;
    }

    public void setErrorStoreAndroidList(List<ErrorStoreAndroid> errorStoreAndroidList) {
        this.errorStoreAndroidList = errorStoreAndroidList;
    }

    public int getGolfGroupID() {
        return golfGroupID;
    }

    public void setGolfGroupID(int golfGroupID) {
        this.golfGroupID = golfGroupID;
    }

    public String getGolfGroupName() {
        return golfGroupName;
    }

    public void setGolfGroupName(String golfGroupName) {
        this.golfGroupName = golfGroupName;
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

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public List<Tournament> getTournamentList() {
        return tournamentList;
    }

    public void setTournamentList(List<Tournament> tournamentList) {
        this.tournamentList = tournamentList;
    }

    public List<GolfGroupParent> getGolfGroupParentList() {
        return golfGroupParentList;
    }

    public void setGolfGroupParentList(List<GolfGroupParent> golfGroupParentList) {
        this.golfGroupParentList = golfGroupParentList;
    }

    public List<GolfGroupVolunteer> getGolfGroupVolunteerList() {
        return golfGroupVolunteerList;
    }

    public void setGolfGroupVolunteerList(List<GolfGroupVolunteer> golfGroupVolunteerList) {
        this.golfGroupVolunteerList = golfGroupVolunteerList;
    }

    public List<Agegroup> getAgeGroupList() {
        return ageGroupList;
    }

    public void setAgeGroupList(List<Agegroup> ageGroupList) {
        this.ageGroupList = ageGroupList;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

   

    public List<GolfGroupPlayer> getGolfGroupPlayerList() {
        return golfGroupPlayerList;
    }

    public void setGolfGroupPlayerList(List<GolfGroupPlayer> golfGroupPlayerList) {
        this.golfGroupPlayerList = golfGroupPlayerList;
    }

    public List<Scorer> getScorerList() {
        return scorerList;
    }

    public void setScorerList(List<Scorer> scorerList) {
        this.scorerList = scorerList;
    }

    public List<Administrator> getAdministratorList() {
        return administratorList;
    }

    public void setAdministratorList(List<Administrator> administratorList) {
        this.administratorList = administratorList;
    }

   
    @Override
    public String toString() {
        return "com.boha.golfkids.data.GolfGroup[ golfGroupID=" + golfGroupID + " ]";
    }

    public List<VideoClip> getVideoClipList() {
        return videoClipList;
    }

    public void setVideoClipList(List<VideoClip> videoClipList) {
        this.videoClipList = videoClipList;
    }

    public OrderOfMeritPoint getOrderOfMeritPoint() {
        return orderOfMeritPoint;
    }

    public void setOrderOfMeritPoint(OrderOfMeritPoint orderOfMeritPoint) {
        this.orderOfMeritPoint = orderOfMeritPoint;
    }

    public List<GcmDevice> getGcmDeviceList() {
        return gcmDeviceList;
    }

    public void setGcmDeviceList(List<GcmDevice> gcmDeviceList) {
        this.gcmDeviceList = gcmDeviceList;
    }

    public List<AppUserGroup> getAppUserGroupList() {
        return appUserGroupList;
    }

    public void setAppUserGroupList(List<AppUserGroup> appUserGroupList) {
        this.appUserGroupList = appUserGroupList;
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

   

  
    
}
