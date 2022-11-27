package vttp_paf.day23.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp_paf.day23.models.BeerStyle;
import vttp_paf.day23.repositories.BeerRepository;
import vttp_paf.day23.repositories.CacheRepository;

@Service
public class BeerService {

    @Autowired
    private BeerRepository beerRepo;

    @Autowired
    private CacheRepository cacheRepo;

    public List<BeerStyle> getStyles() throws Exception {
        return beerRepo.getStyles();
    }

    public List<String> getBreweries(int id) throws Exception {
        return beerRepo.getBreweries(id);
    }

    public void saveBeerList(int id, List<String> list) {
        cacheRepo.saveBeerList(id, list);
    }

    public Optional<List<String>> getBeerList(int id) {

        return cacheRepo.getBeerList(id);
    }

}
