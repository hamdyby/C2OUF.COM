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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ImagesController {
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
    @GetMapping("/insertimages")
    public void insertImages() throws IOException, SQLException {
        // ****  Create a HashMap object
        HashMap<String, Object> productsMap = new HashMap<>();
        HashMap<String, Object> prodsMap = new HashMap<>();
        HashMap<String, Object> test = new HashMap<>();
        HashMap<String, Object> test3 = new HashMap<>();
        HashMap<String, ArrayList<Object>> test4 = new HashMap<>();
        HashMap<String, Object> test2 = new HashMap<>();
        HashMap<String, Object> test5 = new HashMap<>();


        //*****for fields  l barcha  insert them into hashmap
        String url = "https://api.bigbuy.eu/rest/catalog/products.json?page=1&pageSize=100";
        Object[] products = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();

        // System.out.println(products[0]);
        int j = 0;
        for (Object object : products) {
            productsMap.put("case" + j, products[j]);
            test = (HashMap<String, Object>) productsMap.get("case" + j); // get value by key
            System.out.println("tessssst "+ test);
            String key = String.valueOf(test.get("id"));

            //obtain name and description
            String url2 = "https://api.bigbuy.eu/rest/catalog/productimages/"+key+".json";
            Object image = restTemplate.exchange(url2, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object.class).getBody();
            test2.put("img", image);
            System.out.println("testttt2"+test2);
            test3 = (HashMap<String, Object>) test2.get("img");
            System.out.println("testttt3"+test3);
            //test4= (HashMap<String, ArrayList<Object>>) test3.get("images");
            //test4.put("case" + j, (ArrayList<Object>) test3.get("images"));

            //System.out.println("testttt4"+test4);
            for(int k = 0; k < ((ArrayList<?>) test3.get("images")).size(); k++) {
                test5 = (HashMap<String, Object>) ((ArrayList<Object>) test3.get("images")).get(k);
                System.out.println("testttt5"+test5);
                db.executeUpdate("INSERT INTO images(id,is_cover,name,url) VALUES  ('" + test5.get("id") + "','" +  test5.get("isCover")+"','" +  test5.get("name") + "','" + test5.get("url")  + "')");

            }
            test2.remove("img");
            productsMap.remove("case" + j);
            j++;
        }

        //********** display the hashmap
        System.out.println("*** Display hashmap ***");

        for (Iterator i = test3.keySet().iterator(); i.hasNext(); ) {
            Object key = i.next();
            //System.out.println(key + "=" + test2.get(key));
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
    // ** fields name and description
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