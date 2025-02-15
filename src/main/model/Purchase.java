package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

// Represent one single purchase with time, a list of products purchased, total calculated cost of those products, 
// actual amount paid by customer, a payment method and a payment review status 
public class Purchase {
    private LocalDate date;
    private Map<Product, Integer> purchasedProducts;
    private Double totalCost;
    private Double actualPaidAmount;
    private String paymentMethod;
    private Boolean reviewedStatus;

    // EFFECTS: Construct a new purchase with the amount paid, no prudcts or total cost
    // given payment method, and a false reviewed status 
    public Purchase(Double actualPaidAmount, String paymentMethod) {
        date = LocalDate.now();
        purchasedProducts = new HashMap<Product,Integer>();
        totalCost = (double) 0;
        this.actualPaidAmount = actualPaidAmount;
        this.paymentMethod = paymentMethod;
        reviewedStatus = false;
    }

    // REQUIRES: amount > 0, amount =< product.getQuantity()
    // MODIFIES: this, Product
    // EFFECTS: Add product with bought amount in a purchase, add up price into totalCost,
    // and change the quantity of product accordingly. If product is already in purchasedProducts;
    // addProduct replace the original amount with the new one 
    public void addProduct(Product product, Integer amount) {
        if (!purchasedProducts.containsKey(product)) {
            purchasedProducts.put(product, amount);
            totalCost += product.getSellingPrice() * amount;
            product.sell(amount);
        }
        else {
            Integer originalAmount = purchasedProducts.get(product);
            purchasedProducts.put(product, amount);
            totalCost -= product.getSellingPrice() * originalAmount;
            totalCost += product.getSellingPrice() * amount;
            Integer mistake = amount - originalAmount;
            if (mistake > 0) {
                product.sell(amount - originalAmount);
            }
            else {
                product.restock(originalAmount - amount);
            }
        }
    }

    // EFFECTS: Return the difference between actualPaidAmount and totalCost;
    public Double difference() {
        return actualPaidAmount - totalCost;
    }

    // MODIFIES: this
    // EFFECTS: mark a purchase as reviewed
    public void reviewPurchase() {
        reviewedStatus = true;
    }

    // MODIFIES: this
    // EFFECTS: mark a purchase as unreviewed
    public void unreviewPurchase() {
        reviewedStatus = false;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<Product, Integer> getPurchasedProducts() {
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

    public void setDate(Integer year, Integer month, Integer day) {
        date = LocalDate.of(year, month, day);
    }
    
}
