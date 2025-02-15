package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestInventory {
    Inventory i;
    Product p1;
    Product p2;
    Product p3;

    @BeforeEach
    void runBefore() {
        i = new Inventory();
        p1 = new Product("Cupcake", "123", (double) 10);
        p2 = new Product("Carrot Cake", "456", (double) 5);
        p3 = new Product ("Apple Pie", "789", (double) 15);
    }

    @Test
    void testConstructor() {
        assertTrue(i.getProducts().isEmpty());
    }

    @Test
    void testAddOneProductOnce() {
        i.addProduct(p1);
        assertEquals(p1, i.getProducts().get(0));
        assertEquals(1, i.getProducts().size());
    }

    @Test
    void testAddTwoDifferentProducts() {
        i.addProduct(p1);
        i.addProduct(p2);
        assertEquals(p1, i.getProducts().get(0));
        assertEquals(p2, i.getProducts().get(1));
        assertEquals(2, i.getProducts().size());
    }

    @Test
    void testAddOneProductTwice() {
        i.addProduct(p1);
        i.addProduct(p1);
        assertEquals(p1, i.getProducts().get(0));
        assertEquals(1, i.getProducts().size());
    }

    @Test
    void testFindOneProductEmptyInventory() {
        assertNull(i.findProductWithId("123"));
    }

    @Test 
    void testFindOneProduct() {
        i.addProduct(p1);
        Product result =  i.findProductWithId("123");
        assertEquals(p1, result);
    }

    @Test
    void testFindOneProductNotFound() {
        i.addProduct(p1);
        assertNull(i.findProductWithId("124"));
    }

    @Test
    void testFindTwoProductsInventory() {
        i.addProduct(p1);
        i.addProduct(p2);
        Product result1 = i.findProductWithId("456");
        Product result2 = i.findProductWithId("123");
        assertEquals(p2, result1);
        assertEquals(p1, result2);
    }

    @Test 
    void testRemoveOneProduct() {
        i.addProduct(p1);
        i.addProduct(p2);
        i.addProduct(p3);
        i.removeProduct(p1);
        assertEquals(p2, i.getProducts().get(0));
        assertEquals(p3, i.getProducts().get(1));
        assertEquals(2, i.getProducts().size());
    }

    @Test
    void testRemoveMultipleProducts() {
        i.addProduct(p1);
        i.addProduct(p2);
        i.addProduct(p3);
        
        i.removeProduct(p3);
        assertEquals(p1, i.getProducts().get(0));
        assertEquals(p2, i.getProducts().get(1));
        assertEquals(2, i.getProducts().size());

        i.removeProduct(p1);
        assertEquals(p2, i.getProducts().get(0));
        assertEquals(1, i.getProducts().size());
    }

    @Test
    void testFindProductWithNameEmptyInventory() {
        List<Product> result = i.findProductWithName("a");
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindOneProductWithNameExactName() {
        i.addProduct(p1);
        List<Product> result = i.findProductWithName("Cupcake");
        assertEquals(1, result.size());
        assertEquals(p1, result.get(0));
    }

    @Test
    void testFindOneProductWithNameSubstring() {
        i.addProduct(p1);
        List<Product> result = i.findProductWithName("cup");
        assertEquals(1, result.size());
        assertEquals(p1, result.get(0));
    }

    @Test
    void testFindOneProductInventoryMultipleProducts() {
        i.addProduct(p1);
        i.addProduct(p2);
        i.addProduct(p3);
        List<Product> result1 = i.findProductWithName("pie");
        assertEquals(1, result1.size());
        assertEquals(p3, result1.get(0));

        List<Product> result2 = i.findProductWithName("Cake");
        assertEquals(2, result2.size());
        assertEquals(p1, result2.get(0));
        assertEquals(p2, result2.get(1));
    }

    @Test
    void testClearInventory() {
        i.clearInventory();
        assertTrue(i.getProducts().isEmpty());

        i.addProduct(p1);
        i.addProduct(p2);
        assertEquals(2, i.getProducts().size());
        i.clearInventory();
        assertTrue(i.getProducts().isEmpty());
    }

}
