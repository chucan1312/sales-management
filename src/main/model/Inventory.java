package model;

import java.util.List;

// Represent a store's inventory with a list of products
public class Inventory {
    private List<Product> products;
   
    // EFFECTS: construct an inventory with an empty product list
    public Inventory() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: add a product to the end of list of products if not already in list
    public void addProduct(Product product) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: remove a product, given its id
    public void removeProduct(String id) {
        // stub
    }

    // EFFECTS: return a product when given an id,
    // return null if not found
    public Product findProductWithId(String id) {
        return null; // stub
    }

    // EFFECTS: return a list of products with names containing the search term,
    // return empty list if no products' name contain the search term (case insensitive)
    public List<Product> findProductWithName(String searchTerm) {
        return null; // stub
    }

    // MODIFIES: this
    // EFFECTS: clear products list
    public void clearInventory() {
        //stub
    }

    public List<Product> getProducts() {
        return products;
    }

}
