package vttp_paf.day24.models;

public class Item {

	private int item_id;
    private String description;
    private int quantity;

    public Item() {}
    public Item(String description, Integer quantity) { 
        this.description = description;
        this.quantity = quantity;
    }

    public int getItem_id() {       return item_id;     }
    public void setItem_id(int item_id) {       this.item_id = item_id;     }

    public String getDescription() {     return description;      }
    public void setDescription(String description) {      this.description = description;       }
    
    public int getQuantity() {      return quantity;        }
    public void setQuantity(int quantity) {     this.quantity = quantity;       }

    
}
