package persistence;

import model.*;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            WorkSpace ws = new WorkSpace();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriterEmptyInventoryPurchaseRecord() {
        try {
            WorkSpace ws = new WorkSpace();
            JsonWriter writer = new JsonWriter("data/testWriterEmptyInventory");
            writer.open();
            writer.write(ws);
            writer.close();

            JsonReader reader = new JsonReader("data/testWriterEmptyInventory");
            ws = reader.read();
            assertTrue(ws.getInventory().getProducts().isEmpty());
            assertTrue(ws.getPurchaseRecord().getPurchases().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    
    @Test 
    void testWriterGeneral() {
        try {
            Product p1 = new Product("cake", "123", 12.0);
            Product p2 = new Product("cupcake", "456", 3.0);
            p1.setSellingPrice(15.0);
            p2.setSellingPrice(14.5);
            p1.restock(11);
            p2.restock(14);

            Purchase pc1 = new Purchase(24.0, "Cash");
            Purchase pc2 = new Purchase(7.0, "E-transfer");
            pc1.setDate(2024,12,13);
            pc1.addProduct(p1, 1);
            pc1.addProduct(p2, 2);
            pc1.reviewPurchase();
            pc2.setDate(2025,03,14);
            
            WorkSpace ws = new WorkSpace();
            ws.getInventory().addProduct(p1);
            ws.getInventory().addProduct(p2);
            ws.getPurchaseRecord().addPurchase(pc1);
            ws.getPurchaseRecord().addPurchase(pc2);

            JsonWriter writer = new JsonWriter("data/testWriterGeneral");
            writer.open();
            writer.write(ws);
            writer.close();

            JsonReader reader = new JsonReader("data/testWriterGeneral");
            ws = reader.read();
            List<Product> products = ws.getInventory().getProducts();
            List<Purchase> purchases = ws.getPurchaseRecord().getPurchases();

            assertEquals(2, products.size());
            checkProduct("cake", "123", 12.5, 15.0, 10, products.get(0));
            checkProduct("cookie", "456", 5.2, 14.5, 12, products.get(1));

            assertEquals(2, purchases.size());
            LocalDate date1 = LocalDate.of(2024, 12, 13);
            LocalDate date2 = LocalDate.of(2025, 3, 14);
            Map<Product, Integer> purchased1 = new HashMap<>();
            purchased1.put(products.get(0), 1);
            purchased1.put(products.get(1), 2);
            Map<Product, Integer> purchased2 = new HashMap<>();
            checkPurchase(date1, purchased1, 24.0, "Cash", true, purchases.get(0));
            checkPurchase(date2, purchased2, 7.0, "E-transfer", false, purchases.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

 }