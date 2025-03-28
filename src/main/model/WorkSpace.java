package model;

import org.json.JSONObject;

import persistence.Writable;

// Represent a workspace with an inventory and a purchase record
public class WorkSpace implements Writable {
    private Inventory i;
    private PurchaseRecord pr;

    // EFFECTS: Construct a workspace with a given inventory and purchase record
    public WorkSpace(Inventory i, PurchaseRecord pr) {
        this.i = i;
        this.pr = pr;
    }

    public Inventory getInventory() {
        return i;
    }

    public PurchaseRecord getPurchaseRecord() {
        return pr;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Inventory", i.productsToJson());
        json.put("Purchase Record", pr.purchasesToJson());
        return json;
    }
}
