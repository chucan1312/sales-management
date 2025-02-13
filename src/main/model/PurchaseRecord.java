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

    // EFFECTS: add a purchase to purchases
    public void addPurchase(Purchase purchase) {
        // stub
    }

    // EFFECTS: return a list of all purchases for one day
    public List<Purchase> getOneDayPurchases(LocalDate date) {
        return null; //stub
    }

    // EFFECTS: return a list of all unreviewed purchases for one day with given payment method
    public List<Purchase> getOneDayPurchasesMethod(LocalDate date, String method) {
        return null; //stub
    }

    public List<Purchase> getPurchases() {
        return purchases; 
    }
   
}