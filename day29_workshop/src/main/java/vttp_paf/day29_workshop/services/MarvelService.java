package vttp_paf.day29_workshop.services;

import java.io.StringReader;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp_paf.day29_workshop.repositories.MarvelRepository;
import vttp_paf.day29_workshop.models.Character;
import vttp_paf.day29_workshop.models.Story;

@Service
public class MarvelService {
    
    private static final String charsUrl = "https://gateway.marvel.com:443/v1/public/characters";
    public static final String ATTRIBUTION = "Data provided by Marvel. © 2022 MARVEL";

    @Value("${PUBLIC_KEY}")
    private String publicKey;

    @Value("${PRIVATE_KEY}")
    private String privateKey;
    
    @Autowired
    private MarvelRepository marvelRepo;

    public List<Character> search(String nameStartsWith) {
        return search(nameStartsWith, 10);
    }

    // https://gateway.marvel.com:443/v1/public/characters?nameStartsWith=??? limit=10 ts=??? apikey=??? hash=???
    public List<Character> search(String nameStartsWith, int limit) {

        // Timestamp
        Long ts = System.currentTimeMillis();
        String signature = "%d%s%s".formatted(ts, privateKey, publicKey);
        String hash = "";

        try {

            // Message digest = md5, sha1, sha512
            // Get an instance of MD5
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            // Calculate our hash
            // Update our message digest
            md5.update(signature.getBytes());

            // Get the MD5 digest
            byte[] h = md5.digest();

            // Stringify the MD5 digest hex
            hash = HexFormat.of().formatHex(h);

        } catch (Exception e) {}

        // Create url with query string (add parameters)
        String uri = UriComponentsBuilder.fromUriString(charsUrl)
            .queryParam("nameStartsWith", nameStartsWith)
            .queryParam("limit", limit)
            .queryParam("ts", ts)
            .queryParam("apikey", publicKey)
            .queryParam("hash", hash)
            .toUriString();

        // System.out.printf("uri = %s\n", uri);

        RequestEntity<Void> req = RequestEntity.get(uri)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        // Get payload 
        String payload = resp.getBody();
        // System.out.println(">>> Payload: \n" + payload);

        // Convert payload into JsonObject
        // Create a JsonReader
        JsonReader jsonReader = Json.createReader(new StringReader(payload));
        // Read and save the payload as Json Object
        JsonObject jObject = jsonReader.readObject();

        JsonObject dataObject = jObject.getJsonObject("data");
        JsonArray characterArray = dataObject.getJsonArray("results");

        List<Character> list = new LinkedList<>();

        for (int i = 0; i < characterArray.size(); i++) {
            list.add(Character.create(characterArray.getJsonObject(i)));
        }

        // save to redis
        marvelRepo.cache(nameStartsWith, list);
        return list;
        
        // stream method
        // return characterArray.stream()
        //     .map(v -> (JsonObject)v)
        //     .map(jo -> Character.create(jo))
        //     .toList();
        
    }

    // https://gateway.marvel.com:443/v1/public/characters/<char id>? ts=??? apikey=??? hash=???
    public Character getByCharId(int charId) {

        // Timestamp
        Long ts = System.currentTimeMillis();
        String signature = "%d%s%s".formatted(ts, privateKey, publicKey);
        String hash = "";

        try {

            // Message digest = md5, sha1, sha512
            // Get an instance of MD5
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            // Calculate our hash
            // Update our message digest
            md5.update(signature.getBytes());

            // Get the MD5 digest
            byte[] h = md5.digest();

            // Stringify the MD5 digest hex
            hash = HexFormat.of().formatHex(h);

        } catch (Exception e) {}

        // Create url with query string (add parameters)
        String uri = UriComponentsBuilder.fromUriString(charsUrl)
            .path("/").path(Integer.toString(charId))
            .queryParam("ts", ts)
            .queryParam("apikey", publicKey)
            .queryParam("hash", hash)
            .toUriString();

        // System.out.printf("uri = %s\n", uri);

        RequestEntity<Void> req = RequestEntity.get(uri)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        // Get payload 
        String payload = resp.getBody();
        // System.out.println(">>> Payload: \n" + payload);

        // Convert payload into JsonObject
        // Create a JsonReader
        JsonReader jsonReader = Json.createReader(new StringReader(payload));
        // Read and save the payload as Json Object
        JsonObject jObject = jsonReader.readObject();

        JsonObject dataObject = jObject.getJsonObject("data");
        JsonArray characterArray = dataObject.getJsonArray("results");

        Character c =  Character.create(characterArray.getJsonObject(0));

        // save to redis
        marvelRepo.cacheChar(Integer.toString(charId), c);

        return c;
        
    }

    // https://gateway.marvel.com:443/v1/public/characters/<char id>/stories? ts=??? apikey=??? hash=???
    public List<Story> getStoriesByCharId(int charId) {

        // Timestamp
        Long ts = System.currentTimeMillis();
        String signature = "%d%s%s".formatted(ts, privateKey, publicKey);
        String hash = "";

        try {

            // Message digest = md5, sha1, sha512
            // Get an instance of MD5
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            // Calculate our hash
            // Update our message digest
            md5.update(signature.getBytes());

            // Get the MD5 digest
            byte[] h = md5.digest();

            // Stringify the MD5 digest hex
            hash = HexFormat.of().formatHex(h);

        } catch (Exception e) {}

        // Create url with query string (add parameters)
        String uri = UriComponentsBuilder.fromUriString(charsUrl)
            .path("/").path(Integer.toString(charId))
            .path("/").path("stories")
            .queryParam("ts", ts)
            .queryParam("apikey", publicKey)
            .queryParam("hash", hash)
            .toUriString();

        // System.out.printf("uri = %s\n", uri);

        RequestEntity<Void> req = RequestEntity.get(uri)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        // Get payload 
        String payload = resp.getBody();
        System.out.println(">>> Payload: \n" + payload);

        // Convert payload into JsonObject
        // Create a JsonReader
        JsonReader jsonReader = Json.createReader(new StringReader(payload));
        // Read and save the payload as Json Object
        JsonObject jObject = jsonReader.readObject();

        JsonObject dataObject = jObject.getJsonObject("data");
        JsonArray storiesArray = dataObject.getJsonArray("results");

        List<Story> stories = storiesArray.stream()
                .map(v -> (JsonObject)v)
                .map(jo -> Story.create(jo))
                .toList(); 

        // save to redis
        marvelRepo.cacheStories(Integer.toString(charId), stories);
        
        return stories;
        
    }
}