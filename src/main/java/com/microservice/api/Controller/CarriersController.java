package com.microservice.api.Controller;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.HashMap; // import the HashMap class
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.api.Connection.Database;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservice.api.Service.CarriersService;



@RestController
public class CarriersController {
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  CarriersService carriersService;
  @Autowired
  private ModelMapper modelMapper;

  HttpHeaders createHeaders() {
    return new HttpHeaders() {
      {
        String authHeader = "Bearer YjQwYWVhNTg2MWRhZmUwYjk4YWJlNzY5Y2Q1YjlkYjE5NzY1YTUwMzM2ZTM5NDM1Yjc3M2MzYmExNTI1OWE2Zg";
        set("Authorization", authHeader);
      }
    };
  }

  // field table Carriers
  @GetMapping("/insertcarriers")
  public void insertcarriers() throws IOException, SQLException {
    // ******  Create a HashMap object called capitalCities
    HashMap<String, Object> carriers1map = new HashMap<>();
    HashMap<String, Object> test = new HashMap<>();

    //*****for fields ..... insert them into dashmap
    String url = "https://api.bigbuy.eu/rest/shipping/carriers.json";
    Object[] carriers1 = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();


    int j = 0;
    for (Object object : carriers1) {
      //System.out.println(carriers1[j]);

      carriers1map.put("case" + j, carriers1[j]);
      // System.out.println("++++++++++++++++++++++++++++++");
      test = (HashMap<String, Object>) carriers1map.get("case" + j); // get value by key
      String key = (String) test.get("id");
      carriers1map.remove("case" + j);

      carriers1map.put(key, carriers1[j]);
      j++;

    }
    //System.out.println("The Value is: " + carriers1map.get("5")); // get value by key

    // mapper.writeValue(new File("/json/insertCarriers.json"), String.valueOf(carriers1map));
    /*
    System.out.println(carriers1[1]);
System.out.println("***************************");
System.out.println(carriers1[2]);
    carriers1map.put("test", carriers1);*/

    //********** display the hashmap
    for (Iterator i = carriers1map.keySet().iterator(); i.hasNext(); ) {
      Object key = i.next();
      System.out.println(key + "=" + carriers1map.get(key));
    }
    // *****for fields ..... insert them into hashmap


    //***** generate json file from the hashmap

    ObjectMapper mapper = new ObjectMapper();
    try {

      mapper.writeValue(new File("carriers.json"), carriers1map);

    } catch (IOException e) {
      e.printStackTrace();
    }


    Database db = new Database();
    for (Entry<String,Object> next : carriers1map) {
      db.executeUpdate("INSERT INTO table (Key, Value) VALUES("+next.getKey()+",'"+next.getValue()+"');";
    }
    db.closeConnection();
  }

  //***** insert the json into database
  /*  @Bean
    CommandLineRunner runner(CarriersService carriersService) {
      return args -> {
        // read json and write to db
        ObjectMapper mapper = new ObjectMapper();
        //String path = getCarriers();
        //  System.out.println("ooooooooooooooooo"+path);
        TypeReference<List<Carriers>> typeReference = new TypeReference<List<Carriers>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/insertCarriers.json");
        try {
          List<Carriers> carriers = mapper.readValue(inputStream,typeReference);
          carriersService.save(carriers);
          System.out.println("Carriers Saved!");
        } catch (IOException e){
          System.out.println("Unable to save carriers: " + e.getMessage());
        }

*/
  // ***** insert hashmp in table


}