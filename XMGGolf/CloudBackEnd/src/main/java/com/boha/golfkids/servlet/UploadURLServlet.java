/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.servlet;

import com.boha.golfkids.dto.ResponseDTO;
import com.boha.golfkids.dto.UploadUrlDTO;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
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
public class UploadURLServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        log.log(Level.WARNING, "BlobstoreServlet started............");
        UploadUrlDTO urlDTO = new UploadUrlDTO();
        ResponseDTO resp = new ResponseDTO();
        Gson gson = new Gson();
        try {
            BlobstoreService service = BlobstoreServiceFactory.getBlobstoreService();
            urlDTO.setUrl(service.createUploadUrl("/uploadBlob"));
            resp.setUploadUrl(urlDTO);
            log.log(Level.WARNING, "----- Blobstore url: " + urlDTO.getUrl());
        } catch (Exception e) {
            resp.setStatusCode(99);
            resp.setMessage("Unable to create upload URL");
            log.log(Level.SEVERE, "Unable to create upload URL", e);
        } finally {
            String json = gson.toJson(resp);
            try (PrintWriter out = response.getWriter()) {
                out.println(json);
            }
        }

    }
     // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
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
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
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
    private static final Logger log = Logger.getLogger("BlobstoreServlet");
}
