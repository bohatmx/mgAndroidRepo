/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.servlet;

import com.boha.golfkids.data.ErrorStoreAndroid;
import com.boha.golfkids.data.GolfGroup;
import com.boha.golfkids.util.DataException;
import com.boha.golfkids.util.DataUtil;
import com.boha.golfkids.util.PlatformUtil;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aubreyM
 */
//@WebServlet(name = "CrashReportServlet", urlPatterns = {"/crash"})
public class CrashReportServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        log.log(Level.INFO, "CrashReportSevlet started............");
//        Enumeration<String> rr = request.getParameterNames();
//        StringBuilder sb = new StringBuilder();
//        sb.append("######################################################\n");
//        while (rr.hasMoreElements()) {
//            String parm = rr.nextElement();
//            sb.append(parm).append(" = ").append(request.getParameter(parm)).append("\n");
//        }
//        sb.append("######################################################\n");
//        log.log(Level.OFF, sb.toString());

        DataUtil dataUtil = new DataUtil();
        try {
            getErrorData(request, dataUtil);
        } catch (DataException ex) {
            log.log(Level.SEVERE, null, ex);
            PlatformUtil platformUtil = new PlatformUtil();
            platformUtil.addErrorStore(319, "Unable to add Android Error", "CrashReportServlet");
        }
    }
  //  @EJB

 //   @EJB



    private void getErrorData(HttpServletRequest request,
                              DataUtil dataUtil) throws DataException {
        ErrorStoreAndroid e = new ErrorStoreAndroid();
        e.setAndroidVersion(request.getParameter("ANDROID_VERSION"));
        e.setBrand(request.getParameter("BRAND"));
        e.setPackageName(request.getParameter("PACKAGE_NAME"));
        e.setAppVersionName(request.getParameter("APP_VERSION_NAME"));
        e.setAppVersionCode(request.getParameter("APP_VERSION_CODE"));
        e.setPhoneModel(request.getParameter("PHONE_MODEL"));
        e.setErrorDate(new Date());
        e.setLogCat(request.getParameter("LOGCAT"));
        e.setStackTrace(request.getParameter("STACK_TRACE"));

        String custom = request.getParameter("CUSTOM_DATA");
        //golfGroupID = 21
        //golfGroupName = MLB Golfers
        try {
            if (custom != null || !custom.trim().isEmpty()) {
                int x = custom.indexOf("=");
                int y = custom.indexOf("\n");
                String id = custom.substring(x + 2, y);
                System.out.println("-----------------------> id extracted: " + id);
                GolfGroup gg = dataUtil.getGroupByID(Integer.parseInt(id));
                e.setGolfGroup(gg);
            }
        } catch (Exception ex) {
            log.log(Level.OFF, "no custom data found", ex);
        }

        dataUtil.addAndroidError(e);

    }
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
    private static final Logger log = Logger.getLogger("CrashReportServlet");
}
