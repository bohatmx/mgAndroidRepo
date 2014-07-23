package com.boha.golfkids.util;

import com.google.appengine.api.utils.SystemProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by aubreyM on 2014/07/22.
 */
public class EMUtil {

    static final Logger log = Logger.getLogger(EMUtil.class.getName());

    public static EntityManager getEM() {
        // Set the persistence driver and url based on environment, production or local.
        Map<String, String> properties = new HashMap();

        if (SystemProperty.environment.value() ==
                SystemProperty.Environment.Value.Production) {
            //log.log(Level.WARNING, "######### Running in PRODUCTION ########");
            properties.put("javax.persistence.jdbc.driver",
                    "com.mysql.jdbc.GoogleDriver");
            properties.put("javax.persistence.jdbc.url",
                    "jdbc:google:mysql://mggolf-303:golfdb/kidsgolf");
            properties.put("javax.persistence.jdbc.user", "root");
        } else {
            //log.log(Level.WARNING, "######### Running in DEVELOPMENT ########");
            properties.put("javax.persistence.jdbc.driver",
                    "com.mysql.jdbc.Driver");
            properties.put("javax.persistence.jdbc.url",
                    "jdbc:mysql://127.0.0.1:8889/kidsgolf");
            properties.put("javax.persistence.jdbc.password", "root");
        }
         if (emf == null) {
            emf = Persistence.createEntityManagerFactory(
                    "golfpu", properties);
            log.log(Level.WARNING, "####### createEntityManagerFactory created, seems OK: " + emf.toString());
        }
        if (em == null) {
            em = emf.createEntityManager();
            log.log(Level.WARNING, "==========> createEntityManager created, was null. is Cool. ");
        }
        if (!em.isOpen()) {
            em = emf.createEntityManager();
        }
        return emf.createEntityManager();
    }


    static EntityManagerFactory emf;
    static EntityManager em;

}
