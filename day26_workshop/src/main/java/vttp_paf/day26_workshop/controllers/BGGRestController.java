package vttp_paf.day26_workshop.controllers;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp_paf.day26_workshop.models.Game;
import vttp_paf.day26_workshop.models.Games;
import vttp_paf.day26_workshop.services.SearchBGGService;

@RestController
@RequestMapping("/api/bgg")
public class BGGRestController {
    
    @Autowired
    private SearchBGGService sSvc;

    @GetMapping("/games")
    public ResponseEntity<String> getAllGames(@RequestParam int limit, @RequestParam int offset) {

        List<Game> gameResult = sSvc.getAllGames(limit, offset);

        JsonObject result = null;
        JsonObjectBuilder joBuilder = Json.createObjectBuilder();

        Games gs = new Games();
        gs.setGames(gameResult);
        gs.setLimit(limit);
        gs.setOffset(offset);
        gs.setTotal(gameResult.size());
        gs.setTimestamp(DateTime.now());
        joBuilder.add("bgg", gs.toJson());

        result = joBuilder.build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @GetMapping("/games/rank")
    public ResponseEntity<String> getGamesByRank() {

        List<Game> gamesByRank = sSvc.getGamesByRank();

        JsonArray result = null;
        JsonArrayBuilder joBuilder = Json.createArrayBuilder();

        for (Game g : gamesByRank) {
            joBuilder.add(g.toJson());
        }
        result = joBuilder.build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<String> getGameDetailsById(@PathVariable int gameId) {

        Game gameById = sSvc.getGameDetailsById(gameId);

        JsonObject result = Json.createObjectBuilder()
                            .add("game", gameById.toJson())
                            .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }
}
