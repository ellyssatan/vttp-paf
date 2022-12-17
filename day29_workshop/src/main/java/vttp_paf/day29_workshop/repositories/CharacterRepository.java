package vttp_paf.day29_workshop.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;
import vttp_paf.day29_workshop.models.Character;

@Repository
public class CharacterRepository {
    
    @Autowired
    @Qualifier("redis")
    private RedisTemplate<String, String> rTemplate;

    public void saveCharacterList(String prompt, List<Character> charList) {

        ValueOperations<String, String> valueOps = rTemplate.opsForValue();

        List<JsonObject> chars = new LinkedList<>();

        for (Character c : charList) {
            chars.add(Character.toJson(c));
        }

        valueOps.set(prompt, chars.toString());
        System.out.println("saved char list for " + prompt);
    }

    // how to convert jsonstr to list<chars>
    public List<Character> getCharacterList(String prompt) {

        ValueOperations<String, String> valueOps = rTemplate.opsForValue();

        String payload = valueOps.get(prompt);
        Character c = Character.toJson(payload);
        List<Character> charList = new LinkedList<>();
        System.out.println("retrieved char list for " + prompt);

        return charList;
    }
}
