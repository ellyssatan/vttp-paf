package vttp_paf.day26_workshop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp_paf.day26_workshop.models.Game;
import vttp_paf.day26_workshop.repositories.GameRepository;

@Service
public class SearchBGGService {
    
    @Autowired
    private GameRepository gRepo;

    public List<Game> getAllGames(int limit, int offset) {

        return gRepo.getAllGames(limit, offset);
    }

    public List<Game> getGamesByRank() {

        return gRepo.getGamesByRank();
    }

    public Game getGameDetailsById(int gid) {

        return gRepo.getGameDetailsById(gid);
    }
}
