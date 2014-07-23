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
import javax.persistence.Lob;
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
@Table(name = "videoClip")
@NamedQueries({
    @NamedQuery(name = "VideoClip.findByGroup",
            query = "SELECT v FROM VideoClip v "
            + "where v.golfGroup.golfGroupID = :gID "
            + "order by v.videoDate desc"),
    @NamedQuery(name = "VideoClip.findByVideoByTournament",
            query = "SELECT v FROM VideoClip v WHERE v.tournament.tournamentID = :tID "
            + "order by v.videoDate desc"),

    @NamedQuery(name = "VideoClip.findAllInGroup",
            query = "SELECT v FROM VideoClip v "
            + "WHERE v.tournament.tournamentID = :tID "
            + "or v.golfGroup.golfGroupID = :gID "
            + "order by v.videoDate desc"),})
public class VideoClip implements Serializable {
    @Column(name = "length")
    private Integer length;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "videoClipID")
    private int videoClipID;
    @Basic(optional = false)
    
    @Column(name = "videoDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date videoDate;
    @Basic(optional = false)
    
    @Lob
    @Column(name = "comment")
    private String comment;
    @Column(name = "youTubeID")
    private String youTubeID;
    @JoinColumn(name = "golfGroupID", referencedColumnName = "golfGroupID")
    @ManyToOne(optional = false)
    private GolfGroup golfGroup;
    @JoinColumn(name = "tournamentID", referencedColumnName = "tournamentID")
    @ManyToOne
    private Tournament tournament;

    public VideoClip() {
    }

    public VideoClip(int videoClipID) {
        this.videoClipID = videoClipID;
    }

    public VideoClip(int videoClipID, Date videoDate, String comment) {
        this.videoClipID = videoClipID;
        this.videoDate = videoDate;
        this.comment = comment;
    }

    public int getVideoClipID() {
        return videoClipID;
    }

    public void setVideoClipID(int videoClipID) {
        this.videoClipID = videoClipID;
    }

    public Date getVideoDate() {
        return videoDate;
    }

    public void setVideoDate(Date videoDate) {
        this.videoDate = videoDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getYouTubeID() {
        return youTubeID;
    }

    public void setYouTubeID(String youTubeID) {
        this.youTubeID = youTubeID;
    }

    public GolfGroup getGolfGroup() {
        return golfGroup;
    }

    public void setGolfGroup(GolfGroup golfGroup) {
        this.golfGroup = golfGroup;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @Override
    public String toString() {
        return "com.boha.golfkids.data.VideoClip[ videoClipID=" + videoClipID + " ]";
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

}
