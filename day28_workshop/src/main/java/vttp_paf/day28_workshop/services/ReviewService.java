package vttp_paf.day28_workshop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp_paf.day28_workshop.models.EditedComment;
import vttp_paf.day28_workshop.models.Game;
import vttp_paf.day28_workshop.models.Review;
import vttp_paf.day28_workshop.repositories.ReviewRepository;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository rRepo;
    
    public Review insertReview(Review r) {

        return rRepo.insertReview(r);
    }

    public Review upsertEdited(String _id, EditedComment e) {

        return rRepo.upsertEdited(_id, e);
    }
    
    public Review findReview(String _id) {

        return rRepo.findReview(_id);
    }

    public Optional<Game> aggregateReviews(int gid) {
        return rRepo.aggregateReviews(gid);
    }

    public Optional<List<Review>> aggregateUserReviews(String sort, String user) {
        return rRepo.aggregateUserReviews(sort, user);
    }
}
