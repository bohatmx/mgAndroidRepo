/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.util;

/**
 *
 * @author aubreymalabie
 */
import com.boha.golfkids.dto.RequestDTO;
import com.boha.golfkids.servlet.PhotoServlet;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtility {

    private static final Logger logger = Logger.getLogger(FileUtility.class.getName());

    public static File getFile(String data) {
        Random rand = new Random(System.currentTimeMillis());
        //TODO restore after zip testing
        File dir = GolfProperties.getTemporaryDir();
        File file = new File(dir, "golf" + System.currentTimeMillis() + "_" + rand.nextInt(999999999) + ".data");
        try {
            //FileUtils.writeStringToFile(file, data);
        } catch (Exception ex) {
            Logger.getLogger(FileUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }

    public static List<String> getImageFilesTournament(int golfGroupID, int tournamentID, int type) throws Exception {
        logger.log(Level.OFF, "golfGroup: {0} tournament: {1} type: {2}", new Object[]{golfGroupID, tournamentID, 2});
        List<String> list = new ArrayList<>();
        File rootDir = GolfProperties.getImageDir();
        File ggRoot = new File(rootDir, PhotoServlet.GOLF_GROUP_PREFIX + golfGroupID);

        File dir = null;
        switch (type) {
            case RequestDTO.PICTURES_FULL_SIZE:
                dir = new File(ggRoot, PhotoServlet.TOURNAMENT_PREFIX + tournamentID);
                if (!dir.exists()) {
                    return list;
                }
                if (dir.exists()) {
                    File[] files = dir.listFiles();
                    for (File file : files) {
                        if (file.getName().contains("f")) {
                            list.add(file.getName());
                        }
                    }
                }
                Collections.reverse(list);
                logger.log(Level.OFF, "full size dir: {0}", dir.getAbsolutePath());
                break;
            case RequestDTO.PICTURES_THUMBNAILS:
                File tournDir = new File(ggRoot, PhotoServlet.TOURNAMENT_PREFIX + tournamentID);
                dir = tournDir; //new File(tournDir, PhotoServlet.THUMB_PREFIX + tournamentID);
                logger.log(Level.OFF, "thumb dir: {0}", dir.getAbsolutePath());
                if (dir.exists()) {
                    File[] files = dir.listFiles();
                    for (File file : files) {
                        if (file.getName().contains("t")) {
                            list.add(file.getName());
                        }
                    }
                }
                Collections.reverse(list);
                break;
        }

        logger.log(Level.OFF, "######## Image files found: {0}", list.size());
        return list;
    }

    public static List<String> getImageFilesGolfGroup(int golfGroupID, int type) throws Exception {
        List<String> list = new ArrayList<>();
        File rootDir = GolfProperties.getImageDir();
        File dir = null;
        switch (type) {
            case RequestDTO.PICTURES_FULL_SIZE:
                dir = new File(rootDir, PhotoServlet.GOLF_GROUP_PREFIX + golfGroupID);
                break;
            case RequestDTO.PICTURES_THUMBNAILS:
                dir = new File(rootDir, PhotoServlet.THUMB_PREFIX + golfGroupID);
                break;
        }
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                list.add(file.getName());
            }
        }
        logger.log(Level.OFF, "Image files found: {0}", list.size());
        return list;
    }

}
