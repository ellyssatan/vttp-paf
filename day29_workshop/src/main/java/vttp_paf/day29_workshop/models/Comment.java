package vttp_paf.day29_workshop.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.bson.Document;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Comment {

    private int charId;
    private String user;
    private String comment;
    private LocalDateTime timestamp;

    public int getCharId() {        return charId;        }
    public void setCharId(int charId) {        this.charId = charId;        }

    public String getUser() {        return user;        }
    public void setUser(String user) {        this.user = user;        }

    public String getComment() {        return comment;        }
    public void setComment(String comment) {        this.comment = comment;        }

    public LocalDateTime getTimestamp() {        return timestamp;        }
    public void setTimestamp(LocalDateTime timestamp) {        this.timestamp = timestamp;        }

    public Comment() {}
    public Comment(int charId, String user, String comment) {
        this.charId = charId;
        this.user = user;
        this.comment = comment;
        this.timestamp = LocalDateTime.now();
    }

    // Create Model from JsonObject
    public static Comment create(JsonObject jo) {

        final Comment c = new Comment();
        c.setCharId(jo.getInt("charId"));
        c.setUser(jo.getString("user"));
        c.setComment(jo.getString("comment"));
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
        // c.setTimestamp(LocalDateTime.parse(jo.getString("timestamp"), formatter));
        // c.setTimestamp(LocalDateTime.parse(jo.getString("timestamp")));

        return c;
    }

    public static Comment create(Document doc) {

        final Comment c = new Comment();

        c.setCharId(doc.getInteger("charId"));
        c.setUser(doc.getString("user"));
        c.setComment(doc.getString("comment"));
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
        // c.setTimestamp(LocalDateTime.parse(doc.getDate("timestamp").toString()));

        return c;
    }

    // Convert model to JsonObject
    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("charId", this.getCharId())
            .add("user", this.getUser())
            .add("comment", this.getComment())
            // .add("timestamp", this.getTimestamp().toString())
            .build();
    }
}
