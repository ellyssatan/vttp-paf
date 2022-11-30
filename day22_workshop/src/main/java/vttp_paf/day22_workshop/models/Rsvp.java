package vttp_paf.day22_workshop.models;

import java.io.ByteArrayInputStream;
import java.time.Instant;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Rsvp {

    private int id;
    private String name;
    private String email;
    private int phone;
    private DateTime confirmation_date;
    private String comments;

    public int getId() {       return id;        }
    public int setId(int id) {       return this.id = id;        }

    public String getName() {       return name;        }
    public void setName(String name) {       this.name = name;        }

    public String getEmail() {        return email;        }
    public void setEmail(String email) {        this.email = email;        }

    public int getPhone() {        return phone;        }
    public void setPhone(int phone) {        this.phone = phone;        }

    public DateTime getConfirmation_date() {        return confirmation_date;        }
    public void setConfirmation_date(DateTime confirmation_date) {        this.confirmation_date = confirmation_date;        }
    
    public String getComments() {        return comments;        }
    public void setComments(String comments) {        this.comments = comments;        }

    public static Rsvp create(String jsonStr) throws Exception {

        JsonReader reader = Json.createReader(
                new ByteArrayInputStream(jsonStr.getBytes()));
        return create(reader.readObject());
    }

    public static Rsvp create(String name, String email, int phone, DateTime confirmation_date, String comments) {

        final Rsvp r = new Rsvp();
        
        r.setName(name);
        r.setEmail(email);
        r.setPhone(phone);
        r.setConfirmation_date(new DateTime(confirmation_date));
        r.setComments(comments);

        return r;
    }

    public static Rsvp create(JsonObject jo) {

        final Rsvp r = new Rsvp();
        
        r.setId(jo.getInt("id"));
        r.setName(jo.getString("name"));
        r.setEmail(jo.getString("email"));
        r.setPhone(jo.getInt("phone"));
        r.setConfirmation_date(new DateTime(Instant.parse(jo.getString("confirmation_date"))));
        r.setComments(jo.getString("comments"));

        return r;
    }

    public static Rsvp create(SqlRowSet rs) {

        final Rsvp r = new Rsvp();
        
        r.setId(rs.getInt("id"));
        r.setName(rs.getString("name"));
        r.setEmail(rs.getString("email"));
        r.setPhone(rs.getInt("phone"));
        r.setConfirmation_date(new DateTime(
                            DateTimeFormat.forPattern("dd/MM/yyyy")
                            .parseDateTime(rs.getString("confirmation_date"))));
        r.setComments(rs.getString("comments"));

        return r;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
            .add("name", getName())
            .add("email", getEmail())
            .add("phone", getPhone())
            .add("confirmation_date", getConfirmation_date() != null? getConfirmation_date().toString() : "")
                            // check                if true         if false
            .add("comments", getComments() != null? getComments() : "")
            .build();
    }
}
