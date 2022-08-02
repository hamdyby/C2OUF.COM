package com.microservice.api.Controller;

import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.api.Connection.Database;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;




@RestController
public class ProductController {
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
    @GetMapping("/insertProducts")
    public void insertProducts() throws IOException, SQLException {
        // ****  Create  HashMaps
        HashMap<String, Object> productsMap1 = new HashMap<>();
        HashMap<String, Object> test1 = new HashMap<>();



        HashMap<String, Object> productsMap2 = new HashMap<>();
        HashMap<String, Object> test2= new HashMap<>();


        HashMap<String, Object> inter = new HashMap<>();




        //*****for fields  l barcha  insert them into hashmap
        String url1 = "https://api.bigbuy.eu/rest/catalog/products.json?page=0&pageSize=0";
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
/*
        System.out.println("*** Display hashmap 1 ***");

        for (Iterator i = productsMap1.keySet().iterator(); i.hasNext(); ) {
            Object key = i.next();
            System.out.println(key + "=" + productsMap1.get(key));
        }

*/


System.out.println("*******************************************");



        //*****for fields name w description insert them into hashmap
         String url2 ="https://api.bigbuy.eu/rest/catalog/productsinformation.json?isoCode=fr&pageSize=0&page=0";
        Object[] products2 = restTemplate.exchange(url2, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();
        // System.out.println(products[0]);
        int k = 0;
        for (Object object : products2) {
            productsMap2.put("case" + k, products2[k]);
            test2 = (HashMap<String, Object>) productsMap2.get("case" +k); // get value by key
            String key = String.valueOf(test2.get("id"));
            productsMap2.remove("case" + k);
            productsMap2.put(key, products2[k]);
            k++;

        }

        /*
        //********** display the hashmap with champs name w description
        System.out.println("*** Display hashmap 2 ***");

        for (Iterator n =  productsMap2.keySet().iterator(); n.hasNext(); ) {
            Object key2 = n.next();
            System.out.println(key2 + "=" + productsMap2.get(key2));
        }
*/


// ********** join informations



        for (Iterator x = productsMap1.keySet().iterator(); x.hasNext(); ) {
            Object key1 = x.next();

                System.out.println("key 1   =  " + key1);


                for (Iterator n =  productsMap2.keySet().iterator(); n.hasNext(); ) {
                Object key2 = n.next();
               // System.out.println(key2 + "=" + productsMap2.get(key2));
                //System.out.println(productsMap2.get(key2));

                inter = (HashMap<String, Object>) productsMap2.get(key2); // get value by key

                String id = String.valueOf(inter.get("id"));
               // System.out.println("key 2   =  " + id);


                String name = String.valueOf(inter.get("name"));
                //System.out.println("name   =  " + name);

                String description = String.valueOf(inter.get("description"));
                //System.out.println("description   =  " +description);



                    if (key1==id)
                    {
                        db.executeUpdate("INSERT INTO product(id,name,description,sku,weight,depth,date_upd,date_upd_description,date_upd_images,wholesale_price,retail_price,in_shops_price,height,width,date_upd_stock) VALUES  ('" + productsMap1.get("id") + "','" + name + "','" + description +"','" + productsMap1.get("sku") +  "','" + productsMap1.get("weight")+ "','" + productsMap1.get("depth")+  "','"
                                + productsMap1.get("dateUpd") +  "','" +  productsMap1.get("dateUpdDescription")+  "','" + productsMap1.get("dateUpdImages")+  "','" + productsMap1.get("wholesalePrice")+"','" + productsMap1.get("retailPrice")+  "','" + productsMap1.get("inShopsPrice")+  "','"
                                +productsMap1.get("height") +  "','" + productsMap1.get("width") +  "','" + productsMap1.get("dateUpdStock") +"')");

                    }
                       }


            }

    }






        }












