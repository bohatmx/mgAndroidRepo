package com.boha.malengagolf.CloudBackEnd;

import com.boha.golfkids.data.Agegroup;
import com.boha.golfkids.dto.AgeGroupDTO;
import com.boha.golfkids.dto.ResponseDTO;
import com.boha.golfkids.util.DataException;
import com.boha.golfkids.util.DataUtil;
import com.boha.golfkids.util.EMUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MalengaGolfServlet extends HttpServlet {
    public void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        ResponseDTO r = new ResponseDTO();
        r.setStatusCode(3);
        r.setMessage("MalengaGolfServlet has executed OK. Rejoice!");
        EntityManager em = EMUtil.getEM();
        Query q = em.createNamedQuery("AgeGroup.findByGolfGroup", Agegroup.class);
        q.setParameter("id", 21);
        List<Agegroup> list = q.getResultList();
        List<AgeGroupDTO> dList = new ArrayList<>();
        for (Agegroup a: list) {
            dList.add(new AgeGroupDTO(a));
        }
        r.setAgeGroups(dList);
        DataUtil util = new DataUtil();
        try {
            ResponseDTO d = util.getClubsByCountry(17);
            r.setProvinces(d.getProvinces());
        } catch (DataException e) {
            e.printStackTrace();
        }
        Gson g = new Gson();
        String json = g.toJson(r);
        resp.getWriter().println(json);
    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        System.out.println("MalengaGolfServlet has started in doGet ...");
        processRequest(req,resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        System.out.println("MalengaGolfServlet has started in doPost ...");
        processRequest(req,resp);
    }
}