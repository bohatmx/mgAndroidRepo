/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.util;

import com.boha.golfkids.data.City;
import com.boha.golfkids.data.Club;
import com.boha.golfkids.data.ClubCourse;
import com.boha.golfkids.data.Country;
import com.boha.golfkids.data.Province;
import com.boha.golfkids.dto.CityDTO;
import com.boha.golfkids.dto.ClubDTO;
import com.boha.golfkids.dto.CountryDTO;
import com.boha.golfkids.dto.ProvinceDTO;
import com.boha.golfkids.util.golfdata.LoaderResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import static com.boha.golfkids.util.LeaderBoardUtil.log;

/**
 *
 * @author aubreyM
 */
public class DataLoader {

    @PersistenceContext
    EntityManager em;

    public LoaderResponseDTO getCountries() throws DataException {
        LoaderResponseDTO r = new LoaderResponseDTO();
        r.setCountryList(new ArrayList<CountryDTO>());
        try {
            Query q = em.createNamedQuery("Country.findAll");
            List<Country> list = q.getResultList();
            for (Country country : list) {
                r.getCountryList().add(new CountryDTO(country));
            }
            log.log(Level.INFO, "processed {0} countries", r.getCountryList().size());

        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            throw new DataException("Failed to get data\n"
                    + getErrorString(e));
        }
        return r;
    }

    public LoaderResponseDTO loadCountries(int countryID,
            List<CountryDTO> stateList) throws DataException {
        System.out.println(",,,,,,,,,,,,,loading countries");
        LoaderResponseDTO r = new LoaderResponseDTO();
        r.setCountryList(new ArrayList<CountryDTO>());
        try {
            int cnt = 0;
            for (CountryDTO c : stateList) {
                Country p = addCountry(c);               
                r.getCountryList().add(new CountryDTO(p));
                cnt++;
            }
            Query q = em.createNamedQuery("Province.findAll", Province.class);
            List<Province> list = q.getResultList();
            for (CountryDTO c: r.getCountryList()) {
                c.setProvinces(new ArrayList<ProvinceDTO>());
                for (Province p : list) {
                    if (c.getCountryID() == p.getCountry().getCountryID()) {
                        c.getProvinces().add(new ProvinceDTO(p));
                    }
                }
            }
            log.log(Level.INFO, "processed {0} countries....", cnt);

        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            throw new DataException("Failed to load data\n"
                    + getErrorString(e));
        }
        return r;
    }

