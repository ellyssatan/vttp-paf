package vttp_pa.day22.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp_pa.day22.models.User;

import static vttp_pa.day22.repositories.Queries.*;

@Repository
public class UserRepository {
    
    @Autowired
    private JdbcTemplate template;

    public Integer createUser(User user) throws Exception {
        return template.update(SQL_INSERT_USER,
            user.getUsername(), user.getPassword(), user.getEmail(), user.getPhone(), user.getDob());
    }

    public boolean findUser(User user) throws Exception {
        final SqlRowSet rs = template.queryForRowSet(SQL_FIND_USER, user.getUsername(), user.getPassword());
        // if no record = no user
        if (rs.next()) {
            return rs.getBoolean("auth_state");
        }
        return false;
    }
}
