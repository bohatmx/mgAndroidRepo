/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.util;

import com.boha.golfkids.data.Agegroup;
import com.boha.golfkids.data.LeaderBoard;
import com.boha.golfkids.data.OrderOfMeritPoint;
import com.boha.golfkids.data.Tournament;
import com.boha.golfkids.data.TourneyScoreByRound;
import com.boha.golfkids.dto.AgeGroupDTO;
import com.boha.golfkids.dto.ClubCourseDTO;
import com.boha.golfkids.dto.LeaderBoardCarrierDTO;
import com.boha.golfkids.dto.LeaderBoardDTO;
import com.boha.golfkids.dto.PlayerDTO;
import com.boha.golfkids.dto.ResponseDTO;
import com.boha.golfkids.dto.TourneyScoreByRoundDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Utility class to produce points based leaderBoards. 
 * @author Aubrey Malabie
 */
public class LeaderBoardPointsUtil {

    @PersistenceContext
    EntityManager em;

    public LeaderBoardPointsUtil() {
        em  = EMUtil.getEM();
    }

    private void setScores(Tournament t, List<LeaderBoard> baseList,
            List<LeaderBoardDTO> lbList, List<TourneyScoreByRound> tourneyScoreList) {
        for (LeaderBoard s : baseList) {
            LeaderBoardDTO d = new LeaderBoardDTO();
            d.setTournamentType(s.getTournament().getTournamentType());
            d.setLeaderBoardID(s.getLeaderBoardID());
            d.setWinnerFlag(s.getWinnerFlag());
            d.setPlayer(new PlayerDTO(s.getPlayer()));
            d.setRounds(t.getGolfRounds());
            d.setTournamentID(t.getTournamentID());
            d.setTournamentName(t.getTourneyName());
            d.setHolesPerRound(t.getHolesPerRound());
            d.setScoreRound1(s.getScoreRound1());
            d.setScoreRound2(s.getScoreRound2());
            d.setScoreRound3(s.getScoreRound3());
            d.setScoreRound4(s.getScoreRound4());
            d.setScoreRound5(s.getScoreRound5());
            d.setScoreRound6(s.getScoreRound6());
            d.setTotalScore(d.getTotalScore());
            //points
            d.setPointsRound1(s.getPointsRound1());
            d.setPointsRound2(s.getPointsRound2());
            d.setPointsRound3(s.getPointsRound3());
            d.setPointsRound4(s.getPointsRound4());
            d.setPointsRound5(s.getPointsRound5());
            d.setPointsRound6(s.getPointsRound6());
            d.setTotalPoints(d.getTotalPoints());
            d.setTourneyScoreByRoundList(new ArrayList<TourneyScoreByRoundDTO>());
            for (TourneyScoreByRound tsbr : tourneyScoreList) {
                if (tsbr.getLeaderBoard().getLeaderBoardID()
                        == s.getLeaderBoardID()) {
                    d.getTourneyScoreByRoundList().add(new TourneyScoreByRoundDTO(tsbr));
                }
            }
            lbList.add(d);
        }
    }

    public ResponseDTO getTournamentLeaderBoard(int tournamentID, DataUtil dataUtil)
            throws DataException {
        log.log(Level.OFF, "Getting Points LeaderBoard .......");
        try {
            Tournament t = dataUtil.getTournamentByID(tournamentID);
            if (t.getUseAgeGroups() > 0) {
                return getSectionedLeaderBoards(t);
            } else {
                return getLeaderBoard(t);
            }
        } catch (DataException e) {
            log.log(Level.SEVERE, null, e);
            throw new DataException("Failed to get LeaderBoard\n"
                    + getErrorString(e));
        }
    }

