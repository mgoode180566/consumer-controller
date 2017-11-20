package com.example.demo;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

@RestController
public class ConsumerControllerClient {

    @Autowired
    private EurekaClient eurekaClient;

    //@HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(value = "/")
    public String alive() {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("EMPLOYEE-PRODUCER", false);
        String baseUrl = instance.getHomePageUrl();
        baseUrl = baseUrl + "list";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(baseUrl,
                    HttpMethod.GET, getHeaders(), String.class);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return response.getBody();
    }

    // fallback method for when service call fails or is slow to return
    public String fallback() {
        return "This is the fallback, called as a failure detected by Hystrix";
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(value = "/allemployees")
    public String getAllEmployee() throws RestClientException, IOException {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("EMPLOYEE-PRODUCER", false);
        String baseUrl = instance.getHomePageUrl();
        baseUrl = baseUrl + "/list";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(baseUrl,
                    HttpMethod.GET, getHeaders(), String.class);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return response.getBody();
    }









    @RequestMapping(value = "/findemployee/{id}")
    public String getEmployee(@PathVariable int id) throws RestClientException, IOException {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("EMPLOYEE-PRODUCER", false);
        String baseUrl = instance.getHomePageUrl();
        baseUrl = baseUrl + "/find/{id}";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(baseUrl,
                    HttpMethod.GET, getHeaders(), new ParameterizedTypeReference<String>() {}, id);
            return response.getBody();
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    @RequestMapping(value = "/deleteemployee/{id}")
    public String deleteEmployee(@PathVariable int id) throws RestClientException, IOException {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("EMPLOYEE-PRODUCER", false);
        String baseUrl = instance.getHomePageUrl();
        baseUrl = baseUrl + "/delete/{id}";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(baseUrl,
                    HttpMethod.GET, getHeaders(), new ParameterizedTypeReference<String>() {}, id);
            return response.getBody();
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    private static HttpEntity<?> getHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
