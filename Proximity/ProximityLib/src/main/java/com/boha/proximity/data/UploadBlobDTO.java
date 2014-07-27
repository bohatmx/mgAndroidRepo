package com.boha.proximity.data;

/**
 * Created by aubreyM on 2014/07/27.
 */
public class UploadBlobDTO {
    private String servingUrl, blobKey;

    public String getServingUrl() {
        return servingUrl;
    }

    public void setServingUrl(String servingUrl) {
        this.servingUrl = servingUrl;
    }

    public String getBlobKey() {
        return blobKey;
    }

    public void setBlobKey(String blobKey) {
        this.blobKey = blobKey;
    }
}
