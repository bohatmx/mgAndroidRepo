/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.dto;

import com.boha.golfkids.data.Administrator;
import com.boha.golfkids.data.GolfGroup;
import com.boha.golfkids.data.GolfGroupPlayer;
import com.boha.golfkids.data.Tournament;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aubrey Malabie
 */
public class GolfGroupDTO {

    private int golfGroupID, countryID;
    private String cellphone;
    private long dateRegistered;
    private String email;

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<AdministratorDTO> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(List<AdministratorDTO> administrators) {
        this.administrators = administrators;
    }
    private String countryName;
    private String golfGroupName;
    private List<GolfGroupPlayerDTO> golfGroupPlayers;
    private List<AdministratorDTO> administrators;
    private List<TournamentDTO> tournaments;

    public GolfGroupDTO(GolfGroup a) {
        golfGroupID = a.getGolfGroupID();
        cellphone = a.getCellphone();
        dateRegistered = a.getDateRegistered().getTime();
        email = a.getEmail();
        golfGroupName = a.getGolfGroupName();
        
        if (a.getCountry() != null) {
            countryID = a.getCountry().getCountryID();
            countryName = a.getCountry().getCountryName();
        }
        
    }

    public int getGolfGroupID() {
        return golfGroupID;
    }

    public void setGolfGroupID(int golfGroupID) {
        this.golfGroupID = golfGroupID;
    }

    public String getCellphone() {
        return cellphone;
    }

    public List<GolfGroupPlayerDTO> getGolfGroupPlayers() {
        return golfGroupPlayers;
    }

    public void setGolfGroupPlayers(List<GolfGroupPlayerDTO> golfGroupPlayers) {
        this.golfGroupPlayers = golfGroupPlayers;
    }

    public List<TournamentDTO> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<TournamentDTO> tournaments) {
        this.tournaments = tournaments;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public long getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(long dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGolfGroupName() {
        return golfGroupName;
    }

    public void setGolfGroupName(String golfGroupName) {
        this.golfGroupName = golfGroupName;
    }
}
