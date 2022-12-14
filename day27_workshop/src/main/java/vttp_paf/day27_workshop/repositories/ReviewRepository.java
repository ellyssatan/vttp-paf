package vttp_paf.day27_workshop.repositories;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import vttp_paf.day27_workshop.models.EditedComment;
import vttp_paf.day27_workshop.models.Review;

@Repository
public class ReviewRepository {

    private static final String C_REVIEW = "review";

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
}
