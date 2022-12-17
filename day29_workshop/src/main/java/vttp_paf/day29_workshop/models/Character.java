package vttp_paf.day29_workshop.models;

import java.io.StringReader;
import java.time.LocalDateTime;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;
import jakarta.json.stream.JsonParser;

public class Character {
    
    private int id;
    private String name;
    private String description;
    private String resourceURI;
    private LocalDateTime modified;

    public int getId() {        return id;        }
    public void setId(int id) {        this.id = id;        }

    public String getName() {        return name;        }
    public void setName(String name) {        this.name = name;        }

    public String getDescription() {        return description;        }
    public void setDescription(String description) {        this.description = description;        }

    public String getResourceURI() {        return resourceURI;        }
    public void setResourceURI(String resourceURI) {        this.resourceURI = resourceURI;        }
    
    public LocalDateTime getModified() {        return modified;        }
    public void setModified(LocalDateTime modified) {        this.modified = modified;        }

    // Create Model from JsonObject
    public static Character create(JsonObject jo) {
        Character c = new Character();
        c.setId(jo.getInt("id"));
        c.setName(jo.getString("name"));
        c.setDescription(jo.getString("description"));
        c.setResourceURI(jo.getString("resourceURI"));

        String dateStr = jo.getString("modified");
        LocalDateTime date = LocalDateTime.parse(dateStr);
        c.setModified(date);

        return c;
    }

    // Convert model to JsonObject
    public static JsonObject toJson(Character c) {
        return Json.createObjectBuilder()
            .add("id", c.id)
            .add("name", c.name)
            .add("description", c.description)
            .add("resourceURI", c.resourceURI)
            .add("modified", c.modified.toString())
            .build();
    }

    public static Character toJson(String jsonStr) {

        StringReader reader = new StringReader(jsonStr);
		JsonReader r = Json.createReader(reader);
		return create(r.readObject());

    }
}
