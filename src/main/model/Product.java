package model;

import java.util.Collection;

import org.json.JSONObject;

import persistence.Writable;

// Represents a product with a name, a unique ID, a selling price, an import/unit price, and its quantity
public class Product implements Writable {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((sellingPrice == null) ? 0 : sellingPrice.hashCode());
        result = prime * result + ((unitPrice == null) ? 0 : unitPrice.hashCode());
        result = prime * result + quantity;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    
}
