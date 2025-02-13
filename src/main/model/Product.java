package model;

// Represents a product with a name, a unique ID, a selling price, an import/unit price, and its quantity
public class Product {
    private String name;
    private String id;
    private Double sellingPrice; 
    private Double unitPrice;
    private int quantity;

    // EFFECTS: constructs a new product with given name, id and original unit price, no selling price or quantity
    public Product (String name, String id, Double unitPrice) {
        // stub
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: add amount to quantity
    public void restock(int amount) {
        // stub
    }

    // REQUIRES: amount > 0 
    // MODIFIES: this
    // EFFECTS: subtract amount from quantity
    public void sell(int amount) {
        // stub
    }

    // REQUIRES: sellingPrice > 0
    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    //REQUIRES: unitPrice > 0
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
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

}