    public LoaderResponseDTO loadProvinces(int countryID,
            List<ProvinceDTO> stateList) throws DataException {
        log.log(Level.INFO, "Loading provinces, countryID = {0} states: {1}", 
                new Object[]{countryID, stateList.size()});
        LoaderResponseDTO r = new LoaderResponseDTO();
        r.setProvinceList(new ArrayList<com.boha.golfkids.dto.ProvinceDTO>());
        try {
            Country country = em.find(Country.class, countryID);
            for (ProvinceDTO c : stateList) {
                Province p = addProvince(country, c);
                r.getProvinceList().add(new ProvinceDTO(p));
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            throw new DataException("Failed to load data\n"
                    + getErrorString(e));
        }
        return r;
    }

    public LoaderResponseDTO loadCities(int provinceID, List<CityDTO> cityList) throws DataException {
        LoaderResponseDTO r = new LoaderResponseDTO();
        r.setCityList(new ArrayList<com.boha.golfkids.dto.CityDTO>());
        try {
            Province p = em.find(Province.class, provinceID);
            int cnt = 0;
            for (CityDTO c : cityList) {
                City city = addCity(p, c);
                r.getCityList().add(new CityDTO(city));
                cnt++;
            }
            log.log(Level.INFO, "processed {0} cities", cnt);
        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            throw new DataException("Failed to load data\n"
                    + getErrorString(e));
        }
        return r;
    }

    public LoaderResponseDTO loadClubs(int provinceID,
            List<ClubDTO> courseList) throws DataException {
        LoaderResponseDTO r = new LoaderResponseDTO();
        r.setClubList(new ArrayList<ClubDTO>());
        try {
            Province p = em.find(Province.class, provinceID);
            int cnt = 0;
            for (ClubDTO crs : courseList) {
                Club c = addClub(p, crs);
                r.getClubList().add(new ClubDTO(c));
                cnt++;
            }

            log.log(Level.INFO, "processed {0} clubs", cnt);
        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            throw new DataException("Failed to load data\n"
                    + getErrorString(e));
        }
        return r;
    }

    private Club addClub(Province province, ClubDTO c) throws DataException {
        Club club = null;
        Query q = em.createNamedQuery("Club.findByNameInProvince", Club.class);
        try {
            q.setParameter("clubName", c.getClubName());
            q.setParameter("id", province.getProvinceID());
            club = (Club) q.getSingleResult();
            if (club != null) {
                log.log(Level.OFF, "This clus/course already exists: {0}", club.getClubName());
                return club;
            }
        } catch (NoResultException e) {

        }
        try {
            club = new Club();
            club.setClubName(c.getClubName());
            club.setProvince(province);
            club.setLatitude(c.getLatitude());
            club.setLongitude(c.getLongitude());

            em.persist(club);
            //make clubcourse .....
            club = (Club) q.getSingleResult();

            ClubCourse cc = new ClubCourse();
            cc.setClub(club);
            cc.setCourseName(c.getClubName());
            cc.setPar(72);
            cc.setHoles(18);
            cc.setParHole1(4);
            cc.setParHole2(4);
            cc.setParHole3(4);
            cc.setParHole4(4);
            cc.setParHole5(4);
            cc.setParHole6(4);
            cc.setParHole7(4);
            cc.setParHole8(4);
            cc.setParHole9(4);
            cc.setParHole10(4);
            cc.setParHole11(4);
            cc.setParHole12(4);
            cc.setParHole13(4);
            cc.setParHole14(4);
            cc.setParHole15(4);
            cc.setParHole16(4);
            cc.setParHole17(4);
            cc.setParHole18(4);
            em.persist(cc);
            log.log(Level.INFO, "Club + clubCourse added: {0} - {1}",
                    new Object[]{province.getProvinceName(), c.getClubName()});
        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            throw new DataException("Failed to add club: " + c.getClubName() + "\n"
                    + getErrorString(e));
        }
        return club;
    }

    public City addCity(Province province, CityDTO c) throws DataException {
        City city = null;
        Query q = em.createNamedQuery("City.findCityByNameInProvince", City.class);
        try {
            q.setParameter("cityName", c.getCityName());
            q.setParameter("id", province.getProvinceID());
            city = (City) q.getSingleResult();
            if (city != null) {
                log.log(Level.INFO, "city already exists: {0}", city.getCityName());
                return city;
            }
        } catch (NoResultException e) {

        }
        try {
            city = new City();
            city.setCityName(c.getCityName());
            city.setProvince(province);
            city.setLatitude(c.getLatitude());
            city.setLongitude(c.getLongitude());
            city.setWebKey(c.getWebKey());

            em.persist(city);
            city = (City) q.getSingleResult();
            log.log(Level.INFO, "City added: {0} - {1}",
                    new Object[]{city.getProvince().getProvinceName(), city.getCityName()});
        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            throw new DataException("Failed to add city: " + c.getCityName() + "\n"
                    + getErrorString(e));
        }

        return city;
    }

    private Province addProvince(Country country, ProvinceDTO state) throws DataException {
        Province province = null;
        Query q = em.createNamedQuery("Province.findByNameInCountry", Province.class);
        try {
            q.setParameter("provinceName", state.getProvinceName());
            q.setParameter("id", country.getCountryID());
            province = (Province) q.getSingleResult();
            if (province != null) {
                log.log(Level.INFO, "Province/State already exists: {0} {1}",
                        new Object[]{province.getCountry().getCountryName(), province.getProvinceName()});
                return province;
            }
        } catch (NoResultException e) {

        }
        try {
            province = new Province();
            province.setProvinceName(state.getProvinceName());
            province.setCountry(country);
            province.setWebKey(state.getWebKey());
            em.persist(province);
            province = (Province) q.getSingleResult();
            log.log(Level.INFO, "state/province added - {0}", province.getProvinceName());

        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            throw new DataException("Failed to add province:" + state.getProvinceName() + "\n"
                    + getErrorString(e));
        }

        return province;
    }

    public LoaderResponseDTO loadCountries(List<CountryDTO> list) throws DataException {
        LoaderResponseDTO w = new LoaderResponseDTO();
        
        w.setCountryList(new ArrayList<CountryDTO>());
        for (CountryDTO c : list) {
            Country x = addCountry(c);
            w.getCountryList().add(new CountryDTO(x));
        }
        Query q = em.createNamedQuery("Province.findAll", Province.class);
        List<Province> pList = q.getResultList();
        for (CountryDTO c : w.getCountryList()) {
            c.setProvinces(new ArrayList<ProvinceDTO>());
            for (Province p : pList) {
                if (c.getCountryID() == p.getCountry().getCountryID()) {
                    c.getProvinces().add(new ProvinceDTO(p));
                }
            }
        }
        return w;
    }

    private Country addCountry(CountryDTO c) throws DataException {
        Country country = null;
        Query q = em.createNamedQuery("Country.findByName", Country.class);
        try {
            q.setParameter("countryName", c.getCountryName());
            country = (Country) q.getSingleResult();
            if (country != null) {
                log.log(Level.WARNING, "Country already exists: {0}", country.getCountryName());
                return country;
            }
        } catch (NoResultException e) {

        }
        try {
            country = new Country();
            country.setCountryName(c.getCountryName());
            country.setCountryCode(c.getCountryCode());
            em.persist(country);
            country = (Country) q.getSingleResult();
            log.log(Level.INFO, "######################## country added: {0} - {1}",
                    new Object[]{country.getCountryCode(), country.getCountryName()});
        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            throw new DataException("Failed to add country: " + c.getCountryName() + "\n"
                    + getErrorString(e));
        }

        return country;
    }

    public LoaderResponseDTO getStateCities(int provinceID) throws DataException {
        LoaderResponseDTO r = new LoaderResponseDTO();
        r.setCityList(new ArrayList<CityDTO>());
        try {
            Query q = em.createNamedQuery("City.findByProvince", City.class);
            q.setParameter("id", provinceID);
            List<City> list = q.getResultList();
            
            for (City city : list) {
                r.getCityList().add(new CityDTO(city));
            }
            } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            throw new DataException("Failed to update city: \n"
                    + getErrorString(e));
        }
        return r;
        
    }
    public LoaderResponseDTO updateCityCoordinates(int cityID, double lat, double lng) throws DataException {
        LoaderResponseDTO r = new LoaderResponseDTO();
        try {
            City city = em.find(City.class, cityID);
            city.setLatitude(lat);
            city.setLongitude(lng);
            em.merge(city);
            log.log(Level.INFO, "City coordinates updatedOK");
            r.setMessage("Kool...coords upadted");
        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            throw new DataException("Failed to update city: \n"
                    + getErrorString(e));
        }
        return r;
    }
    public static String getErrorString(Exception e) {
        StringBuilder sb = new StringBuilder();
        if (e.getMessage() != null) {
            sb.append(e.getMessage()).append("\n\n");
        }
        if (e.toString() != null) {
            sb.append(e.toString()).append("\n\n");
        }
        StackTraceElement[] s = e.getStackTrace();
        if (s.length > 0) {
            StackTraceElement ss = s[0];
            String method = ss.getMethodName();
            String cls = ss.getClassName();
            int line = ss.getLineNumber();
            sb.append("Class: ").append(cls).append("\n");
            sb.append("Method: ").append(method).append("\n");
            sb.append("Line Number: ").append(line).append("\n");
        }

        return sb.toString();
    }
}
