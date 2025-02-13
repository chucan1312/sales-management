package model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

// Represent one single purchase with time, a list of products purchased, total calculated cost of those products, 
// actual amount paid by customer, a payment method and a payment review status 
public class Purchase {
    private LocalDateTime dateTime;
    private List<Map<Product, Integer>> purchasedProducts;
    private Double totalCost;
    private Double actualPaidAmount;
    private String paymentMethod;
    private Boolean reviewedStatus;

    // EFFECTS: construct a new purchase with the amount paid, no prudcts or total cost
    // given payment method, and a false payment status 
    public Purchase(Double actualPaidAmount, String paymentMethod) {
        // stub
    }

    // REQUIRES: amount > 0, amount =< product.getQuantity()
    // MODIFIES: this, Product
    // EFFECTS: add product with bought amount in a purchase, add up price into totalCost,
    // and change the quantity of product
    public void addProduct(Product product, int amount) {
        // stub
    }

    // EFFECTS: return the difference between actualPaidAmount and totalCost;
    public Double difference() {
        return -1.1; // stub
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<Map<Product, Integer>> getPurchasedProducts() {
        return purchasedProducts;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public Double getActualPaidAmount() {
        return actualPaidAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public boolean getReviewedStatus() {
        return reviewedStatus;
    }

}
