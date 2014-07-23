package com.boha.golfkids.dto;

/**
 * Created by aubreyM on 2014/07/23.
 */
public class UploadUrlDTO {
    private int statusCode;
    private String message, url;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
