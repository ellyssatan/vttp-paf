package vttp_paf.day28_workshop.models;

import java.time.LocalDateTime;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class EditedComment {
    
    private int rating;
    private String comment;
    private LocalDateTime posted;

    public int getRating() {        return rating;        }
    public void setRating(int rating) {        this.rating = rating;        }

    public String getComment() {        return comment;        }
    public void setComment(String comment) {        this.comment = comment;        }

    public LocalDateTime getPosted() {        return posted;        }
    public void setPosted(LocalDateTime posted) {        this.posted = posted;        }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("rating", this.getRating())
                .add("comment", this.getComment())
                .add("date", this.getPosted() != null ? this.getPosted().toString(): "")
                .build();
    }
    
}
