package vttp_paf.day28_workshop.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp_paf.day28_workshop.models.Game;
import vttp_paf.day28_workshop.models.Review;
import vttp_paf.day28_workshop.services.ReviewService;

@RestController
@RequestMapping("/game")
public class GameReviewRestController {
    
    @Autowired
    private ReviewService rSvc;

    @GetMapping("{gid}/reviews")
    public ResponseEntity<String> getGameReviews(@PathVariable int gid) {
        
        Optional<Game> gResult = rSvc.aggregateReviews(gid);

        JsonObject result = Json.createObjectBuilder()
                                .add("review", gResult.get().toJson())
                                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @GetMapping("/{sort}/{user}")
    public ResponseEntity<String> getUserReviews(@PathVariable String sort, @PathVariable String user) {
        
        Optional<List<Review>> rResult = rSvc.aggregateUserReviews(sort, user);

        JsonObject result = Json.createObjectBuilder()
                                .add("rating", sort)
                                .add("games", rResult.get().stream().map(r -> r.toJsonP()).toList().toString())
                                .add("timestamp", LocalDateTime.now().toString())
                                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }
}
