/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.dto;

import com.boha.golfkids.data.Country;
import com.boha.golfkids.data.Province;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aubrey Malabie
 */
public class CountryDTO  implements Serializable {

    private int countryID;
    private String countryName, countryCode;
    private double latitude;
    private double longitude;
    private List<ProvinceDTO> provinces;

    public CountryDTO(Country a) {
        countryID = a.getCountryID();
        countryName = a.getCountryName();
        countryCode = a.getCountryCode();
        if (a.getProvinceList() != null) {
            provinces = new ArrayList<>();
            for (Province p : a.getProvinceList()) {
                provinces.add(new ProvinceDTO(p));
            }
        }
        
       
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

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

   

    public List<ProvinceDTO> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<ProvinceDTO> provinces) {
        this.provinces = provinces;
    }
}
