/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.util;

/**
 *
 * @author Aubrey Malabie
 */
public class DataException extends Exception {
    public String description;
    public DataException() {}
    public DataException(String description) {
        this.description = description;
    }
}
