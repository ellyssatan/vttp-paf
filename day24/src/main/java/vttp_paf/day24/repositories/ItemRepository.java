package vttp_paf.day24.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp_paf.day24.models.Item;
import vttp_paf.day24.models.PurchaseOrder;

import static vttp_paf.day24.repositories.Queries.*;

@Repository
public class ItemRepository {

    @Autowired
    private JdbcTemplate template;

    public void addItems1(PurchaseOrder po) throws Exception {
        addItems(po.getItems(), po.getOrderId());
    }   
    
    public void addItems(List<Item> items, String orderId) throws Exception {
        /* 
        List<Object[]> data = new LinkedList<>();
        for (Item i : items) {
            Object[] obj = new Object[3];
            obj[0] = i.getDescription();
            obj[1] = i.getQuantity();
            obj[2] = orderId;
            data.add(obj);
        }
        */
        // Stream - alternative to iteration
        List<Object[]> data = items.stream()
            .map(i -> {
                Object[] obj = new Object[3];
                obj[0] = i.getDescription();
                obj[1] = i.getQuantity();
                obj[2] = orderId;
                return obj;
            })
            .toList();
            
        // Batch update
        template.batchUpdate(SQL_INSERT_ITEM, data);
    }
}
