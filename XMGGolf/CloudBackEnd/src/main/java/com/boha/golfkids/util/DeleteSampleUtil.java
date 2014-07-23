/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.golfkids.util;

import com.boha.golfkids.data.Player;
import com.boha.golfkids.data.Scorer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author aubreyM
 */
public class DeleteSampleUtil {
   
    public void deleteSample(int golfGroupID, EntityManager em) throws Exception{
        
        Query r = em.createNamedQuery("Tournament.deleteSamples");
        r.setParameter("id", golfGroupID);
        int e = r.executeUpdate();
        log.log(Level.INFO, "Sample tournaments deleted: {0}", e);
        
        Query q = em.createNamedQuery("Player.getSamplePlayers", Player.class);
        q.setParameter("id", golfGroupID);
        List<Player> list = q.getResultList();
        for (Player player : list) {
            em.remove(player);
        }
         q = em.createNamedQuery("Scorer.getSampleScorers", Scorer.class);
        q.setParameter("id", golfGroupID);
        List<Scorer> listx = q.getResultList();
        for (Scorer s : listx) {
            em.remove(s);
        }
    }
    
    static final Logger log = Logger.getLogger("DeleteSampleUtil");
}
