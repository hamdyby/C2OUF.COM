package com.microservice.api.Controller;

import java.io.*;
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
    public void insertProducts() throws IOException, SQLException {
        // ****  Create  HashMaps
        HashMap<String, Object> test1 = new HashMap<>();

        //*****for fields  l barcha  insert them into hashmap
        String url1 = "https://api.bigbuy.eu/rest/catalog/products.json?page=1&pageSize=500";
        Object[] products1 = restTemplate.exchange(url1, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();
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

    }


    @Async
    @Scheduled(fixedDelay = 5000)
    @GetMapping("/fill_product_name_desc")
    public void fill_product_name_desc() throws SQLException, IOException {
        //*****for fields name w description insert them into hashmap

        HashMap<String, Object> productsMap2 = new HashMap<>();
        HashMap<String, Object> test2 = new HashMap<>();
        HashMap<String, Object> testImage = new HashMap<>();
        HashMap<String, Object> test3Image = new HashMap<>();
        HashMap<String, Object> testStock = new HashMap<>();
        HashMap<String, Object> test3Stock  = new HashMap<>();
        HashMap<String, Object> test4Stock  = new HashMap<>();
        HashMap<String, Object> test5 = new HashMap<>();
        HashMap<String, Object> test3 = new HashMap<>();
        HashMap<String, Object> inter = new HashMap<>();

        for (Iterator i = productsMap1.keySet().iterator(); i.hasNext(); ) {
            Object id = i.next();
            System.out.println(id + "=" + productsMap1.get(id));

            String keyy = (String) id;

            String url2 = "https://api.bigbuy.eu/rest/catalog/productinformationalllanguages/" + keyy + ".json";
            Object products2 = restTemplate.exchange(url2, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object.class).getBody();
            productsMap2.put("case", products2);
            test2 = (HashMap<String, Object>) productsMap2.get("case"); // get value by key
            productsMap2.remove("case");
            test3 = (HashMap<String, Object>) productsMap1.get(keyy);
            productsMap1.remove(keyy);
            String key = String.valueOf(test2.get("id"));

            System.out.println(test2.get("name"));
            System.out.println(productsMap1.get(keyy));

            String name1 = String.valueOf(test2.get("name"));

            String name = name1.replaceAll("'", "#");

            String description1 = String.valueOf(test2.get("description"));

            String description = description1.replaceAll("'", "#");

            // insert products
            db.executeUpdate(
                "INSERT INTO product(id,name,description,url,category_id,iso_code,categories,sku,weight,depth,date_upd,date_upd_description,date_upd_images,wholesale_price,retail_price,in_shops_price,height,width,date_upd_stock) VALUES  ('" + test3.get("id") + "','" + name + "','" + description + "','" + test2.get("url") + "','" + test3.get("category") + "','" + test2
                    .get("isoCode") + "','" + test3.get("categories") + "','" + test3.get("sku") + "','" + test3.get("weight") + "','" + test3
                    .get("depth") + "','" + test3.get("dateUpd") + "','" + test3.get("dateUpdDescription") + "','" + test3.get("dateUpdImages") + "','" + test3
                    .get("wholesalePrice") + "','" + test3.get("retailPrice") + "','" + test3.get("inShopsPrice") + "','"
                    + test3.get("height") + "','" + test3.get("width") + "','" + test3.get("dateUpdStock") + "')"

                    + "ON DUPLICATE KEY UPDATE"+   " name = '"+name + "'," + " description = '"+description + "'," + " url = '" +test2.get("url") + "',"
                    + " category_id = '"+test3.get("category")+ "'," + " iso_code = '"+test2.get("isoCode") + "'," + " categories = '"+ test3.get("categories") + "'," + " sku = '"+test3.get("sku") + "'," + " weight = '"+test3.get("weight") + "'," + " depth = '"+ test3.get("depth") + "',"+ " date_upd = '" +test3.get("dateUpd") + "',"
                    + " date_upd_description = '"+test3.get("dateUpdDescription") + "'," +" date_upd_images = '" +test3.get("dateUpdImages") + "'," + " wholesale_price = '"+test3.get("wholesalePrice") + "'," + " retail_price = '"+ test3.get("retailPrice") + "'," + " in_shops_price = '"+ test3.get("inShopsPrice") + "',"
                    + " height = '"+ test3.get("height") + "'," + " width = '"+test3.get("width") + "'," + " date_upd_stock = '" + test3.get("dateUpdStock") + "'" );

            //insert their images
            String url3 = "https://api.bigbuy.eu/rest/catalog/productimages/" + keyy + ".json";
            Object image = restTemplate.exchange(url3, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object.class).getBody();
            testImage.put("img", image);
            test3Image = (HashMap<String, Object>) testImage.get("img");

            for (int k = 0; k < ((ArrayList<?>) test3Image.get("images")).size(); k++) {
                test5 = (HashMap<String, Object>) ((ArrayList<Object>) test3Image.get("images")).get(k);
                db.executeUpdate(
                    "INSERT INTO images(id,is_cover,name,url,product_id) VALUES  ('" + test5.get("id") + "','" + test5.get("isCover") + "','" + test5
                        .get("name") + "','" + test5.get("url") + "','" + keyy + "')"
                        + "ON DUPLICATE KEY UPDATE" +" is_cover = '" +test5.get("isCover") + "'," + " name = '"
                        +test5.get("name") + "'," + " url = '"+test5.get("url") + "'," +" product_id = '"+ keyy + "'" );

            }

            //insert stock
            String urlStock = "https://api.bigbuy.eu/rest/catalog/productstock/"+keyy+".json";
            Object stock = restTemplate.exchange(urlStock, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object.class).getBody();
            testStock.put("sttt", stock);
            test3Stock = (HashMap<String, Object>) testStock.get("sttt");

            for (int k = 0; k < ((ArrayList<?>) test3Stock.get("stocks")).size(); k++) {
                test4Stock = (HashMap<String, Object>) ((ArrayList<Object>) test3Stock.get("stocks")).get(k);

                db.executeUpdate("INSERT INTO stocks(id,max_handling_days,min_handling_days,quantity,product_id) VALUES  ('" + keyy + "','" + test4Stock.get("maxHandlingDays") + "','" + test4Stock.get("minHandlingDays") + "','" + test4Stock.get("quantity") + "','" +keyy+ "')"
                            + "ON DUPLICATE KEY UPDATE" + " max_handling_days = '"+ test4Stock.get("maxHandlingDays") + "'," + " min_handling_days = '" +test4Stock.get("minHandlingDays") + "'," +" quantity = '" +test4Stock.get("quantity") + "'," +" product_id = '"+keyy+ "'"
                           );

            }

            test2.remove("case");
        }

        //********** display the hashmap with fields name w description
        System.out.println("*** Display hashmap 2 ***");

        for (Iterator n = productsMap2.keySet().iterator(); n.hasNext(); ) {
            Object key2 = n.next();
            System.out.println(key2 + "=" + productsMap2.get(key2));
        }
    }

}