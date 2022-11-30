package vttp_paf.day22_workshop.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp_paf.day22_workshop.models.Rsvp;
import vttp_paf.day22_workshop.repositories.RsvpRepository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class RSVPRestController {

    @Autowired
    private RsvpRepository rRepo;
    
    @GetMapping(path="/rsvps", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllRsvps() {

        List<Rsvp> allRsvp = rRepo.getAllRsvps();

        if (allRsvp.size() <= 0) {
            return ResponseEntity
                    .status(404)
                    .body(Json.createObjectBuilder()
                    .add("error", "No RSVP records found") .build().toString());
        }

        // Build the result
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (Rsvp r : allRsvp)
            arrBuilder.add(r.toJSON());
        JsonArray result = arrBuilder.build();

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }
    

    @GetMapping(path = "/rsvp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> searchRsvp(@RequestParam String q) {

        List<Rsvp> allRsvp = rRepo.getRsvpByName("%" + q + "%");

        if (allRsvp.size()  <= 0) {
            return ResponseEntity
                    .status(404)
                    .body(Json.createObjectBuilder()
                    .add("error", "No RSVP records found for " + q) .build().toString());
        }

        // Build the result
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (Rsvp r : allRsvp)
            arrBuilder.add(r.toJSON());
        JsonArray result = arrBuilder.build();

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());

    }

    @PostMapping(path = "/rsvp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertRsvp(@RequestBody MultiValueMap <String, String> form) {

        String name = form.getFirst("name");
        String email = form.getFirst("email");
        int phone = Integer.parseInt(form.getFirst("phone"));
        DateTime confirmation_date = DateTime.now();
        String comments = form.getFirst("comments");

        Rsvp r = rRepo.insertRsvp(Rsvp.create(name, email, phone, confirmation_date, comments));

        JsonObject result = r.toJSON();

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }

    @PostMapping(path = "/rsvp/updated", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateRsvp(@RequestBody MultiValueMap<String, String> form) {

        String name = form.getFirst("name");
        String email = form.getFirst("email");
        int phone = Integer.parseInt(form.getFirst("phone"));
        DateTime confirmation_date = new DateTime (form.getFirst("date"));
        String comments = form.getFirst("comments");

        Rsvp r = Rsvp.create(name, email, phone, confirmation_date, comments);

        boolean ans = rRepo.updateRsvp(r, email);

        if (ans) {

            JsonObject result = r.toJSON();

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
        }

        return ResponseEntity
                    .status(404)
                    .body(Json.createObjectBuilder()
                    .add("error", "RSVP record not updated " + email) .build().toString());
        
    }

    @RequestMapping(path = "/rsvps/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> rsvpCount() {

        int total = rRepo.countRsvp();

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Json.createObjectBuilder()
            .add("Total count", total).build().toString());

    }
}