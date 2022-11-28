package vttp_paf.day24.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp_paf.day24.models.PurchaseOrder;
import vttp_paf.day24.repositories.ItemRepository;
import vttp_paf.day24.repositories.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Transactional(rollbackFor = OrderException.class)
    public void insertOrder(PurchaseOrder po) throws OrderException {

        // Generate order id
        String orderId = UUID.randomUUID().toString().substring(0, 8);
        System.out.printf(">>>> OrderId: %s\n", orderId);
        po.setOrderId(orderId);

        // Create order
        orderRepo.insertOrder(po);

        throw new OrderException("Exception for orderId %s".formatted(orderId));

        // Create items
        // itemRepo.addItems(po.getItems(), orderId);

    }// Commit
}
