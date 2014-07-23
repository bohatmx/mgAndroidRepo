/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.golfkids.util;

import java.io.File;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aubreyM
 */
public class HouseKeepingScheduler {
    
    public void cleanUp() {
        startDiskCleanup();
    }
 private void startDiskCleanup() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n\n### ##########################################################################\n");
        sb.append("### MalengaGolf Disk Cleanup STARTED: ").append(new Date()).append("\n");
        sb.append("### ##########################################################################\n\n");
        log.log(Level.INFO, sb.toString());

        int count = 0;
        File dir = GolfProperties.getTemporaryDir();
        if (dir.exists()) {
            File[] files = dir.listFiles();
            log.log(Level.INFO, "### startDiskCleanup - temporary files found: {0}", files.length);
            for (File file : files) {
                long now = new Date().getTime();
                long cutOff = now - FIVE_MINUTES;
                if (file.lastModified() < cutOff) {
                    boolean OK = file.delete();
                    if (OK) {
                        count++;
                    }
                }
            }
        }
        log.log(Level.INFO, "### MalengaGolf HouseKeeping cleaned up {0} temporary files", count);
        try {
            platformUtil.addErrorStore(133, "MGGolf temporary files cleaned up", "HouseKeeper");
        } catch (Exception e) {

        }
    }

    PlatformUtil platformUtil;
    private final static int FIVE_MINUTES = 1000 * 60 * 5;
    static final Logger log = Logger.getLogger(HouseKeepingScheduler.class.getName());
}
