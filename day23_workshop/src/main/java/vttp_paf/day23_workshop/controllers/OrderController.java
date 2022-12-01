package vttp_paf.day23_workshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp_paf.day23_workshop.models.Order;
import vttp_paf.day23_workshop.services.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService oSvc;

    @GetMapping("/total/{order_id}/html")
    public String getTotal(@PathVariable("order_id") int order_id, Model model) {

        Order o = oSvc.getTotal(order_id);

        model.addAttribute("order_id", o.getOrder_id());
        model.addAttribute("date", o.getOrder_date());
        model.addAttribute("customer_id", o.getCustomer_id());
        model.addAttribute("total_price", o.getTotal_price());
        model.addAttribute("cost_price", o.getCost_price());

        return "index";
    }
}
