package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPurchase {
    Purchase pc;
    Product p1;
    Product p2;

    @BeforeEach
    void runBefore() {
        pc = new Purchase((double) 15, "Cash");
        p1 = new Product("cake", "123", 15.1, 0.0, 0);
        p2 = new Product("pie", "456", 13.2, 0.0, 0);
        p1.restock(10);
        p2.restock(5);
        p1.setSellingPrice(15.5);
        p2.setSellingPrice(13.5);
    }

    @Test
    void testConstructor() {
        assertTrue(pc.getPurchasedProducts().isEmpty());
        assertEquals(0, pc.getTotalCost());
        assertEquals((double) 15, pc.getActualPaidAmount());
        assertEquals("Cash", pc.getPaymentMethod());
        assertFalse(pc.getReviewedStatus());
    }

    @Test
    void testAddOneProductOnce() {
        pc.addProduct(p1, 2);
        assertEquals(1, pc.getPurchasedProducts().size());
        assertTrue(pc.getPurchasedProducts().containsKey(p1));
        assertEquals(2, pc.getPurchasedProducts().get(p1));
        assertEquals(15.5 * 2, pc.getTotalCost());
        assertEquals(8, p1.getQuantity());
    }

    @Test
    void testAddOneProductTwice() {
        pc.addProduct(p1,1);
        assertEquals(1, pc.getPurchasedProducts().size());
        assertTrue(pc.getPurchasedProducts().containsKey(p1));
        assertEquals(1, pc.getPurchasedProducts().get(p1));
        assertEquals(15.5, pc.getTotalCost());
        assertEquals(9, p1.getQuantity());

        pc.addProduct(p1,3);
        assertEquals(1, pc.getPurchasedProducts().size());
        assertTrue(pc.getPurchasedProducts().containsKey(p1));
        assertEquals(3, pc.getPurchasedProducts().get(p1));
        assertEquals(15.5 * 3, pc.getTotalCost());
        assertEquals(7, p1.getQuantity());

        pc.addProduct(p1, 3);
        assertEquals(1, pc.getPurchasedProducts().size());
        assertTrue(pc.getPurchasedProducts().containsKey(p1));
        assertEquals(3, pc.getPurchasedProducts().get(p1));
        assertEquals(15.5 * 3, pc.getTotalCost());
        assertEquals(7, p1.getQuantity());

        pc.addProduct(p1, 2);
        assertEquals(1, pc.getPurchasedProducts().size());
        assertTrue(pc.getPurchasedProducts().containsKey(p1));
        assertEquals(2, pc.getPurchasedProducts().get(p1));
        assertEquals(15.5 * 2, pc.getTotalCost());
        assertEquals(8, p1.getQuantity());
    }

    @Test
    void testAddTwoProductOnce() {
        pc.addProduct(p1,2);
        pc.addProduct(p2,1);
        assertEquals(2, pc.getPurchasedProducts().size());
        assertTrue(pc.getPurchasedProducts().containsKey(p1));
        assertTrue(pc.getPurchasedProducts().containsKey(p2));
        assertEquals(2, pc.getPurchasedProducts().get(p1));
        assertEquals(1, pc.getPurchasedProducts().get(p2));
        assertEquals(15.5 * 2 + 13.5, pc.getTotalCost());
        assertEquals(8, p1.getQuantity());
        assertEquals(4, p2.getQuantity());
    }

    @Test
    void testAddOneProductAllStock() {
        pc.addProduct(p2, 5);
        assertEquals(1, pc.getPurchasedProducts().size());
        assertTrue(pc.getPurchasedProducts().containsKey(p2));
        assertEquals(5, pc.getPurchasedProducts().get(p2));
        assertEquals(13.5 * 5, pc.getTotalCost());
        assertEquals(0, p2.getQuantity());
    }

    @Test
    void testDifferenceLessThan() {
        pc.addProduct(p2, 1);
        assertEquals(1.5, pc.difference());
    }

    @Test
    void testDifferenceMoreThan() {
        pc.addProduct(p1, 1);
        assertEquals(-0.5, pc.difference());
    }

    @Test
    void testDifferenceEqualTo() {
        Product p3 = new Product("", "", 7.1, 0.0, 0);
        p3.setSellingPrice(7.5);
        pc.addProduct(p3, 2);
        assertEquals(0, pc.difference());
    }

    @Test
    void testReview() {
        pc.reviewPurchase();
        assertTrue(pc.getReviewedStatus());

        pc.unreviewPurchase();
        assertFalse(pc.getReviewedStatus());
    }
}
