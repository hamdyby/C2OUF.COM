package com.microservice.api.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.microservice.api.Connection.Database;

@RestController
public class AttributeGroups {
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

  // Connect to data base
  Database db = new Database();

  // field table Carriers
  @GetMapping("/insertAttributeGroups")
  public void insertCarriers() throws IOException, SQLException {
    // ******  Create  HashMap objects
    HashMap<String, Object> attributeGroupsMap = new HashMap<>();
    HashMap<String, Object> test = new HashMap<>();

    //*****for fields ..... insert them into hashmap
    String url = "https://api.bigbuy.eu/rest/catalog/attributegroups.json?isoCode=fr";
    Object[] attributeGroups = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();


    int j = 0;
    for (Object object : attributeGroups) {
      attributeGroupsMap.put("case" + j, attributeGroups[j]);
      test = (HashMap<String, Object>) attributeGroupsMap.get("case" + j); // get value by key
      String key = String.valueOf(test.get("id"));
      attributeGroupsMap.remove("case" + j);
      attributeGroupsMap.put(key, attributeGroups[j]);
      db.executeUpdate("INSERT INTO attribute_group(id,name,iso_code) VALUES  ('" + test.get("id") + "','" + test.get("name") + "','" + test.get("isoCode") + "')");
      j++;

    }

    //********** display the hashmap
    for (Iterator i = attributeGroupsMap.keySet().iterator(); i.hasNext(); ) {
      Object key = i.next();
      System.out.println(key + "=" + attributeGroupsMap.get(key));
    }
  }
}
