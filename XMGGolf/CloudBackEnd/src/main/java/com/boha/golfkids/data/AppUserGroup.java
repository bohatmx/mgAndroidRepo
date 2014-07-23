/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.golfkids.data;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author aubreyM
 */
@Entity
@Table(name = "appUserGroup")
@NamedQueries({
    @NamedQuery(name = "AppUserGroup.findByAppUser", 
            query = "SELECT a FROM AppUserGroup a WHERE a.appUser.appUserID = :id"),
    @NamedQuery(name = "AppUserGroup.findByGolfGroup", 
            query = "SELECT a FROM AppUserGroup a WHERE a.golfGroup.golfGroupID = :id")
})
public class AppUserGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "appUserGroupID")
    private int appUserGroupID;
    
    @Basic(optional = false)
    @Column(name = "dateRegistered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;
    @JoinColumn(name = "appUserID", referencedColumnName = "appUserID")
    @ManyToOne(optional = false)
    private AppUser appUser;
    
    @JoinColumn(name = "golfGroupID", referencedColumnName = "golfGroupID")
    @ManyToOne(optional = false)
    private GolfGroup golfGroup;

    public AppUserGroup() {
    }

    public AppUserGroup(int appUserGroupID) {
        this.appUserGroupID = appUserGroupID;
    }

   
    public int getAppUserGroupID() {
        return appUserGroupID;
    }

    public void setAppUserGroupID(int appUserGroupID) {
        this.appUserGroupID = appUserGroupID;
    }

   

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public GolfGroup getGolfGroup() {
        return golfGroup;
    }

    public void setGolfGroup(GolfGroup golfGroup) {
        this.golfGroup = golfGroup;
    }

   

    @Override
    public String toString() {
        return "com.boha.golfkids.data.AppUserGroup[ appUserGroupID=" + appUserGroupID + " ]";
    }
    
}
