package com.boha.golfkids.util;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.regex.Pattern;

/**
 *
 * @author aubreyM
 */
public class ReadExcel {

    private String inputFile;

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public static void main(String[] args) throws Exception {

        File file = new File("/workspaces/mggolf.csv");
        Reader rdr = new FileReader(file);
        CSVReader reader = new CSVReader(rdr);
        String[] row = null;
        Pattern pat = Pattern.compile(";");
        while ((row = reader.readNext()) != null) {
            String s = row[0];
            String[] res = pat.split(s);
            String firstname, lastname, email, cellphone, dateBirth, gender;
            
            firstname = res[0];
            lastname = res[1];
            email = res[2];
            cellphone = res[3];
            dateBirth = res[4];
            gender = res[5];
            
            System.out.println(firstname + " " + lastname + " " + email + " " + cellphone + " " + dateBirth + " " + gender);
        }
        reader.close();
    }

}
