package vttp_paf.day29_workshop.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp_paf.day29_workshop.models.Character;
import vttp_paf.day29_workshop.repositories.MarvelRepository;
import vttp_paf.day29_workshop.services.MarvelService;

@Controller
@RequestMapping("/api")
public class CharacterController {
    
    @Autowired
    private MarvelService marvelSvc;

    @Autowired
    private MarvelRepository marvelRepo;


    @GetMapping("/characters")
    public String getCharacters(@RequestParam String name, @RequestParam int limit, Model model) {

        List<Character> results = null;

        Optional<List<Character>> opt = marvelRepo.get(name);
        if (opt.isEmpty()) {
            results = marvelSvc.search(name);
            marvelRepo.cache(name, results);
        } else  { 
            results = opt.get();
            System.out.printf(">>>> from CACHE\n");
        }

        model.addAttribute("results", results);
        return "characterList";
    }

    @GetMapping("/character/{charId}")
    public String getCharacterById(@PathVariable int charId, Model model) {

        
        return "null";
    }

    @PostMapping("/character/{charId}")
    public String addCommentToChar(@PathVariable int charId, Model model) {

        
        return "null";
    }
}
