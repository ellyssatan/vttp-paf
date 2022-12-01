package vttp_paf.day23_workshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import vttp_paf.day23_workshop.models.Order;
import vttp_paf.day23_workshop.services.OrderService;

@RestController
@RequestMapping("/order")
public class OrderRestController {
    
    @Autowired
    private OrderService oSvc;

    @GetMapping("/total/{order_id}")
    public ResponseEntity<String> getTotal(@PathVariable("order_id") int order_id) {

        Order o = oSvc.getTotal(order_id);

        if (o == null) {
            return ResponseEntity
                    .status(404)
                    .body(Json.createObjectBuilder()
                    .add("message", "Cannot find total for Order ID " + order_id) .build().toString());
            
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(o.toJSON().toString());

    }
}
