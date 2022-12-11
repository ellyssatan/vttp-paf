package vttp_paf.day28.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import vttp_paf.day28.models.Comment;
import vttp_paf.day28.models.Game;

@Repository
public class BGGRepository {

    private static final String C_GAMES = "games";
    
    @Autowired
    private MongoTemplate mongoTemplate;

    /*
        db.games.aggregate([
        { 
            $match: { name: 'Gloomhaven' }
        },
        {
            $lookup: {
            from: 'comments',
            foreignField: 'gid',
            localField: 'gid',
            as: 'comments',
            pipeline: [
                { $sort: { rating: -1 } },
                { $limit: 10 }
            ]
            }
        },
        {
            $project: { name: 1, comments: 1 }
        }
        ]);      
     */

    public Optional<Game> search(String name) {
        // Create stages
        // $match name
        MatchOperation matchName = Aggregation.match(
            Criteria.where("name").regex(name, "i")
        );

        // $lookup                                       from (db)   localField foreignField  as
        LookupOperation lookupComments = Aggregation.lookup("comments", "gid", "gid", "comments");

        // $project: _id, name, comments
        ProjectionOperation selectFields = Aggregation.project("_id", "name", "image", "comments");

        // $unwind
        UnwindOperation unwindComments = Aggregation.unwind("comments");
        
        // $sort
        SortOperation sortByRating = Aggregation.sort(Direction.DESC, "comments.rating");

        // $limit
        LimitOperation takeTop10 = Aggregation.limit(10);

        // $group
        GroupOperation groupByName = Aggregation.group("name", "image").push("comments").as("comments");
  
        // Create the pipeline
        Aggregation pipeline = Aggregation.newAggregation(matchName, lookupComments, selectFields, unwindComments, sortByRating, takeTop10 , groupByName);

        // Query the collection
        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, C_GAMES, Document.class);

        if (!results.iterator().hasNext())
            return Optional.empty();

        // Get one result only
        Document doc = results.iterator().next();
        Game g = Game.create(doc);
        List<Comment> comments = doc.getList("comments", Document.class).stream()
            .map(c -> Comment.create(c))
            .toList();
        g.setComments(comments);

        return Optional.of(g);
    }
}
