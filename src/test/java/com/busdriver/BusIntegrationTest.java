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
    public void testValidBusIsStoredCorrectly() {
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        boolean result = repo.add(bus);
        assertTrue(result);
        Bus retrieved = repo.retrieve("12345678");
        assertNotNull(retrieved);
        assertEquals(40, retrieved.getCapacity());
    }

    @Test
    public void testDuplicateBusIsRejected() {
        Bus bus1 = new Bus("12345678", 40, 80.0, "Diesel");
        Bus bus2 = new Bus("12345678", 50, 60.0, "Hybrid");
        repo.add(bus1);
        boolean result = repo.add(bus2);
        assertFalse(result);
    }

    @Test
    public void testUpdateIsPersistedCorrectly() {
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        repo.add(bus);
        repo.update("12345678", 30, 50.0, "Hybrid");
        BusRepository reloadedRepo = new BusRepository(TEST_FILE);
        Bus updated = reloadedRepo.retrieve("12345678");
        assertEquals(30, updated.getCapacity());
        assertEquals("Hybrid", updated.getFuelType());
    }

    @Test
    public void testCountIsUpdatedCorrectly() {
        assertEquals(0, repo.count());
        Bus bus1 = new Bus("12345678", 40, 80.0, "Diesel");
        Bus bus2 = new Bus("87654321", 50, 60.0, "Hybrid");
        repo.add(bus1);
        assertEquals(1, repo.count());
        repo.add(bus2);
        assertEquals(2, repo.count());
    }
}