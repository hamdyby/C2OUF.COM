package com.microservice.api.Controller;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.HashMap; // import the HashMap class
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.api.Connection.Database;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservice.api.Service.CarriersService;



@RestController
public class CarriersController {
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  CarriersService carriersService;
  @Autowired
  private ModelMapper modelMapper;


  HttpHeaders createHeaders() {
    return new HttpHeaders() {
      {
        String authHeader = "Bearer YjQwYWVhNTg2MWRhZmUwYjk4YWJlNzY5Y2Q1YjlkYjE5NzY1YTUwMzM2ZTM5NDM1Yjc3M2MzYmExNTI1OWE2Zg";
        set("Authorization", authHeader);
      }
    };
  }

  // Connect to data base
  Database db = new Database();

  // field table Carriers
  @GetMapping("/insertcarriers")
  public void insertcarriers() throws IOException, SQLException {
    // ******  Create a HashMap object
    HashMap<String, Object> carriersmap = new HashMap<>();
    HashMap<String, Object> test = new HashMap<>();

    //*****for fields ..... insert them into hashmap
    String url = "https://api.bigbuy.eu/rest/shipping/carriers.json";
    Object[] carriers = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();


    int j = 0;
    for (Object object : carriers) {
      carriersmap.put("case" + j, carriers[j]);
      test = (HashMap<String, Object>) carriersmap.get("case" + j); // get value by key
      String key = (String) test.get("id");
      carriersmap.remove("case" + j);
      carriersmap.put(key, carriers[j]);
      db.executeUpdate("INSERT INTO carriers(id,name) VALUES  ('" + test.get("id") + "','" + test.get("name") + "')");

      j++;

    }

    //********** display the hashmap
    for (Iterator i = carriersmap.keySet().iterator(); i.hasNext(); ) {
      Object key = i.next();
      System.out.println(key + "=" + carriersmap.get(key));
    }
    // *****for fields ..... insert them into hashmap..........


    //***** insert in DB with requetes

  }
}