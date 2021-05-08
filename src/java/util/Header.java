/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Asshiddiq
 */
public class Header {

    public String defUrl = "";
    public String UserLogin = "";


    public Header() {

        Properties prop = new Properties();

        String propFileName = "/config/url.properties";

        try {
            InputStream inputStream = getClass().getResourceAsStream(propFileName);
            prop.load(inputStream);
            this.defUrl = prop.getProperty("url").trim();
            inputStream.close();

        } catch (Exception e) {
        }

    }

}
