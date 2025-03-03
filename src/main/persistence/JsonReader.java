package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads 
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads inventory from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Inventory readInventory() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseInventory(jsonObject);
    }

    // EFFECTS: reads purchase record from file and returns it;
    // throws IOException if an error occurs reading date from file
    public PurchaseRecord readPurchaseRecord() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePurchaseRecord(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses inventory from JSON object and returns it
    private Inventory parseInventory(JSONObject jsonObject) {
        Inventory i = new Inventory();
        addProducts(i, jsonObject);
        return i;
    }

    // EFFECTS: parses purchase record from JSON object and returns it
    private PurchaseRecord parsePurchaseRecord(JSONObject jsonObject) {
        PurchaseRecord pr = new PurchaseRecord();
        addPurchases(pr, jsonObject);
        return pr;
    }

    // MODIFIES: i
    // EFFECTS: parses products from JSON object and adds them to inventory
    private void addProducts(Inventory i, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Inventory");
        for (Object json : jsonArray) {
            JSONObject nextProduct = (JSONObject) json;
            addProduct(i, nextProduct);
        }
    }

    // MODIFIES: i
    // EFFECTS: parse product and add it to inventory
    private void addProduct(Inventory i, JSONObject jsonObject) {
        String name = jsonObject.getString("Name");
        String id = jsonObject.getString("ID");
        Double unitPrice = jsonObject.getDouble("Unit price");
        Double sellingPrice = jsonObject.getDouble("Selling price");
        Integer quantity = jsonObject.getInt("Quantity");
        Product p = new Product(name, id, unitPrice);
        p.setSellingPrice(sellingPrice);
        p.restock(quantity);
        i.addProduct(p);
    }


    // MODIFIES: pr
    // EFFECTS: parses purchases from JSON object and adds them to purchase record
    private void addPurchases(PurchaseRecord pr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Purchase Record");
        for (Object json : jsonArray) {
            JSONObject nextPurchase = (JSONObject) json;
            addPurchase(pr, nextPurchase);
        }
    }
    
    // MODIFIES: pr
    // EFFECTS: parses purchase from JSON object and adds it to purchase record
    private void addPurchase(PurchaseRecord pr, JSONObject jsonObject) {
        Integer year = Integer.valueOf(jsonObject.getString("Date").substring(0,4));
        Integer month = Integer.valueOf(jsonObject.getString("Date").substring(5,7));
        Integer day = Integer.valueOf(jsonObject.getString("Date").substring(8));
        Map<Product, Integer> purchaseProducts = new HashMap<Product, Integer>();
        JSONArray a = jsonObject.getJSONArray("Purchased Products");
        Inventory i = parseInventory(jsonObject);
        for (Object p : a) {
            JSONObject nextP = (JSONObject) p;
            Product product = i.findProductWithId(nextP.getString("ID"));
            purchaseProducts.put(product, nextP.getInt("Amount"));
        }
        Double actualPaidAmount = jsonObject.getDouble("Actual paid amount");
        String paymentMethod = jsonObject.getString("Payment method");
        Boolean reviewedStatus = jsonObject.getBoolean("Reviewed status");
        Purchase p = new Purchase(actualPaidAmount, paymentMethod);
        p.setDate(year, month, day);
        for (Map.Entry<Product, Integer> e : purchaseProducts.entrySet()) {
            p.addProduct(e.getKey(), e.getValue());
        }
        if (reviewedStatus) {
            p.reviewPurchase();
        }
    }
}
