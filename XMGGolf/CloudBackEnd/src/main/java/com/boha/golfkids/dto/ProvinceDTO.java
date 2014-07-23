/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.dto;

import com.boha.golfkids.data.Province;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aubrey Malabie
 */
public class ProvinceDTO  implements Serializable{

    private int provinceID, countryID;
    private double latitude;
    private double longitude;
    private String provinceName, webKey;
    private List<ClubDTO> clubs = new ArrayList<>();
    private List<CityDTO> cityList = new ArrayList<>();

    public ProvinceDTO(Province a) {
        provinceID = a.getProvinceID();
        latitude = a.getLatitude();
        longitude = a.getLongitude();
        provinceName = a.getProvinceName();
        countryID = a.getCountry().getCountryID();
        webKey = a.getWebKey();

    }

    public List<CityDTO> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityDTO> cityList) {
        this.cityList = cityList;
    }

    public int getProvinceID() {
        return provinceID;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getWebKey() {
        return webKey;
    }

    public void setWebKey(String webKey) {
        this.webKey = webKey;
    }

    public void setProvinceID(int provinceID) {
        this.provinceID = provinceID;
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

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public List<ClubDTO> getClubs() {
        return clubs;
    }

    public void setClubs(List<ClubDTO> clubs) {
        this.clubs = clubs;
    }
}
