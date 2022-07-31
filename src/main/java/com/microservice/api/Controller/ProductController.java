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


        HashMap<String, Object> productsResMap = new HashMap<>();




        //*****for fields  l barcha  insert them into hashmap
        String url1 = "https://api.bigbuy.eu/rest/catalog/products.json?page=1&pageSize=100";
        Object[] products1 = restTemplate.exchange(url1, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();
        // System.out.println(products[0]);
        int j1 = 0;
        for (Object object : products1) {
            productsMap1.put("case" + j1, products1[j1]);
            test1 = (HashMap<String, Object>) productsMap1.get("case" + j1); // get value by key
            //String id_param = (String) test.get("id");
            //System.out.println("ewwwwwwwwwwwwwwwww"+id_param );
           // System.out.println("tessssst " + test);
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



        //*****for fields name w description insert them into hashmap
         String url2 ="https://api.bigbuy.eu/rest/catalog/productsinformation.json?isoCode=fr&pageSize=110&page=1";
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

        //********** display the hashmap with champs name w description
        System.out.println("*** Display hashmap 2 ***");

        for (Iterator n =  productsMap2.keySet().iterator(); n.hasNext(); ) {
            Object key2 = n.next();
            System.out.println(key2 + "=" + productsMap2.get(key2));
        }



// ********** join informations




            for (Iterator n =  productsMap1.keySet().iterator(); n.hasNext(); ) {
                Object key1 = n.next();
                //System.out.println(key2 + "=" + productsMap2.get(key2));
                         // find the correct name and description
                         Iterator iterator =  test2.entrySet().iterator();
                         Boolean test=false;
                         while (iterator.hasNext() && test)  {

                             Map.Entry mapentry = (Map.Entry) iterator.next();
                             if(  test2.get("id")== key1 )
                             {
                                 test=true;
                             }
                         } // end of while

   if(test==true) {
       // en cas d'egalite
       System.out.println("key 1   =  " + key1);
       System.out.println("id fl hashmap 2   =  " + test2.get("id"));

       String name = String.valueOf(test2.get("name"));
       System.out.println("    name   =" + name);


       String description = String.valueOf(test2.get("description"));
       System.out.println("    description  =" + description);

   }
else
    System.out.println("pas de correspondence");

               // houni insert fl base
            }// end of for





        }

    } // end of class



        //***** insert in DB with requetes



       /* db.executeUpdate("INSERT INTO product(id,sku,weight,depth,date_upd,date_upd_description,date_upd_images,wholesale_price,retail_price,in_shops_price,height,width,date_upd_stock) VALUES  ('" + test.get("id") + "','" + test.get("sku") +  "','" + test.get("weight")+ "','" + test.get("depth")+  "','"
                + test.get("dateUpd") +  "','" +  test.get("dateUpdDescription")+  "','" + test.get("dateUpdImages")+  "','" + test.get("wholesalePrice")+"','" + test.get("retailPrice")+  "','" + test.get("inShopsPrice")+  "','"
                +test.get("height") +  "','" + test.get("width") +  "','" + test.get("dateUpdStock") +"')");  */















