package com.microservice.api.Controller;

import java.io.*;
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
        // ******  Create a HashMap object
        HashMap<String, Object> productsMap = new HashMap<>();
        HashMap<String, Object> test = new HashMap<>();


        HashMap<String, Object> test2 = new HashMap<>();


        //*****for fields  l barcha  insert them into hashmap
        String url = "https://api.bigbuy.eu/rest/catalog/products.json?page=1&pageSize=100";
        Object[] products = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();

        System.out.println(products[0]);
        int j = 0;
        for (Object object : products) {
            productsMap .put("case" + j, products[j]);
            test = (HashMap<String, Object>) productsMap.get("case" + j); // get value by key
           Object id_param =  test.get("id");
            System.out.println("ewwwwwwwwwwwwwwwww"+id_param );

            String key = String.valueOf(test.get("id"));
            productsMap.remove("case" + j);
            productsMap.put(key, products[j]);
              //  obtain name and description
           /* String url2 = "https://api.bigbuy.eu/rest/catalog/productinformationalllanguages/"+id_param+".json";
            Object[] products2 = restTemplate.exchange(url2, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();
            test2 .put("jdida", products2[0]);
  String  name = (String) test2.get("name");
  String description  = (String) test2.get("description");*/




  db.executeUpdate("INSERT INTO product(id,sku,weight,depth,date_upd,date_upd_description,date_upd_images,wholesale_price,retail_price,in_shops_price,height,width,date_upd_stock) VALUES  ('" + test.get("id") + "','" + test.get("sku") +  "','" + test.get("weight")+ "','" + test.get("depth")+  "','" + test.get("dateUpd") +  "','" +  test.get("dateUpdDescription")+  "','" + test.get("dateUpdImages")+  "','" + test.get("wholesalePrice")+"','" + test.get("retailPrice")+  "','" + test.get("inShopsPrice")+  "','" +test.get("height") +  "','" + test.get("width") +  "','" + test.get("dateUpdStock") + "')");

            j++;

        }

        //********** display the hashmap
        System.out.println("           ***** Display hashmap *****          " );

        for (Iterator i = test2.keySet().iterator(); i.hasNext(); ) {
            Object key = i.next();
            System.out.println(key + "=" + test2.get(key));
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