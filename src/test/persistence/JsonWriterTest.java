package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testInventoryWriterInvalidFile() {
        try {
            Inventory i = new Inventory();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testPurchaseRecordWriterInvalidFile() {
        try {
            PurchaseRecord pr = new PurchaseRecord();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriterEmptyInventory() {
        try {
            Inventory i = new Inventory();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyInventoryPurchaseRecord.json");
            writer.open();
            writer.writeInventory(i);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyInventoryPurchaseRecord.json");
            i = reader.readInventory();
            assertEquals(0, i.getProducts().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    
    @Test
    void testWriterEmptyPurchaseRecord() {
        try {
            PurchaseRecord pr = new PurchaseRecord();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyInventoryPurchaseRecord.json");
            writer.open();
            writer.writePurchasesRecord(pr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyInventoryPurchaseRecord.json");
            pr = reader.readPurchaseRecord();
            assertEquals(0, pr.getPurchases().size());
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
            p1.restock(10);
            p2.restock(12);
            
            Inventory i = new Inventory();
            i.addProduct(p1);
            i.addProduct(p2);

            PurchaseRecord pr = new PurchaseRecord();
            Purchase pc1 = new Purchase(10.0, "cash");
            Purchase pc2 = new Purchase(24.15, "credit");
            pc2.setDate(2024, 12, 13);
            pc2.addProduct(p1, 1);
            pc2.addProduct(p2, 2);
            pc2.reviewPurchase();
            pr.addPurchase(pc1);
            pr.addPurchase(pc2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralInventoryPurchaseRecord.json");
            writer.open();
            writer.writeInventory(i);
            writer.writePurchasesRecord(pr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralInventoryPurchaseRecord.json");
            
            i = reader.readInventory();
            assertEquals(2, i.getProducts().size());
            checkProduct("cake", "123", 12.0, 15.0, 10, p1);
            checkProduct("cupcake", "456", 13.0, 14.5, 12, p2);

            pr = reader.readPurchaseRecord();
            assertEquals(2, pr.getPurchases().size());
            Map<String, Integer> none = new HashMap<String,Integer>();
            checkPurchase(LocalDate.now(), none, 10.0, "cash", false, pc1);
            Map<String, Integer> purchasedProducts = new HashMap<String, Integer>();
            purchasedProducts.put("123", 1);
            purchasedProducts.put("456", 2);
            checkPurchase(LocalDate.of(2024, 12, 13), purchasedProducts, 24.15, "credit", true, pc2);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
