/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.util;

/**
 *
 * @author aubreyM
 */
import com.boha.golfkids.data.Club;
import com.boha.golfkids.data.ClubCourse;
import com.boha.golfkids.data.Province;
import com.boha.golfkids.dto.ClubCourseDTO;
import com.boha.golfkids.dto.ClubDTO;
import com.boha.golfkids.dto.ResponseDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


public class WorkerBee {

    @PersistenceContext
    EntityManager em;
    public WorkerBee() {
        em  = EMUtil.getEM();
    }
    private PreparedStatement preparedStatement;

    private static final String SQL_STATEMENT = "select clubID, a.clubName, a.latitude, a.longitude, a.provinceID, provinceName, "
            + "( ? * acos( cos( radians(?) ) * cos( radians( a.latitude) ) "
            + "* cos( radians( a.longitude ) - radians(?) ) + sin( radians(?) ) "
            + "* sin( radians( a.latitude ) ) ) ) "
            + "AS distance FROM club a, province b where a.provinceID = b.provinceID HAVING distance < ? order by distance";
    public static final int KILOMETRES = 1, MILES = 2, PARM_KM = 6371, PARM_MILES = 3959;

    private Connection conn;

    public ResponseDTO getClubsWithinRadius(double latitude, double longitude,
            int radius, int type, int page)
            throws Exception {
        if (conn == null || conn.isClosed()) {
            conn = em.unwrap(Connection.class);
            log.log(Level.INFO, "..........SQL Connection unwrapped from EntityManager");
        }
        if (preparedStatement == null || preparedStatement.isClosed()) {
            preparedStatement = conn.prepareStatement(SQL_STATEMENT);
            log.log(Level.INFO, "..........SQL Statement prepared from Connection");
        }
        switch (type) {
            case KILOMETRES:
                preparedStatement.setInt(1, PARM_KM);
                break;
            case MILES:
                preparedStatement.setInt(1, PARM_MILES);
                break;
            case 0:
                preparedStatement.setInt(1, PARM_KM);
                break;
        }
        ResultSet resultSet;
        preparedStatement.setDouble(2, latitude);
        preparedStatement.setDouble(3, longitude);
        preparedStatement.setDouble(4, latitude);
        preparedStatement.setInt(5, radius);
        resultSet = preparedStatement.executeQuery();

        return buildClubList(resultSet, page);

    }

    private ResponseDTO buildClubList(ResultSet resultSet, int page) throws SQLException {

        ResponseDTO resp = new ResponseDTO();
        List<Club> list = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("clubID");
            String name = resultSet.getString("clubName");
            String province = resultSet.getString("provinceName");
            double distance = resultSet.getDouble("distance");
            double lat = resultSet.getDouble("latitude");
            double lng = resultSet.getDouble("longitude");
            int provinceID = resultSet.getInt("provinceID");
            System.out.println("within radius, clubName: " + id + " - " + distance + " - " + name + " - " + province + " - lat: " + lat + " lng: " + lng);
            Club club = new Club();
            club.setClubID(id);
            Province p = new Province();
            p.setProvinceName(province);
            p.setProvinceID(provinceID);
            club.setProvince(p);
            club.setClubName(name);
            club.setDistance(distance);
            club.setLatitude(lat);
            club.setLongitude(lng);
            club.setClubCourseList(new ArrayList<ClubCourse>());
            list.add(club);
        }
        System.out.println("---- all clubs found: " + list.size());
        int x = list.size() % WorkerBee.ROWS_PER_PAGE;
        if (x > 0) {
            resp.setTotalPages((list.size() / WorkerBee.ROWS_PER_PAGE) + 1);
        } else {
            resp.setTotalPages((list.size() / WorkerBee.ROWS_PER_PAGE));
        }
        List<ClubDTO> cList = getClubs(list, page);
        resp.setClubs(cList);
        resp.setTotalClubs(list.size());
        resultSet.close();
        return resp;
    }

    public List<ClubDTO> getClubs(List<Club> list, int page) {
        if (page == 0) {
            page = 1;
        }
        int startIndex = (page - 1) * ROWS_PER_PAGE;
        System.out.println("startIndex: " + startIndex + " page: " + page);
        List<ClubDTO> cList = new ArrayList<>();
        int rowCount = 0;
        if (startIndex < list.size()) {
            for (int i = startIndex; i < list.size(); i++) {
                if (rowCount == ROWS_PER_PAGE) {
                    break;
                }
                Club club = list.get(i);
                if (em != null) {
                    if (club.getClubCourseList() == null || club.getClubCourseList().isEmpty()) {
                        Query x = em.createNamedQuery("ClubCourse.findByClub", ClubCourse.class);
                        x.setParameter("id", club.getClubID());
                        List<ClubCourse> ccList = x.getResultList();
                        club.setClubCourseList(ccList);
                    }
                }
                ClubDTO dto = new ClubDTO(club);
                dto.setDistance(club.getDistance());
                dto.setClubCourses(new ArrayList<ClubCourseDTO>());
                for (ClubCourse cc : club.getClubCourseList()) {
                    dto.getClubCourses().add(new ClubCourseDTO(cc));
                }
                cList.add(dto);
                rowCount++;
            }
        }

        return cList;
    }

    public static final int ROWS_PER_PAGE = 100;
    static final Logger log = Logger.getLogger(WorkerBee.class.getName());
}
