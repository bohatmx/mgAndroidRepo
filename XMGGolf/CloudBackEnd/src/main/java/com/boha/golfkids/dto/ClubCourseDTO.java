/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.dto;

import com.boha.golfkids.data.Club;
import com.boha.golfkids.data.ClubCourse;

/**
 *
 * @author aubreyM
 */
public class ClubCourseDTO {

    private int clubCourseID;
    private String courseName, clubName;
    private int holes;
    private int par;
    private int parHole1;
    private int parHole2;
    private int parHole3;
    private int parHole4;
    private int parHole5;
    private int parHole6;
    private int parHole7;
    private int parHole8;
    private int parHole9;
    private int parHole10;
    private int parHole11;
    private int parHole12;
    private int parHole13;
    private int parHole14;
    private int parHole15;
    private int parHole16;
    private int parHole17;
    private int parHole18;
    private int clubID;

    public ClubCourseDTO(ClubCourse a) {
        clubCourseID = a.getClubCourseID();
        courseName = a.getCourseName();
        Club c = a.getClub();
        clubID = c.getClubID();
        clubName = c.getClubName();
        holes = a.getHoles();
        par = a.getPar();

        parHole1 = a.getParHole1();
        parHole2 = a.getParHole2();
        parHole3 = a.getParHole3();
        parHole4 = a.getParHole4();
        parHole5 = a.getParHole5();
        parHole6 = a.getParHole6();
        parHole7 = a.getParHole7();
        parHole8 = a.getParHole8();
        parHole9 = a.getParHole9();
        parHole10 = a.getParHole10();
        parHole11 = a.getParHole11();
        parHole12 = a.getParHole12();
        parHole13 = a.getParHole13();
        parHole14 = a.getParHole14();
        parHole15 = a.getParHole15();
        parHole16 = a.getParHole16();
        parHole17 = a.getParHole17();
        parHole18 = a.getParHole18();
    }

    public int getClubCourseID() {
        return clubCourseID;
    }

    public void setClubCourseID(int clubCourseID) {
        this.clubCourseID = clubCourseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public int getHoles() {
        return holes;
    }

    public void setHoles(int holes) {
        this.holes = holes;
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public int getParHole1() {
        return parHole1;
    }

    public void setParHole1(int parHole1) {
        this.parHole1 = parHole1;
    }

    public int getParHole2() {
        return parHole2;
    }

    public void setParHole2(int parHole2) {
        this.parHole2 = parHole2;
    }

    public int getParHole3() {
        return parHole3;
    }

    public void setParHole3(int parHole3) {
        this.parHole3 = parHole3;
    }

    public int getParHole4() {
        return parHole4;
    }

    public void setParHole4(int parHole4) {
        this.parHole4 = parHole4;
    }

    public int getParHole5() {
        return parHole5;
    }

    public void setParHole5(int parHole5) {
        this.parHole5 = parHole5;
    }

    public int getParHole6() {
        return parHole6;
    }

    public void setParHole6(int parHole6) {
        this.parHole6 = parHole6;
    }

    public int getParHole7() {
        return parHole7;
    }

    public void setParHole7(int parHole7) {
        this.parHole7 = parHole7;
    }

    public int getParHole8() {
        return parHole8;
    }

    public void setParHole8(int parHole8) {
        this.parHole8 = parHole8;
    }

    public int getParHole9() {
        return parHole9;
    }

    public void setParHole9(int parHole9) {
        this.parHole9 = parHole9;
    }

    public int getParHole10() {
        return parHole10;
    }

    public void setParHole10(int parHole10) {
        this.parHole10 = parHole10;
    }

    public int getParHole11() {
        return parHole11;
    }

    public void setParHole11(int parHole11) {
        this.parHole11 = parHole11;
    }

    public int getParHole12() {
        return parHole12;
    }

    public void setParHole12(int parHole12) {
        this.parHole12 = parHole12;
    }

    public int getParHole13() {
        return parHole13;
    }

    public void setParHole13(int parHole13) {
        this.parHole13 = parHole13;
    }

    public int getParHole14() {
        return parHole14;
    }

    public void setParHole14(int parHole14) {
        this.parHole14 = parHole14;
    }

    public int getParHole15() {
        return parHole15;
    }

    public void setParHole15(int parHole15) {
        this.parHole15 = parHole15;
    }

    public int getParHole16() {
        return parHole16;
    }

    public void setParHole16(int parHole16) {
        this.parHole16 = parHole16;
    }

    public int getParHole17() {
        return parHole17;
    }

    public void setParHole17(int parHole17) {
        this.parHole17 = parHole17;
    }

    public int getParHole18() {
        return parHole18;
    }

    public void setParHole18(int parHole18) {
        this.parHole18 = parHole18;
    }

    public int getClubID() {
        return clubID;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
    }

}
