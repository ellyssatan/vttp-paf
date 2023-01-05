package vttp_paf.day29_workshop.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Story {
    
    private int sid;
    private String title;
    private String description;

    public int getSid() {        return sid;        }
    public void setSid(int sid) {        this.sid = sid;        }

    public String getTitle() {        return title;        }
    public void setTitle(String title) {        this.title = title;        }

    public String getDescription() {        return description;        }
    public void setDescription(String description) {        this.description = description;        }

    @Override
    public String toString() {
        return "{sid=%d, title=%s, description=%s}"
                .formatted(sid, title, description);
    }

    public static Story create(JsonObject jo) {

        final Story sh = new Story();

        sh.setSid(jo.getInt("id"));
        System.out.println(">>>>>>>>>>>> HERE " + jo.getInt("id"));
        sh.setTitle(jo.getString("title"));
        sh.setDescription(jo.getString("description").trim().length() > 0 ? 
                jo.getString("description"): "No description");

        return sh;
    }

    // Create Model from JsonObject
    public static Story createFromCache(JsonObject jo) {

        final Story s = new Story();
        s.setSid(jo.getInt("sid"));
        s.setTitle(jo.getString("title"));
        s.setDescription(jo.getString("description"));

        return s;
    }

    // Convert model to JsonObject
    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("sid", sid)
            .add("title", title)
            .add("description", description)
            .build();
    }

}
