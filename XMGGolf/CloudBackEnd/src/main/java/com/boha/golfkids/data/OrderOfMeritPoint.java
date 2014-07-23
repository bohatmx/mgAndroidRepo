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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author aubreyM
 */
@Entity
@Table(name = "orderOfMeritPoint")
@NamedQueries({
    @NamedQuery(name = "OrderOfMeritPoint.findByGolfGroup", 
            query = "SELECT o FROM OrderOfMeritPoint o where o.golfGroup.golfGroupID = :id")
})
public class OrderOfMeritPoint implements Serializable {
   
    @Basic(optional = false)
    
    @Column(name = "top3")
    private int top3;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "orderOfMeritPointID")
    private int orderOfMeritPointID;
    @Basic(optional = false)
    
    @Column(name = "win")
    private int win;
    @Basic(optional = false)
    
    @Column(name = "tiedFirst")
    private int tiedFirst;
    
    
    @Column(name = "runnerUp")
    private int runnerUp;
    
    @Basic(optional = false)
    
    @Column(name = "top5")
    private int top5;
    @Basic(optional = false)
    
    @Column(name = "top10")
    private int top10;
    @Basic(optional = false)
    
    @Column(name = "top20")
    private int top20;
    @Basic(optional = false)
    
    @Column(name = "top30")
    private int top30;
    @Basic(optional = false)
    
    @Column(name = "top40")
    private int top40;
    @Basic(optional = false)
    
    @Column(name = "top50")
    private int top50;
    @Basic(optional = false)
    
    @Column(name = "top100")
    private int top100;
    @JoinColumn(name = "golfGroupID", referencedColumnName = "golfGroupID")
    @OneToOne(optional = false)
    private GolfGroup golfGroup;

    public OrderOfMeritPoint() {
    }

    public OrderOfMeritPoint(int orderOfMeritPointID) {
        this.orderOfMeritPointID = orderOfMeritPointID;
    }


    public int getOrderOfMeritPointID() {
        return orderOfMeritPointID;
    }

    public void setOrderOfMeritPointID(int orderOfMeritPointID) {
        this.orderOfMeritPointID = orderOfMeritPointID;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getTiedFirst() {
        return tiedFirst;
    }

    public void setTiedFirst(int tiedFirst) {
        this.tiedFirst = tiedFirst;
    }

    public int getTop5() {
        return top5;
    }

    public void setTop5(int top5) {
        this.top5 = top5;
    }

    public int getTop10() {
        return top10;
    }

    public void setTop10(int top10) {
        this.top10 = top10;
    }

    public int getTop20() {
        return top20;
    }

    public void setTop20(int top20) {
        this.top20 = top20;
    }

    public int getTop30() {
        return top30;
    }

    public void setTop30(int top30) {
        this.top30 = top30;
    }

    public int getTop40() {
        return top40;
    }

    public void setTop40(int top40) {
        this.top40 = top40;
    }

    public int getTop50() {
        return top50;
    }

    public int getRunnerUp() {
        return runnerUp;
    }

    public void setRunnerUp(int runnerUp) {
        this.runnerUp = runnerUp;
    }

    public void setTop50(int top50) {
        this.top50 = top50;
    }

    public int getTop100() {
        return top100;
    }

    public void setTop100(int top100) {
        this.top100 = top100;
    }

   

    @Override
    public String toString() {
        return "com.boha.golfkids.data.OrderOfMeritPoint[ orderOfMeritPointID=" + orderOfMeritPointID + " ]";
    }

    public int getTop3() {
        return top3;
    }

    public GolfGroup getGolfGroup() {
        return golfGroup;
    }

    public void setGolfGroup(GolfGroup golfGroup) {
        this.golfGroup = golfGroup;
    }

    public void setTop3(int top3) {
        this.top3 = top3;
    }

   
    
}
