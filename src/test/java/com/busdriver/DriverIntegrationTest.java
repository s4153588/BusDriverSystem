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
    public void test_3_1_ValidDriverStoredCorrectly() {
        Driver driver = new Driver("56!@21ABCD", "John Smith", 5, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000");
        boolean result = repo.add(driver);
        assertTrue(result);
        Driver retrieved = repo.retrieve("56!@21ABCD");
        assertNotNull(retrieved);
        assertEquals("John Smith", retrieved.getName());
    }

    @Test
    public void test_3_2_DuplicateDriverIDRejected() {
        Driver driver1 = new Driver("56!@21ABCD", "John Smith", 5, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000");
        Driver driver2 = new Driver("56!@21ABCD", "David Brown", 3, "Light",
                "99|Queen St|Brisbane|QLD|Australia", "05-05-1992");
        repo.add(driver1);
        boolean result = repo.add(driver2);
        assertFalse(result);
    }

    @Test
    public void test_3_3_AddressUpdatePersistedToFile() {
        Driver driver = new Driver("56!@21ABCD", "John Smith", 5, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000");
        repo.add(driver);
        repo.update("56!@21ABCD", "99|Queen St|Brisbane|QLD|Australia", "", 5);
        DriverRepository reloadedRepo = new DriverRepository(TEST_FILE);
        Driver updated = reloadedRepo.retrieve("56!@21ABCD");
        assertEquals("99|Queen St|Brisbane|QLD|Australia", updated.getAddress());
    }

    @Test
    public void test_3_4_DriverCountUpdatesCorrectly() {
        assertEquals(0, repo.count());
        Driver driver1 = new Driver("56!@21ABCD", "John Smith", 5, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000");
        Driver driver2 = new Driver("67#@5678CD", "David Brown", 3, "Light",
                "99|Queen St|Brisbane|QLD|Australia", "05-05-1992");
        repo.add(driver1);
        assertEquals(1, repo.count());
        repo.add(driver2);
        assertEquals(2, repo.count());
    }

    @Test
    public void test_3_5_InvalidDriverRejectedAndCountRemainsZero() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("INVALID", "John Smith", 5, "Heavy",
                        "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000"));
        assertEquals(0, repo.count());
    }

    @Test
    public void test_3_6_RetrieveNonExistentDriverReturnsNull() {
        Driver result = repo.retrieve("99@@!!@@ZZ");
        assertNull(result);
    }

    @Test
    public void test_3_7_MultipleDriversPersistedAndRetrievedCorrectly() {
        Driver driver1 = new Driver("56!@21ABCD", "John Smith", 5, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000");
        Driver driver2 = new Driver("67#@5678CD", "David Brown", 3, "Light",
                "99|Queen St|Brisbane|QLD|Australia", "05-05-1992");
        repo.add(driver1);
        repo.add(driver2);
        DriverRepository reloadedRepo = new DriverRepository(TEST_FILE);
        assertEquals("John Smith", reloadedRepo.retrieve("56!@21ABCD").getName());
        assertEquals("David Brown", reloadedRepo.retrieve("67#@5678CD").getName());
    }

    @Test
    public void test_3_8_LicenceUpdateBlockedAndOriginalPreserved() {
        Driver driver = new Driver("56!@21ABCD", "John Smith", 11, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000");
        repo.add(driver);
        assertThrows(UnsupportedOperationException.class, () ->
                repo.update("56!@21ABCD", "", "Light", 11));
        DriverRepository reloadedRepo = new DriverRepository(TEST_FILE);
        assertEquals("Heavy", reloadedRepo.retrieve("56!@21ABCD").getLicenseType());
    }
}