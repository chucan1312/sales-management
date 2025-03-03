package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {
    
    @Test
    void testInventoryReaderInvalidFile() {
        try {
            Inventory i = new Inventory();
            JsonReader reader = new JsonReader("./data/my\0illegal:fileName.json");
            reader.readInventory();
            fail("IOException was expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testPurchaseRecordReaderInvalidFile() {
        try {
            PurchaseRecord pr = new PurchaseRecord();
            JsonReader reader = new JsonReader("./data/my\0illegal:fileName.json");
            reader.readPurchaseRecord();
            fail("IOException was expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test 
    void testReaderEmptyInventory() {
        JsonReader reader = new JsonReader("./data/testReaderEMmptyInventoryPurchaseRecord.json");
        try {
            Inventory i = reader.readInventory();
            assertEquals(0, i.getProducts().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        } 
    }

    @Test  
    void testReaderEmptyPurchaseRecord() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyInventoryPurchaseRecord.json");
        try {
            PurchaseRecord pr = reader.readPurchaseRecord();
            assertEquals(0, pr.getPurchases().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        } 
    }

    @Test 
    void testReaderGeneralInventory() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralInventoryPurchaseRecord.json");
        try {
            Inventory i = reader.readInventory();
            assertEquals(2, i.getProducts().size());
            checkProduct("cake", "123", 12.5, 15.0, 12, i.getProducts().get(0));
            checkProduct("cookie", "456", 5.2, 6.0, 8, i.getProducts().get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test 
    void testReaderGeneralPurchaseRecord() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralInventoryPurchaseRecord.json");
        try {
            PurchaseRecord pr = reader.readPurchaseRecord();
            assertEquals(2, pr.getPurchases().size());
            Map<String, Integer> p2 = new HashMap<String, Integer>();
            p2.put("123", 1);
            p2.put("456", 3);
            Map<String, Integer> none = new HashMap<String, Integer>();
            checkPurchase(LocalDate.of(2025, 2, 3), none, 20.5, "Cash", false, pr.getPurchases().get(0));
            checkPurchase(LocalDate.of(2024, 12, 13), p2, 24.0, "Credit", true, pr.getPurchases().get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}