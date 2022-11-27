package vttp_paf.day23.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class CacheRepository {
    
    @Autowired
    @Qualifier("redis")
    private RedisTemplate<String, String> redisTemplate;

    public void saveBeerList(int id, List<String> list) {

        String key = Integer.toString(id);
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        System.out.println("Saved in redis");

        valueOps.set(key, list.toString(), 20, TimeUnit.SECONDS);
    }

    public Optional<List<String>> getBeerList(int id) {

        System.out.println("Entered getBeerList redis");

        String key = Integer.toString(id);
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();

        if (redisTemplate.hasKey(key)) {

            System.out.println("took from redis");
            String result = valueOps.get(key);

            List<String> BeerList = new LinkedList<>();
            result = result.replaceAll("\\[", "").replaceAll("\\]", "").trim();

            String[] beerBreweries = result.split(",");
            System.out.println(">>>>Beer list: " + beerBreweries.toString());

            for (String s: beerBreweries) {

                BeerList.add(s);
            }
            
            return Optional.of(BeerList);
        }

        return Optional.empty();
    }    
}
