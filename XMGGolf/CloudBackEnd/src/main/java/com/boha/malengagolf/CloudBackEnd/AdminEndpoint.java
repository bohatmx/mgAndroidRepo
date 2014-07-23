package com.boha.malengagolf.CloudBackEnd;

import com.boha.golfkids.dto.ResponseDTO;
import com.boha.golfkids.util.WorkerBee;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/** AdminEndpoint exposes all the method calls needed by the Admin app */

@Api(name = "admin", version = "v1",
        namespace = @ApiNamespace(ownerDomain = "CloudBackEnd.malengagolf.boha.com",
                ownerName = "CloudBackEnd.malengagolf.boha.com",
                packagePath=""))

public class AdminEndpoint {

    /** Get Golf Clubs nearby using coordinates and radius in km or miles */
    @ApiMethod(name = "getClubsNearby")
    public ResponseDTO getClubsNearby(@Named("latitude") double latitude,
                                      @Named("longitude") double longitude,
                                      @Named("radius") int radius,
                                      @Named("page") int page,
                                      @Named("type") int type) {
        WorkerBee wb = new WorkerBee();
        ResponseDTO response = null;
        try {
            response = wb.getClubsWithinRadius(latitude,longitude,radius, type, page);
            response.setMessage("Clubs listed in response: " + response.getClubs().size());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return response;
    }

}