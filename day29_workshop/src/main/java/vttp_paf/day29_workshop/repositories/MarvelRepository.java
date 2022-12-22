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
import vttp_paf.day29_workshop.models.Character;

@Repository
public class MarvelRepository {
    
    @Autowired
    @Qualifier("redis")
    private RedisTemplate<String, String> redisTemplate;

    public void cache(String prompt, List<Character> charList) {

        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        charList.stream()
            .forEach(c -> { 
                arrBuilder.add(c.toJson());
            });
        
        valueOps.set(prompt, arrBuilder.build().toString(), Duration.ofSeconds(300));
        System.out.println("saved char list for " + prompt);
    }

    public Optional<List<Character>> get(String prompt) {

        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();

        String payload = valueOps.get(prompt);
        if (null == payload)
            return Optional.empty();

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonArray results = reader.readArray();

        List<Character> heroes = results.stream()
                .map(v -> (JsonObject)v)
                .map(v -> Character.create(v))
                .toList();

        
        System.out.println("retrieved char list for " + prompt);
        return Optional.of(heroes);

    }
}
