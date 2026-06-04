package com.busdriver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class DriverIntegrationTest {

    private DriverRepository repo;
    private static final String TEST_FILE = "test_drivers.txt";

    @BeforeEach
    public void setUp() {
        repo = new DriverRepository(TEST_FILE);
    }

    @AfterEach
    public void tearDown() {
        new File(TEST_FILE).delete();
    }

    @Test
    public void testValidDriverIsStoredCorrectly() {
        Driver driver = new Driver("23@@!!@@AB", "John", 5, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        boolean result = repo.add(driver);
        assertTrue(result);
        Driver retrieved = repo.retrieve("23@@!!@@AB");
        assertNotNull(retrieved);
        assertEquals("John", retrieved.getName());
    }

    @Test
    public void testDuplicateDriverIsRejected() {
        Driver driver1 = new Driver("23@@!!@@AB", "John", 5, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        Driver driver2 = new Driver("23@@!!@@AB", "Jane", 3, "Light",
                "15|King St|Sydney|NSW|Australia", "05-05-1992");
        repo.add(driver1);
        boolean result = repo.add(driver2);
        assertFalse(result);
    }

    @Test
    public void testUpdateIsPersistedCorrectly() {
        Driver driver = new Driver("23@@!!@@AB", "John", 5, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        repo.add(driver);
        repo.update("23@@!!@@AB", "99|Queen St|Brisbane|QLD|Australia", "", 5);
        DriverRepository reloadedRepo = new DriverRepository(TEST_FILE);
        Driver updated = reloadedRepo.retrieve("23@@!!@@AB");
        assertEquals("99|Queen St|Brisbane|QLD|Australia", updated.getAddress());
    }

    @Test
    public void testCountIsUpdatedCorrectly() {
        assertEquals(0, repo.count());
        Driver driver1 = new Driver("23@@!!@@AB", "John", 5, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        Driver driver2 = new Driver("34##$$##CD", "Jane", 3, "Light",
                "15|King St|Sydney|NSW|Australia", "05-05-1992");
        repo.add(driver1);
        assertEquals(1, repo.count());
        repo.add(driver2);
        assertEquals(2, repo.count());
    }
}