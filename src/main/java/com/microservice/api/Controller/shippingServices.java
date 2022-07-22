package com.microservice.api.Controller;

    import java.io.File;
    import java.io.IOException;
    import java.security.KeyStore;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.Iterator;
    import java.util.HashMap; // import the HashMap class
    import java.util.Map;

    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.microservice.api.Connection.Database;
    import org.modelmapper.ModelMapper;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
    import org.springframework.http.HttpEntity;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpMethod;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RestController;
    import org.springframework.web.client.RestTemplate;

    import com.microservice.api.Service.CarriersService;



@RestController
public class shippingServices {
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

  // Connect to data base
  Database db = new Database();

  // field table Carriers
  @GetMapping("/insertshipping")
  public void insertshipping() throws IOException, SQLException {

    // ******  Create a HashMap object
    HashMap<String, Object> shippingsmap = new HashMap<>();
    HashMap<String, Object> test = new HashMap<>();
    HashMap<String, ArrayList<Object>> shippingsmaptest = new HashMap<>();
    HashMap<String, Object> test5 = new HashMap<>();

      //*****for fields ..... insert them into hashmap
    String url = "https://api.bigbuy.eu/rest/shipping/carriers.json";
    Object[] shippings = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();


    int j = 0;
    for (Object object : shippings) {
      shippingsmap.put("case" + j, shippings[j]);
      test = (HashMap<String, Object>) shippingsmap.get("case" + j); // get value by key
      //System.out.println("obj"+object);
      String key = (String) test.get("id");

        shippingsmaptest.put("case" + j, (ArrayList<Object>) test.get("shippingServices"));
        int k=0;
        int n=0;
        while (k < ((ArrayList<Object>) test.get("shippingServices")).size()) {
            //System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhh" + ((ArrayList<Object>) test.get("shippingServices")).get(k));
            test5.put("key"+k,((ArrayList<Object>) test.get("shippingServices")).get(k));

            k=k+j;

        }

       // System.out.println(test.get("shippingServices"));
      shippingsmap.remove("case" + j);
      shippingsmap.put(key, shippings[j]);

      //db.executeUpdate("INSERT INTO shipping_services(id,delay,name,carriers_id) VALUES  ('" + test.get("shippingServices") + "','" + test.get("delay") + "','"
                      //  + test.get("name") + "','" + key+"')");

      j++;

    }

    //********** display the hashmap
      for (Iterator<String> i = test5.keySet().iterator(); i.hasNext(); ) {
          Object key = i.next();
          System.out.println(key + "=" + test5.get(key));
      }
    // *****for fields ..... insert them into hashmap..........


    //***** insert in DB with requetes

  }
}