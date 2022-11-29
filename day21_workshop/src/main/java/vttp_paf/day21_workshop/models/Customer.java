package vttp_paf.day21_workshop.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Customer {

    private String id;
    private String name;

    public String getId() {     return id;      }
    public void setId(String id) {      this.id = id;       }

    public String getName() {       return name;        }
    public void setName(String name) {      this.name = name;       }

    public static Customer create(SqlRowSet rs) {
        Customer c = new Customer();

        c.setId(rs.getString("id"));
        c.setName(rs.getString("name"));

        return c;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
            .add("id", getId())
            .add("name", getName())
            .build();
    }
}
