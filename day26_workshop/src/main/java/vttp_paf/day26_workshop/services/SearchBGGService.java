package vttp_paf.day26_workshop.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp_paf.day26_workshop.models.Comment;
import vttp_paf.day26_workshop.models.Game;
import vttp_paf.day26_workshop.repositories.CommentRepository;
import vttp_paf.day26_workshop.repositories.GameRepository;

@Service
public class SearchBGGService {
    
    @Autowired
    private GameRepository gRepo;

    @Autowired
    private CommentRepository cRepo;

    public List<Game> getAllGames(int limit, int offset) {

        return gRepo.getAllGames(limit, offset);
    }

    public List<Game> getGamesByRank() {

        return gRepo.getGamesByRank();
    }

    public Game getGameDetailsById(int gid) {

        return gRepo.getGameDetailsById(gid);
    }

    public List<Comment> searchCommentByKeyword(String s, int limit, int offset) {

        List<String> includes = new LinkedList<>();
        List<String> excludes = new LinkedList<>();
        
        for (String w : s.split(" ")) {
            if (w.startsWith("-")) {
                excludes.add(w.split("-")[1]);
            } else {
                includes.add(w);
            }
        }

        return cRepo.searchCommentByText(includes, excludes, limit, offset);
    }
}
