package model;

import org.json.JSONObject;

import persistence.Writable;

// Represent a workspace with an inventory and a purchase record
public class WorkSpace implements Writable {
    private Inventory i;
    private PurchaseRecord pr;

    // EFFECTS: Construct a workspace with a new inventory and purchase record
    public WorkSpace() {
        i = new Inventory();
        pr = new PurchaseRecord();
    }

    public Inventory getInventory() {
        return i;
    }

    public PurchaseRecord getPurchaseRecord() {
        return pr;
    }

    public void setInventory(Inventory i) {
        this.i = i;
    }

    public void setPurchaseRecord(PurchaseRecord pr) {
        this.pr = pr;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Inventory", i.productsToJson());
        json.put("Purchase Record", pr.purchasesToJson());
        return json;
    }
}
