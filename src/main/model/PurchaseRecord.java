package model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

// Represents a record of all purchases made
public class PurchaseRecord {
    private List<Purchase> purchases;
   
    // EFFECTS: construct a record with an empty list of purchases
    public PurchaseRecord() {
        purchases = new ArrayList<Purchase>();
    }

    // EFFECTS: add a purchase to purchases
    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
    }

    // EFFECTS: return a list of all purchases from start date to end date
    public List<Purchase> getPurchasesBetween(LocalDate start, LocalDate end) {
        List<Purchase> result = new ArrayList<Purchase>();
        for (Purchase p : purchases) {
            LocalDate date = p.getDate();
            if (date.isEqual(start) || date.isEqual(end) || (date.isAfter(start) && date.isBefore(end))) {
                result.add(p);
            }
        }
        return result;
    }

    // EFFECTS: return a list of all unreviewed purchases for one day with given payment method (case sensitive)
    public List<Purchase> getOneDayPurchasesMethod(LocalDate date, String method) {
        List<Purchase> result = new ArrayList<Purchase>();
        for (Purchase p : purchases) {
            LocalDate pDate = p.getDate();
            if (pDate.isEqual(date) && !p.getReviewedStatus() && p.getPaymentMethod().equals(method)) {
                result.add(p);
            }
        }
        return result;
    }

    // EFFECTS: return the profit from start date to end date 
    public Double getProfit(LocalDate start, LocalDate end) {
        Double expenses = (double) 0;
        Double revenue = (double) 0;
        for (Purchase p : purchases) {
            LocalDate date = p.getDate();
            if (date.isEqual(start) || date.isEqual(end) || (date.isAfter(start) && date.isBefore(end))) {
                Map<Product, Integer> purchasedProducts = p.getPurchasedProducts();
                for (Map.Entry<Product, Integer> entry : purchasedProducts.entrySet()) {
                    expenses += entry.getKey().getUnitPrice() * entry.getValue();
                }
                revenue += p.getActualPaidAmount();
            }
        }
        return revenue - expenses;
    }

    // MODIFIES: this
    // EFFECTS: clear purchases record
    public void clearPurchase() {
        purchases.clear();
    }

    public List<Purchase> getPurchases() {
        return purchases; 
    }
   
}