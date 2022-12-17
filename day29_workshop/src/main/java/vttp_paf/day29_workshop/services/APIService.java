package vttp_paf.day29_workshop.services;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp_paf.day29_workshop.repositories.CharacterRepository;
import vttp_paf.day29_workshop.models.Character;

@Service
public class APIService {
    
    private static final String charsUrl = "https://gateway.marvel.com:443/v1/public/characters";
    private static final String categoriesUrl = "https://opentdb.com/api_category.php";

    @Value("${PUBLIC_KEY}")
    private String publicKey;

    @Value("${PRIVATE_KEY}")
    private String privateKey;
    
    @Autowired
    private CharacterRepository cRepo;

    // https://gateway.marvel.com:443/v1/public/characters?nameStartsWith=S&limit=20&offset=0&apikey=262e222ee851279182f366e2aa6b6d42
    public List<Character> getCharacters(String nameStartsWith) {

        // limit and offset parameters defaults to 20 and 0
        String payload;

        // Create url with query string (add parameters)
        String uri = UriComponentsBuilder.fromUriString(charsUrl)
        .queryParam("apikey", publicKey)
        .queryParam("limit", 20)
        .queryParam("offset", 0)
        // .queryParam("hash", )
        .toUriString();

        RequestEntity<Void> req = RequestEntity.get(uri).build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp;

        try {
            resp = template.exchange(req, String.class);
        } catch (Exception e) {
            System.err.printf("Error: %s\n", e);
            return Collections.emptyList();
        }

        if (resp.getStatusCode().value() != 200) {
            System.err.println("Error status code is not 200\n");
            return Collections.emptyList();
        }

        // Get payload 
        payload = resp.getBody();
        // System.out.println(">>> Payload: \n" + payload);
        
        // Convert payload into JsonObject
        // Convert string to a Reader
        Reader strReader = new StringReader(payload);
        // Create a JsonReader from reader
        JsonReader jsonReader = Json.createReader(strReader);
        // Read and save the payload as Json Object
        JsonObject jObject = jsonReader.readObject();

        JsonObject dataObject = jObject.getJsonObject("data");
        JsonArray characterArray = dataObject.getJsonArray("results");

        List<Character> list = new LinkedList<>();

        for (int i = 0; i < characterArray.size(); i++) {
            JsonObject jo = characterArray.getJsonObject(i);
            System.out.println(jo);
            list.add(Character.create(jo));
        }

        // save to redis 1h
        cRepo.saveCharacterList(nameStartsWith, list);
        return list;
    }

    // public Page<Trivia> listByPage(int pageNo) {
    //     List<Trivia> list = tRepo.getTrivia();
    //     int totalpages = list.size() / QUIZ_PER_PAGE;
    //     // PageRequest pg = new PageRequest(pageNo, pagesize);
    //     PageRequest pageable = PageRequest.of(pageNo, QUIZ_PER_PAGE);

    //     int max = pageNo >= totalpages ? list.size() : QUIZ_PER_PAGE*(pageNo+1);
    //     int min = pageNo > totalpages ? max : QUIZ_PER_PAGE*pageNo;

    //     Page<Trivia> pageResponse = new PageImpl<Trivia>(list.subList(min, max), pageable, list.size());

    //     return pageResponse;
    // }

    // // Get list of categories
    // public List<Category> getCategories() {

    //     String payload;
    //     String URI = UriComponentsBuilder.fromUriString(categoriesUrl)
    //             .toUriString();
    //     // Create the GET request, GET url
    //     RequestEntity<Void> request = RequestEntity.get(URI).build();

    //     RestTemplate template = new RestTemplate();
    //     ResponseEntity<String> response;

    //     try {
    //         response = template.exchange(request, String.class);
    //     } catch (Exception e) {
    //         System.err.printf("Error: %s\n", e);
    //         return Collections.emptyList();
    //     }

    //     if (response.getStatusCodeValue() != 200) {
    //         System.err.println("Error status code is not 200\n");
    //         return Collections.emptyList();
    //     }

    //     // Get payload 
    //     payload = response.getBody();
    //     // System.out.println(">>> Payload: \n" + payload);
        
    //     // Convert payload into JsonObject
    //     // Convert string to a Reader
    //     Reader strReader = new StringReader(payload);
    //     // Create a JsonReader from reader
    //     JsonReader jsonReader = Json.createReader(strReader);
    //     // Read and save the payload as Json Object
    //     JsonObject jObject = jsonReader.readObject();

    //     JsonArray triviaArray = jObject.getJsonArray("trivia_categories");

    //     List<Category> categoryList = new LinkedList<>();
    //     for (int i = 0; i < triviaArray.size(); i++) {

    //         JsonObject jo = triviaArray.getJsonObject(i);

    //         categoryList.add(Category.create(jo));
    //     }
    //     return categoryList;
    // }

    // public List<Trivia> getSavedTrivia() {
        
    //     return tRepo.getTrivia();
    // }
    
    // // Get list of correct answers
    // public List<String> getAnswers(List<Trivia> list) {

    //     return tRepo.getAnswers(list);
    // }


    // public int getScore(List<String> answers, String[] userInput) {

    //     int score = 0;

    //     for (int i = 0; i < answers.size(); i++) {
    //         if (answers.get(i).equals(userInput[i])) {
    //             score++;
    //         }
    //     }
    //     return score;
    // }


}
