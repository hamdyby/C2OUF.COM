package com.microservice.api.Controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import com.microservice.api.Connection.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

@RestController
public class TagsController {
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
  Database db = new Database();

  @GetMapping("/inserttags")
  public  void inserttags() throws IOException, SQLException {
    HashMap<String, Object> tagsMap = new HashMap<>();
    HashMap<String, Object> test = new HashMap<>();

    String url = "https://api.bigbuy.eu/rest/catalog/tags.json?isoCode=fr" ;
    Object[] tags = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();


    int j = 0;
    for (Object object : tags) {
      tagsMap.put("case" + j, tags[j]);
      test = (HashMap<String, Object>) tagsMap .get("case" + j); // get value by key
      String key = String.valueOf(test.get("id"));
      tagsMap.remove("case" + j);
      tagsMap.put(key,tags[j]);
      db.executeUpdate("INSERT INTO tags(id,sku,tag) VALUES  ('" + test.get("id") + "','" + test.get("sku") +  "','" + test.get("tag") +"')");

      j++;

    }

    //********** display the hashmap
    for (Iterator i =tagsMap.keySet().iterator(); i.hasNext(); ) {
      Object key = i.next();
      System.out.println(key + "=" + tagsMap.get(key));
    }
  }

  }


