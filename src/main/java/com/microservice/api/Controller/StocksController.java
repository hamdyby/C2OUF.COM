package com.microservice.api.Controller;

import com.microservice.api.Connection.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

@RestController
public class StocksController {
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
    // field table product
    @GetMapping("/insertstocks")
    public void insertstocks() throws IOException, SQLException {
        // ****  Create a HashMap object
        HashMap<String, Object> productsMap = new HashMap<>();
        HashMap<String, Object> prodsMap = new HashMap<>();
        HashMap<String, Object> test = new HashMap<>();
        HashMap<String, Object> test3 = new HashMap<>();
        HashMap<String, Object> test4 = new HashMap<>();
        HashMap<String, Object> test2 = new HashMap<>();


        //*****for fields  l barcha  insert them into hashmap
        String url = "https://api.bigbuy.eu/rest/catalog/products.json?page=1&pageSize=100";
        Object[] products = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();

        // System.out.println(products[0]);
        int j = 0;
        for (Object object : products) {
            productsMap.put("case" + j, products[j]);
            test = (HashMap<String, Object>) productsMap.get("case" + j); // get value by key
            String key = String.valueOf(test.get("id"));
            productsMap.remove("case" + j);
            productsMap.put(key, products[j]);
            //obtain name and description
            String url2 = "https://api.bigbuy.eu/rest/catalog/productstock/" + key + ".json";
            Object stock = restTemplate.exchange(url2, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object.class).getBody();
            test2.put("sttt", stock);
            System.out.println(test2);
            test3 = (HashMap<String, Object>) test2.get("sttt");
            System.out.println(test3);

            for (int k = 0; k < ((ArrayList<?>) test3.get("stocks")).size(); k++) {
                test4 = (HashMap<String, Object>) ((ArrayList<Object>) test3.get("stocks")).get(k);
                System.out.println(test4);
                db.executeUpdate("INSERT INTO stocks(max_handling_days,min_handling_days,quantity) VALUES  ('" + test4.get("maxHandlingDays") + "','" + test4.get("minHandlingDays") + "','" + test4.get("quantity") + "')");

            }
            j++;


            //********** display the hashmap
       /* System.out.println("*** Display hashmap ***");

        for (Iterator i = test3.keySet().iterator(); i.hasNext(); ) {
            Object key = i.next();
            //System.out.println(key + "=" + test2.get(key));
        }*/
            // *****for fields ..... insert them into hashmap..........


            //***** insert in DB with requetes

        }
    }}
