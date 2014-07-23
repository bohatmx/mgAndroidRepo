/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.util;

import com.boha.golfkids.data.Club;
import com.boha.golfkids.data.ClubCourse;
import com.boha.golfkids.data.GolfGroup;
import com.boha.golfkids.data.GolfGroupPlayer;
import com.boha.golfkids.data.LeaderBoard;
import com.boha.golfkids.data.Player;
import com.boha.golfkids.data.Scorer;
import com.boha.golfkids.data.Tournament;
import com.boha.golfkids.data.TourneyScoreByRound;
import com.boha.golfkids.dto.LeaderBoardDTO;
import com.boha.golfkids.dto.PlayerDTO;
import com.boha.golfkids.dto.RequestDTO;
import com.boha.golfkids.dto.ResponseDTO;
import com.boha.golfkids.dto.TournamentDTO;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.Years;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Query;

/**
 *
 * @author aubreyM
 */
public class NewGolfGroupUtil {

    public static final int GENDER_BOYS = 1, GENDER_GIRLS = 2;
    public static final int PECANWOOD = 314, PROVINCE = 16;
    static final Logger log = Logger.getLogger("NewGolfGroupUtil");

    public static void generate(GolfGroup gg, DataUtil dataUtil, PlatformUtil platformUtil) throws DataException {
        List<Player> pList = generatePlayers(gg, dataUtil);
        Club club = dataUtil.getClubByID(PECANWOOD);
        if (club == null) {
            Query q = dataUtil.em.createNamedQuery("Club.findByProvince", Club.class);
            q.setParameter("id", PROVINCE);
            List<Club> cList = q.getResultList();
            if (cList.size() > 0) {
                club = cList.get(0);
            } else {
                log.log(Level.SEVERE, "Club for example generation not found");
                platformUtil.addErrorStore(PROVINCE, "Club for example generation not found for golfGroup: "
                        + gg.getGolfGroupName() + " id: " + gg.getGolfGroupID(),
                        NewGolfGroupUtil.class.getName());
                return;
            }
        }
        //update Pecanwood par - par on each hole
        Query q = dataUtil.em.createNamedQuery("ClubCourse.findByClub", ClubCourse.class);
        q.setParameter("id", club.getClubID());
        List<ClubCourse> ccList = q.getResultList();
        if (!ccList.isEmpty()) {
            ClubCourse cc = ccList.get(0);
            cc.setPar(72);
            cc.setHoles(18);
            cc.setParHole1(4);
            cc.setParHole2(4);
            cc.setParHole3(3);
            cc.setParHole4(4);
            cc.setParHole5(5);
            cc.setParHole6(4);
            cc.setParHole7(5);
            cc.setParHole8(3);
            cc.setParHole9(4);
            cc.setParHole10(5);
            cc.setParHole11(4);
            cc.setParHole12(5);
            cc.setParHole13(3);
            cc.setParHole14(4);
            cc.setParHole15(4);
            cc.setParHole16(4);
            cc.setParHole17(3);
            cc.setParHole18(4);
            dataUtil.em.merge(cc);

        }
        generateExampleTournament(true, gg, pList, club.getClubID(), "Sample Tournament #1", 1, true, 18, 0, dataUtil, platformUtil);
        generateExampleTournament(true, gg, pList, club.getClubID(), "Sample Tournament #2", 3, true, 18, 0, dataUtil, platformUtil);

        generateExampleTournament(true, gg, pList, club.getClubID(), "Sample Tournament #3", 4, false, 18, GENDER_BOYS, dataUtil, platformUtil);
        generateExampleTournament(false, gg, pList, club.getClubID(), "Sample Tournament #4", 4, false, 18, GENDER_GIRLS, dataUtil, platformUtil);

        generateExampleTournament(false, gg, pList, club.getClubID(), "Sample Tournament #5", 3, false, 9, 0, dataUtil, platformUtil);
        generateExampleTournament(true, gg, pList, club.getClubID(), "Sample Tournament #6", 6, false, 18, GENDER_BOYS, dataUtil, platformUtil);

        getPictureFiles(gg, pList);

        String msg = MESSAGE + gg.getGolfGroupName();
        log.log(Level.OFF, "*************** DONE! - SampleExample players, parents and tournaments generated: {0}", gg.getGolfGroupName());
        
        platformUtil.addErrorStore(777, msg, "NewGolfGroupUtil");
    }

