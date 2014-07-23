/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.servlet;

import com.boha.golfkids.dto.RequestDTO;
import com.boha.golfkids.dto.ResponseDTO;
import com.boha.golfkids.util.DataException;
import com.boha.golfkids.util.DataUtil;
import com.boha.golfkids.util.LeaderBoardUtil;
import com.boha.golfkids.util.PersonalUtil;
import com.boha.golfkids.util.PlatformUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author AubreyM
 */
//@WebServlet(name = "PersonalPlayerServlet", urlPatterns = {"/personalPlayer"})
public class PersonalPlayerServlet extends HttpServlet {

    //@EJB
    DataUtil dataUtil;
    //@EJB
    LeaderBoardUtil leaderBoardUtil;
    //@EJB
    PlatformUtil platformUtil;
    //@EJB
    PersonalUtil personalUtil;

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
        //
        if (dto == null) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("Bad Request, nobody's home for you!");
            platformUtil.addErrorStore(9, "Bad, rogue request detected", "GolfAdminServlet");
            out.close();
        } else {
            log.log(Level.WARNING, "---> PersonalPlayerServlet started ... requestType: {0}", dto.getRequestType());
            try {
                switch (dto.getRequestType()) {
                    case RequestDTO.ADD_PERSONAL_PLAYER:
                        resp = personalUtil.registerPlayer(
                                dto.getPersonalPlayer(), dataUtil);
                        break;
                    case RequestDTO.UPDATE_PERSONAL_PLAYER:

                        break;
                    case RequestDTO.PERSONAL_PLAYER_LOGIN:
                        resp = personalUtil.login(dto.getEmail(), dto.getPin());
                        break;
                    case RequestDTO.GET_PERSONAL_SCORES:
                        resp = personalUtil.getScores(dto.getPersonalPlayerID());
                        break;
                    case RequestDTO.ADD_PERSONAL_SCORE:
                        resp = personalUtil.addScore(dto.getPersonalScore(), dataUtil);
                        break;

                    default:
                        platformUtil.addErrorStore(7, "Request Type specified not on", "GolfAdminServlet");
                        resp.setStatusCode(7);
                        break;
                }

            } catch (DataException e) {
                resp.setStatusCode(ResponseDTO.DATA_EXCEPTION);
                resp.setMessage("Database failed. Please try again later");
                log.log(Level.SEVERE, "Database failed", e);
                platformUtil.addErrorStore(8, "Database related error\n"
                        + e.description, "PersonalPlayerServlet");

            } catch (Exception e) {
                resp.setStatusCode(ResponseDTO.GENERIC_EXCEPTION);
                resp.setMessage("Server process failed. Please try again later");
                log.log(Level.SEVERE, "Server failed", e);
                platformUtil.addErrorStore(8, "Generic server app error\n"
                        + dataUtil.getErrorString(e), "PersonalPlayerServlet");
            } finally {
                String json = gson.toJson(resp);

                response.setContentType("application/json;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println(json);
                out.close();
            }

            long end = System.currentTimeMillis();
            platformUtil.addTimeElapsedWarning(start, end, dto, "PersonalPlayerServlet");
            log.log(Level.INFO, "---> PersonalPlayerServlet completed in {0} seconds", getElapsed(start, end));

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
