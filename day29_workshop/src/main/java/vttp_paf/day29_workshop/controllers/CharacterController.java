package vttp_paf.day29_workshop.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp_paf.day29_workshop.models.Character;
import vttp_paf.day29_workshop.models.Comment;
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
            // System.out.printf(">>>> retrieved from CACHE\n");
        }

        model.addAttribute("results", results);
        return "characterList";
    }

    @GetMapping("/character/{charId}")
    public String getCharacterById(@PathVariable int charId, Model model) {

        // Character c = marvelSvc.getByCharId(charId);

        Character c = null;
        Optional<Character> opt = marvelRepo.getByCharId(Integer.toString(charId));

        if (opt.isEmpty()) {
            c = marvelSvc.getByCharId(charId);
            marvelRepo.cacheChar(Integer.toString(charId), c);
        } else  { 
            c = opt.get();
            // System.out.printf(">>>> retrieved from CACHE\n");
        }

        List<Comment> cList = marvelRepo.getComments(charId);
        
        model.addAttribute("c", c);
        model.addAttribute("comments", cList);
        return "character";
    }

    @PostMapping("/character/savedComment")
    public String addCommentToChar(@RequestBody MultiValueMap<String, String> form, Model model) {

        int charId = Integer.parseInt(form.getFirst("charId"));
        String user = form.getFirst("user");
        String comment = form.getFirst("comment");

        Character character = null;
        Optional<Character> opt = marvelRepo.getByCharId(Integer.toString(charId));

        if (opt.isEmpty()) {
            character = marvelSvc.getByCharId(charId);
            marvelRepo.cacheChar(Integer.toString(charId), character);
        } else  { 
            character = opt.get();
        }
        
        Comment c = new Comment(charId, user, comment);
        
        // add comment into mongoDB
        marvelRepo.insertComment(c);

        // get existing list of comments for charId
        List<Comment> cList = marvelRepo.getComments(charId);

        model.addAttribute("c", character);
        model.addAttribute("comments", cList);
        return "character";
    }
}
