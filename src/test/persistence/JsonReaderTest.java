package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {
    
    @Test
    void testInventoryReaderInvalidFile() {
        try {
            WorkSpace ws = new WorkSpace();
            JsonReader reader = new JsonReader("./data/noSuchFile.json");
            ws = reader.read();
            fail("IOException was expected");
        } catch (IOException e) {
            //pass
        }
    }
    
    @Test 
    void testReaderEmptyInventory() {
        JsonReader reader = new JsonReader("./data/testReaderEmpty");
        try {
            WorkSpace ws = new WorkSpace();
            ws = reader.read();
            assertTrue(ws.getInventory().getProducts().isEmpty());
            assertTrue(ws.getPurchaseRecord().getPurchases().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test 
    void testReaderGeneralInventory() {
        JsonReader reader = new JsonReader("./data/testReaderGeneral");
        try {
            WorkSpace ws = new WorkSpace();
            ws = reader.read();
            List<Product> products = ws.getInventory().getProducts();
            List<Purchase> purchases = ws.getPurchaseRecord().getPurchases();

            assertEquals(2, products.size());
            checkProduct("cake", "123", 12.5, 15.0, 12, products.get(0));
            checkProduct("cookie", "456", 5.2, 6.0, 8, products.get(1));

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
            fail("Couldn't read from file");
        }
    }

}