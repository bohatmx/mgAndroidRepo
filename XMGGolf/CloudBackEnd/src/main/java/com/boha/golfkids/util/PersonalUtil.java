/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.util;

import com.boha.golfkids.data.PersonalPlayer;
import com.boha.golfkids.data.PersonalScore;
import com.boha.golfkids.dto.PersonalPlayerDTO;
import com.boha.golfkids.dto.PersonalScoreDTO;
import com.boha.golfkids.dto.ResponseDTO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author aubreyM
 */

public class PersonalUtil {

    @PersistenceContext
    EntityManager em;
    public PersonalUtil() {
        em  = EMUtil.getEM();
    }
    public ResponseDTO getScores(int personalPlayerID) throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            Query q = em.createNamedQuery("PersonalScore.findByPlayer",PersonalScore.class);
            q.setParameter("id", personalPlayerID);
            List<PersonalScore> list = q.getResultList();
            r.setPersonalScoreList(new ArrayList<PersonalScoreDTO>());
            for (PersonalScore ps : list) {
                r.getPersonalScoreList().add(new PersonalScoreDTO(ps));
            }
        
        }catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to get PersonalScores", e);
            throw new DataException("Failed to get PersonalScores\n"
                    + getErrorString(e));
        }
            return r;
    }
    public ResponseDTO login(String email, String pin)
            throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            Query q = em.createNamedQuery("PersonalPlayer.login", PersonalPlayer.class);
            q.setParameter("email", email);
            q.setParameter("pin", pin);
            q.setMaxResults(1);
            PersonalPlayer pp = (PersonalPlayer) q.getSingleResult();
            r.setPersonalPlayer(new PersonalPlayerDTO(pp));
        } catch (PersistenceException e) {
            r.setStatusCode(ResponseDTO.LOGIN_EXCEPTION);
            r.setMessage("Email address or pin invalid. Please try again");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to sign in PersonalPlayer", e);
            throw new DataException("Failed to sign in PersonalPlayer\n"
                    + getErrorString(e));
        }
        return r;
    }

    public ResponseDTO updateScore(PersonalScoreDTO score,
            DataUtil dataUtil) throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            PersonalScore ps = getPersonalScoreByID(score.getPersonalScoreID());
            setFields(ps, score, dataUtil);           
            em.merge(ps);
            logger.log(Level.OFF, "PersonalScore updated");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to sign in PersonalPlayer", e);
            throw new DataException("Failed to sign in PersonalPlayer\n"
                    + getErrorString(e));
        }
        return r;
    }

    public ResponseDTO addScore(PersonalScoreDTO score,
            DataUtil dataUtil) throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            PersonalScore ps = new PersonalScore();
            ps.setPersonalPlayer(getPersonalPlayerByID(score.getPersonalPlayerID()));
            setFields(ps, score, dataUtil);
            em.persist(ps);
            
            Query q = em.createNamedQuery("PersonalScore.findByPlayer", PersonalScore.class);
            q.setParameter("id", score.getPersonalPlayerID());
            List<PersonalScore> psList = q.getResultList();
            List<PersonalScoreDTO> dto = new ArrayList<>();
            for (PersonalScore psp : psList) {
                dto.add(new PersonalScoreDTO(psp));
            }
            r.setPersonalScoreList(dto);
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to add PersonalScore", e);
            throw new DataException("Failed to add PersonalScore\n"
                    + getErrorString(e));
        }
        return r;
    }

    private void setFields(PersonalScore ps, PersonalScoreDTO score,
            DataUtil dataUtil) {
        if (score.getDatePlayed() > 0) {
            ps.setDatePlayed(new Date(score.getDatePlayed()));
        } else {
            Calendar c = GregorianCalendar.getInstance();
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            c.set(Calendar.HOUR_OF_DAY, 0);
            ps.setDatePlayed(c.getTime());
        }

        ps.setFairwaysHit(score.getFairwaysHit());
        ps.setGreensHit(score.getGreensHit());
        ps.setNumberOfPutts(score.getNumberOfPutts());
        if (score.getClubID() > 0) {
            ps.setClub(dataUtil.getClubByID(score.getClubID()));
        }

        ps.setScore1(score.getScore1());
        ps.setScore2(score.getScore2());
        ps.setScore3(score.getScore3());
        ps.setScore4(score.getScore4());
        ps.setScore5(score.getScore5());
        ps.setScore6(score.getScore6());
        ps.setScore7(score.getScore7());
        ps.setScore8(score.getScore8());
        ps.setScore9(score.getScore9());
        ps.setScore10(score.getScore10());
        ps.setScore11(score.getScore11());
        ps.setScore12(score.getScore12());
        ps.setScore13(score.getScore13());
        ps.setScore14(score.getScore14());
        ps.setScore15(score.getScore15());
        ps.setScore16(score.getScore16());
        ps.setScore17(score.getScore17());
        ps.setScore18(score.getScore18());
    }

    private PersonalPlayer getPersonalPlayerByID(int id) {
        PersonalPlayer p = em.find(PersonalPlayer.class, id);
        return p;
    }

    private PersonalScore getPersonalScoreByID(int id) {
        PersonalScore p = em.find(PersonalScore.class, id);
        return p;
    }

    public ResponseDTO registerPlayer(PersonalPlayerDTO player,
            DataUtil dataUtil) throws DataException {
        ResponseDTO r = new ResponseDTO();
        try {
            PersonalPlayer pp = new PersonalPlayer();
            pp.setCellphone(player.getCell());
            if (player.getClubID() > 0) {
                pp.setClub(dataUtil.getClubByID(player.getClubID()));
            }
            if (player.getCountryID() > 0) {
                pp.setCountry(dataUtil.getCountryByID(player.getCountryID()));
            }
            pp.setEmail(player.getEmail());
            pp.setFirstName(player.getFirstName());
            pp.setLastName(player.getLastName());
            pp.setPin(player.getPin());
            em.persist(pp);
            Query q = em.createNamedQuery("PersonalPlayer.findByEmail", PersonalPlayer.class);
            q.setParameter("email", player.getEmail());
            pp = (PersonalPlayer) q.getSingleResult();
            r.setPersonalPlayer(new PersonalPlayerDTO(pp));

            logger.log(Level.INFO, "PersonalPlayer added");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to add PersonalPlayer", e);
            throw new DataException("Failed to add PersonalPlayer\n"
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

    static final Logger logger = Logger.getLogger("PersonUtil");
}
