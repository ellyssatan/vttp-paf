package vttp_paf.day24.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp_paf.day24.models.Item;

@Controller
@RequestMapping(path="/checkout")
public class CheckOutController {
    
    @PostMapping
    public String postCheckout(Model model, HttpSession sess) {

        List<Item> items = (List<Item>) sess.getAttribute("cart");

        // Destroy the session
        sess.invalidate();

        model.addAttribute("total", items.size() * 2.5);

        return "checkout";
    }
}
