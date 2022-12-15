package vttp_paf.day28_workshop.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Review extends EditedComment {
    
    private String _id;
    private String user;
    private int gid;
    private String name;
    private List<EditedComment> edited;
    private boolean is_edited;
    // private String review_id;

    public Review() {

    }

    public Review(String user, int rating, String comment, int gid, String name) {
        // this._id = _id;
        this.user = user;
        super.setRating(rating);
        super.setComment(comment);
        this.gid = gid;
        this.name = name;
        super.setPosted(LocalDateTime.now());
        this.is_edited=false;
        this.edited= new LinkedList<>();
        // this.review_id= review_id;
    }

    public String get_id() {        return _id;        }
    public void set_id(String _id) {        this._id = _id;        }

    public String getUser() {        return user;        }
    public void setUser(String user) {        this.user = user;        }

    public int getGid() {        return gid;        }
    public void setGid(int gid) {        this.gid = gid;        }

    public String getName() {        return name;        }
    public void setName(String name) {        this.name = name;        }

    public List<EditedComment> getEdited() {        return edited;        }
    public void setEdited(List<EditedComment> edited) {        this.edited = edited;        }

    public boolean isIs_edited() {        return is_edited;        }
    public void setIs_edited(boolean is_edited) {        this.is_edited = is_edited;        }

    // public static Review create(Document d) {

    //     Review r = new Review();

    //     r.set_id(d.getObjectId("_id").toString());
    //     r.setUser(d.getString("user"));
    //     r.setRating(d.getInteger("rating"));
    //     r.setComment(d.getString("comment"));
    //     r.setGid(d.getInteger("gid"));
    //     LocalDateTime date = Instant.ofEpochMilli(d.getDate("posted").getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    //     r.setPosted(date);
    //     r.setName(d.getString("name"));

    //     return r;
    // }

    public JsonObject toJson() {
        List<JsonObject> editedArray = this.getEdited()
                                .stream().map(c -> c.toJson()).toList();
        return Json.createObjectBuilder()
                .add("_id", this.get_id())
                .add("user", this.getUser())
                .add("rating", this.getRating())
                .add("comment", this.getComment())
                .add("gid", this.getGid())
                .add("posted", this.getPosted().toString())
                .add("name", this.getName())
                .add("edited", editedArray.toString())
                .add("is_edited", editedArray.size() > 1 ? true : false)
                .build();
    }

    // always refer to PROJECTED DOCUMENT NAMING SCHEMA
    public static Review createProject(Document d) {

        Review r = new Review();
        System.out.println(">> createProject " + d);
        r.set_id(d.getObjectId("review_id").toString());
        r.setName(d.getString("name"));
        r.setRating(d.getInteger("rating"));
        r.setUser(d.getString("user"));
        r.setComment(d.getString("comment"));
        r.setGid(d.getInteger("_id"));
        // System.out.println(">>>>>> " + r.getReview_id());
        return r;
    }

    public JsonObject toJsonP() {
        return Json.createObjectBuilder()
                .add("_id", this.getGid())
                .add("name", this.getName())
                .add("rating", this.getRating())
                .add("user", this.getUser())
                .add("comment", this.getComment())
                .add("review_id", this.get_id())
                .build();
    }
}
