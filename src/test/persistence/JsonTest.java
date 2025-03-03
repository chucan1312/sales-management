package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class JsonTest {
    protected void checkProduct(String name, String id, Double unitPrice, Double sellingPrice, Integer quantity, Product p) {
        assertEquals(name, p.getName());
        assertEquals(id, p.getId());
        assertEquals(unitPrice, p.getUnitPrice());
        assertEquals(sellingPrice, p.getSellingPrice());
        assertEquals(quantity, p.getQuantity());
    }
}