    static final String MESSAGE = "Sample tournaments generated for ";
    private static void generateExampleTournament(boolean isLive, GolfGroup gg, List<Player> pList,
            int clubID,
            String tournamentName, int rounds, boolean useAgeGroups, int holesPerRound, int gender,
            DataUtil dataUtil, PlatformUtil platformUtil) throws DataException {
        DateTime dt = new DateTime();
        dt = dt.minusDays(7);
        //
        TournamentDTO t = new TournamentDTO();
        t.setTourneyName(tournamentName);
        t.setGolfRounds(rounds);
        t.setStartDate(dt.getMillis());
        t.setClubID(clubID);
        t.setGolfGroupID(gg.getGolfGroupID());
        t.setHolesPerRound(holesPerRound);
        t.setExampleFlag(1);
        t.setTournamentType(RequestDTO.STROKE_PLAY_INDIVIDUAL);

        if (holesPerRound == 9) {
            t.setPar(36);
        } else {
            t.setPar(72);
        }
        if (rounds > 1) {
            for (int i = 0; i < rounds; i++) {
                dt = dt.plusDays(i);
            }
            t.setEndDate(dt.getMillis());
        } else {
            t.setEndDate(t.getStartDate());
        }
        if (useAgeGroups) {
            t.setUseAgeGroups(1);
        }
        ResponseDTO r = dataUtil.addTournament(t, platformUtil);
        Tournament tournament = new Tournament();
        List<TournamentDTO> tList = r.getTournaments();
        for (TournamentDTO tn : tList) {
            if (tn.getTourneyName().equalsIgnoreCase(t.getTourneyName())) {
                tournament = dataUtil.getTournamentByID(tn.getTournamentID());
                break;
            }
        }
        List<LeaderBoard> list = addPlayersToTournament(pList, tournament, gender, dataUtil, platformUtil);
        log.log(Level.INFO, "LeaderBoard items generated, about to score: {0}", list.size());

        List<TourneyScoreByRound> tsbrList = generateScores(list, tournament.getTournamentID(), dataUtil, tournament.getGolfRounds());
        if (isLive) {
            generateScoresForLiveLeaderBoard(tsbrList, list, tournament, dataUtil, rounds);
        }

        log.log(Level.OFF, "Sample Tournament generated: {0} - {1}", new Object[]{gg.getGolfGroupName(), tournamentName});
    }

    public static List<TourneyScoreByRound> generateScores(List<LeaderBoard> list, int tournamentID, DataUtil dataUtil, int holes) throws DataException {
        log.log(Level.INFO, "------- generating Scores ...");
        int cnt = 0;
        Query q = dataUtil.em.createNamedQuery("TourneyScoreByRound.getByTourney", TourneyScoreByRound.class);
        q.setParameter("id", tournamentID);
        List<TourneyScoreByRound> tsbrList = q.getResultList();
        Random rand = new Random(System.currentTimeMillis());
        for (TourneyScoreByRound tsbr : tsbrList) {
            if (holes == 9) {
                int sum = scoreFirstNine(tsbr, rand);
                tsbr.setTotalScore(sum);

            } else {
                int front = scoreFirstNine(tsbr, rand);
                int back = scoreBackNine(tsbr, rand);
                tsbr.setTotalScore(front + back);
            }
            //
            tsbr.setScoringComplete(1);
            dataUtil.em.merge(tsbr);
            cnt++;
        }
        //update round scores on leaderboard item
        for (LeaderBoard b : list) {
            dataUtil.scoreTotals(b);
        }

        log.log(Level.INFO, "Updated scores, count: {0}", cnt);
        return tsbrList;
    }

    private static int scoreFirstNine(TourneyScoreByRound tsbr, Random rand) {

        tsbr.setScore1(getScore(rand));
        tsbr.setScore2(getScore(rand));
        tsbr.setScore3(getScore(rand));
        tsbr.setScore4(getScore(rand));
        tsbr.setScore5(getScore(rand));
        tsbr.setScore6(getScore(rand));
        tsbr.setScore7(getScore(rand));
        tsbr.setScore8(getScore(rand));
        tsbr.setScore9(getScore(rand));
        int sum = tsbr.getScore1() + tsbr.getScore2() + tsbr.getScore3() + tsbr.getScore4()
                + tsbr.getScore5() + tsbr.getScore6() + tsbr.getScore7() + tsbr.getScore8()
                + tsbr.getScore9();
        return sum;
    }

