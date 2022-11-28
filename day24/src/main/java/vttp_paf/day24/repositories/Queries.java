package vttp_paf.day24.repositories;

public class Queries {
    public static final String SQL_INSERT_ORDER =
            "insert into purchase_order (order_id, name, order_date, order_address) value (?, ?, ?, ?)";
    public static final String SQL_INSERT_ITEM =
            "insert into order_items (order_id, description, quantity) values (?, ?, ?)";
}
