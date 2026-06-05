package com.busdriver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class BusIntegrationTest {

    private BusRepository repo;
    private static final String TEST_FILE = "test_buses.txt";

    @BeforeEach
    public void setUp() {
        repo = new BusRepository(TEST_FILE);
    }

    @AfterEach
    public void tearDown() {
        new File(TEST_FILE).delete();
    }

    @Test
    public void test_4_1_ValidBusStoredCorrectly() {
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        boolean result = repo.add(bus);
        assertTrue(result);
        Bus retrieved = repo.retrieve("12345678");
        assertNotNull(retrieved);
        assertEquals(40, retrieved.getCapacity());
    }

    @Test
    public void test_4_2_DuplicateBusIDRejected() {
        Bus bus1 = new Bus("12345678", 40, 80.0, "Diesel");
        Bus bus2 = new Bus("12345678", 50, 60.0, "Hybrid");
        repo.add(bus1);
        boolean result = repo.add(bus2);
        assertFalse(result);
    }

    @Test
    public void test_4_3_CapacityAndFuelTypeUpdatePersistedToFile() {
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        repo.add(bus);
        repo.update("12345678", 30, 50.0, "Hybrid");
        BusRepository reloadedRepo = new BusRepository(TEST_FILE);
        Bus updated = reloadedRepo.retrieve("12345678");
        assertEquals(30, updated.getCapacity());
        assertEquals("Hybrid", updated.getFuelType());
    }

    @Test
    public void test_4_4_BusCountUpdatesCorrectly() {
        assertEquals(0, repo.count());
        Bus bus1 = new Bus("12345678", 40, 80.0, "Diesel");
        Bus bus2 = new Bus("87654321", 50, 60.0, "Hybrid");
        repo.add(bus1);
        assertEquals(1, repo.count());
        repo.add(bus2);
        assertEquals(2, repo.count());
    }

    @Test
    public void test_4_5_InvalidBusIDRejectedAndCountRemainsZero() {
        assertThrows(IllegalArgumentException.class, () ->
                new Bus("INVALID123", 40, 80.0, "Diesel"));
        assertEquals(0, repo.count());
    }

    @Test
    public void test_4_6_RetrieveNonExistentBusReturnsNull() {
        Bus result = repo.retrieve("99999999");
        assertNull(result);
    }

    @Test
    public void test_4_7_MultipleBusesPersistedAndRetrievedCorrectly() {
        Bus bus1 = new Bus("12345678", 40, 80.0, "Diesel");
        Bus bus2 = new Bus("87654321", 50, 60.0, "Hybrid");
        repo.add(bus1);
        repo.add(bus2);
        BusRepository reloadedRepo = new BusRepository(TEST_FILE);
        assertEquals(40, reloadedRepo.retrieve("12345678").getCapacity());
        assertEquals(50, reloadedRepo.retrieve("87654321").getCapacity());
    }

    @Test
    public void test_4_8_CapacityIncreaseBlockedAndOriginalPreserved() {
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        repo.add(bus);
        assertThrows(IllegalArgumentException.class, () ->
                repo.update("12345678", 60, 80.0, "Diesel"));
        BusRepository reloadedRepo = new BusRepository(TEST_FILE);
        assertEquals(40, reloadedRepo.retrieve("12345678").getCapacity());
    }
}