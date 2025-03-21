package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads 
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workspace from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WorkSpace read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkSpace(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workspace from JSON object and returns it
    private WorkSpace parseWorkSpace(JSONObject jsonObject) {
        WorkSpace ws = new WorkSpace();
        Inventory i = this.parseInventory(jsonObject);
        PurchaseRecord pr = this.parsePurchaseRecord(jsonObject);
        ws.setInventory(i);
        ws.setPurchaseRecord(pr);
        return ws;
    }

    // EFFECTS: parses inventory from JSON object and returns it
    private Inventory parseInventory(JSONObject jsonObject) {
        Inventory i = new Inventory();
        addProducts(i, jsonObject);
        return i;
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
    // EFFECTS: parses product and adds it to inventory
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

    // EFFECTS: parses purchase record from JSON object and returns it
    private PurchaseRecord parsePurchaseRecord(JSONObject jsonObject) {
        PurchaseRecord pr = new PurchaseRecord();
        addPurchases(pr, jsonObject);
        return pr;
    }

    // MODIFIES: pr
    // EFFECTS: parses purchases from JSON object and adds them to inventory 
    private void addPurchases(PurchaseRecord pr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Purchase Record");
        for (Object json : jsonArray) {
            JSONObject nextPurchase = (JSONObject) json;
            addPurchase(pr, nextPurchase, jsonObject);
        }
    }

    // MODIFIES: pr
    // EFFECTS: parses purchase and adds it to inventory 
    private void addPurchase(PurchaseRecord pr, JSONObject jsonObject, JSONObject workspace) {
        String dateString = jsonObject.getString("Date");
        int year = Integer.parseInt(dateString.substring(0,4));
        int month = Integer.parseInt(dateString.substring(5, 7));
        int day = Integer.parseInt(dateString.substring(8));

        Double actualPaidAmount = jsonObject.getDouble("Actual Paid Amount");
        String paymentMethod = jsonObject.getString("Payment Method");
        Boolean reviewedStatus = jsonObject.getBoolean("Reviewed Status");

        Purchase p = new Purchase(actualPaidAmount, paymentMethod);
        p.setDate(year, month, day);
        if (reviewedStatus) {
            p.reviewPurchase();
        }
        Map<String, Integer> map = parsePurchasedProducts(jsonObject);
        Set<String> all = map.keySet();
        for (String one : all) {
            Integer amount = map.get(one);
            Inventory i = this.parseInventory(workspace);
            Product product = i.findProductWithId(one);        
            p.addProduct(product, amount);
        }
        pr.addPurchase(p);
    }

        
    private Map<String, Integer> parsePurchasedProducts(JSONObject jsonObject) {
        Map<String, Integer> map = new HashMap();
        JSONArray jsonArray = jsonObject.getJSONArray("Purchased Products");
        for (Object o : jsonArray) {
            JSONObject json = (JSONObject) o;
            String id = json.getString("Product's ID");
            Integer amount = json.getInt("Quantity");
            map.put(id, amount);
        }
        return map;
    }
}