package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads 
public class JsonReader {
    
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        // stub
    }

    // EFFECTS: reads inventory from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Inventory readInventory() throws IOException {
        // stub
    }

    // EFFECTS: reads purchase record from file and returns it;
    // throws IOException if an error occurs reading date from file
    public PurchaseRecord readPurchaseRecord() throws IOException {
        // stub
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        // stub
    }

    // EFFECTS: parses inventory from JSON object and returns it
    private Inventory parseInventory(JSONObject jsonObject) {
        // stub
    }

    // EFFECTS: parses purchase record from JSON object and returns it
    private Inventory parsePurchaseRecord(JSONObject jsonObject) {
        // stub
    }

    // EFFECTS: parses products from JSON object and adds them to inventory
    private void addProducts(Inventory i, JSONObject jsonObject) {
        // stub
    }

    // EFFECTS: parses product from JSON object and adds it to inventory
    private void addProduct(Inventory i, JSONObject jsonObject) {
        // stub
    }

    // EFFECTS: parses purchases from JSON object and adds them to purchase record
    private void addPurchases(PurchaseRecord pr, JSONObject jsonObject) {
        // stub
    }
    
    // EFFECTS: parses purchase from JSON object and adds it to purchase record
    private void addPurchase(PurchaseRecord pr, JSONObject jsonObject) {
        // stub
    }
}
