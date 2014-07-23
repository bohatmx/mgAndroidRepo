package com.boha.golfkids.util;

import com.google.appengine.api.utils.SystemProperty;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by aubreyM on 2014/07/22.
 */
public class EMUtil {

    public static EntityManager getEM() {
        // Set the persistence driver and url based on environment, production or local.
        Map<String, String> properties = new HashMap();

        for (SystemProperty.Environment.Value c : SystemProperty.Environment.Value.values()) {
            System.out.println("SystemProperty.Environment.Value : " + c);
        }

        if (SystemProperty.environment.value() ==
                SystemProperty.Environment.Value.Production) {
            System.out.println("######### Running in PRODUCTION ########");
            properties.put("javax.persistence.jdbc.driver",
                    "com.mysql.jdbc.GoogleDriver");
            properties.put("javax.persistence.jdbc.url",
                    "jdbc:google:mysql://mggolf-303:mggolf-303:golfdb/kidsgolf");
            properties.put("javax.persistence.jdbc.password", "kktiger3");
        } else {
            System.out.println("######### Running in DEVELOPMENT ########");
            properties.put("javax.persistence.jdbc.driver",
                    "com.mysql.jdbc.Driver");
            properties.put("javax.persistence.jdbc.url",
                    "jdbc:mysql://127.0.0.1:8889/kidsgolf");
            properties.put("javax.persistence.jdbc.password", "root");
            //<property name="javax.persistence.jdbc.password" value="kktiger3"/>
        }
        // Create a EntityManager which will perform operations on the database.
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(
                    "golfpu", properties);
            System.out.println("createEntityManagerFactory seems OK: " + emf.toString());
        }
        if (em == null) {
            em = emf.createEntityManager();
            System.out.println("createEntityManager is Cool. ");
        }
        if (!em.isOpen()) {
            em = emf.createEntityManager();
        }
        return emf.createEntityManager();
    }


    static EntityManagerFactory emf;
    static EntityManager em;

}
