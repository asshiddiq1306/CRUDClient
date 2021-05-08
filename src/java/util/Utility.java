/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import controller.PropertiesController;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Asshiddiq
 */
public class Utility extends Header {

    PropertiesController pu = new PropertiesController();

    public void createLogFE(String strError) {
        String logDirektori = createDirectoryFile("logfunc", null);
        File folder = new File(logDirektori);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length - Integer.parseInt(pu.getValProp("url", "jmlhdir")); i++) {
            listOfFiles[i].delete();
        }
        String logFilename = logDirektori + "Function-" + new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()) + ".txt";
        try {
            Writer writer;
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFilename, true), "UTF-8"));
            writer.append(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + " : " + strError);
            writer.append(System.lineSeparator());
            writer.close();
        } catch (IOException ex) {
        }
    }

    private String createDirectoryFile(String dirName, String dirAdd) {
        String strDirektori = pu.getValProp("url", "hddrive") + pu.getValProp("url", "maindir");
        File dirFirst = new File(strDirektori);
        if (!dirFirst.exists()) {
            dirFirst.mkdir();
        }
        if (dirAdd == null) {
            strDirektori = strDirektori + pu.getValProp("url", dirName);
            File dirSecond = new File(strDirektori);
            if (!dirSecond.exists()) {
                dirSecond.mkdir();
            }
        } else {
            strDirektori = strDirektori + pu.getValProp("url", dirName) + dirAdd;
            File dirThird = new File(strDirektori);
            if (!dirThird.exists()) {
                dirThird.mkdirs();
            }
        }
        return strDirektori;
    }

    public String gettimeNow(String format) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateNow = sdf.format(cal.getTime());
        return dateNow;
    }
}
