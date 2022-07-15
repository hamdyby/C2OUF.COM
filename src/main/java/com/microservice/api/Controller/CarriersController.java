package com.microservice.api.Controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class CarriersController {
  @Autowired
  private RestTemplate restTemplate;

  HttpHeaders createHeaders() {
    return new HttpHeaders() {
      {
        String authHeader = "Bearer YjQwYWVhNTg2MWRhZmUwYjk4YWJlNzY5Y2Q1YjlkYjE5NzY1YTUwMzM2ZTM5NDM1Yjc3M2MzYmExNTI1OWE2Zg";
        set("Authorization", authHeader);
      }
    };
  }
  @GetMapping("/carriers")
  public List<Object> getCarriers(){
    String url = "https://api.bigbuy.eu/rest/shipping/carriers.json" ;
    Object[] carriers = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();

    ObjectMapper mapper = new ObjectMapper();
    try {

      // Writing to a file
      mapper.writeValue(new File("carriers.json"), carriers);

    } catch (IOException e) {
      e.printStackTrace();
    }
    return  Arrays.asList(carriers);


  }
}
