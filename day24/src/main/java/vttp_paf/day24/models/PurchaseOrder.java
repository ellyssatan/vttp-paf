package vttp_paf.day24.models;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class PurchaseOrder {

    private String orderId;
    private String name;
    private Date orderDate;
    private String orderAddress;
    private List<Item> items = new LinkedList<>();

    public String getOrderId() {    return orderId;     }
    public void setOrderId(String orderId) {    this.orderId = orderId;     }
    
    public String getName() {       return name;        }
    public void setName(String name) {      this.name = name;       }

    public Date getOrderDate() {        return orderDate;      }
    public void setOrderDate(Date orderDate) {      this.orderDate = orderDate;     }

    public String getOrderAddress() {       return orderAddress;    }
    public void setOrderAddress(String orderAddress) {      this.orderAddress = orderAddress;       }

    public List<Item> getItems() {      return items;       }
    public void setItems(List<Item> items) {    this.items = items;     }
    public void addItems(Item item) {    this.items.add(item);     }

    
}
