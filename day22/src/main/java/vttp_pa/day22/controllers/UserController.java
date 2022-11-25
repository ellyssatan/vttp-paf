package vttp_pa.day22.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp_pa.day22.models.User;
import vttp_pa.day22.services.UserService;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private UserService uSvc;

    @PostMapping("/user")
    public String postUser(@RequestBody MultiValueMap<String, String> form, Model model) {

        String username = form.getFirst("username");
        String password = form.getFirst("password");
        String email = form.getFirst("email");
        String phone = form.getFirst("phone");
        String dob = form.getFirst("dob");

        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setPassword(password);
        u.setDob(dob);
        u.setPhone(phone);

        try {
            if (!uSvc.createUser(u)) {
                System.out.println(">>> User not created!");
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "error";
        }

        model.addAttribute("name", username);

        return "created";
    }
}
