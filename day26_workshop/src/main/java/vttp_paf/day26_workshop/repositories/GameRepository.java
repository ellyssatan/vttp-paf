package vttp_paf.day26_workshop.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp_paf.day26_workshop.models.Game;

@Repository
public class GameRepository {

    private static final String C_GAME = "game";

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Game> getAllGames(int limit, int offset) {

        final Pageable pageableRequest = PageRequest.of(offset, limit);

        Query q = new Query();
        q.with(pageableRequest);

        return mongoTemplate.find(q, Document.class, C_GAME)
            .stream()
            .map(d -> Game.create(d))
            .toList();
    }

    public List<Game> getGamesByRank() {

        Query q = new Query();
        q.with(Sort.by(Sort.Direction.ASC, "ranking"));

        return mongoTemplate.find(q, Document.class, C_GAME)
            .stream()
            .map(d -> Game.create(d))
            .toList();
    }

    public Game getGameDetailsById(int gid) {

        Query q = new Query();
        q.addCriteria(Criteria.where("gid").is(gid));

        return mongoTemplate.findOne(q, Game.class, C_GAME);
    }
    
}
