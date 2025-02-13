package model;


import java.time.LocalDate;
import java.util.List;

// Represents a record of all purchases made
public class PurchaseRecord {
    private List<Purchase> purchases;
   
    // EFFECTS: construct a record with an empty list of purchases
    public PurchaseRecord() {
        //stub
    }

    // EFFECTS: add a purchase to purchases and add actualPaidAmount to total
    public void addPurchase(Purchase purchase) {
        //stub
    }

    // EFFECTS: return a list of all purchases from start date to end date
    public List<Purchase> getPurchasesBetween(LocalDate start, LocalDate end) {
        return null; //stub
    }

    // EFFECTS: return a list of all unreviewed purchases for one day with given payment method
    public List<Purchase> getOneDayPurchasesMethod(LocalDate date, String method) {
        return null; //stub
    }

    // EFFECTS: return the profit from start date to end date 
    public Double getProfit(LocalDate start, LocalDate end) {
        return -1.1; //stub
    }

    // MODIFIES: this
    // EFFECTS: clear purchases record
    public void clearPurchase() {
        //stub
    }

    public List<Purchase> getPurchases() {
        return purchases; 
    }
   
}