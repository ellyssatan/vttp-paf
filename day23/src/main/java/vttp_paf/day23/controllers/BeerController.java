package vttp_paf.day23.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp_paf.day23.models.BeerStyle;
import vttp_paf.day23.services.BeerService;

@Controller
@RequestMapping
public class BeerController {
    
    @Autowired
    private BeerService beerSvc;

    @GetMapping
    public String getSearch(Model model) throws Exception {

        List<BeerStyle> styles = beerSvc.getStyles();

        model.addAttribute("styles", styles);

        return "index";
    }

    @GetMapping("/search")
    public String getSearch(@RequestParam int id, Model model) throws Exception {

        // System.out.println(">>>>>>CONTROLLER: SELECTED " + id);
        List<String> places;
        if (beerSvc.getBeerList(id).isPresent()) {
            places = beerSvc.getBeerList(id).get();
        } else {
            places = beerSvc.getBreweries(id);
            beerSvc.saveBeerList(id, places);
        }

        model.addAttribute("noBeer", places.isEmpty());
        model.addAttribute("places", places);

        return "results";
    }

}

