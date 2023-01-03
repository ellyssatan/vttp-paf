package vttp_paf.day29_workshop.models;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Character {
    
    private int id;
    private String name;
    private String description;
    private String image;
    private String details;

    public int getId() {        return id;        }
    public void setId(int id) {        this.id = id;        }

    public String getName() {        return name;        }
    public void setName(String name) {        this.name = name;        }

    public String getDescription() {        return description;        }
    public void setDescription(String description) {        this.description = description;        }

    public String getImage() {        return image;        }
    public void setImage(String image) {        this.image = image;        }

    public String getDetails() {        return details;        }
    public void setDetails(String details) {        this.details = details;        }

    @Override
    public String toString() {
        return "{id=%d, name=%s, description=%s, image=%s, details=%s}"
                .formatted(id, name, description, image, details);
    }

    public static Character create(JsonObject jo) {

        final Character sh = new Character();

        sh.setId(jo.getInt("id"));
        sh.setName(jo.getString("name"));
        sh.setDescription(jo.getString("description").trim().length() > 0 ? 
                jo.getString("description"): "No description");

        JsonObject img = jo.getJsonObject("thumbnail");
        sh.setImage("%s.%s".formatted(img.getString("path"), img.getString("extension")));

        JsonArray urls = jo.getJsonArray("urls");

        for (int i = 0; i < urls.size(); i++) {

            JsonObject d = urls.getJsonObject(i);

            if (d.getString("type").equals("detail")) {
                sh.setDetails(d.getString("url"));
                break;
            }
        }

        return sh;
    }

    // Create Model from JsonObject
    public static Character createFromCache(JsonObject jo) {

        final Character c = new Character();
        c.setId(jo.getInt("id"));
        c.setName(jo.getString("name"));
        c.setDescription(jo.getString("description"));
        c.setImage(jo.getString("image"));
        c.setDetails(jo.getString("details"));

        return c;
    }

    // public static Character create(String jsonStr) {

    //     StringReader reader = new StringReader(jsonStr);
    //     JsonReader r = Json.createReader(reader);
    //     return create(r.readObject());
    // }

    // Convert model to JsonObject
    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("id", id)
            .add("name", name)
            .add("description", description)
            .add("image", image)
            .add("details", details)
            .build();
    }
    

    
}
