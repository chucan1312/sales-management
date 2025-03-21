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

    protected void checkPurchase(LocalDate date, Map<Product, Integer> purchasedProducts, Double actualPaidAmount, String paymentMethod, Boolean reviewedStatus, Purchase p) {
        assertEquals(date, p.getDate());
        assertEquals(purchasedProducts, p.getPurchasedProducts());
        assertEquals(actualPaidAmount, p.getActualPaidAmount());
        assertEquals(paymentMethod, p.getPaymentMethod());
        assertEquals(reviewedStatus, p.getReviewedStatus());
    }
}

