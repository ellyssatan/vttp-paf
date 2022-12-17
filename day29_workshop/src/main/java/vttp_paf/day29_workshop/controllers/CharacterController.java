package vttp_paf.day29_workshop.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp_paf.day29_workshop.models.Character;
import vttp_paf.day29_workshop.services.APIService;

@Controller
@RequestMapping("/api")
public class CharacterController {
    
    @Autowired
    private APIService apiSvc;


    @GetMapping("/characters")
    public String getCharacters(@RequestParam String name, Model model) {

        List<Character> list= apiSvc.getCharacters(name);

        List<String> charList = new LinkedList<>();

        for (Character c : list) {
            charList.add(c.getName());
        }

        model.addAttribute("charList", charList);
        return "characterList";
    }
}
