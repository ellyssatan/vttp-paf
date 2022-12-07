package vttp_paf.day27.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import vttp_paf.day27.models.Comment;

@Repository
public class CommentRepository {

    public static final String C_COMMENTS = "comments";
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Comment> search(List<String> includes, List<String> excludes) {
        return getsearched(includes, excludes, 10, 0);
    }
    public List<Comment> search(List<String> includes, List<String> excludes, Integer limit) {
        return getsearched(includes, excludes, limit, 0);
    }

    /*
    * db.comments.find({
    *      $text: {
    *          $search: "..."
    *      },
    * },
    * {
    *      score: {
    *          $meta: "textScore"
    *      }
    * })
    * .sort({ score: 1 })
    * .limit(m)
    * .skip(n)
    */
    public List<Comment> getsearched(List<String> includes, List<String> excludes, Integer limit, Integer offset) {

        TextCriteria textCriteria = TextCriteria.forDefaultLanguage()
            // convert the include array to a string array
            .matchingAny(includes.toArray(new String[includes.size()]))
            .notMatchingAny(excludes.toArray(new String[excludes.size()]));

        TextQuery query = (TextQuery) TextQuery.queryText(textCriteria)
                .includeScore("score")
                .sortByScore()
                .limit(limit)
                .skip(offset);

        return mongoTemplate.find(query, Document.class, C_COMMENTS)
                .stream()
                .map(d -> Comment.create(d))
                .toList();
    }
}
