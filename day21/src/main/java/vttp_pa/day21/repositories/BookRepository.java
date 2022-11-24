package vttp_pa.day21.repositories;

import static vttp_pa.day21.repositories.Queries.*;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp_pa.day21.models.Book;

@Repository
public class BookRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Book> getBooksByRating(Float rating) {

        // Perform query
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_BOOKS_BY_RATING, rating);
        final List<Book> books = new LinkedList<>();

        // Attempt to move cursor to next row
        while (rs.next()) {
            // have record -> add to list
            books.add(Book.create(rs));

        }

        return books;
    }

    public List<Book> getBooksByName(String input, int limit, int offset) {
        
        String search = "%".concat(input).concat("%");
        // Perform query
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_BOOKS_BY_NAME, search, limit, offset);
        final List<Book> books = new LinkedList<>();

        // Attempt to move cursor to next row
        while (rs.next()) {
            // have record -> add to list
            books.add(Book.create(rs));

        }
        return books;
    }
}
