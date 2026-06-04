package com.busdriver;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DriverTest {

    // D1 - Driver ID Rules

    @Test
    public void testValidDriverID() {
        Driver driver = new Driver("23@@!!@@AB", "John", 5, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        assertEquals("23@@!!@@AB", driver.getDriverID());
    }

    @Test
    public void testDriverIDTooShort() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("23@@AB", "John", 5, "Heavy",
                        "12|Main St|Melbourne|VIC|Australia", "01-01-1990"));
    }

    @Test
    public void testDriverIDFirstTwoNotDigits() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("AB@@!!@@CD", "John", 5, "Heavy",
                        "12|Main St|Melbourne|VIC|Australia", "01-01-1990"));
    }

    @Test
    public void testDriverIDFirstDigitOutOfRange() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("13@@!!@@AB", "John", 5, "Heavy",
                        "12|Main St|Melbourne|VIC|Australia", "01-01-1990"));
    }

    @Test
    public void testDriverIDLastTwoNotUppercase() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("23@@!!@@ab", "John", 5, "Heavy",
                        "12|Main St|Melbourne|VIC|Australia", "01-01-1990"));
    }

    @Test
    public void testDriverIDLessThanTwoSpecialChars() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("23@ABCDEAB", "John", 5, "Heavy",
                        "12|Main St|Melbourne|VIC|Australia", "01-01-1990"));
    }

    // D2 - Address Format

    @Test
    public void testValidAddress() {
        Driver driver = new Driver("23@@!!@@AB", "John", 5, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        assertEquals("12|Main St|Melbourne|VIC|Australia", driver.getAddress());
    }

    @Test
    public void testAddressMissingParts() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("23@@!!@@AB", "John", 5, "Heavy",
                        "12|Main St|Melbourne", "01-01-1990"));
    }

    @Test
    public void testAddressEmptyPart() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("23@@!!@@AB", "John", 5, "Heavy",
                        "12||Melbourne|VIC|Australia", "01-01-1990"));
    }

    // D3 - Birthdate Format

    @Test
    public void testValidBirthdate() {
        Driver driver = new Driver("23@@!!@@AB", "John", 5, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "15-06-1990");
        assertEquals("15-06-1990", driver.getBirthdate());
    }

    @Test
    public void testInvalidBirthdateFormat() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("23@@!!@@AB", "John", 5, "Heavy",
                        "12|Main St|Melbourne|VIC|Australia", "1990-06-15"));
    }

    @Test
    public void testInvalidBirthdateValue() {
        assertThrows(IllegalArgumentException.class, () ->
                new Driver("23@@!!@@AB", "John", 5, "Heavy",
                        "12|Main St|Melbourne|VIC|Australia", "32-13-1990"));
    }

    // D4 - License Update Restriction

    @Test
    public void testLicenseUpdateAllowedUnder10Years() {
        Driver driver = new Driver("23@@!!@@AB", "John", 5, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        driver.setLicenseType("Light");
        assertEquals("Light", driver.getLicenseType());
    }

    @Test
    public void testLicenseUpdateBlockedOver10Years() {
        Driver driver = new Driver("23@@!!@@AB", "John", 11, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        assertThrows(UnsupportedOperationException.class, () ->
                driver.setLicenseType("Light"));
    }

    @Test
    public void testLicenseUpdateBlockedExactly11Years() {
        Driver driver = new Driver("23@@!!@@AB", "John", 11, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        assertThrows(UnsupportedOperationException.class, () ->
                driver.setLicenseType("Medium"));
    }

    // D5 - Immutable Fields

    @Test
    public void testDriverIDCannotBeChanged() {
        Driver driver = new Driver("23@@!!@@AB", "John", 5, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        assertThrows(UnsupportedOperationException.class, () ->
                driver.setDriverID("99@@!!@@ZZ"));
    }

    @Test
    public void testNameCannotBeChanged() {
        Driver driver = new Driver("23@@!!@@AB", "John", 5, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        assertThrows(UnsupportedOperationException.class, () ->
                driver.setName("Jane"));
    }

    @Test
    public void testDriverIDRemainsAfterUpdate() {
        Driver driver = new Driver("23@@!!@@AB", "John", 5, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        assertThrows(UnsupportedOperationException.class, () ->
                driver.setDriverID("55@@!!@@ZZ"));
        assertEquals("23@@!!@@AB", driver.getDriverID());
    }
}