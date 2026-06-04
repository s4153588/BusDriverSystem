package com.busdriver;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BusTest {

    // B1 - Bus ID Rules

    @Test
    public void testValidBusID() {
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        assertEquals("12345678", bus.getBusID());
    }

    @Test
    public void testBusIDTooShort() {
        assertThrows(IllegalArgumentException.class, () ->
                new Bus("1234", 40, 80.0, "Diesel"));
    }

    @Test
    public void testBusIDContainsLetters() {
        assertThrows(IllegalArgumentException.class, () ->
                new Bus("1234567A", 40, 80.0, "Diesel"));
    }

    @Test
    public void testBusIDTooLong() {
        assertThrows(IllegalArgumentException.class, () ->
                new Bus("123456789", 40, 80.0, "Diesel"));
    }

    @Test
    public void testBusIDAllZeros() {
        Bus bus = new Bus("00000000", 40, 80.0, "Diesel");
        assertEquals("00000000", bus.getBusID());
    }

    // B2 - Capacity Update Restriction

    @Test
    public void testCapacityCanDecrease() {
        Bus bus = new Bus("12345678", 50, 80.0, "Diesel");
        bus.setCapacity(40);
        assertEquals(40, bus.getCapacity());
    }

    @Test
    public void testCapacityCannotIncrease() {
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        assertThrows(IllegalArgumentException.class, () ->
                bus.setCapacity(50));
    }

    @Test
    public void testCapacityCanRemainSame() {
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        bus.setCapacity(40);
        assertEquals(40, bus.getCapacity());
    }

    // B3 - Driver Age Restriction

    @Test
    public void testDriverOver50CannotDriveLargeBus() {
        Bus bus = new Bus("12345678", 50, 80.0, "Diesel");
        Driver driver = new Driver("23@@!!@@AB", "John", 5, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1970");
        assertFalse(bus.isDriverAgeAllowed(driver));
    }

    @Test
    public void testDriverUnder50CanDriveLargeBus() {
        Bus bus = new Bus("12345678", 50, 80.0, "Diesel");
        Driver driver = new Driver("23@@!!@@AB", "John", 5, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        assertTrue(bus.isDriverAgeAllowed(driver));
    }

    @Test
    public void testDriverOver50CanDriveSmallBus() {
        Bus bus = new Bus("12345678", 30, 80.0, "Diesel");
        Driver driver = new Driver("23@@!!@@AB", "John", 5, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1970");
        assertTrue(bus.isDriverAgeAllowed(driver));
    }

    // B4 - Electric Bus Restriction

    @Test
    public void testDriverWith5YearsCanDriveElectric() {
        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");
        Driver driver = new Driver("23@@!!@@AB", "John", 5, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        assertTrue(bus.isDriverExperienceAllowed(driver));
    }

    @Test
    public void testDriverUnder5YearsCannotDriveElectric() {
        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");
        Driver driver = new Driver("23@@!!@@AB", "John", 3, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        assertFalse(bus.isDriverExperienceAllowed(driver));
    }

    @Test
    public void testDriverUnder5YearsCanDriveDiesel() {
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        Driver driver = new Driver("23@@!!@@AB", "John", 3, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        assertTrue(bus.isDriverExperienceAllowed(driver));
    }

    // B5 - Driver Licence Restriction

    @Test
    public void testHeavyLicenceCanDriveElectric() {
        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");
        Driver driver = new Driver("23@@!!@@AB", "John", 5, "Heavy",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        assertTrue(bus.isDriverLicenceAllowed(driver));
    }

    @Test
    public void testPublicTransportLicenceCanDriveHybrid() {
        Bus bus = new Bus("12345678", 40, 80.0, "Hybrid");
        Driver driver = new Driver("23@@!!@@AB", "John", 5, "PublicTransport",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        assertTrue(bus.isDriverLicenceAllowed(driver));
    }

    @Test
    public void testLightLicenceCannotDriveElectric() {
        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");
        Driver driver = new Driver("23@@!!@@AB", "John", 5, "Light",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        assertFalse(bus.isDriverLicenceAllowed(driver));
    }

    @Test
    public void testLightLicenceCanDriveDiesel() {
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        Driver driver = new Driver("23@@!!@@AB", "John", 5, "Light",
                "12|Main St|Melbourne|VIC|Australia", "01-01-1990");
        assertTrue(bus.isDriverLicenceAllowed(driver));
    }
}