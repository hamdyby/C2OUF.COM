package com.microservice.api.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import com.microservice.api.Connection.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class CategoryController {
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
  @GetMapping("/insertCategory")
  public void insertCategory() throws IOException, SQLException {
    HashMap<String, Object>  categoryMap = new HashMap<>();
    HashMap<String, Object> test = new HashMap<>();
    String URL = "https://api.bigbuy.eu/rest/catalog/categories.json?isoCode=fr" ;
    Object[] category = restTemplate.exchange(URL, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();

    int j = 0;
    for (Object object : category) {
      categoryMap.put("case" + j, category[j]);
      test = (HashMap<String, Object>) categoryMap.get("case" + j); // get value by key

      String key = String.valueOf(test.get("id"));

      categoryMap.remove("case" + j);
      categoryMap.put(key, category[j]);

      String name1=String.valueOf(test.get("name"));
      String name = name1.replaceAll("'", "#");
      String url1=String.valueOf(test.get("url"));
      String url = url1.replaceAll("'", "#");

      db.executeUpdate("INSERT INTO category(id,date_add,date_upd,name,url) VALUES  ('" + test.get("id") + "','" + test.get("dateAdd") +"','" + test.get("dateUpd")+"','" + name + "','" + url + "')");

      j++;

    }

    //********** display the hashmap
    for (Iterator i = categoryMap.keySet().iterator(); i.hasNext(); ) {
      Object key = i.next();
      System.out.println(key + "=" + categoryMap.get(key));
    }
  }

}

