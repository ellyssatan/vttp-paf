package vttp_paf.day21_workshop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp_paf.day21_workshop.models.Customer;
import vttp_paf.day21_workshop.models.Order;
import vttp_paf.day21_workshop.repositories.CustomerRepository;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository cRepo;

    public List<Customer> getCustomers(int limit, int offset) {
        return cRepo.getCustomers(limit, offset);
    }

    public Customer getCustomer(int id) {
        return cRepo.getCustomer(id);
    }

    public List<Order> getCustomerOrder(int id) {
        return cRepo.getCustomerOrder(id);
    }
}
