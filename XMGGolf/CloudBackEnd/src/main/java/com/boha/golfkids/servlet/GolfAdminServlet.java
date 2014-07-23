/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.servlet;

import com.boha.golfkids.dto.AgeGroupDTO;
import com.boha.golfkids.dto.CountryDTO;
import com.boha.golfkids.dto.RequestDTO;
import com.boha.golfkids.dto.ResponseDTO;
import com.boha.golfkids.dto.TournamentDTO;
import com.boha.golfkids.util.CloudMessagingRegistrar;
import com.boha.golfkids.util.DataException;
import com.boha.golfkids.util.DataUtil;
import com.boha.golfkids.util.LeaderBoardPointsUtil;
import com.boha.golfkids.util.LeaderBoardUtil;
import com.boha.golfkids.util.LoginException;
import com.boha.golfkids.util.PlatformUtil;
import com.boha.golfkids.util.WorkerBee;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author AubreyM
 */
//@WebServlet(name = "GolfAdminServlet", urlPatterns = {"/admin"})
public class GolfAdminServlet extends HttpServlet {

    ////@EJB


    /**
     * Serves as the main administration servlet
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long start = System.currentTimeMillis();
        Gson gson = new Gson();
        ResponseDTO resp = new ResponseDTO();
        RequestDTO dto = getRequest(gson, request);
        DataUtil dataUtil = new DataUtil();
        LeaderBoardUtil leaderBoardUtil = new LeaderBoardUtil();
        PlatformUtil platformUtil = new PlatformUtil();
        WorkerBee workerBee = new WorkerBee();
        LeaderBoardPointsUtil leaderBoardPointsUtil = new LeaderBoardPointsUtil();
        if (dto == null) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("Bad Request, nobody's home for you!");
            platformUtil.addErrorStore(9, "Bad, rogue request detected", "GolfAdminServlet");
            out.close();
        } else {
            log.log(Level.WARNING, "################################### "
                    + "---> GolfAdminServlet started ... requestType: {0}", dto.getRequestType());
            try {

                switch (dto.getRequestType()) {
                    case RequestDTO.IMPORT_PLAYERS:
                        resp = dataUtil.importPlayers(dto.getImportPlayers(), dto.getGolfGroupID());
                        break;
                    case RequestDTO.GET_PLAYER_GROUPS:
                        resp = dataUtil.getPlayerGroups(dto.getPlayerID());
                        break;
                    case RequestDTO.GET_APPUSER_GROUPS:
                        resp = dataUtil.getAppUserGroups(dto.getAppUserID());
                        break;
                    case RequestDTO.GET_TOURNAMENTS:
                        List<TournamentDTO> list = dataUtil.getTournamentByGroup(dto.getGolfGroupID());
                        resp.setTournaments(list);
                        break;
                    case RequestDTO.SIGNIN_APP_USER:
                        resp = dataUtil.signInAppUser(dto.getEmail(), dto.getGcmDevice(), platformUtil);
                        break;
                    case RequestDTO.REGISTER_APP_USER:
                        resp = dataUtil.addAppUser(dto.getGolfGroupID(), dto.getEmail(), platformUtil);
                        break;
                    case RequestDTO.SIGN_IN_SCORER:
                        resp = dataUtil.signInScorer(dto.getEmail(), dto.getPin(), dto.getGcmDevice(), platformUtil);
                        break;
                    case RequestDTO.SIGN_IN_PLAYER:
                        resp = dataUtil.signInPlayer(dto.getEmail(), dto.getPin(), dto.getGcmDevice(), platformUtil);
                        break;
                    case RequestDTO.ADMIN_LOGIN:
                        resp = dataUtil.signInAdministrator(dto.getEmail(), dto.getPin(), dto.getGcmDevice(), platformUtil);
                        break;
                    case RequestDTO.GET_ERROR_REPORTS:
                        resp = dataUtil.getMalengaGolfEvents(0, 0);
                        break;
                    case RequestDTO.SEND_GCM_REGISTRATION:
                        resp = CloudMessagingRegistrar.sendRegistration(dto.getGcmRegistrationID(),
                                platformUtil);
                        break;
                    case RequestDTO.UPDATE_TEE_TIMES:
                        resp = dataUtil.updateTeeTime(dto.getTourneyScoreByRound());
                        break;
                    case RequestDTO.DELETE_TOURNAMENT:
                        resp = dataUtil.deleteTournament(dto.getTournamentID());
                        break;
                    case RequestDTO.CLOSE_TOURNAMENT:
                        resp = dataUtil.closeTournament(dto.getTournamentID());
                        break;
                    case RequestDTO.CLOSE_LEADERBORD:
                        resp = leaderBoardUtil.closeLeaderBoard(dto.getTournamentID());
                        break;

                    case RequestDTO.UPDATE_WINNER_FLAG:
                        resp = dataUtil.updateWinnerFlag(dto.getLeaderBoardID(), dto.getWinnerFlag());
                        break;
                    case RequestDTO.WITHDRAW_PLAYER:
                        resp = dataUtil.withdrawPlayer(dto.getTournamentID(), dto.getLeaderBoardID());
                        break;

                    case RequestDTO.GET_GOLF_GROUP_DATA:
                        resp = dataUtil.getGolfGroupData(dto.getGolfGroupID(),
                                dto.getCountryID());
                        break;
                    case RequestDTO.GET_CLUBS_IN_PROVINCE:
                        resp = dataUtil.getClubsByProvince(dto.getProvinceID(), dto.getPage(), workerBee);
                        break;
                    case RequestDTO.DELETE_SAMPLE_TOURNAMENTS:
                        resp = dataUtil.deleteSample(dto.getGolfGroupID(), dto.getCountryID());
                        break;
                    case RequestDTO.GET_CLUBS_NEARBY:
                        resp = workerBee.getClubsWithinRadius(
                                dto.getLatitude(), dto.getLongitude(),
                                dto.getRadius(), dto.getRadiusType(), dto.getPage());

                        break;
                    case RequestDTO.GET_LEADERBOARD:
                        log.log(Level.INFO, "getting Leaderboard type is: {0}", dto.getTournamentType());
                        switch (dto.getTournamentType()) {
                            case RequestDTO.STROKE_PLAY_INDIVIDUAL:
                                resp = leaderBoardUtil.getTournamentLeaderBoard(dto.getTournamentID(), dataUtil);
                                break;
                            case RequestDTO.STABLEFORD_INDIVIDUAL:
                                resp = leaderBoardPointsUtil.getTournamentLeaderBoard(dto.getTournamentID(), dataUtil);
                                break;

                        }

                        break;

                    case RequestDTO.UPDATE_TOURNAMENT_SCORES:
                        resp = dataUtil.updateTournamentScoreByRound(dto.getLeaderBoard());
                        break;
                    case RequestDTO.UPDATE_TOURNAMENT_SCORE_TOTALS:
                        resp = dataUtil.updateTournamentScore(dto.getLeaderBoard());
                        break;
                    case RequestDTO.ADD_TOURNAMENT_PLAYERS:
                        resp = dataUtil.addTournamentPlayers(dto.getIdList(), dto.getTournamentID(), platformUtil);
                        break;
                    case RequestDTO.ADD_TOURNAMENT_PLAYER:
                        resp = dataUtil.addTournamentPlayer(dto.getLeaderBoard(), platformUtil);
                        break;

                    case RequestDTO.ADD_TOURNAMENT:
                        resp = dataUtil.addTournament(dto.getTournament(), platformUtil);

                        break;
                    case RequestDTO.ADD_PLAYER:
                        resp = dataUtil.addPlayer(dto.getPlayer(), dto.getGolfGroupID(), platformUtil);
                        break;
                    case RequestDTO.UPDATE_PLAYER:
                        dataUtil.updatePlayer(dto.getPlayer());
                        break;

                    case RequestDTO.ADD_PARENT:
                        resp = dataUtil.addParent(dto.getParent(), dto.getGolfGroupID(), platformUtil);
                        break;
                    case RequestDTO.UPDATE_PARENT:
                        dataUtil.updateParent(dto.getParent());
                        break;

                    case RequestDTO.ADD_ADMINISTRATOR:
                        resp = dataUtil.addGolfGroupAdmin(dto.getAdministrator());

                        break;
                    case RequestDTO.ADD_GOLF_GROUP:
                        resp = dataUtil.addGolfGroup(dto.getGolfGroup(),
                                dto.getAdministrator(), platformUtil);
                        break;
                    case RequestDTO.UPDATE_GOLF_GROUP:
                        dataUtil.updateGolfGroup(dto.getGolfGroup());
                        break;
                    case RequestDTO.ADD_CLUB:
                        resp = dataUtil.addClub(dto.getClub());
                        break;
                    case RequestDTO.GET_CLUBS_IN_COUNTRY:
                        resp = dataUtil.getClubsByCountry(dto.getCountryID());
                        break;

                    case RequestDTO.GET_PROVINCES:
                        resp = dataUtil.getProvincesByCountry(dto.getCountryID());
                        break;

                    case RequestDTO.UPDATE_PLAYER_PARENT:
                        //TODO - think!
                        break;
                    case RequestDTO.UPDATE_ADMINISTRATOR:
                        dataUtil.updateAdmin(dto.getAdministrator());
                        break;
                    case RequestDTO.UPDATE_CLUB:
                        dataUtil.updateClub(dto.getClub());
                        break;
                    case RequestDTO.UPDATE_CLUB_COURSE:
                        dataUtil.updateClubCourse(dto.getClubCourse());
                        break;
                    case RequestDTO.GET_AGE_GROUPS:
                        List<AgeGroupDTO> ageList = dataUtil.getAgeGroups(dto.getGolfGroupID());
                        resp.setAgeGroups(ageList);
                        break;
                    case RequestDTO.GET_AGE_GROUPS_BOYS:
                        List<AgeGroupDTO> ageList1 = dataUtil.getAgeGroupsBoys(dto.getGolfGroupID());
                        resp.setAgeGroups(ageList1);
                        break;
                    case RequestDTO.GET_AGE_GROUPS_GIRLS:
                        List<AgeGroupDTO> ageList2 = dataUtil.getAgeGroupsGirls(dto.getGolfGroupID());
                        resp.setAgeGroups(ageList2);
                        break;
                    case RequestDTO.GET_COUNTRIES:
                        List<CountryDTO> cntrList = dataUtil.getCountries();
                        resp.setCountries(cntrList);
                        break;

                    case RequestDTO.ADD_SCORER:
                        resp = dataUtil.addScorer(dto.getScorer(), dto.getGolfGroupID(), platformUtil);
                        break;
                    case RequestDTO.UPDATE_SCORER:
                        dataUtil.updateScorer(dto.getScorer());
                        break;

                    case RequestDTO.GET_TOURNAMENT_PLAYERS:
                        resp = dataUtil.getTournamentPlayers(dto.getTournamentID());
                        break;
                    case RequestDTO.REMOVE_TOURNAMENT_PLAYER:
                        resp = dataUtil.removeTournamentPlayer(dto.getTournamentID(),
                                dto.getPlayerID());
                        break;
                    case RequestDTO.UPDATE_TOURNAMENT:
                        resp = dataUtil.updateTournament(dto.getTournament());
                        break;
                    case RequestDTO.GET_PLAYER_HISTORY:
                        resp = leaderBoardUtil.getPlayerHistory(dto.getPlayerID());
                        break;
                    case RequestDTO.ADD_VIDEO_CLIP:
                        resp = dataUtil.addVideoClip(dto.getVideoClip());
                        break;
                    case RequestDTO.GET_VIDEO_CLIPS_BY_GOLF_GROUP:
                        resp = dataUtil.getVideoClips(dto.getGolfGroupID());
                        break;
                    default:
                        platformUtil.addErrorStore(7, "Request Type specified not on", "GolfAdminServlet");
                        resp.setStatusCode(7);
                        resp.setMessage("Request Type specified not on");
                        break;
                }
            } catch (LoginException e) {
                resp.setStatusCode(ResponseDTO.DATA_EXCEPTION);
                resp.setMessage("SignIn failed. Please try again later");
                log.log(Level.SEVERE, "SignIn failed", e);
                platformUtil.addErrorStore(8, "SignIn failed", "GolfAdminServlet");
            } catch (DataException e) {
                resp.setStatusCode(ResponseDTO.DATA_EXCEPTION);
                resp.setMessage("Database failed. Please try again later");
                log.log(Level.SEVERE, "Database failed", e);
                platformUtil.addErrorStore(8, "Database related error\n"
                        + e.description, "GolfAdminServlet");

            } catch (Exception e) {
                resp.setStatusCode(ResponseDTO.GENERIC_EXCEPTION);
                resp.setMessage("Server process failed. Please try again later");
                log.log(Level.SEVERE, "Server failed", e);
                platformUtil.addErrorStore(8, "Generic server app error\n"
                        + dataUtil.getErrorString(e), "GolfAdminServlet");
            } finally {
                String json = gson.toJson(resp);
                response.setContentType("application/json;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    out.println(json);
                }
            }

            long end = System.currentTimeMillis();
            //platformUtil.addTimeElapsedWarning(start, end, dto, "GolfAdminServlet");
            log.log(Level.INFO, "---> GolfAdminServlet completed in {0} seconds", getElapsed(start, end));

        }
    }

    public static double getElapsed(long start, long end) {
        BigDecimal m = new BigDecimal(end - start).divide(new BigDecimal(1000));
        return m.doubleValue();
    }

    private RequestDTO getRequest(Gson gson, HttpServletRequest req) {

        String json = req.getParameter("JSON");
        RequestDTO cr = gson.fromJson(json, RequestDTO.class);

        if (cr == null) {
            cr = new RequestDTO();
        }

        return cr;
    }

    private static final Logger log = Logger.getLogger("GolfAdmin");
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.log(Level.OFF, "################# POST REQUEST COMING IN ..........");
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
