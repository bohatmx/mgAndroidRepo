/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.dto;

import com.boha.golfkids.data.VideoClip;

/**
 *
 * @author aubreyM
 */
public class VideoClipDTO {

    private int videoClipID;
    private long videoDate;
    private String comment, tournamentName, golfGroupName;
    private int length;
    private String youTubeID;
    private int golfGroupID;
    private int tournamentID;

    public VideoClipDTO(VideoClip a) {
        videoClipID = a.getVideoClipID();
        videoDate = a.getVideoDate().getTime();
        comment = a.getComment();
        youTubeID = a.getYouTubeID();
        length = a.getLength();
        if (a.getTournament() != null) {
            tournamentName = a.getTournament().getTourneyName();
            tournamentID = a.getTournament().getTournamentID();
        }
        if (a.getGolfGroup() != null) {
            golfGroupID = a.getGolfGroup().getGolfGroupID();
            golfGroupName = a.getGolfGroup().getGolfGroupName();
        }
    }

    public int getVideoClipID() {
        return videoClipID;
    }

    public void setVideoClipID(int videoClipID) {
        this.videoClipID = videoClipID;
    }

    public long getVideoDate() {
        return videoDate;
    }

    public void setVideoDate(long videoDate) {
        this.videoDate = videoDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getGolfGroupName() {
        return golfGroupName;
    }

    public void setGolfGroupName(String golfGroupName) {
        this.golfGroupName = golfGroupName;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getYouTubeID() {
        return youTubeID;
    }

    public void setYouTubeID(String youTubeID) {
        this.youTubeID = youTubeID;
    }

    public int getGolfGroupID() {
        return golfGroupID;
    }

    public void setGolfGroupID(int golfGroupID) {
        this.golfGroupID = golfGroupID;
    }

    public int getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
    }

}
