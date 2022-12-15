package vttp_paf.day28_workshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp_paf.day28_workshop.models.Review;
import vttp_paf.day28_workshop.services.ReviewService;

@Controller
@RequestMapping("/review")
public class ReviewController {
    
    @Autowired
    private ReviewService rSvc;

    @PostMapping
    public String insertReview(@RequestBody MultiValueMap<String, String> form, Model model) {

        String user = form.getFirst("user");
        int rating = Integer.parseInt(form.getFirst("rating"));
        String comment = form.getFirst("comment");
        int gid = Integer.parseInt(form.getFirst("gid"));
        String name = form.getFirst("name");

        Review inserted = new Review(user, rating, comment, gid, name);
        Review insertR = rSvc.insertReview(inserted);

        model.addAttribute("review", insertR);
        return "review_result";
    }
}
