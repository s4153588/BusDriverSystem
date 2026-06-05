package com.busdriver;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DriverTest {

    // D1 - Driver ID Rules

    @Test
    public void test_1_1_01_ValidDriverIDAccepted() {
        Driver driver = new Driver("56!@21ABCD", "John Smith", 5, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000");
        assertEquals("56!@21ABCD", driver.getDriverID());
    }

    @Test
    public void test_1_1_02_DriverIDFewerThan10CharsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("56!@ABCD", "John Smith", 5, "Heavy",
                        "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000"));
    }

    @Test
    public void test_1_1_03_DriverIDMoreThan10CharsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("56!@21ABCDEFX", "John Smith", 5, "Heavy",
                        "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000"));
    }

    @Test
    public void test_1_1_04_DriverIDFirstDigitOutOfRangeRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("16!@21ABCD", "John Smith", 5, "Heavy",
                        "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000"));
    }

    @Test
    public void test_1_1_05_DriverIDSecondDigitOutOfRangeRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("50!@21ABCD", "John Smith", 5, "Heavy",
                        "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000"));
    }

    @Test
    public void test_1_1_06_DriverIDWithoutTwoSpecialCharsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("56A121ABCD", "John Smith", 5, "Heavy",
                        "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000"));
    }

    @Test
    public void test_1_1_07_DriverIDEndingWithLowercaseRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("56!@21ABcd", "John Smith", 5, "Heavy",
                        "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000"));
    }

    // D2 - Address Format Validation

    @Test
    public void test_1_2_01_ValidAddressAccepted() {
        Driver driver = new Driver("56!@21ABCD", "John Smith", 5, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000");
        assertEquals("124|La Trobe St|Melbourne|VIC|Australia", driver.getAddress());
    }

    @Test
    public void test_1_2_02_AddressWithCommasRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("56!@21ABCD", "John Smith", 5, "Heavy",
                        "124,La Trobe St,Melbourne,VIC,Australia", "12-02-2000"));
    }

    @Test
    public void test_1_2_03_AddressWithMissingFieldsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("56!@21ABCD", "John Smith", 5, "Heavy",
                        "124|La Trobe St|Melbourne|VIC", "12-02-2000"));
    }

    // D3 - Birthday Format Validation

    @Test
    public void test_1_3_01_ValidBirthdateAccepted() {
        Driver driver = new Driver("56!@21ABCD", "John Smith", 5, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000");
        assertEquals("12-02-2000", driver.getBirthdate());
    }

    @Test
    public void test_1_3_02_BirthdateInWrongFormatRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("56!@21ABCD", "John Smith", 5, "Heavy",
                        "124|La Trobe St|Melbourne|VIC|Australia", "2000-02-12"));
    }

    @Test
    public void test_1_3_03_InvalidCalendarDateRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("56!@21ABCD", "John Smith", 5, "Heavy",
                        "124|La Trobe St|Melbourne|VIC|Australia", "42-15-2000"));
    }

    // D4 - License Update Restriction Validation

    @Test
    public void test_1_4_01_LicenseUpdateAllowedUnder10Years() {
        Driver driver = new Driver("56!@21ABCD", "John Smith", 5, "Light",
                "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000");
        driver.setLicenseType("Heavy");
        assertEquals("Heavy", driver.getLicenseType());
    }

    @Test
    public void test_1_4_02_LicenseUpdateBlockedOver10Years() {
        Driver driver = new Driver("56!@21ABCD", "John Smith", 15, "Light",
                "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000");
        assertThrows(UnsupportedOperationException.class, () ->
                driver.setLicenseType("Heavy"));
    }

    @Test
    public void test_1_4_03_OtherDetailsCanBeUpdatedOver10Years() {
        Driver driver = new Driver("56!@21ABCD", "John Smith", 15, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000");
        driver.setAddress("99|Queen St|Brisbane|QLD|Australia");
        assertEquals("99|Queen St|Brisbane|QLD|Australia", driver.getAddress());
    }

    // D5 - Immutable Field Validations

    @Test
    public void test_1_5_01_DriverIDCannotBeModified() {
        Driver driver = new Driver("56!@1234AB", "John Smith", 5, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000");
        assertThrows(UnsupportedOperationException.class, () ->
                driver.setDriverID("67#@5678CD"));
    }

    @Test
    public void test_1_5_02_DriverNameCannotBeModified() {
        Driver driver = new Driver("56!@1234AB", "John Smith", 5, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000");
        assertThrows(UnsupportedOperationException.class, () ->
                driver.setName("David Brown"));
    }

    @Test
    public void test_1_5_03_MutableFieldsCanBeModified() {
        Driver driver = new Driver("56!@1234AB", "John Smith", 5, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "12-02-2000");
        driver.setAddress("99|Queen St|Brisbane|QLD|Australia");
        assertEquals("99|Queen St|Brisbane|QLD|Australia", driver.getAddress());
    }
}