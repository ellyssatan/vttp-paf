package vttp_paf.day21_workshop.repositories;

public class Queries {
    public static final String SQL_GET_CUSTOMERS = "select id, concat(first_name, \" \", last_name) as name, job_title from customers limit ? offset ?";
    public static final String SQL_SEARCH_CUSTOMER = "select id, concat(first_name, \" \", last_name) as name, job_title  from customers where id = ?";
    public static final String SQL_SEARCH_CUSTOMER_ORDERS = "select * from customer_order_details where customer_id = ?";
}
