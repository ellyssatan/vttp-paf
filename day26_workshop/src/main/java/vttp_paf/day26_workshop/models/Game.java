package vttp_paf.day26_workshop.models;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Game {
    
    private String _id;
    private int gid;
    private String name;
    private int year;
    private int ranking;
    private int users_rated;
    private String url;
    private String image;

    public String get_id() {        return _id;        }
    public void set_id(String _id) {        this._id = _id;        }

    public int getGid() {        return gid;        }
    public void setGid(int gid) {        this.gid = gid;        }

    public String getName() {        return name;        }
    public void setName(String name) {        this.name = name;        }

    public int getYear() {        return year;        }
    public void setYear(int year) {        this.year = year;        }

    public int getRanking() {        return ranking;        }
    public void setRanking(int ranking) {        this.ranking = ranking;        }

    public int getUsers_rated() {        return users_rated;        }
    public void setUsers_rated(int users_rated) {        this.users_rated = users_rated;        }

    public String getUrl() {        return url;        }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public static Game create(Document doc) {

        Game g = new Game();
        g.set_id(doc.getObjectId("_id").toString());
        g.setGid(doc.getInteger("gid"));
        g.setName(doc.getString("name"));
        g.setYear(doc.getInteger("year"));
        g.setRanking(doc.getInteger("ranking"));
        g.setUsers_rated(doc.getInteger("users_rated"));
        g.setUrl(doc.getString("url"));
        g.setImage(doc.getString("image"));

        return g;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("_id", this.get_id())
            .add("gid", this.getGid())
            .add("name", this.getName())
            .add("year", this.getYear())
            .add("ranking", this.getRanking())
            .add("users_rated", this.getUsers_rated())
            .add("url", this.getUrl())
            .add("image", this.getImage())
            .build();
    }
    
}
