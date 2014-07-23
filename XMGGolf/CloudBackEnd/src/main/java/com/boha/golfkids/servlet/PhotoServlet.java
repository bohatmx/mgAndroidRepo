/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.servlet;

import com.boha.golfkids.dto.RequestDTO;
import com.boha.golfkids.dto.ResponseDTO;
import com.boha.golfkids.util.FileUploadException;
import com.boha.golfkids.util.FileUtility;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet accepts image files uploaded from CourseMaker devices and saves
 * them on disk according to the requestor's role.
 *
 * @author aubreyM
 */
//@WebServlet(name = "PhotoServlet", urlPatterns = {"/photo"})
public class PhotoServlet extends HttpServlet {

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
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        long start = System.currentTimeMillis();

        ResponseDTO ur = new ResponseDTO();
        String json;
        Gson gson = new Gson();
        try {
            boolean isMultipart = false; //ServletFileUpload.isMultipartContent(request);
            if (isMultipart) {
               // ur = downloadPhotos(request);
            } else {
                RequestDTO dto = getRequest(gson, request);
                switch (dto.getRequestType()) {
                    case RequestDTO.GET_TOURNAMENT_PICTURES:
                        ur.setImageFileNames(FileUtility.getImageFilesTournament(
                                dto.getGolfGroupID(), dto.getTournamentID(), RequestDTO.PICTURES_FULL_SIZE));
                        break;
                    case RequestDTO.GET_TOURNAMENT_THUMBNAILS:
                        ur.setImageFileNames(FileUtility.getImageFilesTournament(
                                dto.getGolfGroupID(), dto.getTournamentID(), RequestDTO.PICTURES_THUMBNAILS));
                        break;
                    case RequestDTO.GET_GOLFGROUP_PICTURES:
                        ur.setImageFileNames(FileUtility.getImageFilesGolfGroup(
                                dto.getGolfGroupID(), RequestDTO.PICTURES_FULL_SIZE));
                        break;
                    case RequestDTO.GET_GOLFGROUP_THUMBNAILS:
                        ur.setImageFileNames(FileUtility.getImageFilesGolfGroup(
                                dto.getGolfGroupID(), RequestDTO.PICTURES_THUMBNAILS));
                        break;

                }

            }

        } catch (FileUploadException ex) {
            Logger.getLogger(PhotoServlet.class.getName()).log(Level.SEVERE, "File upload fucked", ex);
            ur.setStatusCode(ResponseDTO.GENERIC_EXCEPTION);
            ur.setMessage("Error. Unable to download file(s) sent. Contact Support");

        } catch (Exception e) {
            Logger.getLogger(PhotoServlet.class.getName()).log(Level.SEVERE, "Servlet file upload fucked", e);
            ur.setStatusCode(ResponseDTO.GENERIC_EXCEPTION);
            ur.setMessage("Error. Generic server exception");

        } finally {
            json = gson.toJson(ur);
            out.println(json);
            out.close();
            long end = System.currentTimeMillis();
            logger.log(Level.INFO, "PhotoServlet done, elapsed: {0} seconds", getElapsed(start, end));
        }
    }

    /*
    private ResponseDTO downloadPhotos(HttpServletRequest request) throws FileUploadException {
        logger.log(Level.INFO, "######### starting PHOTO DOWNLOAD process\n\n");
        ResponseDTO resp = new ResponseDTO();
        InputStream stream = null;
        File rootDir;
        try {
            rootDir = GolfProperties.getImageDir();
            logger.log(Level.INFO, "rootDir - {0}", rootDir.getAbsolutePath());
            if (!rootDir.exists()) {
                rootDir.mkdir();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Properties file problem", ex);
            resp.setMessage("Server file unavailable. Please try later");
            resp.setStatusCode(ResponseDTO.GENERIC_EXCEPTION);

            return resp;
        }

        PhotoUploadDTO dto = null;
        Gson gson = new Gson();
        File golfGroupDir = null, tournamentDir = null, playerDir = null,
                parentDir = null, scorerDir = null, adminDir = null;
        try {
            ServletFileUpload upload = new ServletFileUpload();
            FileItemIterator iter = upload.getItemIterator(request);
            while (iter.hasNext()) {
                FileItemStream item = iter.next();
                String name = item.getFieldName();
                stream = item.openStream();
                if (item.isFormField()) {
                    if (name.equalsIgnoreCase("JSON")) {
                        String json = Streams.asString(stream);
                        if (json != null) {
                            logger.log(Level.INFO, "picture with associated json: {0}", json);
                            dto = gson.fromJson(json, PhotoUploadDTO.class);
                            if (dto != null) {
                                golfGroupDir = createGolfGroupDirectory(rootDir, golfGroupDir, dto.getGolfGroupID());
                                if (dto.getPlayerID() > 0) {
                                    playerDir = createPlayerDirectory(golfGroupDir, playerDir);
                                }
                                if (dto.getParentID() > 0) {
                                    parentDir = createParentDirectory(golfGroupDir, parentDir);
                                }
                                if (dto.getScorerID() > 0) {
                                    scorerDir = createScorerDirectory(golfGroupDir, scorerDir);
                                }
                                if (dto.getAdministratorID() > 0) {
                                    adminDir = createAdminDirectory(golfGroupDir, adminDir);
                                }
                                if (dto.getTournamentID() > 0) {
                                    tournamentDir = createTournamentDirectory(golfGroupDir, tournamentDir, 
                                            dto.getTournamentID());
                                }
                            }
                        } else {
                            logger.log(Level.WARNING, "JSON input seems fucked up! is NULL..");
                        }
                    }
                } else {
                    logger.log(Level.OFF, "name of item to be processed into file: {0}", name);
                    File imageFile = null;
                    if (dto == null) {
                        continue;
                    }
                    DateTime dt = new DateTime();
                    String suffix = "" + dt.getMillis() + ".jpg";
                    if (dto.getTournamentID() == 0 
                            && dto.getPlayerID() == 0 
                            && dto.getParentID() == 0 
                            && dto.getScorerID() == 0) {
                        switch (dto.getType()) {
                            case PhotoUploadDTO.PICTURES_FULL_SIZE:
                                imageFile = new File(golfGroupDir, "f" + suffix);
                                break;
                            case PhotoUploadDTO.PICTURES_THUMBNAILS:
                                imageFile = new File(golfGroupDir, "t" + suffix);
                                break;
                        }
                    }
                    if (dto.getTournamentID()> 0) {
                        switch (dto.getType()) {
                            case PhotoUploadDTO.PICTURES_FULL_SIZE:
                                imageFile = new File(tournamentDir, "f" + suffix);
                                break;
                            case PhotoUploadDTO.PICTURES_THUMBNAILS:
                                imageFile = new File(tournamentDir, "t" + suffix);
                                break;
                        }
                    }
                    if (dto.getPlayerID() > 0) {
                        logger.log(Level.OFF, "player photo about to download", dto.getPlayerID());
                        switch (dto.getType()) {
                            case PhotoUploadDTO.PICTURES_FULL_SIZE:
                                imageFile = new File(playerDir, "f" + dto.getPlayerID() + ".jpg");
                                break;
                            case PhotoUploadDTO.PICTURES_THUMBNAILS:
                                imageFile = new File(playerDir, "t" + dto.getPlayerID() + ".jpg");
                                break;
                        }
                    }
                    if (dto.getParentID() > 0) {
                        switch (dto.getType()) {
                            case PhotoUploadDTO.PICTURES_FULL_SIZE:
                                imageFile = new File(parentDir, "f" + dto.getParentID() + ".jpg");
                                break;
                            case PhotoUploadDTO.PICTURES_THUMBNAILS:
                                imageFile = new File(parentDir, "t" + dto.getParentID() + ".jpg");
                                break;
                        }
                    }
                    if (dto.getScorerID() > 0) {
                        logger.log(Level.OFF, "scorer photo about to download", dto.getScorerID());
                        switch (dto.getType()) {
                            case PhotoUploadDTO.PICTURES_FULL_SIZE:
                                imageFile = new File(scorerDir, "f" + dto.getScorerID() + ".jpg");
                                break;
                            case PhotoUploadDTO.PICTURES_THUMBNAILS:
                                imageFile = new File(scorerDir, "t" + dto.getScorerID() + ".jpg");
                                break;
                        }
                    }
                    if (dto.getAdministratorID()> 0) {
                        logger.log(Level.OFF, "admin photo about to download", dto.getAdministratorID());
                        switch (dto.getType()) {
                            case PhotoUploadDTO.PICTURES_FULL_SIZE:
                                imageFile = new File(adminDir, "f" + dto.getAdministratorID() + ".jpg");
                                break;
                            case PhotoUploadDTO.PICTURES_THUMBNAILS:
                                imageFile = new File(adminDir, "t" + dto.getAdministratorID() + ".jpg");
                                break;
                        }
                    }

                    writeFile(stream, imageFile);
                    resp.setMessage("Photo downloaded from mobile app ");

                }
            }

        } catch (FileUploadException | IOException | JsonSyntaxException ex) {
            logger.log(Level.SEVERE, "Servlet failed on IOException, images NOT uploaded", ex);
            throw new FileUploadException();
        }

        return resp;
    }
*/
    private File createTournamentDirectory(File golfGroupDir, File tournamentDir, int id) {
        logger.log(Level.INFO, "tournament photo to be downloaded");
        tournamentDir = new File(golfGroupDir, TOURNAMENT_PREFIX + id);
        if (!tournamentDir.exists()) {
            tournamentDir.mkdir();
            logger.log(Level.INFO, "tournament  directory created - {0}",
                    tournamentDir.getAbsolutePath());
        }
       return tournamentDir; 
    }

    private File createScorerDirectory(File golfGroupDir, File scorerDir) {
        logger.log(Level.INFO, "scorer photo to be downloaded");
        scorerDir = new File(golfGroupDir, SCORER_DIR);
        if (!scorerDir.exists()) {
            scorerDir.mkdir();
            logger.log(Level.INFO, "scorer  directory created - {0}",
                    scorerDir.getAbsolutePath());

        }
        return scorerDir;
    }
    private File createAdminDirectory(File golfGroupDir, File adminDir) {
        logger.log(Level.INFO, "admin photo to be downloaded");
        adminDir = new File(golfGroupDir, ADMIN_DIR);
        if (!adminDir.exists()) {
            adminDir.mkdir();
            logger.log(Level.INFO, "admin  directory created - {0}",
                    adminDir.getAbsolutePath());

        }
        return adminDir;
    }

    private File createParentDirectory(File golfGroupDir, File parentDir) {
        logger.log(Level.INFO, "parent photo to be downloaded");
        parentDir = new File(golfGroupDir, PARENT_DIR);
        if (!parentDir.exists()) {
            parentDir.mkdir();
            logger.log(Level.INFO, "parent  directory created - {0}",
                    parentDir.getAbsolutePath());

        }
        return parentDir;
    }

    private File createPlayerDirectory(File golfGroupDir, File playerDir) {
        logger.log(Level.INFO, "player photo to be downloaded");
        playerDir = new File(golfGroupDir, PLAYER_DIR);
        logger.log(Level.INFO, "just after new {0}", playerDir);
        if (!playerDir.exists()) {
            playerDir.mkdir();
            logger.log(Level.INFO, "player  directory created - {0}",
                    playerDir.getAbsolutePath());

        }
        return playerDir;
    }

    private File createGolfGroupDirectory(File rootDir, File golfGroupDir, int id) {
        golfGroupDir = new File(rootDir, GOLF_GROUP_PREFIX + id);
        if (!golfGroupDir.exists()) {
            golfGroupDir.mkdir();
            logger.log(Level.INFO, "golfgroup directory created - {0}",
                    golfGroupDir.getAbsolutePath());
        }
       return golfGroupDir; 
    }

    private void writeFile(InputStream stream, File imageFile) throws FileNotFoundException, IOException {

        FileOutputStream fos = new FileOutputStream(imageFile);
        int read;
        byte[] bytes = new byte[2048];
        while ((read = stream.read(bytes)) != -1) {
            fos.write(bytes, 0, read);
        }
        stream.close();
        fos.flush();
        fos.close();

        logger.log(Level.INFO, "\n### File downloaded: {0} size: {1}",
                new Object[]{imageFile.getAbsolutePath(), imageFile.length()});
    }

    public static double getElapsed(long start, long end) {
        BigDecimal m = new BigDecimal(end - start).divide(new BigDecimal(1000));
        return m.doubleValue();
    }
    static final Logger logger = Logger.getLogger("PhotoServlet");
    public static final String GOLF_GROUP_PREFIX = "golfgroup";
    public static final String TOURNAMENT_PREFIX = "tournament";
    public static final String THUMB_PREFIX = "thumbnails";
    public static final String PLAYER_DIR = "player";
    public static final String PARENT_DIR = "parent";
    public static final String SCORER_DIR = "scorer";
    public static final String ADMIN_DIR = "admin";
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

    private RequestDTO getRequest(Gson gson, HttpServletRequest req) {

        String json = req.getParameter("JSON");
        RequestDTO cr = gson.fromJson(json, RequestDTO.class);

        if (cr == null) {
            cr = new RequestDTO();
        }

        return cr;
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
