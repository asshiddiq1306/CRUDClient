/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import util.Header;
import util.Utility;

/**
 *
 * @author Asshiddiq
 */
@Controller
public class ControllerCRUDExec extends Header {

    CallWebService service = new CallWebService();
    Utility u = new Utility();
    Header header = new Header();
    PropertiesController pc = new PropertiesController();

    JSONParser parser = new JSONParser();

    @RequestMapping(value = "/ListData", method = RequestMethod.GET)
    public @ResponseBody
    String ListData() {
        JSONObject hasil = new JSONObject();
        try {
            RestTemplate rest = new RestTemplate();
            String urlServer = pc.getValProp("url", "urlServer");

            List ListData = rest.getForObject("http://" + urlServer + "/ListData", List.class);
            hasil.put("status", 0);
            hasil.put("listData", ListData);
        } catch (Exception ex) {
            ex.printStackTrace();
            hasil.put("status", 1);
            hasil.put("message", ex.getMessage());
        }

        return hasil.toString();
    }

    @RequestMapping(value = "/CreateData", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String CreateData(
            @RequestParam("namafile") MultipartFile file,
            @RequestParam("user_id") String userId,
            @RequestParam("trx_id") String trxId,
            HttpServletRequest request, HttpServletResponse response) {

        JSONObject jsonHasil = new JSONObject();
        String err_message = "Terjadi kesalahan saat proses input";

        try {
            RestTemplate rest = new RestTemplate();
            String urlServer = pc.getValProp("url", "urlServer");

            String imageName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(imageName);
            String name_only = FilenameUtils.getBaseName(imageName);
            request.getSession().setAttribute("extension", extension);
            byte[] bytes = file.getBytes();

            String filePath = pc.getValProp("url", "PATH_SAVE");
            File dir = new File(filePath);

            if (!dir.exists()) {
                dir.mkdirs();
            }
            String name_save = name_only;
            String path = dir.getAbsolutePath() + File.separator + name_save + "." + extension;
            File serverFile = new File(path);

            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

            stream.write(bytes);
            stream.close();

            //param yang dikirim ke BL
            JSONObject sender = new JSONObject();
            sender.put("USER_ID", userId);
            sender.put("TRX_ID", trxId);
            sender.put("IMAGE", imageName);

            int statusCreate = rest.postForObject("http://" + urlServer + "/CreateData", sender, Integer.class);
            if (statusCreate == -77) {
                jsonHasil.put("statusCreate", "1");
                jsonHasil.put("message", "Image Name has been used");
            } else if (statusCreate < 0) {
                jsonHasil.put("statusCreate", "1");
                jsonHasil.put("message", "Failed to Save Data");
            } else {
                jsonHasil.put("statusCreate", "0");
                jsonHasil.put("message", "Success save new data");
            }

        } catch (Exception ex) {
            customLog(ex);
            jsonHasil.put("statusCreate", "1");
            jsonHasil.put("message", err_message);
        }

        return jsonHasil.toString();
    }

    @RequestMapping(value = "/ReadData", method = RequestMethod.GET)
    public @ResponseBody
    String ReadData(HttpServletRequest request, HttpServletResponse response) {
        JSONObject hasil = new JSONObject();

        try {
            RestTemplate rest = new RestTemplate();
            String urlServer = pc.getValProp("url", "urlServer");

            String data = rest.getForObject("http://" + urlServer + "/ReadData?no="
                    + request.getParameter("NO"),
                    String.class);
            JSONObject jsonData = (JSONObject) parser.parse(data);
            if (!jsonData.get("status").equals("success")) {
                throw new Exception("Failed to get Data");
            }

            hasil.put("status", 0);
            hasil.put("current_data", jsonData.get("data"));
            hasil.put("message", jsonData.get("message"));

        } catch (Exception ex) {
            ex.printStackTrace();
            hasil.put("status", 1);
            hasil.put("message", ex.getMessage());
        }

        return hasil.toString();
    }

    @RequestMapping(value = "/DeleteData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String DeleteData(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject param) {

        JSONObject jsonHasil = new JSONObject();

        try {
            RestTemplate rest = new RestTemplate();
            String urlServer = pc.getValProp("url", "urlServer");
            JSONObject sender = new JSONObject();

            sender.put("NO", param.get("NO"));
            int statusPost = rest.postForObject("http://" + urlServer + "/DeleteData", sender, Integer.class);
            if (statusPost < 0) {
                throw new Exception("data not found");
            }

            jsonHasil.put("message", "Data deleted successfully");
            jsonHasil.put("statusDelete", "0");
        } catch (Exception ex) {
            jsonHasil.put("statusDelete", "1");
            jsonHasil.put("message", ex.getMessage());
        }

        return jsonHasil.toString();
    }

    @RequestMapping(value = "/UpdateDataWithImage", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String UpdateDataWithImage(
            @RequestParam("namafile") MultipartFile file,
            @RequestParam("user_id") String userId,
            @RequestParam("trx_id") String trxId,
            @RequestParam("no") String no,
            HttpServletRequest request, HttpServletResponse response) {

        JSONObject jsonHasil = new JSONObject();
        String err_message = "Terjadi kesalahan saat proses input";

        try {
            RestTemplate rest = new RestTemplate();
            String urlServer = pc.getValProp("url", "urlServer");

            String imageName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(imageName);
            String name_only = FilenameUtils.getBaseName(imageName);
            request.getSession().setAttribute("extension", extension);
            byte[] bytes = file.getBytes();

            String filePath = pc.getValProp("url", "PATH_SAVE");
            File dir = new File(filePath);

            if (!dir.exists()) {
                dir.mkdirs();
            }
            String name_save = name_only;
            String path = dir.getAbsolutePath() + File.separator + name_save + "." + extension;
            File serverFile = new File(path);

            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

            stream.write(bytes);
            stream.close();

            JSONObject sender = new JSONObject();
            sender.put("NO", no);
            sender.put("USER_ID", userId);
            sender.put("TRX_ID", trxId);
            sender.put("IMAGE", imageName);

            int statusUpd = rest.postForObject("http://" + urlServer + "/UpdateDataWithImage", sender, Integer.class);
            if (statusUpd == -77) {
                jsonHasil.put("statusUpd", "1");
                jsonHasil.put("message", "Image Name has been used");
            } else if (statusUpd < 0) {
                jsonHasil.put("statusUpd", "1");
                jsonHasil.put("message", "Failed to Update Data");
            } else {
                jsonHasil.put("statusUpd", "0");
                jsonHasil.put("message", "Success Update data");
            }

        } catch (Exception ex) {
            customLog(ex);
            jsonHasil.put("statusUpd", "1");
            jsonHasil.put("message", err_message);
        }

        return jsonHasil.toString();
    }

    @RequestMapping(value = "/UpdateData", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String UpdateData(
            @RequestParam("user_id") String userId,
            @RequestParam("trx_id") String trxId,
            @RequestParam("no") String no,
            HttpServletRequest request, HttpServletResponse response) {

        JSONObject jsonHasil = new JSONObject();
        String err_message = "Terjadi kesalahan saat proses input";

        try {
            RestTemplate rest = new RestTemplate();
            String urlServer = pc.getValProp("url", "urlServer");
            JSONObject sender = new JSONObject();
            sender.put("NO", no);
            sender.put("USER_ID", userId);
            sender.put("TRX_ID", trxId);

            int statusUpd = rest.postForObject("http://" + urlServer + "/UpdateData", sender, Integer.class);
            if (statusUpd < 0) {
                jsonHasil.put("statusUpd", "1");
                jsonHasil.put("message", "Failed to Update Data");
            } else {
                jsonHasil.put("statusUpd", "0");
                jsonHasil.put("message", "Success Update data");
            }

        } catch (Exception ex) {
            customLog(ex);
            jsonHasil.put("statusUpd", "1");
            jsonHasil.put("message", err_message);
        }

        return jsonHasil.toString();
    }

    private void customLog(Exception ex) {
        u.createLogFE(ex.getStackTrace()[0].toString() + "\n"
                + ex.getStackTrace()[1].toString() + "\n"
                + ex.getStackTrace()[2].toString() + "\n"
                + ex.getStackTrace()[3].toString());
    }
}
