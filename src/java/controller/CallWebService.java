/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Asshiddiq
 */
public class CallWebService {

    public Map callGet(String defUrl) {
        Map listRet = new HashMap();
        String errCode = "";
        String errMsg = "";
        try {
            URL url = new URL(defUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream stream = conn.getInputStream();
            Scanner out = new Scanner(stream).useDelimiter("\\A");
            if (out.hasNext()) {
                errMsg = out.next();
            }
            errCode = "0";
        } catch (Exception ex) {
            System.out.println("err : " + ex);
            errCode = "1";
            errMsg = ex.getMessage();
        }

        listRet.put("errCode", errCode);
        listRet.put("errMsg", errMsg);
        return listRet;
    }

    public Map callPost(String defUrl, String param) {
        Map listRet = new HashMap();
        String errCode = "";
        String errMsg = "";
        try {
            URL url = new URL(defUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(param.getBytes("UTF-8"));
            os.flush();

            StringBuilder sb = new StringBuilder();
            int HttpResult = conn.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                errCode = "0";
                errMsg = sb.toString();
            } else {
                errCode = "1";
                System.out.println(conn.getResponseMessage());
                errMsg = conn.getResponseMessage();
            }
        } catch (IOException ex) {
            errCode = "1";
            errMsg = ex.getMessage();
            System.out.println(ex.getMessage());
        }

        listRet.put("errCode", errCode);
        listRet.put("errMsg", errMsg);
        
        return listRet;
    }

}
