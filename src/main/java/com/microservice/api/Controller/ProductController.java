package com.microservice.api.Controller;

import java.io.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.microservice.api.Connection.Database;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping("/insertproducts")
    public void insertproducts() throws IOException, SQLException {
        // ******  Create a HashMap object
        HashMap<String, Object> productsmap = new HashMap<>();
        HashMap<String, Object> test = new HashMap<>();

        //*****for fields  l barcha  insert them into hashmap
        String url = "https://api.bigbuy.eu/rest/catalog/products.json?page=1&pageSize=2";
        Object[] products = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();

System.out.println(products[0]);
        int j = 0;
        for (Object object : products) {
            productsmap .put("case" + j, products[j]);
            test = (HashMap<String, Object>) productsmap.get("case" + j); // get value by key
            System.out.println("********************"+test.get("id"));
            String key = String.valueOf(test.get("id"));
            productsmap.remove("case" + j);
            productsmap.put(key, products[j]);
            //db.executeUpdate("INSERT INTO product(id,width) VALUES  ('" + test.get("id") + "','" + test.get("width") + "')");

            j++;

        }

        //********** display the hashmap
        for (Iterator i = test.keySet().iterator(); i.hasNext(); ) {
            Object key = i.next();
            System.out.println(key + "=" + test.get(key));
        }
        // *****for fields ..... insert them into hashmap..........


        //***** insert in DB with requetes

    }


    //get product by id
  /*@RequestMapping(value = "/produit/{id}")
  public String getProductById(@PathVariable("id") long id) {
    return restTemplate.exchange("https://api.bigbuy.eu/rest/catalog/product/"+id +".json", HttpMethod.GET,new HttpEntity<String>(createHeaders()), String.class).getBody();
  }*/



/*


    //get product by id
    @RequestMapping(value = "/produit/{id}")
    public Object getProductById(@PathVariable("id") long id) {
        Object product = restTemplate.exchange("https://api.bigbuy.eu/rest/catalog/product/" + id + ".json", HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object.class).getBody();
        return product;
    }

    //get all products (specify page size and page)
    // **** fields name and description
    @GetMapping("/produits")
    public List<Object> getProducts(@RequestParam("isoCode") String isoCode, @RequestParam("pageSize") long pageSize, @RequestParam("page") long page) throws IOException, JSONException {
        String url = "https://api.bigbuy.eu/rest/catalog/productsinformation.json?isoCode=" + isoCode + "&pageSize=" + pageSize + "&page=" + page;
        Object[] produits = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();
        System.out.println("hello");
        ObjectMapper mapper = new ObjectMapper();
        try {

            // Writing to a file
            mapper.writeValue(new File("product.json"), produits);

           } catch (IOException e) {
            e.printStackTrace();
                                   }
        return  Arrays.asList(produits);


    }*/
}