/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
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
//@WebServlet(name = "FortumoServlet", urlPatterns = {"/fortumo"})
public class FortumoServlet extends HttpServlet {

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
        log.log(Level.INFO, "Fortumo Servlet starting ...");
        long start = System.currentTimeMillis();
        try {

            String addr = request.getRemoteAddr();
            boolean isOK = false;
            for (int i = 0; i < addresses.length; i++) {
                String s = addresses[i];
                if (s.equalsIgnoreCase(addr)) {
                    isOK = true;
                    break;
                }
            }
            if (!isOK) {
                System.out.println("IP address of request: " + addr);
                //throw new UnsupportedOperationException();
            }
            String test = request.getParameter("test");
            String signature = request.getParameter("sig");
            Enumeration f = request.getParameterNames();
            List<String> list = new ArrayList<>();
            while (f.hasMoreElements()) {
                String parm = (String) f.nextElement();
                parm = parm + "=" + request.getParameter(parm);
                list.add(parm);
            }
            Collections.sort(list);
            for (String s : list) {
                System.out.println(s);
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, "Fortumo Servlet error", e); 
        } finally {

            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("Thanks for the subscription. Processed at " + new Date().toString());
            }

            long end = System.currentTimeMillis();
            log.log(Level.INFO, "Fortumo Servlet done, elapsed: {0} seconds", (end - start) / 1000);
        }

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

    private static final String[] addresses = {
        "54.72.6.126",
        "54.72.6.27",
        "54.72.6.17",
        "54.72.6.23",
        "79.125.125.1",
        "79.125.5.205",
        "79.125.5.95"};
    static final String FORTUMO_SERVICE_ID = "0bc3d36b5bf29df913eb3c6836e5000e";
    static final String FORTUMO_IN_APPLICATION_SECRET = "d8b68a20637f938ce329554c00af15c6";
    static final String FORTUMO_SECRET = "bef8ec81c92092e73e1b22ef8e36edd4";
}
