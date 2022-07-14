package com.microservice.api.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

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
  @RequestMapping(value = "/produit/{id}")
  public ResponseEntity<String> getProductById(@PathVariable("id") long id) throws IOException {
    ResponseEntity<String> response = restTemplate.exchange("https://api.bigbuy.eu/rest/catalog/product/"+id +".json", HttpMethod.GET,new HttpEntity<String>(createHeaders()), String.class,1);

      BufferedWriter writer = new BufferedWriter(new FileWriter("product.json", true));
      writer.append(response.getBody());
      writer.close();

    return response;
  }

  //get product by id
  /*@RequestMapping(value = "/produit/{id}")
  public Object getProductById(@PathVariable("id") long id) throws IOException {
    Object product = restTemplate.exchange("https://api.bigbuy.eu/rest/catalog/product/"+id +".json", HttpMethod.GET,new HttpEntity<String>(createHeaders()), Object.class).getBody();
    return product;

  }*/

  //get all products (specify page size and page)
 /* @GetMapping("/produits")
  public List<Object> getProducts(@RequestParam("isoCode") String isoCode, @RequestParam("pageSize") long pageSize, @RequestParam("page") long page)
      throws IOException {
    BufferedWriter bw = null;
    String url ="https://api.bigbuy.eu/rest/catalog/productsinformation.json?isoCode="+isoCode+"&pageSize="+pageSize+"&page="+page;
    Object[] produits = restTemplate.exchange(url, HttpMethod.GET,new HttpEntity<String>(createHeaders()), Object[].class).getBody();
    try {
      BufferedWriter writer = new BufferedWriter( new FileWriter( "prod.txt" ) );
      writer.write(String.valueOf(produits));
      writer.flush();
      writer.close();
      System.out.println("File written Successfully");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return  Arrays.asList(produits);
  }*/

  //get all products (specify page size and page)
  // ** fields name and description
  @GetMapping("/produits")
  public List<Object> getProducts(@RequestParam("isoCode") String isoCode, @RequestParam("pageSize") long pageSize, @RequestParam("page") long page) throws IOException, JSONException {
    String url = "https://api.bigbuy.eu/rest/catalog/productsinformation.json?isoCode=" + isoCode + "&pageSize=" + pageSize + "&page=" + page;
    Object[] produits = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();
    ObjectMapper mapper = new ObjectMapper();
    try {

      // Writing to a file
      mapper.writeValue(new File("product.json"), produits);

    } catch (IOException e) {
      e.printStackTrace();
    }

    return  Arrays.asList(produits);

  }
}
