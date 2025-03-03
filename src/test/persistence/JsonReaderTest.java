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
            JsonReader reader = new JsonReader("./data/noSuchFile.json");
            reader.readInventory();
            fail("IOException was expected");
        } catch (IOException e) {
            //pass
        }
    }
    
    @Test 
    void testReaderEmptyInventory() {
        JsonReader reader = new JsonReader("./data/testReaderEmpty");
        try {
            Inventory i = reader.readInventory();
            assertEquals(0, i.getProducts().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test 
    void testReaderGeneralInventory() {
        JsonReader reader = new JsonReader("./data/testReaderGeneral");
        try {
            Inventory i = reader.readInventory();
            assertEquals(2, i.getProducts().size());
            checkProduct("cake", "123", 12.5, 15.0, 12, i.getProducts().get(0));
            checkProduct("cookie", "456", 5.2, 6.0, 8, i.getProducts().get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}