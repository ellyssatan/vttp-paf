package vttp_paf.day29_workshop.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Comment {

    private int charId;
    private String user;
    private String comment;

    public int getCharId() {        return charId;        }
    public void setCharId(int charId) {        this.charId = charId;        }

    public String getUser() {        return user;        }
    public void setUser(String user) {        this.user = user;        }

    public String getComment() {        return comment;        }
    public void setComment(String comment) {        this.comment = comment;        }

    public Comment() {}
    public Comment(int charId, String user, String comment) {
        this.charId = charId;
        this.user = user;
        this.comment = comment;
    }

    // Create Model from JsonObject
    public static Comment create(JsonObject jo) {

        final Comment c = new Comment();
        c.setCharId(jo.getInt("charId"));
        c.setUser(jo.getString("user"));
        c.setComment(jo.getString("comment"));

        return c;
    }

    // Convert model to JsonObject
    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("charId", this.getCharId())
            .add("user", this.getUser())
            .add("comment", this.getComment())
            .build();
    }
}
