package vttp_paf.day27_workshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp_paf.day27_workshop.models.EditedComment;
import vttp_paf.day27_workshop.models.Review;
import vttp_paf.day27_workshop.repositories.ReviewRepository;

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
}
