package vttp_paf.day26.controllers;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import vttp_paf.day26.models.TVShow;
import vttp_paf.day26.repositories.TVShowsRepository;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class TVShowRestController {

    @Autowired
    private TVShowsRepository tvShowRepo;

    @GetMapping("/genres")
    public ResponseEntity<String> getAllGenres() {


        List<String> genres = tvShowRepo.getAllGenres();

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (String g : genres)
            arrBuilder.add(g);
        JsonArray result = arrBuilder.build();

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }    

    @GetMapping("/tvshow/{genre}")
    public ResponseEntity<String> getGenre(@PathVariable String genre) {

        List<TVShow> results = tvShowRepo.getSpecificGenres(genre);

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (TVShow d : results)
            arrBuilder.add(d.toJSON());
        JsonArray result = arrBuilder.build();

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }  
}
