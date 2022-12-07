package vttp_paf.day27.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp_paf.day27.models.Comment;
import vttp_paf.day27.repositories.CommentRepository;
import vttp_paf.day27.repositories.LogRepository;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private LogRepository logRepo;

    public List<Comment> search(String q, Float score, Integer limit, Integer offset) {

        List<String> include = new LinkedList<>();
        List<String> exclude = new LinkedList<>();

        for (String w: q.split(" "))
            if (w.startsWith("-"))
                exclude.add(w);
            else 
                include.add(w);
    
        try {
            return commentRepo.search(include, exclude, limit)
                .stream()
                .filter(c -> c.getScore() >= score)
                .toList();
        } finally {
            logRepo.log(q, score);
        }
    }
}
