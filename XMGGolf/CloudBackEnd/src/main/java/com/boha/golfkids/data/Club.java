/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author aubreyM
 */
@Entity
@Table(name = "club")
@NamedQueries({
    @NamedQuery(name = "Club.findByCountry",
            query = "SELECT c FROM Club c "
            + "where c.province.country.countryID = :id "
            + "order by c.clubName"),

    @NamedQuery(name = "Club.findByNameInProvince",
            query = "select c from Club c where c.clubName = :clubName "
            + "and c.province.provinceID = :id "),

    @NamedQuery(name = "Club.findByProvince",
            query = "select c from Club c "
            + "where c.province.provinceID = :id order by c.clubName"),})
@NamedNativeQueries({
    @NamedNativeQuery(name = "Club.findWithinRadiusKM",
            query = "select a, ( 6371 * acos( cos( radians(:lat) ) * cos( radians( latitude) ) "
            + "* cos( radians( longitude ) - radians(:lng) ) + sin( radians(:lat) ) "
            + "* sin( radians( latitude ) ) ) ) "
            + "AS distance FROM club HAVING distance < :radius")
})
/*
 //TODO -32.690986
//      26.291829
/*
     for miles, use 3959
 */
public class Club implements Serializable {

    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "club")
    private List<ClubCourse> clubCourseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "club")
    private List<PersonalScore> personalScoreList;
    @OneToMany(mappedBy = "club")
    private List<PersonalPlayer> personalPlayerList;
    @Basic(optional = false)
    @Column(name = "par")
    private int par;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clubID")
    private int clubID;
    @Column(name = "clubName")
    private String clubName;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Column(name = "email")
    private String email;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "address")
    private String address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "club")
    private List<Tournament> tournamentList;

    @JoinColumn(name = "provinceID", referencedColumnName = "provinceID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Province province;

 
    private double distance;
    public Club() {
    }

    public Club(int clubID) {
        this.clubID = clubID;
    }

    public int getClubID() {
        return clubID;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Tournament> getTournamentList() {
        return tournamentList;
    }

    public void setTournamentList(List<Tournament> tournamentList) {
        this.tournamentList = tournamentList;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "com.boha.golfkids.data.Club[ clubID=" + clubID + " ]";
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<PersonalPlayer> getPersonalPlayerList() {
        return personalPlayerList;
    }

    public void setPersonalPlayerList(List<PersonalPlayer> personalPlayerList) {
        this.personalPlayerList = personalPlayerList;
    }

    public List<PersonalScore> getPersonalScoreList() {
        return personalScoreList;
    }

    public void setPersonalScoreList(List<PersonalScore> personalScoreList) {
        this.personalScoreList = personalScoreList;
    }

    public List<ClubCourse> getClubCourseList() {
        return clubCourseList;
    }

    public void setClubCourseList(List<ClubCourse> clubCourseList) {
        this.clubCourseList = clubCourseList;
    }

}
