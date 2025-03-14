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

    @Override
    public JSONObject toJson() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toJson'");
    }
}
