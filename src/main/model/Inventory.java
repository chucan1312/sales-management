package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represent a store's inventory with a list of products
public class Inventory {
    private List<Product> products;
   
    // EFFECTS: construct an inventory with an empty product list
    public Inventory() {
        products = new ArrayList<Product>();
    }
    
    // EFFECTS: return a product when given an id,
    // return null if not found
    public Product findProductWithId(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: add a product to the end of list of products if not already in list
    public void addProduct(Product product) {
        if (!products.contains(product)) {
            products.add(product);
        }
    }

    // REQUIRES: findProductWithId(id) != null
    // MODIFIES: this
    // EFFECTS: remove a product 
    public void removeProduct(Product p) {
        products.remove(p);
    }

    // REQUIRES: searchTerm != ""
    // EFFECTS: return a list of products with names containing the search term,
    // return empty list if no products' name contain the search term (case insensitive)
    public List<Product> findProductWithName(String searchTerm) {
        List<Product> list = new ArrayList<Product>();
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                list.add(product);
            }
        }

        return list;
    }

    // MODIFIES: this
    // EFFECTS: clear products list
    public void clearInventory() {
        products.clear();
    }

    public List<Product> getProducts() {
        return products;
    }

    public JSONArray productsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Product p : products) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
    
}
