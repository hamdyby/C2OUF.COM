package com.microservice.api.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.HashMap;

import com.microservice.api.Connection.Database;
import org.springframework.beans.factory.annotation.Autowired;
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
  @GetMapping("/insertCarriers")
  public void insertCarriers() throws IOException, SQLException {
    // ******  Create  HashMap objects
    HashMap<String, Object> carriersMap = new HashMap<>();
    HashMap<String, Object> test = new HashMap<>();

    //*****for fields ..... insert them into hashmap
    String url = "https://api.bigbuy.eu/rest/shipping/carriers.json";
    Object[] carriers = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();


    int j = 0;
    for (Object object : carriers) {
      carriersMap.put("case" + j, carriers[j]);
      test = (HashMap<String, Object>) carriersMap.get("case" + j); // get value by key
      String key = (String) test.get("id");
      carriersMap.remove("case" + j);
      carriersMap.put(key, carriers[j]);
      db.executeUpdate("INSERT INTO carriers(id,name) VALUES  ('" + test.get("id") + "','" + test.get("name") + "')");

      j++;

    }

    //********** display the hashmap
    for (Iterator i = carriersMap.keySet().iterator(); i.hasNext(); ) {
      Object key = i.next();
      System.out.println(key + "=" + carriersMap.get(key));
    }
  }
}