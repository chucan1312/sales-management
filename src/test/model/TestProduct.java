package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestProduct {
    Product p;

    @BeforeEach
    void runBefore() {
        p = new Product("name", "123", (double) 10, 12.5, 3);
    }

    @Test
    void testConstructor() {
        assertEquals("name", p.getName());
        assertEquals("123", p.getId());
        assertEquals(10, p.getUnitPrice());
        assertEquals(12.5, p.getSellingPrice());
        assertEquals(3, p.getQuantity());
    }

    @Test
    void testRestockOneProductOnce() {
        p.restock(10);
        assertEquals(13, p.getQuantity());    
    }

    @Test
    void testRestockOneProductTwice() {
        p.restock(15);
        p.restock(10);
        assertEquals(28, p.getQuantity());
    }

    @Test
    void testRestockTwoProductsOnce() {
        Product p2 = new Product("name1", "234", (double) 15, 20.0, 0);
        p.restock(3);
        p2.restock(5);

        assertEquals(6, p.getQuantity());
        assertEquals(5, p2.getQuantity());
    }

    @Test
    void testSellOneProduct() {
        p.restock(10);
        p.sell(5);
        assertEquals(8, p.getQuantity());
    }

    @Test
    void testSellOneProductMax() {
        p.restock(10);
        assertEquals(13, p.getQuantity());
        p.sell(10);
        assertEquals(3, p.getQuantity());
    }

    @Test
    void testSellTwoProductOnce() {
        Product p2 = new Product("name1", "234", (double) 15, 25.5, 0);
        p2.restock(20);
        p.restock(10);
        p2.sell(10);
        p.sell(2);

        assertEquals(11, p.getQuantity());
        assertEquals(10, p2.getQuantity());
    }

    @Test
    void testSetter() {
        p.setSellingPrice(15.99);
        p.setUnitPrice(14.5);
        p.setName("OMFG");
        assertEquals("OMFG", p.getName());
        assertEquals(15.99, p.getSellingPrice());
        assertEquals(14.5, p.getUnitPrice());
    }

}