    private static int scoreBackNine(TourneyScoreByRound tsbr, Random rand) {
        tsbr.setScore10(getScore(rand));
        tsbr.setScore11(getScore(rand));
        tsbr.setScore12(getScore(rand));
        tsbr.setScore13(getScore(rand));
        tsbr.setScore14(getScore(rand));
        tsbr.setScore15(getScore(rand));
        tsbr.setScore16(getScore(rand));
        tsbr.setScore17(getScore(rand));
        tsbr.setScore18(getScore(rand));
        int sum = tsbr.getScore10() + tsbr.getScore11() + tsbr.getScore12() + tsbr.getScore13()
                + tsbr.getScore14() + tsbr.getScore15() + tsbr.getScore16() + tsbr.getScore17()
                + tsbr.getScore18();
        return sum;
    }

    private static int getScore(Random rand) {
        int score = rand.nextInt(9);
        if (score < 3) {
            if (rand.nextInt(100) > 70) {
                score = 2;  //great!
                //log.log(Level.OFF, "Someone got lucky with a 2....cheap!");
            } else {
                score = 4;
            }
        }
        if (score > 7) {
            if (rand.nextInt(100) > 80) {
                score = 3;  //great!
                //log.log(Level.OFF, "Someone got lucky with a 4, for mahala........");
            } else {
                score = 4;
            }
        }
        return score;
    }

    private static void generateScoresForLiveLeaderBoard(List<TourneyScoreByRound> tsbrList, List<LeaderBoard> list,
            Tournament tournament, DataUtil dataUtil, int holes) throws DataException {
        int cnt = 0;
        log.log(Level.OFF, "########## enabling live! leaderBoard");

        //all scored, now remove some scores to make it live!
        int lastRound = 1, holeCnt = holes, pair = 0;
        if (tournament.getGolfRounds() > 1) {
            lastRound = tournament.getGolfRounds();
            for (TourneyScoreByRound tsbr : tsbrList) {
                if (tsbr.getGolfRound() == lastRound) {
                    tsbr.setScoringComplete(0);
                    processHole(tsbr, dataUtil, holeCnt, pair, holes);
                    cnt++;
                    if (holeCnt == 0) {
                        break;
                    }
                }
            }
        } else {
            for (TourneyScoreByRound tsbr : tsbrList) {
                tsbr.setScoringComplete(0);
                processHole(tsbr, dataUtil, holeCnt, pair, holes);
                cnt++;
                if (holeCnt == 0) {
                    break;
                }
            }
        }
        //update round scores on leaderboard item
        for (LeaderBoard b : list) {
            b.setScoringComplete(0);
            b.setTotalScore(0);
            dataUtil.scoreTotals(b);
        }

        log.log(Level.INFO, "***** Updated live! scores, count: {0}", cnt);
    }

    private static void processHole(TourneyScoreByRound tsbr, DataUtil dataUtil, int holeCnt, int pair, int holes) {
        switch (holeCnt) {
            case 18:
                setCounters(holeCnt, pair);
                tsbr.setScore18(0);
                dataUtil.em.merge(tsbr);
                ////log.log(Level.INFO, "enabling live leaderBoard, hole: 18");
                break;
            case 17:
                setCounters(holeCnt, pair);
                tsbr.setScore18(0);
                tsbr.setScore17(0);
                dataUtil.em.merge(tsbr);
                //log.log(Level.INFO, "enabling live leaderBoard, hole: 17");
                break;
            case 16:
                setCounters(holeCnt, pair);
                tsbr.setScore18(0);
                tsbr.setScore17(0);
                tsbr.setScore16(0);
                dataUtil.em.merge(tsbr);
                //log.log(Level.INFO, "enabling live leaderBoard, hole: 16");
                break;
            case 15:
                setCounters(holeCnt, pair);
                tsbr.setScore18(0);
                tsbr.setScore17(0);
                tsbr.setScore16(0);
                tsbr.setScore15(0);
                dataUtil.em.merge(tsbr);
                //log.log(Level.INFO, "enabling live leaderBoard, hole: 15");
            case 14:
                setCounters(holeCnt, pair);
                tsbr.setScore18(0);
                tsbr.setScore17(0);
                tsbr.setScore16(0);
                tsbr.setScore15(0);
                tsbr.setScore14(0);
                dataUtil.em.merge(tsbr);
                //log.log(Level.INFO, "enabling live leaderBoard, hole: 14");
                break;
            case 13:
                setCounters(holeCnt, pair);
                tsbr.setScore18(0);
                tsbr.setScore17(0);
                tsbr.setScore16(0);
                tsbr.setScore15(0);
                tsbr.setScore14(0);
                tsbr.setScore13(0);
                dataUtil.em.merge(tsbr);
            case 12:
                setCounters(holeCnt, pair);
                tsbr.setScore18(0);
                tsbr.setScore17(0);
                tsbr.setScore16(0);
                tsbr.setScore15(0);
                tsbr.setScore14(0);
                tsbr.setScore13(0);
                tsbr.setScore12(0);
                dataUtil.em.merge(tsbr);
            case 11:
                setCounters(holeCnt, pair);
                tsbr.setScore18(0);
                tsbr.setScore17(0);
                tsbr.setScore16(0);
                tsbr.setScore15(0);
                tsbr.setScore14(0);
                tsbr.setScore13(0);
                tsbr.setScore12(0);
                tsbr.setScore11(0);
                dataUtil.em.merge(tsbr);
            case 10:
                setCounters(holeCnt, pair);
                tsbr.setScore18(0);
                tsbr.setScore17(0);
                tsbr.setScore16(0);
                tsbr.setScore15(0);
                tsbr.setScore14(0);
                tsbr.setScore13(0);
                tsbr.setScore12(0);
                tsbr.setScore11(0);
                tsbr.setScore10(0);
                dataUtil.em.merge(tsbr);
            case 9:
                setCounters(holeCnt, pair);
                tsbr.setScore18(0);
                tsbr.setScore17(0);
                tsbr.setScore16(0);
                tsbr.setScore15(0);
                tsbr.setScore14(0);
                tsbr.setScore13(0);
                tsbr.setScore12(0);
                tsbr.setScore11(0);
                tsbr.setScore10(0);
                tsbr.setScore9(0);
                dataUtil.em.merge(tsbr);
                break;

        }
    }

