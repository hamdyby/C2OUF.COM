package com.microservice.api.Controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
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

  //get product by id
  /*@RequestMapping(value = "/produit/{id}")
  public String getProductById(@PathVariable("id") long id) {
    return restTemplate.exchange("https://api.bigbuy.eu/rest/catalog/product/"+id +".json", HttpMethod.GET,new HttpEntity<String>(createHeaders()), String.class).getBody();
  }*/

  //get product by id
  @RequestMapping(value = "/produit/{id}")
  public Object getProductById(@PathVariable("id") long id) {
    Object product = restTemplate.exchange("https://api.bigbuy.eu/rest/catalog/product/"+id +".json", HttpMethod.GET,new HttpEntity<String>(createHeaders()), Object.class).getBody();
    return product;
  }

  //get all products (specify page size and page)
  // **** fields name and description
  @GetMapping("/produits")
  public List<Object> getProducts(@RequestParam("isoCode") String isoCode, @RequestParam("pageSize") long pageSize, @RequestParam("page") long page) throws IOException, JSONException {
    String url ="https://api.bigbuy.eu/rest/catalog/productsinformation.json?isoCode="+isoCode+"&pageSize="+pageSize+"&page="+page;
    Object[] produits = restTemplate.exchange(url, HttpMethod.GET,new HttpEntity<String>(createHeaders()), Object[].class).getBody();
    Gson gson = new Gson();
    //JSONObject myObject = new JSONObject(Arrays.toString(produits));

    gson.toJson( produits, new FileWriter("test.json")); // store result in json

System.out.println("hello");
    return  Arrays.asList(produits);

  }
}
