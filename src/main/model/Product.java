package model;

import java.util.Collection;

import org.json.JSONObject;

// Represents a product with a name, a unique ID, a selling price, an import/unit price, and its quantity
public class Product {
    private String name;
    private String id;
    private Double sellingPrice; 
    private Double unitPrice;
    private int quantity;

    // EFFECTS: constructs a new product with given name, id and original unit price, no selling price or quantity
    public Product (String name, String id, Double unitPrice) {
        this.name = name;
        this.id = id;
        sellingPrice = (double) 0;
        this.unitPrice = unitPrice;
        quantity = 0;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: add amount to quantity
    public void restock(int amount) {
        quantity += amount;
    }

    // REQUIRES: amount > 0, amount <= quantity 
    // MODIFIES: this
    // EFFECTS: subtract amount from quantity
    public void sell(int amount) {
        quantity -= amount;
    }

    // REQUIRES: sellingPrice > 0
    // MODIFIES: this
    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    //REQUIRES: unitPrice > 0
    // MODIFIES: this
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    // MODIFIES: this
    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }


    public Double getSellingPrice() {
        return sellingPrice;
    }


    public Double getUnitPrice() {
        return unitPrice;
    }


    public String getName() {
        return name;
    }


    public String getId() {
        return id;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Name", name);
        json.put("ID", id);
        json.put("Unit price", unitPrice);
        json.put("Selling price", sellingPrice);
        json.put("Quantity", quantity);
        return json;
    }

}
