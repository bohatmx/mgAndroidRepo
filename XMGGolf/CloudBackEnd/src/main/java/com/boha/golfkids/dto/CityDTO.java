/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.dto;

import com.boha.golfkids.data.City;

/**
 *
 * @author aubreyM
 */
public class CityDTO {

    private int cityID;
    private String cityName, webKey;
    private double latitude;
    private double longitude;
    private int provinceID;
    
    public CityDTO(City a) {
        cityID = a.getCityID();
        cityName = a.getCityName();
        latitude = a.getLatitude();
        longitude = a.getLongitude();
        webKey = a.getWebKey();
        provinceID = a.getProvince().getProvinceID();
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public String getWebKey() {
        return webKey;
    }

    public void setWebKey(String webKey) {
        this.webKey = webKey;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public int getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(int provinceID) {
        this.provinceID = provinceID;
    }
    
}
