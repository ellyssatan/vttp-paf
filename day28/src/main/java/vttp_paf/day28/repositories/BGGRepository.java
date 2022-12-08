package vttp_paf.day28.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

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

    public void search(String name) {
        // Create stages
        // $match name
        MatchOperation matchName = Aggregation.match(
            Criteria.where("name").is(name)
        );

        // $lookup                                       from (db)   localField foreignField  as
        LookupOperation lookupComments = Aggregation.lookup("comments", "gid", "gid", "comments");

        // $unwind
        UnwindOperation unwindComments = Aggregation.unwind("comments");
        
        // $sort
        SortOperation sortByRating = Aggregation.sort(null, null);

        // $project: _id, name, comments
        ProjectionOperation idAndNameOnly = Aggregation.project("_id", "name", "comments");
        
        // Create the pipeline
        Aggregation pipeline = Aggregation.newAggregation(matchName, lookupComments, idAndNameOnly);

        // Query the collection
        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, C_GAMES, Document.class);

        for (Document d : results) {
            System.out.println(d.toJson());
        }
    }
}