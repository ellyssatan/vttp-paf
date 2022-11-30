package vttp_paf.day22_workshop.controllers;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp_paf.day22_workshop.models.Rsvp;
import vttp_paf.day22_workshop.repositories.RsvpRepository;

@Controller
@RequestMapping("/api")
public class RsvpController {

    @Autowired
    private RsvpRepository rRepo;

    @RequestMapping(path = "/rsvp/{email}")
    public String getOldRsvp(@PathVariable String email, Model model) {
        
        Rsvp r = rRepo.findRsvpByEmail(email);

        String name = r.getName();
        System.out.println("NAMEEEEE: " + name);
        int phone = r.getPhone();
        DateTime confirmation_date = r.getConfirmation_date();
        String comments = r.getComments();

        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("phone", phone);
        model.addAttribute("date", confirmation_date);
        model.addAttribute("comments", comments);

        return "update";
    }
}
