package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestWorkSpace {
    WorkSpace ws;

    @Test
    void testConstructor() {
        ws = new WorkSpace();
        assertTrue(ws.getInventory().getProducts().isEmpty());
        assertTrue(ws.getPurchaseRecord().getPurchases().isEmpty());
    }
}
