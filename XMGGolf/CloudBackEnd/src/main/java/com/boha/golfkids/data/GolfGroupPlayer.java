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
import javax.persistence.FetchType;
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
@Table(name = "golfGroupPlayer")
@NamedQueries({
    @NamedQuery(name = "GolfGroupPlayer.findByGolfGroup", 
            query = "SELECT g FROM GolfGroupPlayer g "
                    + "where g.golfGroup.golfGroupID = :id"),
    
    @NamedQuery(name = "GolfGroupPlayer.findByGolfGroupPlayer", 
            query = "SELECT g FROM GolfGroupPlayer g where g.player.playerID = :pID "
                    + " and g.golfGroup.golfGroupID = :gID"),
    
    @NamedQuery(name = "GolfGroupPlayer.findByPlayer", 
            query = "SELECT g FROM GolfGroupPlayer g where g.player.playerID = :id"),

    @NamedQuery(name = "GolfGroupPlayer.deleteByGolfGroup", 
            query = "delete FROM GolfGroupPlayer g "
                    + "where g.golfGroup.golfGroupID = :id")
})
public class GolfGroupPlayer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "golfGroupPlayerID")
    private int golfGroupPlayerID;
    @Basic(optional = false)
    @Column(name = "dateRegistered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;
    @JoinColumn(name = "golfGroupID", referencedColumnName = "golfGroupID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GolfGroup golfGroup;
    @JoinColumn(name = "playerID", referencedColumnName = "playerID")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Player player;

    public GolfGroupPlayer() {
    }

    public GolfGroupPlayer(int golfGroupPlayerID) {
        this.golfGroupPlayerID = golfGroupPlayerID;
    }

    public GolfGroupPlayer(int golfGroupPlayerID, Date dateRegistered) {
        this.golfGroupPlayerID = golfGroupPlayerID;
        this.dateRegistered = dateRegistered;
    }

    public int getGolfGroupPlayerID() {
        return golfGroupPlayerID;
    }

    public void setGolfGroupPlayerID(int golfGroupPlayerID) {
        this.golfGroupPlayerID = golfGroupPlayerID;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }



    @Override
    public String toString() {
        return "com.boha.golfkids.data.GolfGroupPlayer[ golfGroupPlayerID=" + golfGroupPlayerID + " ]";
    }
    
}
