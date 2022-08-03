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
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
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

    public static HashMap<String, Object> productsMap1 = new HashMap<>();
    // field table product
    @GetMapping("/insertProducts")
    public  void insertProducts() throws IOException, SQLException {
        // ****  Create  HashMaps
        //HashMap<String, Object> productsMap1 = new HashMap<>();
        HashMap<String, Object> test1 = new HashMap<>();

        //*****for fields  l barcha  insert them into hashmap
        String url1 = "https://api.bigbuy.eu/rest/catalog/products.json?page=4&pageSize=200";
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
        System.out.println("*** Display hashmap 1 ***");

        for (Iterator i = productsMap1.keySet().iterator(); i.hasNext(); ) {
            Object key = i.next();
            System.out.println(key + "=" + productsMap1.get(key));
        }


        System.out.println("*******************************************");
    }




    @Async
    @Scheduled(fixedDelay = 5000)
    @GetMapping("/fill_product_name_desc")
    public void fill_product_name_desc() throws SQLException, IOException {
//*****for fields name w description insert them into hashmap


    HashMap<String, Object> productsMap2 = new HashMap<>();
    HashMap<String, Object> test2 = new HashMap<>();
    HashMap<String, Object> test3 = new HashMap<>();
    HashMap<String, Object> inter = new HashMap<>();




    for (Iterator i = productsMap1.keySet().iterator(); i.hasNext(); ) {
        Object id = i.next();
        System.out.println(id + "=" + productsMap1.get(id));

        String keyy = (String) id;


    String url2 ="https://api.bigbuy.eu/rest/catalog/productinformationalllanguages/"+keyy+".json";
    Object products2 = restTemplate.exchange(url2, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object.class).getBody();
        productsMap2.put("case" , products2);
        test2 = (HashMap<String, Object>) productsMap2.get("case"); // get value by key
        productsMap2.remove("case");
        test3 = (HashMap<String, Object>) productsMap1.get(keyy);
        productsMap1.remove(keyy);
        String key = String.valueOf(test2.get("id"));

        System.out.println(test2.get("name"));
        System.out.println(productsMap1.get(keyy));

        String name1=String.valueOf(test2.get("name"));

        String name = name1.replaceAll("'", "#");

        String description1=String.valueOf(test2.get("description"));

        String description = description1.replaceAll("'", "#");


        // insert
        db.executeUpdate("INSERT INTO product(id,name,description,url,iso_code,categories,sku,weight,depth,date_upd,date_upd_description,date_upd_images,wholesale_price,retail_price,in_shops_price,height,width,date_upd_stock) VALUES  ('" + test3.get("id") + "','" + name + "','" + description + "','" + test2.get("url") +"','" + test2.get("isoCode") +"','" + test3.get("categories") +"','" + test3.get("sku")+  "','" + test3.get("weight")+ "','" + test3.get("depth")+  "','"
            + test3.get("dateUpd") +  "','" +  test3.get("dateUpdDescription")+  "','" + test3.get("dateUpdImages")+  "','" + test3.get("wholesalePrice")+"','" + test3.get("retailPrice")+  "','" + test3.get("inShopsPrice")+  "','"
            +test3.get("height") +  "','" + test3.get("width") +  "','" + test3.get("dateUpdStock") +"')");

        test2.remove("case");

    }

        //********** display the hashmap with champs name w description
        System.out.println("*** Display hashmap 2 ***");

        for (Iterator n =  productsMap2.keySet().iterator(); n.hasNext(); ) {
            Object key2 = n.next();
            System.out.println(key2 + "=" + productsMap2.get(key2));
        }
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

/*
        System.out.println("*** Display hashmap 1 ***");

        for (Iterator i = productsMap1.keySet().iterator(); i.hasNext(); ) {
            Object key = i.next();
            System.out.println(key + "=" + productsMap1.get(key));
        }




        for (Iterator n =  productsMap2.keySet().iterator(); n.hasNext(); ) {
            Object key2 = n.next();
            System.out.println(key2 + "=" + productsMap2.get(key2));
        }


        for (Iterator x = productsMap1.keySet().iterator(); x.hasNext(); ) {
            Object key1 = x.next();

                System.out.println("key 1   =  " + key1);


                for (Iterator n =  productsMap2.keySet().iterator(); n.hasNext(); ) {
                Object key2 = n.next();
               // System.out.println(key2 + "=" + productsMap2.get(key2));
                //System.out.println(productsMap2.get(key2));

                inter = (HashMap<String, Object>) productsMap2.get(key2); // get value by key

                String id = String.valueOf(inter.get("id"));
                System.out.println("key 2   =  " + id);

/*
                String name = String.valueOf(inter.get("name"));
                System.out.println("name   =  " + name);

                String description = String.valueOf(inter.get("description"));
                System.out.println("description   =  " +description);

*/
    /*

                    if (key1==id)
                    {
                      /*  db.executeUpdate("INSERT INTO product(id,name,description,sku,weight,depth,date_upd,date_upd_description,date_upd_images,wholesale_price,retail_price,in_shops_price,height,width,date_upd_stock) VALUES  ('" + productsMap1.get("id") + "','" + name + "','" + description +"','" + productsMap1.get("sku") +  "','" + productsMap1.get("weight")+ "','" + productsMap1.get("depth")+  "','"
                                + productsMap1.get("dateUpd") +  "','" +  productsMap1.get("dateUpdDescription")+  "','" + productsMap1.get("dateUpdImages")+  "','" + productsMap1.get("wholesalePrice")+"','" + productsMap1.get("retailPrice")+  "','" + productsMap1.get("inShopsPrice")+  "','"
                                +productsMap1.get("height") +  "','" + productsMap1.get("width") +  "','" + productsMap1.get("dateUpdStock") +"')");
*/
    /*
                        System.out.println("yeeeeeeeeeeeees");
                    }
                    else
                        System.out.println("pas de correspendence");

                }  // enf for 2



            } // end for1

    }



*/


        }   // end class












