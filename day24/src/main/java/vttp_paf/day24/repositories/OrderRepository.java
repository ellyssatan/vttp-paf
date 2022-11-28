package vttp_paf.day24.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp_paf.day24.models.PurchaseOrder;

import static vttp_paf.day24.repositories.Queries.*;

@Repository
public class OrderRepository {
    
    @Autowired
    private JdbcTemplate template;

    public boolean insertOrder(PurchaseOrder po) {

        return template.update(SQL_INSERT_ORDER,
            po.getOrderId(), po.getName(), po.getOrderDate(), po.getOrderAddress()) > 0;

    }
}
