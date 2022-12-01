package vttp_paf.day23_workshop.models;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Order {
    
    private int order_id;
    private DateTime order_date;
    private int customer_id;
    private Double total_price;
    private Double cost_price;

    public int getOrder_id() {      return order_id;        }
    public void setOrder_id(int order_id) {      this.order_id = order_id;      }

    public DateTime getOrder_date() {      return order_date;      }
    public void setOrder_date(DateTime order_date) {      this.order_date = order_date;      }

    public int getCustomer_id() {      return customer_id;      }
    public void setCustomer_id(int customer_id) {      this.customer_id = customer_id;      }

    public Double getTotal_price() {      return total_price;      }
    public void setTotal_price(Double total_price) {      this.total_price = total_price;      }

    public Double getCost_price() {      return cost_price;      }
    public void setCost_price(Double cost_price) {      this.cost_price = cost_price;      }

    public static Order create(SqlRowSet rs) {

        Order o = new Order();

        o.setOrder_id(rs.getInt("order_id"));
        o.setOrder_date(new DateTime(
            DateTimeFormat.forPattern("dd/MM/yyyy")
                    .parseDateTime(rs.getString("order_date"))));
        o.setCustomer_id(rs.getInt("customer_id"));
        o.setTotal_price(rs.getDouble("total_price"));
        o.setCost_price(rs.getDouble("cost_price"));

        return o;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("order_id", getOrder_id())
                .add("order_date", getOrder_date() != null ? getOrder_date().toString() : "")
                .add("customer_id", getCustomer_id())
                .add("discounted_price", getTotal_price().toString())
                .add("cost_price", getCost_price().toString())
                .build();
    }
}
