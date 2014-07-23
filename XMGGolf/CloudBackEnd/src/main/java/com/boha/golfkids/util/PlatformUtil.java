/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.util;

import com.boha.golfkids.data.ErrorStore;
import com.boha.golfkids.dto.RequestDTO;
import com.boha.golfkids.servlet.GolfAdminServlet;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author aubreyM
 */

public class PlatformUtil {

    @PersistenceContext
    EntityManager em;
    public PlatformUtil() {
        em  = EMUtil.getEM();
    }
    public void addTimeElapsedWarning(long start, long end, RequestDTO dto, String origin) {
        if (dto == null) {
            dto = new RequestDTO();
        }
        if (end - start > (1000 * THRESHOLD_SECONDS)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Servlet took more than ").append(THRESHOLD_SECONDS)
                    .append(" seconds to process request\nRequest type is ")
                    .append(dto.getRequestType()).append("\n");
            sb.append("Elapsed time in seconds: ").append(GolfAdminServlet.getElapsed(start, end));
            addErrorStore(111, sb.toString(), origin);
        }
    }

    public void addErrorStore(int statusCode, String message, String origin) {
        log.log(Level.OFF, "------ adding errorStore, message: {0} origin: {1}", new Object[]{message, origin});
        try {
            ErrorStore t = new ErrorStore();
            t.setDateOccured(new Date());
            t.setMessage(message);
            t.setStatusCode(statusCode);
            t.setOrigin(origin);
            em.persist(t);
            log.log(Level.INFO, "####### ErrorStore row added, origin {0} message: {1}",
                    new Object[]{origin, message});
        } catch (Exception e) {
            log.log(Level.SEVERE, "####### Failed to add errorStore from " + origin + "\n" + message, e);
        }
    }
    static final int THRESHOLD_SECONDS = 5;
    static final Logger log = Logger.getLogger(PlatformUtil.class.getName());
}
