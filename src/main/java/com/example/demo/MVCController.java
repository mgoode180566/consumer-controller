package com.example.demo;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.util.List;

/**
 * Created by michaelgoode on 16/11/2017.
 */
@EnableWebMvc
@Controller
public class MVCController {

    @Autowired
    EurekaClient eurekaClient;

    @RequestMapping(value="/showall", method = RequestMethod.GET)
    public ModelAndView getEmployees() {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("EMPLOYEE-PRODUCER", false);
        String baseUrl = instance.getHomePageUrl();
        baseUrl = baseUrl + "list";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(baseUrl,
                    HttpMethod.GET, getHeaders(), String.class);
            ModelAndView modelAndView = new ModelAndView("employees");
            modelAndView.addObject("list", getEmployeesFromJSON(response.getBody()) );
            return modelAndView;
        } catch (Exception ex) {
            return null;
        }
    }

    private List<Employee> getEmployeesFromJSON( String body ) {
        Gson gson = new Gson();
        TypeToken<List<Employee>> token = new TypeToken<List<Employee>>(){};
        List<Employee> employees = gson.fromJson(body, token.getType());
        return employees;
    }

    private static HttpEntity<?> getHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
