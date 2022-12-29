package vttp_paf.day29_workshop.repositories;

import java.io.StringReader;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

@Repository
public class MarvelRepository {
    
    @Autowired @Qualifier(AppConfig.CACHE_MARVEL)
    private RedisTemplate<String, String> rTemplate;

    public void cache(String prompt, List<Character> charList) {

        ValueOperations<String, String> valueOps = rTemplate.opsForValue();

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        charList.stream()
            .forEach(c -> { 
                arrBuilder.add(c.toJson());
            });
        
        valueOps.set(prompt, arrBuilder.build().toString(), Duration.ofSeconds(3000));
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
        
        valueOps.set(charId, c.toString(), Duration.ofSeconds(3000));
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
        Character c = Character.create(payload);

        
        System.out.printf("\n>>> retrieved char info for %s\n", charId);
        return Optional.of(c);

    }
}