    private ResponseDTO getSectionedLeaderBoards(Tournament t) throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            Query q = em.createNamedQuery("AgeGroup.findByGolfGroup", Agegroup.class);
            q.setParameter("id", t.getGolfGroup().getGolfGroupID());
            List<Agegroup> ageList = q.getResultList();
            r.setLeaderBoardCarriers(new ArrayList<LeaderBoardCarrierDTO>());
            for (Agegroup ag : ageList) {
                List<LeaderBoardDTO> list = getLeaderBoardByAgeGroup(t, ag.getAgeGroupID());
                LeaderBoardCarrierDTO carrier = new LeaderBoardCarrierDTO();
                carrier.setAgeGroup(new AgeGroupDTO(ag));
                carrier.setLeaderBoardList(list);
                r.getLeaderBoardCarriers().add(carrier);
            }
            //create all leaderboards
            LeaderBoardCarrierDTO carrier = new LeaderBoardCarrierDTO();
            carrier.setAgeGroup(null);
            List<LeaderBoardDTO> combinedList = getLeaderBoard(t).getLeaderBoardList();
            carrier.setLeaderBoardList(combinedList);
            r.getLeaderBoardCarriers().add(carrier);

        } catch (DataException e) {
            log.log(Level.SEVERE, null, e);
            throw new DataException("Failed to get LeaderBoardByAgeGroup\n"
                    + getErrorString(e));
        }
        return r;
    }

   
    private void removeWithdrawnPlayers(List<LeaderBoard> list) {
        int index = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (LeaderBoard lb : list) {
            if (lb.getWithDrawn() > 0) {
                if (!map.containsKey(index)) {
                    map.put(index, lb.getLeaderBoardID());
                }
                
            }
            index++;
        }
        for (Map.Entry pairs : map.entrySet()) {           
            int leaderBoardID = (Integer) pairs.getValue();
            int u = getIndex(list, leaderBoardID);
            list.remove(u);
                
                
        }
        
    }
    private int getIndex(List<LeaderBoard> list, int leaderBoardID) {
        int index = 0;
        for (LeaderBoard lb : list) {
            if (lb.getLeaderBoardID() == leaderBoardID) {
                return index;
            }
            index++;
        }
        return -1;
    }
    private List<LeaderBoardDTO> getLeaderBoardByAgeGroup(Tournament t, int ageGroupID) throws DataException {
        List<LeaderBoardDTO> lbList = new ArrayList<>();
        try {

            Query q = em.createNamedQuery("LeaderBoard.findByAgeGroup",
                    LeaderBoard.class);
            q.setParameter("tID", t.getTournamentID());
            q.setParameter("aID", ageGroupID);
            List<LeaderBoard> leaderBoardList = q.getResultList();
            //check for withdrawn
            removeWithdrawnPlayers(leaderBoardList);
            Query qq = em.createNamedQuery("TourneyScoreByRound.getByTourneyAgeGroup",
                    TourneyScoreByRound.class);
            qq.setParameter("tID", t.getTournamentID());
            qq.setParameter("aID", ageGroupID);
            List<TourneyScoreByRound> rList = qq.getResultList();
            setScores(t, leaderBoardList, lbList, rList);
            //calculate current par status and position
            calculateLeaderboard(lbList);
            //get possible order of merit points
            Query z = em.createNamedQuery("OrderOfMeritPoint.findByGolfGroup", OrderOfMeritPoint.class);
            z.setParameter("id", t.getGolfGroup().getGolfGroupID());
            OrderOfMeritPoint meritPoint = (OrderOfMeritPoint) z.getSingleResult();
            for (LeaderBoardDTO dto : lbList) {
                setPoints(meritPoint, dto);
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            throw new DataException("Failed to get LeaderBoardByAgeGroup\n"
                    + getErrorString(e));
        }

        return lbList;
    }

    private ResponseDTO getLeaderBoard(Tournament t) throws DataException {
        ResponseDTO r = new ResponseDTO();
        List<LeaderBoardDTO> lbList = new ArrayList<>();
        try {
            Query q = em.createNamedQuery("LeaderBoard.findByTournament",
                    LeaderBoard.class);
            q.setParameter("id", t.getTournamentID());
            List<LeaderBoard> leaderBoardList = q.getResultList();
            removeWithdrawnPlayers(leaderBoardList);
            Query qq = em.createNamedQuery("TourneyScoreByRound.getByTourney",
                    TourneyScoreByRound.class);
            qq.setParameter("id", t.getTournamentID());
            List<TourneyScoreByRound> rList = qq.getResultList();
            setScores(t, leaderBoardList, lbList, rList);
            //calculate current par status and position
            calculateLeaderboard(lbList);
            //get possible order of merit points
            Query z = em.createNamedQuery("OrderOfMeritPoint.findByGolfGroup", OrderOfMeritPoint.class);
            z.setParameter("id", t.getGolfGroup().getGolfGroupID());
            OrderOfMeritPoint meritPoint = (OrderOfMeritPoint) z.getSingleResult();
            for (LeaderBoardDTO dto : lbList) {
                setPoints(meritPoint, dto);
            }
            r.setLeaderBoardList(lbList);

        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
            throw new DataException("Failed to get LeaderBoard\n"
                    + getErrorString(e));
        }

        return r;
    }

    public ResponseDTO closeLeaderBoard(List<LeaderBoardDTO> list, int tournamentID) throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            //close tournament
            Tournament t = em.find(Tournament.class, tournamentID);
            t.setClosedForScoringFlag(1);
            em.merge(t);
            //get order of merit points
            Query z = em.createNamedQuery("OrderOfMeritPoint.findByGolfGroup", OrderOfMeritPoint.class);
            z.setParameter("id", t.getGolfGroup().getGolfGroupID());
            OrderOfMeritPoint meritPoint = (OrderOfMeritPoint) z.getSingleResult();
            Query y = em.createNamedQuery("TourneyScoreByRound.getByTourney", TourneyScoreByRound.class);
            y.setParameter("id", tournamentID);
            List<TourneyScoreByRound> tsbrList = y.getResultList();
            for (LeaderBoardDTO lb : list) {
                LeaderBoard b = em.find(LeaderBoard.class, lb.getLeaderBoardID());
                setPointsPermanently(meritPoint, b);
                em.merge(b);
                for (TourneyScoreByRound tsbr : tsbrList) {
                    if (tsbr.getLeaderBoard().getLeaderBoardID() == lb.getLeaderBoardID()) {
                        tsbr.setScoringComplete(1);
                        em.merge(tsbr);
                    }
                }
                
            }
         } catch (DataException e) {
            log.log(Level.SEVERE, null, e);
            throw new DataException("Failed to close LeaderBoard\n"
                    + getErrorString(e));
        }
        return r;
    }
    private void setPointsPermanently(OrderOfMeritPoint meritPoint, LeaderBoard dto) throws DataException {

        if (dto.getPosition() == 1) {
            processPosition1Permanently(meritPoint, dto);
            return;
        }
        if (dto.getPosition() == 2) {
            dto.setOrderOfMeritPoints(meritPoint.getRunnerUp());
            return;
        }
        if (dto.getPosition() < 4) {
            dto.setOrderOfMeritPoints(meritPoint.getTop3());
            return;
        }
        if (dto.getPosition() < 6) {
            dto.setOrderOfMeritPoints(meritPoint.getTop5());
            return;
        }
        if (dto.getPosition() < 11) {
            dto.setOrderOfMeritPoints(meritPoint.getTop10());
            return;
        }
        if (dto.getPosition() < 21) {
            dto.setOrderOfMeritPoints(meritPoint.getTop20());
            return;
        }
        if (dto.getPosition() < 31) {
            dto.setOrderOfMeritPoints(meritPoint.getTop30());
            return;
        }
        if (dto.getPosition() < 41) {
            dto.setOrderOfMeritPoints(meritPoint.getTop40());
            return;
        }
        if (dto.getPosition() < 51) {
            dto.setOrderOfMeritPoints(meritPoint.getTop50());
            return;
        }
        if (dto.getPosition() < 101) {
            dto.setOrderOfMeritPoints(meritPoint.getTop100());
        } else {
            dto.setOrderOfMeritPoints(0);
        }

    }
    private void processPosition1Permanently(OrderOfMeritPoint meritPoint, LeaderBoard dto)  {

        if (dto.getTied() > 0) {
            if (dto.getWinnerFlag() > 0) {
                dto.setOrderOfMeritPoints(meritPoint.getWin());
            } else {
                dto.setOrderOfMeritPoints(meritPoint.getTiedFirst());
            }

        } else {
            dto.setOrderOfMeritPoints(meritPoint.getWin());
        }
    }
    private void setPoints(OrderOfMeritPoint meritPoint, LeaderBoardDTO dto) throws DataException {

       
        if (dto.getPosition() == 1) {
            processPosition1(meritPoint, dto);
            return;
        }
        if (dto.getPosition() == 2) {
            dto.setOrderOfMeritPoints(meritPoint.getRunnerUp());
            return;
        }
        
        if (dto.getPosition() < 4) {
            dto.setOrderOfMeritPoints(meritPoint.getTop3());
            return;
        }
        if (dto.getPosition() < 6) {
            dto.setOrderOfMeritPoints(meritPoint.getTop5());
            return;
        }
        if (dto.getPosition() < 11) {
            dto.setOrderOfMeritPoints(meritPoint.getTop10());
             return;
        }
        if (dto.getPosition() < 21) {
            dto.setOrderOfMeritPoints(meritPoint.getTop20());
            return;
        }
        if (dto.getPosition() < 31) {
            dto.setOrderOfMeritPoints(meritPoint.getTop30());
            return;
        }
        if (dto.getPosition() < 41) {
            dto.setOrderOfMeritPoints(meritPoint.getTop40());
            return;
        }
        if (dto.getPosition() < 51) {
            dto.setOrderOfMeritPoints(meritPoint.getTop50());
            return;
        }
        if (dto.getPosition() < 101) {
            dto.setOrderOfMeritPoints(meritPoint.getTop100());
        } else {
            dto.setOrderOfMeritPoints(0);
            log.log(Level.OFF, "points set to zero: {0}", 
                    new Object[]{ 
                    dto.getPlayer().getFirstName() + " " + dto.getPlayer().getLastName()});
        }

    }

    private void updateOrderOfMeritPoints(LeaderBoard lb) throws DataException {
        try {
            em.merge(lb);
            log.log(Level.INFO, "Updated orderOfMeritPoints: {0} {1} points: {2}", 
                    new Object[]{lb.getPlayer().getFirstName(), lb.getPlayer().getLastName(), 
                        lb.getOrderOfMeritPoints()});
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to update orderOfMeritPoints", e);
            throw new DataException("Failed to update orderOfMeritPoints\n" + getErrorString(e));
        }
    }
    private void processPosition1(OrderOfMeritPoint meritPoint, LeaderBoardDTO dto)  {

        if (dto.isTied()) {
            if (dto.getWinnerFlag() > 0) {
                dto.setOrderOfMeritPoints(meritPoint.getWin());
            } else {
                dto.setOrderOfMeritPoints(meritPoint.getTiedFirst());
            }

        } else {
            dto.setOrderOfMeritPoints(meritPoint.getWin());
        }
    }

    public ResponseDTO getPlayerHistory(int playerID) throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            Query z = em.createNamedQuery("TourneyScoreByRound.getByPlayer", TourneyScoreByRound.class);
            z.setParameter("id", playerID);
            List<TourneyScoreByRound> tsbList = z.getResultList();

            //
            Query q = em.createNamedQuery("LeaderBoard.findByPlayer", LeaderBoard.class);
            q.setParameter("id", playerID);
            List<LeaderBoard> list = q.getResultList();
            r.setLeaderBoardList(new ArrayList());
            for (LeaderBoard lb : list) {
                LeaderBoardDTO b = new LeaderBoardDTO(lb, true);
                b.setTourneyScoreByRoundList(new ArrayList<TourneyScoreByRoundDTO>());
                for (TourneyScoreByRound tsb : tsbList) {
                    if (tsb.getTournamentIDx()
                            == b.getTournamentID()) {
                        b.getTourneyScoreByRoundList().add(new TourneyScoreByRoundDTO(tsb));
                    }
                }

                r.getLeaderBoardList().add(b);
            }

            Logger.getLogger("me").log(Level.OFF, "LeaderBoard items found: {0}",
                    r.getLeaderBoardList().size());
        } catch (Exception e) {
            throw new DataException("Failed to get player history\n"
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
        //
        return sb.toString();
    }

    private void setPositions(List<LeaderBoardDTO> list) {

        int mPosition = 1;
        int running = 1, score = 999;
        for (LeaderBoardDTO lb : list) {
            if (lb.isTied()) {
                if (score == 999) {
                    score = lb.getTotalPoints();
                    mPosition = running;
                    lb.setPosition(mPosition);
                } else {
                    if (score == lb.getTotalPoints()) {
                        lb.setPosition(mPosition);
                    } else {
                        score = lb.getTotalPoints();
                        mPosition = running;
                        lb.setPosition(mPosition);
                    }
                }

            } else {
                score = 999;
                lb.setPosition(running);
            }

            running++;
        }
        //get possible order of merit points
        Query q = em.createNamedQuery("OrderOfMeritPoint.findByGolfGroup", OrderOfMeritPoint.class);
        q.setParameter("id", score);
        List<OrderOfMeritPoint> olist = q.getResultList();
        
        //TODO - order of merit points processing ....
    }
    public static final int NO_PAR_STATUS = 9999;

   
    private void setCurrentRoundStatus(LeaderBoardDTO lb, TourneyScoreByRoundDTO r) {

        int cnt = 0;
        ClubCourseDTO cc = r.getClubCourse();
        int parStatus = 0, totalPoints = 0;
        if (r.getScore1() == 0) {
            cnt++;
        } else {
            parStatus += cc.getParHole1() - r.getScore1();
            totalPoints += r.getPoints1();
        }
        if (r.getScore2() == 0) {
            cnt++;
        } else {
            parStatus += cc.getParHole2() - r.getScore2();
            totalPoints += r.getPoints2();
        }
        if (r.getScore3() == 0) {
            cnt++;
        } else {
            parStatus += cc.getParHole3() - r.getScore3();
            totalPoints += r.getPoints3();
        }
        if (r.getScore4() == 0) {
            cnt++;
        } else {
            parStatus += cc.getParHole4() - r.getScore4();
            totalPoints += r.getPoints4();
        }
        if (r.getScore5() == 0) {
            cnt++;
        } else {
            parStatus += cc.getParHole5() - r.getScore5();
            totalPoints += r.getPoints5();
        }
        if (r.getScore6() == 0) {
            cnt++;
        } else {
            parStatus += cc.getParHole6() - r.getScore6();
            totalPoints += r.getPoints6();
        }
        if (r.getScore7() == 0) {
            cnt++;
        } else {
            parStatus += cc.getParHole7() - r.getScore7();
            totalPoints += r.getPoints7();
        }
        if (r.getScore8() == 0) {
            cnt++;
        } else {
            parStatus += cc.getParHole8() - r.getScore8();
            totalPoints += r.getPoints8();
        }
        if (r.getScore9() == 0) {
            cnt++;
        } else {
            parStatus += cc.getParHole9() - r.getScore9();
            totalPoints += r.getPoints9();
        }
        if (r.getScore10() == 0) {
            cnt++;
        } else {
            parStatus += cc.getParHole10() - r.getScore10();
            totalPoints += r.getPoints10();
        }
        if (r.getScore11() == 0) {
            cnt++;
        } else {
            parStatus += cc.getParHole11() - r.getScore11();
            totalPoints += r.getPoints11();
        }
        if (r.getScore12() == 0) {
            cnt++;
        } else {
            parStatus += cc.getParHole12() - r.getScore12();
            totalPoints += r.getPoints11();
        }
        if (r.getScore13() == 0) {
            cnt++;
        } else {
            parStatus += cc.getParHole13() - r.getScore13();
            totalPoints += r.getPoints13();
        }
        if (r.getScore14() == 0) {
            cnt++;
        } else {
            parStatus += cc.getParHole14() - r.getScore14();
            totalPoints += r.getPoints14();
        }
        if (r.getScore15() == 0) {
            cnt++;
        } else {
            parStatus += cc.getParHole15() - r.getScore15();
            totalPoints += r.getPoints15();
        }
        if (r.getScore16() == 0) {
            cnt++;
        } else {
            parStatus += cc.getParHole16() - r.getScore16();
            totalPoints += r.getPoints16();
        }
        if (r.getScore17() == 0) {
            cnt++;
        } else {
            parStatus += cc.getParHole17() - r.getScore17();
            totalPoints += r.getPoints17();
        }
        if (r.getScore18() == 0) {
            cnt++;
        } else {
            parStatus += cc.getParHole18() - r.getScore18();
            totalPoints += r.getPoints18();
        }
        lb.setTotalPoints(totalPoints);
        if (cnt < 18) {
            lb.setCurrentRoundStatus(parStatus);
        }

    }

    private void setParStatus(LeaderBoardDTO lb) {
        int parStatus = 0, totalPoints = 0;
        int cnt = 0;
        for (TourneyScoreByRoundDTO r : lb.getTourneyScoreByRoundList()) {
            setCurrentRoundStatus(lb, r);
            ClubCourseDTO cc = r.getClubCourse();
            totalPoints += r.getPoints1();
            totalPoints += r.getPoints2();
            totalPoints += r.getPoints3();
            totalPoints += r.getPoints4();
            totalPoints += r.getPoints5();
            totalPoints += r.getPoints6();
            totalPoints += r.getPoints7();
            totalPoints += r.getPoints8();
            totalPoints += r.getPoints9();
            
            totalPoints += r.getPoints10();
            totalPoints += r.getPoints11();
            totalPoints += r.getPoints12();
            totalPoints += r.getPoints13();
            totalPoints += r.getPoints14();
            totalPoints += r.getPoints15();
            totalPoints += r.getPoints16();
            totalPoints += r.getPoints17();
            totalPoints += r.getPoints18();
            
            if (r.getScore1() > 0) {
                parStatus += cc.getParHole1() - r.getScore1();
                lb.setLastHole(1);
                cnt++;
            }
            if (r.getScore2() > 0) {
                parStatus += cc.getParHole2() - r.getScore2();
                lb.setLastHole(2);
                cnt++;
            }
            if (r.getScore3() > 0) {
                parStatus += cc.getParHole3() - r.getScore3();
                lb.setLastHole(3);
                cnt++;
            }
            if (r.getScore4() > 0) {
                parStatus += cc.getParHole4() - r.getScore4();
                lb.setLastHole(4);
                cnt++;
            }
            if (r.getScore5() > 0) {
                parStatus += cc.getParHole5() - r.getScore5();
                lb.setLastHole(5);
                cnt++;
            }
            if (r.getScore6() > 0) {
                parStatus += cc.getParHole6() - r.getScore6();
                lb.setLastHole(6);
                cnt++;
            }
            if (r.getScore7() > 0) {
                parStatus += cc.getParHole7() - r.getScore7();
                lb.setLastHole(7);
                cnt++;
            }
            if (r.getScore8() > 0) {
                parStatus += cc.getParHole8() - r.getScore8();
                lb.setLastHole(8);
                cnt++;
            }
            if (r.getScore9() > 0) {
                parStatus += cc.getParHole9() - r.getScore9();
                lb.setLastHole(9);
                cnt++;
            }
            if (r.getScore10() > 0) {
                parStatus += cc.getParHole10() - r.getScore10();
                lb.setLastHole(10);
                cnt++;
            }
            if (r.getScore11() > 0) {
                parStatus += cc.getParHole11() - r.getScore11();
                lb.setLastHole(11);
                cnt++;
            }
            if (r.getScore12() > 0) {
                parStatus += cc.getParHole12() - r.getScore12();
                lb.setLastHole(12);
                cnt++;
            }
            if (r.getScore13() > 0) {
                parStatus += cc.getParHole13() - r.getScore13();
                lb.setLastHole(13);
                cnt++;
            }
            if (r.getScore14() > 0) {
                parStatus += cc.getParHole14() - r.getScore14();
                lb.setLastHole(14);
                cnt++;
            }
            if (r.getScore15() > 0) {
                parStatus += cc.getParHole15() - r.getScore15();
                lb.setLastHole(15);
                cnt++;
            }
            if (r.getScore16() > 0) {
                parStatus += cc.getParHole16() - r.getScore16();
                lb.setLastHole(16);
                cnt++;
            }
            if (r.getScore17() > 0) {
                parStatus += cc.getParHole17() - r.getScore17();
                lb.setLastHole(17);
                cnt++;
            }
            if (r.getScore18() > 0) {
                parStatus += cc.getParHole18() - r.getScore18();
                lb.setLastHole(18);
                cnt++;
            }
            lb.setTotalPoints(totalPoints);
            if (cnt == 0) {
                lb.setParStatus(NO_PAR_STATUS);
            } else {
                lb.setParStatus(parStatus);
            }

        }
    }

    private void calculateLeaderboard(List<LeaderBoardDTO> list) {

        for (LeaderBoardDTO lb : list) {
            setParStatus(lb);
            lb.setWinnerFlag(0);
            lb.setSortType(LeaderBoardDTO.SORT_BY_TOTAL_POINTS);
        }
        Collections.sort(list);
        //set positions
        HashMap<Integer, Integer> map = new HashMap<>();
        int pos = 1;
        for (LeaderBoardDTO board : list) {
            if (map.containsKey(board.getTotalPoints())) {
                continue;
            }
            map.put(board.getTotalPoints(), pos);
            pos++;
        }

        for (LeaderBoardDTO b : list) {
            if (map.containsKey(b.getTotalPoints())) {
                b.setPosition(map.get(b.getTotalPoints()));
            }
        }

        //check for tied
        HashMap<Integer, Integer> map2 = new HashMap<>();
        HashMap<Integer, Integer> tied = new HashMap<>();

        for (LeaderBoardDTO d : list) {
            
            if (map2.containsKey(d.getTotalPoints())) {
                tied.put(d.getTotalPoints(), d.getTotalPoints());
            } else {
                map2.put(d.getTotalPoints(), d.getTotalPoints());
            }

        }
        for (Map.Entry pairs : tied.entrySet()) {
            for (LeaderBoardDTO d : list) {
                int a = (Integer) pairs.getKey();
                if (d.getTotalPoints() == a) {
                    d.setTied(true);
                }
            }
        }
        setPositions(list);
    }
    static final Logger log = Logger.getLogger("LeaderBoardSFUtil");
}
