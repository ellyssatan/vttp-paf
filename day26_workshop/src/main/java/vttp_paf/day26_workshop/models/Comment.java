package vttp_paf.day26_workshop.models;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Comment {

    private String _id;
    private String c_id;
    private String user;
    private int rating;
    private String c_text;
    private int gid;

    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public String getC_id() {
        return c_id;
    }
    public void setC_id(String c_id) {
        this.c_id = c_id;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getC_text() {
        return c_text;
    }
    public void setC_text(String c_text) {
        this.c_text = c_text;
    }
    public int getGid() {
        return gid;
    }
    public void setGid(int gid) {
        this.gid = gid;
    }

    public static Comment create(Document doc) {

        Comment c = new Comment();
        c.set_id(doc.getObjectId("_id").toString());
        c.setC_id(doc.getString("c_id"));
        c.setUser(doc.getString("user"));
        c.setRating(doc.getInteger("rating"));
        c.setC_text(doc.getString("c_text"));
        c.setGid(doc.getInteger("gid"));

        return c;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("_id", this.get_id())
            .add("c_id", this.getC_id())
            .add("user", this.getUser())
            .add("rating", this.getRating())
            .add("c_text", this.getC_text())
            .add("gid", this.getGid())
            .build();
    }
}
