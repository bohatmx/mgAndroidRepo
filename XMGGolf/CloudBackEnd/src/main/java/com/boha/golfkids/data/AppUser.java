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
@Table(name = "appUser")
@NamedQueries({
    
    @NamedQuery(name = "AppUser.findByEmail", query = "SELECT a FROM AppUser a WHERE a.email = :email"),
    @NamedQuery(name = "AppUser.findByDateRegistered", query = "SELECT a FROM AppUser a WHERE a.dateRegistered "
            + "= :dateRegistered")})
public class AppUser implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appUser")
    private List<GcmDevice> gcmDeviceList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "appUserID")
    private int appUserID;
    @Basic(optional = false)

    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "dateRegistered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appUser")
    private List<AppUserGroup> appUserGroupList;

    public AppUser() {
    }

    public AppUser(int appUserID) {
        this.appUserID = appUserID;
    }

    public AppUser(int appUserID, String email, Date dateRegistered) {
        this.appUserID = appUserID;
        this.email = email;
        this.dateRegistered = dateRegistered;
    }

    public int getAppUserID() {
        return appUserID;
    }

    public void setAppUserID(int appUserID) {
        this.appUserID = appUserID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public List<AppUserGroup> getAppUserGroupList() {
        return appUserGroupList;
    }

    public void setAppUserGroupList(List<AppUserGroup> appUserGroupList) {
        this.appUserGroupList = appUserGroupList;
    }

    

    @Override
    public String toString() {
        return "com.boha.golfkids.data.AppUser[ appUserID=" + appUserID + " ]";
    }

    public List<GcmDevice> getGcmDeviceList() {
        return gcmDeviceList;
    }

    public void setGcmDeviceList(List<GcmDevice> gcmDeviceList) {
        this.gcmDeviceList = gcmDeviceList;
    }
    
}
