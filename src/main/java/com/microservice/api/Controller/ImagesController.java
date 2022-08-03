package com.microservice.api.Controller;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import com.microservice.api.Connection.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ImagesController {
   /* @Autowired
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
    public static HashMap<String, Object> productsMap1 = new HashMap<>();

    // field table product
    @GetMapping("/insertProd")
    public void insertImages() throws IOException, SQLException {
        // **  Create a HashMap object
        HashMap<String, Object> test1 = new HashMap<>();

        //*****for fields  l barcha  insert them into hashmap
        String url1 = "https://api.bigbuy.eu/rest/catalog/products.json?page=2&pageSize=6";
        Object[] products1 = restTemplate.exchange(url1, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();
        // System.out.println(products[0]);
        int j1 = 0;
        for (Object object : products1) {
            productsMap1.put("case" + j1, products1[j1]);
            test1 = (HashMap<String, Object>) productsMap1.get("case" + j1); // get value by key

            String key = String.valueOf(test1.get("id"));

            productsMap1.remove("case" + j1);
            productsMap1.put(key, products1[j1]);
            j1++;

        }
        //*****  Display hashmap 1
        System.out.println("* Display hashmap 1 *");

        for (Iterator i = productsMap1.keySet().iterator(); i.hasNext(); ) {
            Object key = i.next();
            System.out.println(key + "=" + productsMap1.get(key));
        }


        System.out.println("*****************************************");
    }

    @Async
    @Scheduled(fixedDelay = 5000)
    @GetMapping("/insertImage")
    public void products_image() throws SQLException, IOException {

        HashMap<String, Object> test2 = new HashMap<>();
        HashMap<String, Object> test3 = new HashMap<>();
        HashMap<String, Object> test5 = new HashMap<>();

        for (Iterator i = productsMap1.keySet().iterator(); i.hasNext(); ) {
            Object id = i.next();
            System.out.println(id + "=" + productsMap1.get(id));

            String keyy = (String) id;
            String url2 = "https://api.bigbuy.eu/rest/catalog/productimages/" + keyy + ".json";
            Object image = restTemplate.exchange(url2, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object.class).getBody();
            test2.put("img", image);
            test3 = (HashMap<String, Object>) test2.get("img");

            for (int k = 0; k < ((ArrayList<?>) test3.get("images")).size(); k++) {
                test5 = (HashMap<String, Object>) ((ArrayList<Object>) test3.get("images")).get(k);
                db.executeUpdate("INSERT INTO images(id,is_cover,name,url,product_id) VALUES  ('" + test5.get("id") + "','" + test5.get("isCover") + "','" + test5.get("name") + "','" + test5.get("url") +  "','" + keyy+"')");

            }
            test2.remove("img");
            productsMap1.remove(keyy);

        }

        //********** display the hashmap
        System.out.println("* Display hashmap 2***");

        for (Iterator i = test3.keySet().iterator(); i.hasNext(); ) {
            Object key = i.next();
            //System.out.println(key + "=" + test2.get(key));
        }

    }*/
}