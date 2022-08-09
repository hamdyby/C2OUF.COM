package com.microservice.api.Controller;

    import java.io.IOException;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.Iterator;
    import java.util.HashMap; // import the HashMap class
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
public class ShippingServicesController {
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
  @GetMapping("/insertShippingServices")
  public void insertShippingServices() throws IOException, SQLException {

    // ******  Create a HashMap object
    HashMap<String, Object> shippingsServicesMap = new HashMap<>();
    HashMap<String, Object> test = new HashMap<>();
    HashMap<String, Object> test2 = new HashMap<>();
      HashMap<String, ArrayList<Object>> shippingMapTest = new HashMap<>();


      //*****for fields ..... insert them into hashmap
    String url = "https://api.bigbuy.eu/rest/shipping/carriers.json";
    Object[] shippings = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(createHeaders()), Object[].class).getBody();


    int j = 0;
    for (Object object : shippings) {
      shippingsServicesMap.put("case" + j, shippings[j]);
      test = (HashMap<String, Object>) shippingsServicesMap.get("case" + j); // get value by key
      String key = (String) test.get("id");

      shippingMapTest.put("case" + j, (ArrayList<Object>) test.get("shippingServices"));
        for(int k = 0; k < ((ArrayList<?>) test.get("shippingServices")).size(); k++) {

          test2 = (HashMap<String, Object>) ((ArrayList<Object>) test.get("shippingServices")).get(k);
          db.executeUpdate("INSERT INTO shipping_services(id,delay,name,carriers_id) VALUES  ('" + test2.get("id") + "','" + test2.get("delay") + "','"
              + test2.get("name") + "','" + key+"')");
        }
      shippingsServicesMap.remove("case" + j);
      shippingsServicesMap.put(key, shippings[j]);

      j++;
    }

    //********** display the hashmap
    for (Iterator i = shippingMapTest.keySet().iterator(); i.hasNext(); ) {
      Object key = i.next();
      System.out.println(key + "=" + shippingMapTest.get(key));
    }
  }
}