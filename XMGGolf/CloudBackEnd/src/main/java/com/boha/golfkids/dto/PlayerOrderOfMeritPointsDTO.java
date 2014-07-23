/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.golfkids.dto;

/**
 *
 * @author aubreyM
 */
public class PlayerOrderOfMeritPointsDTO {
    private int winCount;
    private int top5Count, top3Count;
    private int top10Count;
    private int top20Count;
    private int top30Count;
    private int top40Count;
    private int top50Count, top100Count;
    private int playerID;
    private int tiedFirstCount, totalPoints;
    private long startDate, endDate;

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getTop5Count() {
        return top5Count;
    }

    public void setTop5Count(int top5Count) {
        this.top5Count = top5Count;
    }

    public int getTop3Count() {
        return top3Count;
    }

    public void setTop3Count(int top3Count) {
        this.top3Count = top3Count;
    }

    public int getTop10Count() {
        return top10Count;
    }

    public void setTop10Count(int top10Count) {
        this.top10Count = top10Count;
    }

    public int getTop20Count() {
        return top20Count;
    }

    public void setTop20Count(int top20Count) {
        this.top20Count = top20Count;
    }

    public int getTop30Count() {
        return top30Count;
    }

    public void setTop30Count(int top30Count) {
        this.top30Count = top30Count;
    }

    public int getTop40Count() {
        return top40Count;
    }

    public void setTop40Count(int top40Count) {
        this.top40Count = top40Count;
    }

    public int getTop50Count() {
        return top50Count;
    }

    public void setTop50Count(int top50Count) {
        this.top50Count = top50Count;
    }

    public int getTop100Count() {
        return top100Count;
    }

    public void setTop100Count(int top100Count) {
        this.top100Count = top100Count;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getTiedFirstCount() {
        return tiedFirstCount;
    }

    public void setTiedFirstCount(int tiedFirstCount) {
        this.tiedFirstCount = tiedFirstCount;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }
    
    
}
