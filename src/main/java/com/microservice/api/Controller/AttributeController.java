package com.microservice.api.Controller;

import com.microservice.api.Connection.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

@RestController
public class AttributeController {
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
    @GetMapping("/insertattribute")
    public void insertattribute() throws IOException, SQLException {
        // ****  Create a HashMap object
        HashMap<String, Object> attributsMap = new HashMap<>();
        HashMap<String, Object> test = new HashMap<>();
        HashMap<String, Object> test3 = new HashMap<>();

        HashMap<String, Object> test2 = new HashMap<>();


        //*****for fields  l barcha  insert them into hashmap
        String url = "https://api.bigbuy.eu/rest/catalog/attributes.json?isoCode=fr";
        Object[] attributs = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();

        // System.out.println(products[0]);
        int j = 0;
        for (Object object : attributs) {
            attributsMap.put("case" + j,attributs[j]);
            test = (HashMap<String, Object>)attributsMap.get("case" + j); // get value by key
            //String id_param = (String) test.get("id");
            //System.out.println("ewwwwwwwwwwwwwwwww"+id_param );
            System.out.println("tessssst "+ test);
            String key = String.valueOf(test.get("id"));
            attributsMap.remove("case" + j);
            attributsMap.put(key,attributs[j]);

            db.executeUpdate("INSERT INTO attributes(id,iso_code,name) VALUES  ('" + test.get("id") + "','" + test.get("isoCode") +  "','" + test.get("name")+"')");

            //obtain name and description




            j++;

        }

        //********** display the hashmap
        System.out.println("*** Display hashmap ***");

        for (Iterator i = test2.keySet().iterator(); i.hasNext(); ) {
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

