package vttp_paf.day28.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp_paf.day28.models.Game;
import vttp_paf.day28.repositories.BGGRepository;

@Controller
@RequestMapping
public class BGGController {
    
    @Autowired
    private BGGRepository bggRepo;

    @GetMapping("/search")
    public String search(@RequestParam String q, Model model) {

        Optional<Game> opt = bggRepo.search(q);

        if (opt.isEmpty())
            return "not-found";

        Game game = opt.get();

        model.addAttribute("game", game);
        model.addAttribute("comments", game.getComments());

        return "results";
    }
}
