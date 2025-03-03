package persistence;

import model.*;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
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
    void testWriterEmptyInventory() {
        try {
            Inventory i = new Inventory();
            JsonWriter writer = new JsonWriter("data/testWriterEmptyInventory");
            writer.open();
            writer.writeInventory(i);
            writer.close();

            JsonReader reader = new JsonReader("data/testWriterEmptyInventory");
            i = reader.readInventory();
            assertEquals(0, i.getProducts().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    
    @Test 
    void testWriterGeneralInventory() {
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

            JsonWriter writerI = new JsonWriter("data/testWriterInventory");
            writerI.open();
            writerI.writeInventory(i);
            writerI.close();

            JsonReader reader = new JsonReader("data/testWriterInventory");
            i = reader.readInventory();
            assertEquals(2, i.getProducts().size());
            checkProduct("cake", "123", 12.0, 15.0, 10, p1);
            checkProduct("cupcake", "456", 3.0, 14.5, 12, p2);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

 }