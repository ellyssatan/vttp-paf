package vttp_paf.day23_workshop.repositories;

public class Queries {
    public static final String SQL_GET_TOTAL = """
        SELECT
            o.id as order_id,
            DATE_FORMAT(o.order_date, "%d/%m/%Y") as order_date,
            o.customer_id as customer_id,
            sum((od.quantity * od.unit_price) - (od.quantity * od.unit_price * od.discount)) as total_price,
            sum(od.quantity * p.standard_cost) as cost_price
        FROM
            orders o
            join customers c
            on o.customer_id = c.id
            join order_details od
            on o.id = od.order_id
            join products p
            on od.product_id = p.id
        WHERE   
            order_id = ?
    """;
}
