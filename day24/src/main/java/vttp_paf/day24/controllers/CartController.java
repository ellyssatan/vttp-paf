package vttp_paf.day24.controllers;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp_paf.day24.models.Item;

@Controller
@RequestMapping(path="/cart")
public class CartController {
    
    @PostMapping
    public String postCart(@RequestBody MultiValueMap<String, String> form, Model model, HttpSession sess) {

        String item = form.getFirst("item");
        Integer quantity = Integer.parseInt(form.getFirst("quantity"));

        List<Item> items = (List<Item>) sess.getAttribute("cart");

        if (items == null) {
            System.out.printf("This is a new session, %s", sess.getId());
            items = new LinkedList<>();
            sess.setAttribute("cart", items);
        }

        items.add(new Item(item, quantity));

        for (Item i : items) {
            System.out.printf("description - %s, quantity - %d", i.getDescription(), i.getQuantity());
        }
        
        model.addAttribute("items", items);
        
        return "cart";
    }

}