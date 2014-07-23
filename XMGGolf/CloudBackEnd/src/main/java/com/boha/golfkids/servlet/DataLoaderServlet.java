/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.servlet;

import com.boha.golfkids.dto.ResponseDTO;
import com.boha.golfkids.util.DataException;
import com.boha.golfkids.util.DataLoader;
import com.boha.golfkids.util.DataUtil;
import com.boha.golfkids.util.PlatformUtil;
import com.boha.golfkids.util.WorkerBee;
import com.boha.golfkids.util.golfdata.LoaderRequestDTO;
import com.boha.golfkids.util.golfdata.LoaderResponseDTO;
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
 *
 * @author AubreyM
 */
//@WebServlet(name = "DataLoaderServlet", urlPatterns = {"/loader"})
public class DataLoaderServlet extends HttpServlet {

    /**
     *
     * Serves as data loader servlet
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long start = System.currentTimeMillis();
        Gson gson = new Gson();
        LoaderResponseDTO resp = new LoaderResponseDTO();
        LoaderRequestDTO dto = getRequest(gson, request);

        DataLoader loader = new DataLoader();
        DataUtil dataUtil = new DataUtil();
        PlatformUtil platformUtil = new PlatformUtil();
        WorkerBee workerBee = new WorkerBee();
        //
        if (dto.getRequestType() == 0) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("Bad Request, nobody's home for your ass!");
            platformUtil.addErrorStore(9, "Bad, rogue request detected", "DataLoaderServlet");
            out.close();
        } else {
            log.log(Level.WARNING, "---> DataLoaderServlet started ... requestType: {0}",
                    dto.getRequestType());
            try {
                switch (dto.getRequestType()) {
                    case LoaderRequestDTO.LOAD_COUNTRIES:
                        resp = loader.loadCountries(dto.getCountryList());
                        break;
                    case LoaderRequestDTO.LOAD_PROVINCES:
                        resp = loader.loadProvinces(dto.getCountryID(), dto.getProvinceList());
                        break;
                    case LoaderRequestDTO.LOAD_PROVINCE_CITIES:
                        resp = loader.loadCities(dto.getProvinceID(), dto.getCityList());
                        break;
                    case LoaderRequestDTO.LOAD_CITY_CLUBS:
                        resp = loader.loadClubs(dto.getProvinceID(), dto.getClubList());
                        break;
                    case LoaderRequestDTO.FIND_CLUBS_WITHIN_RADIUS:
                        resp = dataUtil.findLoadedClubsWithinRadius(dto.getLatitude(), 
                                dto.getLongitude(), dto.getRadius(),
                                WorkerBee.KILOMETRES, dto.getPage(), workerBee);
                        break;
                    case LoaderRequestDTO.UPDATE_CITY_COORDINATES:
                        resp = loader.updateCityCoordinates(dto.getCityID(),
                                dto.getLatitude(), dto.getLongitude());
                        break;
                    case LoaderRequestDTO.GET_STATE_CITIES:
                        resp = loader.getStateCities(dto.getProvinceID());
                        break;
                    default:
                        platformUtil.addErrorStore(7, "Request Type specified not on", "DataLoaderServlet");
                        resp.setStatusCode(7);
                        resp.setMessage("Unknown Request Type");
                        break;
                }

            } catch (DataException e) {
                resp.setStatusCode(ResponseDTO.DATA_EXCEPTION);
                resp.setMessage("Database failed. Please try again later");
                log.log(Level.SEVERE, "Database failed", e);
                platformUtil.addErrorStore(8, "Database related issue\n"
                        + e.description, "DataLoaderServlet");

            } catch (Exception e) {
                resp.setStatusCode(ResponseDTO.GENERIC_EXCEPTION);
                resp.setMessage("Server process failed. Please try again later");
                log.log(Level.SEVERE, "Server failed", e);
                platformUtil.addErrorStore(8, "Generic server app error\n"
                        + loader.getErrorString(e), "DataLoaderServlet");
            } finally {
                String json = gson.toJson(resp);
                log.log(Level.INFO, json);
                response.setContentType("application/json;charset=UTF-8");
                try (
                    PrintWriter out = response.getWriter()) {
                    out.println(json);
                } catch (Exception e) {

                }

                long end = System.currentTimeMillis();
                log.log(Level.INFO, "---> DataLoaderServlet completed in {0} seconds", getElapsed(start, end));
            }
        }
    }

    public static double getElapsed(long start, long end) {
        BigDecimal m = new BigDecimal(end - start).divide(new BigDecimal(1000));
        return m.doubleValue();
    }

    private LoaderRequestDTO getRequest(Gson gson, HttpServletRequest req) {

        String json = req.getParameter("JSON");
        LoaderRequestDTO cr = gson.fromJson(json, LoaderRequestDTO.class);

        if (cr == null) {
            cr = new LoaderRequestDTO();
        }

        return cr;
    }
    private static final Logger log = Logger.getLogger("Loader");
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
