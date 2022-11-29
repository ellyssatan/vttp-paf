package vttp_paf.day21_workshop.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp_paf.day21_workshop.models.Customer;
import vttp_paf.day21_workshop.models.Order;

import static vttp_paf.day21_workshop.repositories.Queries.*;

import java.util.LinkedList;
import java.util.List;

@Repository
public class CustomerRepository {
    
    @Autowired
    private JdbcTemplate template;

    public List<Customer> getCustomers(int limit, int offset) {

        List<Customer> customerList = new LinkedList<>();

        final SqlRowSet rs = template.queryForRowSet(SQL_GET_CUSTOMERS, limit, offset);

        while(rs.next()) {

            customerList.add(Customer.create(rs));
        }
        return customerList;
    }

    public Customer getCustomer(int id) {

        Customer c = new Customer();

        final SqlRowSet rs = template.queryForRowSet(SQL_SEARCH_CUSTOMER, id);

        while(rs.next()) {

            c = Customer.create(rs);
        }
        return c;
    }

    public List<Order> getCustomerOrder(int id) {

        List<Order> orders = new LinkedList<>();

        final SqlRowSet rs = template.queryForRowSet(SQL_SEARCH_CUSTOMER_ORDERS, id);

        while(rs.next()) {

           orders.add(Order.create(rs));
        }
        return orders;
    }
}
