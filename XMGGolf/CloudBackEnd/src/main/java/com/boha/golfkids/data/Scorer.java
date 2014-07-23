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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author aubreyM
 */
@Entity
@Table(name = "scorer")
@NamedQueries({
    @NamedQuery(name = "Scorer.login", 
            query = "SELECT a FROM Scorer a "
                    + "where a.email = :email and a.pin = :pin"),
 
    @NamedQuery(name = "Scorer.findByEmail", 
            query = "SELECT a FROM Scorer a "
                    + "where a.email = :email"),
    
    @NamedQuery(name = "Scorer.getSampleScorers",
            query = "SELECT t FROM Scorer t where t.exampleFlag > 0 and t.golfGroup.golfGroupID = :id "),
 
    
    @NamedQuery(name = "Scorer.findByGolfGroup", 
            query = "SELECT s FROM Scorer s where s.golfGroup.golfGroupID = :id "
                    + "order by s.lastName, s.firstName")})
public class Scorer implements Serializable {
   
    
    @OneToMany(mappedBy = "scorer")
    private List<GcmDevice> gcmDeviceList;
   
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "scorerID")
    private int scorerID;
    @Basic(optional = false)
    
    @Column(name = "firstName")
    private String firstName;
    @Basic(optional = false)
    
    @Column(name = "lastName")
    private String lastName;
    @Basic(optional = false)
    
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    
    @Column(name = "cellphone")
    private String cellphone;
    @Column(name = "exampleFlag")
    private int exampleFlag;
    
    @Basic(optional = false)
    
    @Column(name = "pin")
    private String pin;
    
    
    @Basic(optional = false)
    
    @Column(name = "dateRegistered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;
    @JoinColumn(name = "golfGroupID", referencedColumnName = "golfGroupID")
    @ManyToOne(optional = false)
    private GolfGroup golfGroup;

    public Scorer() {
    }

    public Scorer(int scorerID) {
        this.scorerID = scorerID;
    }

    public Scorer(int scorerID, String firstName, String lastName, String email, String cellphone, Date dateRegistered) {
        this.scorerID = scorerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cellphone = cellphone;
        this.dateRegistered = dateRegistered;
    }

    public int getScorerID() {
        return scorerID;
    }

    public void setScorerID(int scorerID) {
        this.scorerID = scorerID;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getExampleFlag() {
        return exampleFlag;
    }

    public void setExampleFlag(int exampleFlag) {
        this.exampleFlag = exampleFlag;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public GolfGroup getGolfGroup() {
        return golfGroup;
    }

    public void setGolfGroup(GolfGroup golfGroup) {
        this.golfGroup = golfGroup;
    }

 

    @Override
    public String toString() {
        return "com.boha.golfkids.data.Scorer[ scorerID=" + scorerID + " ]";
    }

    public List<GcmDevice> getGcmDeviceList() {
        return gcmDeviceList;
    }

    public void setGcmDeviceList(List<GcmDevice> gcmDeviceList) {
        this.gcmDeviceList = gcmDeviceList;
    }

    
}
