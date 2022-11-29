package vttp_paf.day21_workshop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp_paf.day21_workshop.models.Customer;
import vttp_paf.day21_workshop.models.Order;
import vttp_paf.day21_workshop.services.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRESTController {

    @Autowired
    private CustomerService cSvc;

    @GetMapping(path = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCustomers(@RequestParam int limit, @RequestParam int offset) {

        List<Customer> customerList = cSvc.getCustomers(limit, offset);

        // Build the result
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (Customer c : customerList)
            arrBuilder.add(c.toJSON());
        JsonArray result = arrBuilder.build();

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }

    @GetMapping(path = "/customer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCustomer(@RequestParam int id) {

        Customer c = cSvc.getCustomer(id);

        if (c == null) {
            return ResponseEntity
                    .status(404)
                    .body(Json.createObjectBuilder()
                    .add("message", "Cannot find customer ID " + id) .build().toString());
        }

        // Build the result
        JsonObject result = c.toJSON();

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }

    @GetMapping(path = "/customer/{id}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCustomerOrder(@RequestParam int id) {

        List<Order> orders = cSvc.getCustomerOrder(id);

        if (orders.size() <= 0) {
            return ResponseEntity
                    .status(404)
                    .body(Json.createObjectBuilder()
                    .add("message", "No orders for customer ID " + id) .build().toString());
        }

        // Build the result
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (Order o : orders)
            arrBuilder.add(o.toJSON());
        JsonArray result = arrBuilder.build();

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }
    
}
