package vttp_paf.day23_workshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp_paf.day23_workshop.models.Order;
import vttp_paf.day23_workshop.repositories.OrderRepository;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository oRepo;

    public Order getTotal(int id) {
        
        return oRepo.getTotal(id);
    }
}
