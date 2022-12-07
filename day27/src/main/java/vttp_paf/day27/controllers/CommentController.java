package vttp_paf.day27.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp_paf.day27.models.Comment;
import vttp_paf.day27.services.CommentService;

@Controller
@RequestMapping
public class CommentController {
    
    @Autowired
    private CommentService cSvc;

    @GetMapping("/search")
    public String getSearched (@RequestParam String q, @RequestParam Float score, Model model) {

        System.out.printf("q = %s, score = %f\n", q, score);

        List<Comment> results = cSvc.search(q, score, 20, 0);

        model.addAttribute("q", q);
        model.addAttribute("score", score);
        model.addAttribute("results", results);

        return "search-result";
    }
}