    private static void setCounters(int holeCnt, int pair) {
        if (pair > 2) {
            holeCnt--;
            pair = 0;
        } else {
            pair++;
        }
    }

    static Connection conn;
    static PreparedStatement preparedStatement;

    private static List<Player> generatePlayers(GolfGroup gg, DataUtil dataUtil) throws DataException {

        List<Player> pList = new ArrayList<>();
        try {
            if (conn == null || conn.isClosed()) {
                conn = dataUtil.em.unwrap(Connection.class);
            }
            if (preparedStatement == null || preparedStatement.isClosed()) {
                preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            }

//add boys
            //pList.addAll(addPlayersByAgeGender(gg, 10, GENDER_BOYS, dataUtil));
            pList.addAll(addPlayersByAgeGender(gg, 12, GENDER_BOYS, dataUtil));
            //pList.addAll(addPlayersByAgeGender(gg, 14, GENDER_BOYS, dataUtil));
            pList.addAll(addPlayersByAgeGender(gg, 16, GENDER_BOYS, dataUtil));
            //pList.addAll(addPlayersByAgeGender(gg, 18, GENDER_BOYS, dataUtil));
            //add girls
            //pList.addAll(addPlayersByAgeGender(gg, 10, GENDER_GIRLS, dataUtil));
            pList.addAll(addPlayersByAgeGender(gg, 12, GENDER_GIRLS, dataUtil));
            // pList.addAll(addPlayersByAgeGender(gg, 14, GENDER_GIRLS, dataUtil));
            pList.addAll(addPlayersByAgeGender(gg, 16, GENDER_GIRLS, dataUtil));
            //pList.addAll(addPlayersByAgeGender(gg, 18, GENDER_GIRLS, dataUtil));

            log.log(Level.OFF, "###### Players generated: {0}", pList.size());
            //add scorers
            Random rand = new Random(System.currentTimeMillis());
            Scorer s1 = new Scorer();
            s1.setGolfGroup(gg);
            s1.setFirstName(firstNames[rand.nextInt(firstNames.length - 1)]);
            s1.setLastName(lastNames[rand.nextInt(lastNames.length - 1)]);
            s1.setEmail(s1.getFirstName().toLowerCase()
                    + "." + s1.getLastName().toLowerCase()
                    + "." + (rand.nextInt())
                    + "@gmail.com");
            s1.setCellphone("999 999 9999");
            s1.setPin("12345");
            s1.setExampleFlag(1);
            s1.setDateRegistered(new Date());
            dataUtil.em.persist(s1);
            //
            Scorer s2 = new Scorer();
            s2.setGolfGroup(gg);
            s2.setFirstName(firstNames[rand.nextInt(firstNames.length - 1)]);
            s2.setLastName(lastNames[rand.nextInt(lastNames.length - 1)]);
            s2.setEmail(s2.getFirstName().toLowerCase()
                    + "." + s2.getLastName().toLowerCase()
                    + "." + (rand.nextInt())
                    + "@gmail.com");
            s2.setCellphone("999 999 9999");
            s2.setPin("12345");
            s2.setExampleFlag(1);
            s2.setDateRegistered(new Date());
            dataUtil.em.persist(s2);
            //
            Scorer s3 = new Scorer();
            s3.setGolfGroup(gg);
            s3.setFirstName(girls[rand.nextInt(girls.length - 1)]);
            s3.setLastName(lastNames[rand.nextInt(lastNames.length - 1)]);
            s3.setEmail(s3.getFirstName().toLowerCase()
                    + "." + s3.getLastName().toLowerCase()
                    + "." + (rand.nextInt())
                    + "@gmail.com");
            s3.setCellphone("999 999 9999");
            s3.setPin("12345");
            s3.setExampleFlag(1);
            s3.setDateRegistered(new Date());
            dataUtil.em.persist(s3);
            //
            Scorer s4 = new Scorer();
            s4.setGolfGroup(gg);
            s4.setFirstName(firstNames[rand.nextInt(firstNames.length - 1)]);
            s4.setLastName(lastNames[rand.nextInt(lastNames.length - 1)]);
            s4.setEmail(s4.getFirstName().toLowerCase()
                    + "." + s4.getLastName().toLowerCase()
                    + "." + (rand.nextInt())
                    + "@gmail.com");
            s4.setCellphone("999 999 9999");
            s4.setPin("12345");
            s4.setDateRegistered(new Date());
            s4.setExampleFlag(1);
            dataUtil.em.persist(s4);

        } catch (SQLException ex) {
            Logger.getLogger(NewGolfGroupUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pList;
    }
    static String sql = "INSERT INTO player (firstName, lastName, dateOfBirth, gender, email, pin, exampleFlag, yearJoined, dateRegistered) "
            + "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static List<Player> addPlayersByAgeGender(
            GolfGroup gg,
            int age, int gender,
            DataUtil dataUtil) throws DataException {
        log.log(Level.WARNING, "############## Adding example players, age: {0} gender: {1} - {2}", new Object[]{age, gender, gg.getGolfGroupName()});
        List<Player> pList = new ArrayList<>();

        Random rand = new Random(System.currentTimeMillis());
        int fNameIndex;
        int lNameIndex;
        int genderCount;
        if (gender == GENDER_BOYS) {
            genderCount = rand.nextInt(16);
            if (genderCount < 8) {
                genderCount = 8;
            }
        } else {
            genderCount = rand.nextInt(8);
            if (genderCount < 4) {
                genderCount = 4;
            }
        }

        for (int i = 0; i < genderCount; i++) {
            Player p = new Player();
            if (gender == GENDER_BOYS) {
                fNameIndex = rand.nextInt(firstNames.length - 1);
                p.setFirstName(firstNames[fNameIndex]);
            } else {
                fNameIndex = rand.nextInt(girls.length - 1);
                p.setFirstName(girls[fNameIndex]);
            }
            lNameIndex = rand.nextInt(lastNames.length - 1);
            //static String sql = "INSERT INTO player (firstName, lastName, dateOfBirth, gender, email, pin, exampleFlag, yearJoined, dateRegistered) "
            //            + "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            p.setDateRegistered(new Date());
            p.setLastName(lastNames[lNameIndex]);
            p.setEmail(p.getFirstName().toLowerCase()
                    + "." + p.getLastName().toLowerCase()
                    + "." + (rand.nextInt())
                    + "@mg.com");
            p.setCellphone("999 999 9999");
            p.setGender(gender);
            p.setPin("12345");
            p.setYearJoined(2013);
            p.setDateOfBirth(getDateOfBirth(age));
            p.setExampleFlag(1);

            try {
                dataUtil.em.persist(p);
                Query q = dataUtil.em.createQuery("select a from Player a where a.email = :email");
                q.setParameter("email", p.getEmail());
                Player player = (Player) q.getSingleResult();
                if (player != null) {
                    log.log(Level.INFO, "Player retrieved {0} {1} {2}", new Object[]{player.getPlayerID(), player.getFirstName(), player.getLastName()});
                    GolfGroupPlayer ggp = new GolfGroupPlayer();
                    ggp.setDateRegistered(new Date());
                    ggp.setGolfGroup(gg);
                    ggp.setPlayer(player);
                    dataUtil.em.persist(ggp);
                    pList.add(ggp.getPlayer());
                } else {
                    log.log(Level.INFO, "************ have to do it the hard way - WHY??....");
                    preparedStatement.setString(1, p.getFirstName());
                    preparedStatement.setString(2, p.getLastName());
                    preparedStatement.setDate(3, new java.sql.Date(p.getDateOfBirth().getTime()));
                    preparedStatement.setInt(4, gender);
                    preparedStatement.setString(5, p.getEmail());
                    preparedStatement.setString(6, "12345");
                    preparedStatement.setInt(7, 1);
                    preparedStatement.setInt(8, 2013);
                    preparedStatement.setDate(9, new java.sql.Date(new Date().getTime()));

                    boolean isOK = preparedStatement.execute();
                    player = new Player();
                    if (isOK) {
                        ResultSet rs = preparedStatement.getResultSet();
                        int id = 0;
                        if (rs.next()) {
                            player.setPlayerID(rs.getInt(1));
                            //log.log(Level.INFO, "######### playerID returned: {0}", id);
                        }
                        //
                        if (player.getPlayerID() > 0) {
                            GolfGroupPlayer ggp = new GolfGroupPlayer();
                            ggp.setDateRegistered(new Date());
                            ggp.setGolfGroup(gg);
                            ggp.setPlayer(dataUtil.em.find(Player.class, player.getPlayerID()));
                            dataUtil.em.persist(ggp);
                            pList.add(ggp.getPlayer());
                        }
                    } else {
                        //log.log(Level.OFF, "..return from execute {0}", isOK);
                    }
                }

            } catch (SQLException e) {
                log.log(Level.SEVERE, "Fucked!!!", e);
            }

        }

        return pList;
    }
    /* 
     Parent f = new Parent();
     f.setExampleFlag(1);
     f.setLastName(player.getLastName());
     if (gender == GENDER_BOYS) {
     f.setFirstName(firstNames[parentIndex]);
     } else {
     f.setFirstName(girls[parentIndex]);
     }
     f.setEmail(f.getFirstName().toLowerCase()
     + "." + f.getLastName().toLowerCase()
     + "." + (rand.nextInt())
     + "@gmail.com");
     f.setCellphone("999 999 9999");
     f.setPin("12345");
     dataUtil.em.persist(f);
     Query w = dataUtil.em.createNamedQuery("Parent.findByEmail", Parent.class);
     w.setParameter("email", f.getEmail());
     Parent parent = (Parent) w.getSingleResult();
     GolfGroupParent d = new GolfGroupParent();
     d.setDateRegistered(new Date());
     d.setGolfGroup(gg);
     d.setParent(parent);
     dataUtil.em.persist(d); */

    private static List<LeaderBoard> addPlayersToTournament(List<Player> pList,
            Tournament t, int gender, DataUtil dataUtil, PlatformUtil platformUtil) throws DataException {
        List<LeaderBoard> lbList;
        switch (gender) {
            case 0: //All players
                for (Player player : pList) {
                    if (t.getHolesPerRound() == 9) {
                        int age = getPlayerAge(player.getDateOfBirth().getTime());
                        if (age > 12) {
                            continue;
                        }
                    }
                    LeaderBoardDTO lb = new LeaderBoardDTO();
                    lb.setTournamentID(t.getTournamentID());
                    lb.setPlayer(new PlayerDTO(player));
                    dataUtil.addTournamentPlayer(lb, platformUtil);

                }
                break;
            case GENDER_BOYS:
                for (Player player : pList) {
                    if (player.getGender() == GENDER_GIRLS) {
                        continue;
                    }
                    if (t.getHolesPerRound() == 9) {
                        int age = getPlayerAge(player.getDateOfBirth().getTime());
                        if (age > 12) {
                            continue;
                        }
                    }
                    LeaderBoardDTO lb = new LeaderBoardDTO();
                    lb.setTournamentID(t.getTournamentID());
                    lb.setPlayer(new PlayerDTO(player));
                    dataUtil.addTournamentPlayer(lb, platformUtil);

                }
                break;
            case GENDER_GIRLS:
                for (Player player : pList) {
                    if (player.getGender() == GENDER_BOYS) {
                        continue;
                    }
                    if (t.getHolesPerRound() == 9) {
                        int age = getPlayerAge(player.getDateOfBirth().getTime());
                        if (age > 12) {
                            continue;
                        }
                    }
                    LeaderBoardDTO lb = new LeaderBoardDTO();
                    lb.setTournamentID(t.getTournamentID());
                    lb.setPlayer(new PlayerDTO(player));
                    dataUtil.addTournamentPlayer(lb, platformUtil);

                }
                break;
        }

        Query q = dataUtil.em.createNamedQuery("LeaderBoard.findByTournament", LeaderBoard.class);
        q.setParameter("id", t.getTournamentID());
        lbList = q.getResultList();

        return lbList;
    }

    private static int getPlayerAge(long date) {
        LocalDateTime birthday = new LocalDateTime(date);
        LocalDateTime start = new LocalDateTime();
        Years years = Years.yearsBetween(birthday, start);
        return years.getYears();
    }

    private static Date getDateOfBirth(int age) {

        DateTime dt = new DateTime();
        dt = dt.minusYears(age);
        Random rand = new Random(System.currentTimeMillis());
        int mth = rand.nextInt(dt.getMonthOfYear());
        int day = rand.nextInt(28);
        if (mth == 0) {
            mth = 1;
        }
        if (day == 0) {
            day = 1;
        }
        DateTime x = new DateTime(dt.getYear(), mth, day, 0, 0);
        return new Date(x.getMillis());
    }
    public static String[] firstNames = {
        "Benjamin", "Johnny", "Tom", "Sam", "Thomas", "Zeke", "John",
        "Tommy", "Peter", "Paul", "Forrest", "Bennie", "Mark", "MacDonald",
        "McLean", "Chris", "Frank", "Mark", "Ronald", "Ronnie", "Blake",
        "John", "Vincent", "Jack", "Bobby", "Malenga", "Sean", "Shane",
        "Mack", "Jonty", "Lance", "David", "Adam", "Luke",
        "Jean", "Peter", "Francois", "Stephen", "Geoffrey", "Omar", "Andre", "Robert", "Hunter",
        "William", "Harry", "Boyce", "Lee", "Lawrence", "Michael", "Noonan", "Caleb", "Jacob",
        "Samuel", "Andrew", "Bernard", "Jack", "Tommy", "Johannes", "Lance", "Jeremiah", "Jerry",
        "Trayvon", "Newton", "Sam", "Fred", "TJ", "Raymond", "Godfrey", "Charlie", "Mingus",
        "Ryan", "Steve", "Stephen", "Hunter", "Henry", "Jordan", "Andrew",
        "Brandon", "Louis", "Christopher", "Daniel", "Eli", "Liam", "Carter", "Dominic",
        "Parker", "Anthony", "Benjamin", "Lucas", "Connor", "Zachary",
        "Cameron", "Matthew", "Justin", "Nathan", "Sebastian", "Brody",
        "Alexander", "Alex", "Levi", "James", "Aiden", "Cooper", "Xavier", "Ryder",
        "Michael", "Tyler", "Ethan", "Jonathan", "Robert", "Roberto", "Gabriel",
        "Chase", "Logan", "Hudson", "Julian", "Aaron", "Severiano", "Owen"
    };
    public static String[] lastNames = {
        "Armstrong", "Maringa", "Scott", "Oosthuizen", "Els", "Schwartzel",
        "Botha", "Smythe", "Baker", "Watson", "Jobs", "Player", "Locke",
        "Black", "Charles", "Grainger", "Jones", "Brown", "Peterson", "Mickels",
        "Pollack", "Peyton", "Williams", "Zuckerberg", "Samuels", "Hernandez", "Johnson", "Gray",
        "Davidson", "Lombardi", "Smith", "Jackson", "Chauke", "Morris", "Peterson", "Paulson",
        "Remington", "Priest", "Church", "Charles", "Burmingham", "Naidoo", "Bala", "Renoir", "Switzer",
        "Dennison", "Johnson", "Jerram", "Adams", "Wilson", "Hepburn", "Giggs", "Stephens",
        "Dafoe", "Daggett", "Dahlberg", "Dangerfield", "Danziger", "Daniels", "Smith", "Smythe",
        "Calandrino", "Cadwell", "Callaghan", "California", "Villegas", "Camilleri",
        "Hackney", "Hackman", "Hackett", "Haagensen", "Hackworth", "Hacker",
        "Hachmeister", "Hack", "Duff", "Haigwood", "Wood", "Woods", "Mickelson",
        "Taglieri", "Tanaka", "Tailor", "Talarico", "Talbot", "Tafoya", "Tartaglia",
        "Gaffney", "Gagliardi", "Gaillard", "Galaska", "Dufner", "Gambetta",
        "Fabiani", "Factor", "Fahlstrom", "Fagin", "Faldo", "Fariello", "Packwood",
        "Pacino", "Paganelli", "Page", "Pagani", "Palinski", "Rafferty", "Rabinovitz",
        "Radcliffe", "Raindford", "Rainsford"

    };

    public static String[] girls = {
        "Mary", "Louise", "Brenda", "Samantha", "Ivanka", "Petra", "Maria",
        "Sue", "Thabitah", "Henrietta", "Fannie", "Bernande", "Linda", "Catherine",
        "Lee", "Christina", "Denise", "Yvonne", "Isabella", "Mia", "Blake",
        "Alexis", "Sofia", "Claire", "Melanie", "Sarah", "Brianna", "Jasmine",
        "Grace", "Hannah", "Elizabeth", "Natalie", "Allison", "Zoe",
        "Jean", "Julia", "Lucy", "Hailey", "Leah", "Andrea", "Kaylee", "Victoria", "Jocelyn",
        "Brooklyn", "Sophie", "Madison", "Taylor", "Alexandra", "Alexa", "Riley",
        "Aubree", "Naomi", "Kayla", "Michelle", "Bernande", "Arianne",
        "Anna", "Gabriella", "Madeline", "Maggie", "Evelyn", "Lily", "Bella", "Savannah",
        "Kimberley", "Charlotte",
        "Stella", "Leah", "Ella", "Serenity", "Katherine", "Reagan",
        "Godiva", "Caroline", "Alyssa", "Sarah", "Molly", "Morgan",
        "Chloe"
    };

    private static void getPictureFiles(GolfGroup gg, List<Player> pList) {
        log.log(Level.INFO, "########## Image file processing starting ...");
        Collections.sort(pList);
        File dir = GolfProperties.getImageDir(); //golf_images
        File ggDir = new File(dir, "golfgroup" + gg.getGolfGroupID());
        if (!ggDir.exists()) {
            ggDir.mkdir();
        }
        File playerDir = new File(ggDir, "player");
        if (!playerDir.exists()) {
            playerDir.mkdir();
        }

        File templateDir = GolfProperties.getImageTemplateDir();
        File boysDir = new File(templateDir, "boys");
        File girlsDir = new File(templateDir, "girls");

        File[] boysFiles = boysDir.listFiles();
        File[] girlsFiles = girlsDir.listFiles();
        log.log(Level.INFO, "#picture dirs; {0} girls: {1}",
                new Object[]{boysDir.getAbsolutePath(), girlsDir.getAbsolutePath()});

        List<Player> females = new ArrayList<>();
        List<Player> males = new ArrayList<>();
        for (Player player : pList) {
            if (player.getGender() == GENDER_BOYS) {
                males.add(player);
            } else {
                females.add(player);
            }
        }

        int index = 0;
        for (File file : girlsFiles) {
            if (!file.getName().contains(".jpg")) {
                continue;
            }
            if (index < females.size()) {
                Player p = females.get(index);
                File newFile = new File(playerDir, "t" + p.getPlayerID() + ".jpg");
                String name = p.getFirstName() + " " + p.getLastName();
                try {
                    copyFile(file, newFile, name);
                } catch (IOException ex) {
                    log.log(Level.SEVERE, null, ex);
                }
            }
            index++;
        }
        index = 0;
        for (File file : boysFiles) {
            if (!file.getName().contains(".jpg")) {
                continue;
            }
            if (index < males.size()) {
                Player p = males.get(index);
                File newFile = new File(playerDir, "t" + p.getPlayerID() + ".jpg");
                String name = p.getFirstName() + " " + p.getLastName();
                try {
                    copyFile(file, newFile, name);
                } catch (IOException ex) {
                    log.log(Level.SEVERE, "Shite!", ex);
                }
            }
            index++;
        }

    }

    private static void copyFile(File fromFile, File toFile, String name) throws IOException {
        Path FROM = Paths.get(fromFile.getAbsolutePath());
        Path TO = Paths.get(toFile.getAbsolutePath());
        //overwrite existing file, if exists...
        CopyOption[] options = new CopyOption[]{
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.COPY_ATTRIBUTES
        };
        Files.copy(FROM, TO, options);
        log.log(Level.OFF, "Image file copied, created: {0}, file: {1}", new Object[]{name, toFile.getAbsolutePath()});
    }
}
