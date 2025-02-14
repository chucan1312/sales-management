package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPurchaseRecord {
    PurchaseRecord pr;
    Purchase pc1;
    Purchase pc2;
    Purchase pc3;
    Product p1;
    Product p2;
    Product p3;

    @BeforeEach
    void runBefore() {
        pr = new PurchaseRecord();
        pc1 = new Purchase((double) 20, "Cash");
        pc2 = new Purchase(30.5, "E-transfer");
        pc3 = new Purchase(15.5, "Cash");
        p1 = new Product("product 1", "123", 2.5);
        p2 = new Product("product 2", "456", (double) 2);
    }

    @Test
    void testConstructor() {
        assertTrue(pr.getPurchases().isEmpty());
    }

    @Test
    void testAddOnePurchase() {
        pr.addPurchase(pc1);
        assertEquals(1, pr.getPurchases().size());
        assertEquals(pc1, pr.getPurchases().get(0));
    }

    @Test
    void testAddTwoPurchases() {
        pr.addPurchase(pc1);
        pr.addPurchase(pc2);
        assertEquals(2, pr.getPurchases().size());
        assertEquals(pc1, pr.getPurchases().get(0));
        assertEquals(pc2, pr.getPurchases().get(1));
    }

    @Test
    void testAddOnePurchaseTwice() {
        pr.addPurchase(pc1);
        pr.addPurchase(pc1);
        assertEquals(2, pr.getPurchases().size());
        assertEquals(pc1, pr.getPurchases().get(0));
        assertEquals(pc1, pr.getPurchases().get(1));
    }

    @Test
    void testGetPurchasesBetweenSameDay() {
        pc1.setDate(2025, 1, 1);
        pr.addPurchase(pc1);
        LocalDate date = LocalDate.of(2025,1,1);
        List<Purchase> result = pr.getPurchasesBetween(date, date);
        assertEquals(1, result.size());
        assertEquals(pc1, result.get(0));
    }

    @Test
    void testGetPurchasesBetweenARange() {
        pc1.setDate(2025,1,1);
        pr.addPurchase(pc1);
        LocalDate date1 = LocalDate.of(2024,12,13);
        LocalDate date2 = LocalDate.of(2025,2,1);
        List<Purchase> result = pr.getPurchasesBetween(date1, date2);
        assertEquals(1, result.size());
        assertEquals(pc1, result.get(0));
    }

    @Test
    void testGetPurchasesBetweenOutOfRange() {
        pc1.setDate(2025,1,1);
        pr.addPurchase(pc1);
        LocalDate date1 = LocalDate.of(2025,1,2);
        LocalDate date2 = LocalDate.of(2025,1,5);
        List<Purchase> result1 = pr.getPurchasesBetween(date1, date2);
        assertEquals(0, result1.size());
        assertFalse(result1.contains(pc1));

        LocalDate date3 = LocalDate.of(2024,1,1);
        LocalDate date4 = LocalDate.of(2024,11,1);
        List<Purchase> result2 = pr.getPurchasesBetween(date3, date4);
        assertEquals(0, result2.size());
        assertFalse(result1.contains(pc1));
    }

    @Test
    void testGetPurchasesBetweenListOfTwo() {
        pc1.setDate(2025,1,1);
        pc2.setDate(2025,2,1);
        pr.addPurchase(pc2);
        pr.addPurchase(pc1);
        LocalDate date1 = LocalDate.of(2025,1,5);
        LocalDate date2 = LocalDate.of(2025,2,14);
        List<Purchase> result1 = pr.getPurchasesBetween(date1, date2);
        assertEquals(1, result1.size());
        assertEquals(pc2, result1.get(0));

        LocalDate date3 = LocalDate.of(2024, 12,5);
        LocalDate date4 = LocalDate.of(2025,2,1);
        List<Purchase> result2 = pr.getPurchasesBetween(date3, date4);
        assertEquals(2, result2.size());
        assertEquals(pc2, result2.get(0));
        assertEquals(pc1, result2.get(1));
    }

    @Test
    void testGetOneDayPurchasesMethodOnePurchase() {
        pc1.setDate(2025,1,1);
        pr.addPurchase(pc1);
        LocalDate date = LocalDate.of(2025,1,1);
        List<Purchase> result1 = pr.getOneDayPurchasesMethod(date, "Cash");
        assertEquals(1, result1.size());
        assertEquals(pc1, result1.get(0));

        pc1.reviewPurchase();
        List<Purchase> result2 = pr.getOneDayPurchasesMethod(date, "Cash");
        assertEquals(0, result2.size());
        assertFalse(result2.contains(pc1));
    }

    @Test
    void testGetOneDayPurchasesMethodThreePurchase() {
        pc1.setDate(2025,1,1);
        pc2.setDate(2025,1, 1);
        pc3.setDate(2025,1,1);
        pr.addPurchase(pc1);
        pr.addPurchase(pc2);
        pr.addPurchase(pc3);
        LocalDate date = LocalDate.of(2025,1,1);
        List<Purchase> result1 = pr.getOneDayPurchasesMethod(date, "Cash");
        assertEquals(2, result1.size());
        assertEquals(pc1, result1.get(0));
        assertEquals(pc3, result1.get(1));

        pc1.setDate(2024, 1, 1);
        List<Purchase> result2 = pr.getOneDayPurchasesMethod(date, "Cash");
        assertEquals(1, result2.size());
        assertEquals(pc3, result2.get(0));

        pc1.setDate(2025,1,1);
        pc3.reviewPurchase();
        List<Purchase> result3 = pr.getOneDayPurchasesMethod(date, "Cash");
        assertEquals(1, result3.size());
        assertEquals(pc1, result3.get(0));
    }

    @Test
    void testGetProfitOnePurchaseOneProduct() {
        pc1.setDate(2025,1,1);
        pc1.addProduct(p1, 5);
        pr.addPurchase(pc1);
        LocalDate date1 = LocalDate.of(2025,1,1);
        assertEquals(7.5, pr.getProfit(date1, date1));

        LocalDate date2 = LocalDate.of(2025,1,2);
        LocalDate date3 = LocalDate.of(2024,12,31);
        assertEquals(7.5, pr.getProfit(date1, date2));
        assertEquals(7.5, pr.getProfit(date3, date1));
        assertEquals(7.5, pr.getProfit(date3, date2));
        assertEquals(0, pr.getProfit(date2, date3));
        LocalDate date4 = LocalDate.of(2024,12,13);
        LocalDate date5 = LocalDate.of(2025,2,14);
        assertEquals(0, pr.getProfit(date2, date5));
        assertEquals(0, pr.getProfit(date4, date3));
    }

    @Test
    void testGetProfitNegativeProfit() {
        pc1.setDate(2025, 1, 2);
        pr.addPurchase(pc1);
        pc1.addProduct(p1, 10);
        LocalDate date1 = LocalDate.of(2025,1,1);
        LocalDate date2 = LocalDate.of(2025,1,3);
        assertEquals(-5, pr.getProfit(date1, date2));
    }

    @Test
    void testGetProfitOnePurchaseTwoProduct() {
        pc1.setDate(2025,1,2);
        pc1.addProduct(p1,2);
        pc1.addProduct(p2,5);
        pr.addPurchase(pc1);
        LocalDate date1 = LocalDate.of(2025,1,1);
        LocalDate date2 = LocalDate.of(2025,1,3);
        assertEquals(5.0, pr.getProfit(date1, date2));
    }

    @Test
    void testGetProfitTwoPurchases() {
        pc1.setDate(2025,1,3);
        pc3.setDate(2025,2,1);
        pr.addPurchase(pc1);
        pr.addPurchase(pc3);
        pc1.addProduct(p1,4);
        pc3.addProduct(p2,10);
        LocalDate date1 = LocalDate.of(2025,1,1);
        LocalDate date2 = LocalDate.of(2025,2,3);
        assertEquals(5.5, pr.getProfit(date1, date2));
    }

    @Test
    void testClearPurchases() {
        pr.addPurchase(pc1);
        pr.addPurchase(pc2);
        assertEquals(2, pr.getPurchases().size());
        pr.clearPurchase();
        assertEquals(0, pr.getPurchases().size());
    }
}
