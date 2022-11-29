package vttp_paf.day21_workshop.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Order {

    private int order_id;
    private int product_id;
    private String product_name;

    public int getOrder_id() {      return order_id;        }
    public void setOrder_id(int order_id) {         this.order_id = order_id;       }

    public int getProduct_id() {        return product_id;      }
    public void setProduct_id(int product_id) {         this.product_id = product_id;       }

    public String getProduct_name() {         return product_name;      }
    public void setProduct_name(String product_name) {          this.product_name = product_name;       }
 
    public static Order create(SqlRowSet rs) {

        Order c = new Order();

        c.setOrder_id(rs.getInt("order_id"));
        c.setProduct_name(rs.getString("product_name"));
        c.setProduct_id(rs.getInt("product_id"));

        return c;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
            .add("order_id", getOrder_id())
            .add("product_name", getProduct_name())
            .add("product_id", getProduct_id())
            .build();
    }

}
