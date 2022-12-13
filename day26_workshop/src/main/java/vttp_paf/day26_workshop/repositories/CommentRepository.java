package vttp_paf.day26_workshop.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import vttp_paf.day26_workshop.models.Comment;

@Repository
public class CommentRepository {
    
    private static final String C_COMMENT = "comment";

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Comment> searchCommentByText(List<String> includes,
                    List<String> excludes, int limit, int offset) {
        
        TextCriteria tc = TextCriteria.forDefaultLanguage()
                .matchingAny(includes.toArray(new String[includes.size()]))
                .notMatchingAny(excludes.toArray(new String[excludes.size()]));

        TextQuery tq = (TextQuery) TextQuery.queryText(tc)
                    .includeScore()
                    .sortByScore()
                    .limit(limit)
                    .skip(offset);
        
        return mongoTemplate.find(tq, Document.class, C_COMMENT)
                    .stream()
                    .map(c -> Comment.create(c))
                    .toList();
    }
}
