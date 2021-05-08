package controller;

import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuCSVersion  {

    Gson gson = new Gson();

    @RequestMapping(value = "/menuCSVersion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String menuCSVersion(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject json = new JSONObject();

        json.put("version", "CRUD Client");
        json.put("lastUpdate", "07/05/2021");

        return json.toString();
    }

}
