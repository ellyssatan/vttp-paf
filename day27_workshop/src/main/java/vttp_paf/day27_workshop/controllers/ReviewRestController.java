package vttp_paf.day27_workshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp_paf.day27_workshop.models.EditedComment;
import vttp_paf.day27_workshop.models.Review;
import vttp_paf.day27_workshop.services.ReviewService;

@RestController
@RequestMapping("/api/review")
public class ReviewRestController {
    
    @Autowired
    private ReviewService rSvc;

    @PutMapping(path = "{_id}")
    public ResponseEntity<String> addReview(@PathVariable String _id, @RequestBody EditedComment e) {

        Review upsertR = rSvc.upsertEdited(_id, e);
        System.out.println(">>> " + upsertR);
        JsonObject result = Json.createObjectBuilder()
                                .add("review", upsertR.toJson())
                                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @GetMapping(path = "{_id}")
    public ResponseEntity<String> searchReview(@PathVariable String _id) {

        Review search = rSvc.findReview(_id);
        System.out.println(">>> " + search);
        JsonObject result = Json.createObjectBuilder()
                                .add("review", search.toJson())
                                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @GetMapping(path = "{_id}/history")
    public ResponseEntity<String> searchReviewHistory(@PathVariable String _id) {

        Review search = rSvc.findReview(_id);
        System.out.println(">>> " + search);
        JsonObject result = Json.createObjectBuilder()
                                .add("review", search.toJson())
                                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }
}
