/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.golfkids.util;

import com.boha.golfkids.data.Tournament;
import com.boha.golfkids.data.TourneyScoreByRound;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Animation of tournament scoring
 * @author aubreyM
 */
public class LeaderBoardAnimator {
    
    public void startAnimation(int tournamentID, EntityManager em) {
        Tournament t = em.find(Tournament.class, tournamentID);
        try {
            Query q = em.createNamedQuery("TourneyScoreByRound.getByTourney",TourneyScoreByRound.class);
            q.setParameter("id", tournamentID);
            List<TourneyScoreByRound> tList = q.getResultList();
            if (tList.size() > 0) {
                
            }
            
        } catch (Exception e) {
            
        }
    }
    private void scheduleScoring() {
        //
       
    }
}
