package vttp_paf.day29_workshop.repositories;

import java.io.StringReader;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.StringOperators.Concat;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp_paf.day29_workshop.AppConfig;
import vttp_paf.day29_workshop.models.Character;
import vttp_paf.day29_workshop.models.Comment;
import vttp_paf.day29_workshop.models.Story;

@Repository
public class MarvelRepository {

    private static final String C_COMMENT = "comments";

    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    @Qualifier(AppConfig.CACHE_MARVEL)
    private RedisTemplate<String, String> rTemplate;

    public void cache(String prompt, List<Character> charList) {

        ValueOperations<String, String> valueOps = rTemplate.opsForValue();

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        charList.stream()
            .forEach(c -> { 
                arrBuilder.add(c.toJson());
            });
        
        valueOps.set(prompt, arrBuilder.build().toString(), Duration.ofSeconds(120));
        System.out.println(">>> saved char list for " + prompt);
    }

    public Optional<List<Character>> get(String prompt) {

        ValueOperations<String, String> valueOps = rTemplate.opsForValue();

        String payload = valueOps.get(prompt);
        if (null == payload) {
            System.out.println(">>> no saved record in redis for " + prompt);
            return Optional.empty();
        }
        
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonArray results = reader.readArray();

        List<Character> heroes = results.stream()
                .map(v -> (JsonObject)v)
                .map(v -> Character.createFromCache(v))
                .toList();

        
        System.out.println(">>> retrieved char list for " + prompt);
        return Optional.of(heroes);

    }

    public void cacheChar(String charId, Character c) {

        ValueOperations<String, String> valueOps = rTemplate.opsForValue();
        
        valueOps.set(charId, c.toJson().toString(), Duration.ofSeconds(120));
        System.out.printf("\n>>> saved char info for %s\n", charId);
        System.out.printf("\n>>> saved %s\n", c.toString());
    }

    public Optional<Character> getByCharId(String charId) {

        ValueOperations<String, String> valueOps = rTemplate.opsForValue();

        String payload = valueOps.get(charId);

        if (null == payload) {
            System.out.printf("\n>>> no saved record in redis for %s\n", charId);
            return Optional.empty();
        }

        System.out.println(">>> converting to char...");

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject results = reader.readObject();

        Character c = Character.createFromCache(results);

        System.out.printf("\n>>> retrieved char info for %s\n", charId);
        return Optional.of(c);

    }

    /*
     * db.comments.insert({
            charId: 992820,
            user: "bob",
            comment: "still ok"
        })
     */
    public Comment insertComment(Comment c) {
        return mongoTemplate.insert(c, C_COMMENT);
    }

    public List<Comment> getComments(int charId) {
        
        Criteria c = Criteria.where("charId").is(charId);
        Query q = Query.query(c);

        return mongoTemplate.find(q, Document.class, C_COMMENT)
            .stream()
            .map(d -> Comment.create(d))
            .toList();
    
    }

    // stories
    public void cacheStories(String prompt, List<Story> storyList) {

        ValueOperations<String, String> valueOps = rTemplate.opsForValue();

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        storyList.stream()
            .forEach(s -> { 
                arrBuilder.add(s.toJson());
            });
        String key = prompt.concat("Story");
        valueOps.set(key, arrBuilder.build().toString(), Duration.ofSeconds(120));
        System.out.println(">>> saved Story list for " + key);
    }

    public Optional<List<Story>> getStories(String prompt) {

        ValueOperations<String, String> valueOps = rTemplate.opsForValue();

        String key = prompt.concat("Story");
        String payload = valueOps.get(key);
        if (null == payload) {
            System.out.println(">>> no saved record in redis for " + key);
            return Optional.empty();
        }
        
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonArray results = reader.readArray();

        List<Story> stories = results.stream()
                .map(s -> (JsonObject)s)
                .map(s -> Story.createFromCache(s))
                .toList();

        
        System.out.println(">>> retrieved stories list for " + key);
        return Optional.of(stories);

    }
}
