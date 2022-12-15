package vttp_paf.day28_workshop.repositories;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import vttp_paf.day28_workshop.models.EditedComment;
import vttp_paf.day28_workshop.models.Game;
import vttp_paf.day28_workshop.models.Review;

@Repository
public class ReviewRepository {

    private static final String C_REVIEW = "review";
    private static final String C_GAME = "game";

    @Autowired
    private MongoTemplate mongoTemplate;
    
    public Review insertReview(Review r) {

        return mongoTemplate.insert(r, C_REVIEW);
    }

    public Review upsertEdited(String _id, EditedComment e) {

        ObjectId objId = new ObjectId(_id);

        Review existingR = mongoTemplate.findById(objId, Review.class, C_REVIEW);

        // check if record is null (new)
        if (existingR != null) {
            // Make new object
            EditedComment er = new EditedComment();
            er.setComment(e.getComment());
            er.setRating(e.getRating());
            er.setPosted(LocalDateTime.now());

            // if got existing comment
            if (existingR.getEdited() != null) {

                existingR.getEdited().add(er);

            } else {
                // Add as new comment
                List<EditedComment> eArr = new LinkedList<EditedComment>();
                eArr.add(e);
                existingR.setEdited(eArr);
            }
            mongoTemplate.save(existingR, C_REVIEW);
        } 

        return existingR;
    }

    public Review findReview(String _id) {

        ObjectId objId = new ObjectId(_id);

        Review existingR = mongoTemplate.findById(objId, Review.class, C_REVIEW);

        return existingR;
    }


    /*
     * db.game.aggregate([
        {
            $match: { gid:? }
        },
        {
            $lookup:
            {
                from: "reviews",
                localField: "gid",
                foreignField: "gid",
                as: "reviewDoc",
            }
        },
        {
            $project : { _id: 1, gid: 1,
            name: 1, year: 1, ranking: 1, users_rated: 1, url: 1, image:1, reviews: "$reviewDoc._id",timestamp: "$$NOW"}
        },

        ]);
     */
    public Optional<Game> aggregateReviews(int gid) {
        
        MatchOperation matchGid = Aggregation.match(
            Criteria.where("gid").is(gid));
        
        LookupOperation linkReviewsGame = Aggregation.lookup(C_REVIEW, "gid", "gid", "reviewDoc");

        ProjectionOperation project = Aggregation.project(
            "_id", "gid", "name", "year", "ranking", "users_rated", "url", "image")
            .and("reviewDoc._id").as("review");

        // add field
        AddFieldsOperation newFieldOps = Aggregation.addFields()
                                        .addFieldWithValue("timestamp", LocalDateTime.now())
                                        .build();


        Aggregation pipeline = Aggregation.newAggregation(matchGid, linkReviewsGame, project, newFieldOps);

        AggregationResults<Document> result = mongoTemplate.aggregate(pipeline, C_GAME, Document.class);

        if (!result.iterator().hasNext()) {
            return Optional.empty();
        }

        Document doc = result.iterator().next();
        Game g = Game.create(doc);
        return Optional.of(g);
    }

    /*
     * 
     * db.review.aggregate([
            {
                $match: { user: "bellywellyjelly" }
            },
            {
                $project : { _id: "$gid",
                name: 1, rating: 1, user: 1, comment: 1, review_id: "$_id"}
            },
            {
                $sort: { rating: 1 }
            }
        ]);
     */
    public Optional<List<Review>> aggregateUserReviews(String sort, String user) {
        
        MatchOperation matchUser = Aggregation.match(Criteria.where("user").is(user));

        ProjectionOperation project = Aggregation.project(
            "name", "rating", "user", "comment")
            .and("gid").as("_id")       // .and(existing field).as(new name)
            .and("_id").as("review_id");

        SortOperation sortByRating;
        if (sort.equals("highest")) {
            sortByRating = Aggregation.sort(Sort.by(Direction.DESC, "rating"));
        } else {
            sortByRating = Aggregation.sort(Sort.by(Direction.ASC, "rating"));
        }
        Aggregation pipeline = Aggregation.newAggregation(matchUser, project, sortByRating);

        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, C_REVIEW, Document.class);

        if (!results.iterator().hasNext()) {
            System.out.println(">>>>> ITS NULL");
            return Optional.empty();
        }

        List<Review> rList = new LinkedList<>();

        for (Document r : results) {
            rList.add(Review.createProject(r));
        }
        return Optional.of(rList);
    }
}
