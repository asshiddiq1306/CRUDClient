/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Asshiddiq
 */
public class PropertiesController {
    
     public String getValProp(String propFile, String propName) {
        java.util.Properties prop = new java.util.Properties();

        String propFileName = "/config/" + propFile + ".properties";
        InputStream inputStream = getClass().getResourceAsStream(propFileName);
        try {
            prop.load(inputStream);
        } catch (IOException ex) {
            System.err.println(" Error Prop : " + ex.getMessage());
        }
        return prop.getProperty(propName);
    }
     
     
     public String getProp(){
        System.out.println("DATA");
        String defUrl;
        java.util.Properties prop = new java.util.Properties();
        
        String proFileName = "/config/url.properties";
        InputStream inputStream = getClass().getResourceAsStream(proFileName);
        try {
            prop.load(inputStream);
        } catch (IOException ex) {
            System.err.println("Error Prop : " + ex.getMessage());
        }
        
        defUrl = prop.getProperty("url");
        System.out.println("URL PROP : " + defUrl);
        return defUrl;
    }
    
}
