package vttp_paf.day23_workshop.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp_paf.day23_workshop.models.Order;

import static vttp_paf.day23_workshop.repositories.Queries.*;;

@Repository
public class OrderRepository {

    @Autowired
    private JdbcTemplate template;

    public Order getTotal(int id) {

        Order o = new Order();
        final SqlRowSet rs = template.queryForRowSet(SQL_GET_TOTAL, id);

        while (rs.next()) {
           o = Order.create(rs);
        }

        return o;
    }
}
