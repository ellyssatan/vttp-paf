package vttp_paf.day26_workshop.models;

import java.util.List;

import org.joda.time.DateTime;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Games {
    
    private List<Game> games;
    private int limit;
    private int offset;
    private int total;
    private DateTime timestamp;

    public List<Game> getGames() {
        return games;
    }
    public void setGames(List<Game> games) {
        this.games = games;
    }
    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }
    public int getOffset() {
        return offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public DateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("games", this.getGames().stream().map(g -> g.toJson()).toList().toString())
            .add("limit", this.getLimit())
            .add("offset", this.getOffset())
            .add("total", this.getTotal())
            .add("timestamp", this.getTimestamp().toString())
            .build();
    }
}
