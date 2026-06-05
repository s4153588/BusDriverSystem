package com.busdriver;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BusTest {

    // B1 - Bus ID Rules

    @Test
    public void test_2_1_01_ValidBusIDAccepted() {
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        assertEquals("12345678", bus.getBusID());
    }

    @Test
    public void test_2_1_02_BusIDFewerThan8DigitsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Bus("1234", 40, 80.0, "Diesel"));
    }

    @Test
    public void test_2_1_03_BusIDContainingLettersRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Bus("1234567A", 40, 80.0, "Diesel"));
    }

    @Test
    public void test_2_1_04_BusIDMoreThan8DigitsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Bus("123456789", 40, 80.0, "Diesel"));
    }

    @Test
    public void test_2_1_05_BusIDAllZerosAccepted() {
        Bus bus = new Bus("00000000", 40, 80.0, "Diesel");
        assertEquals("00000000", bus.getBusID());
    }

    // B2 - Capacity Update Restriction

    @Test
    public void test_2_2_01_BusCapacityCanDecrease() {
        Bus bus = new Bus("12345678", 50, 80.0, "Diesel");
        bus.setCapacity(40);
        assertEquals(40, bus.getCapacity());
    }

    @Test
    public void test_2_2_02_BusCapacityCannotIncrease() {
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        assertThrows(IllegalArgumentException.class, () ->
                bus.setCapacity(50));
    }

    @Test
    public void test_2_2_03_BusCapacityCanRemainSame() {
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        bus.setCapacity(40);
        assertEquals(40, bus.getCapacity());
    }

    // B3 - Driver Age Restriction

    @Test
    public void test_2_3_01_DriverOver50CannotDriveLargeBus() {
        Bus bus = new Bus("12345678", 50, 80.0, "Diesel");
        Driver driver = new Driver("56!@21ABCD", "John Smith", 5, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1970");
        assertFalse(bus.isDriverAgeAllowed(driver));
    }

    @Test
    public void test_2_3_02_DriverUnder50CanDriveLargeBus() {
        Bus bus = new Bus("12345678", 50, 80.0, "Diesel");
        Driver driver = new Driver("56!@21ABCD", "John Smith", 5, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990");
        assertTrue(bus.isDriverAgeAllowed(driver));
    }

    @Test
    public void test_2_3_03_DriverOver50CanDriveSmallBus() {
        Bus bus = new Bus("12345678", 30, 80.0, "Diesel");
        Driver driver = new Driver("56!@21ABCD", "John Smith", 5, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1970");
        assertTrue(bus.isDriverAgeAllowed(driver));
    }

    // B4 - Electric Bus Restriction

    @Test
    public void test_2_4_01_DriverWith5YearsCanDriveElectric() {
        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");
        Driver driver = new Driver("56!@21ABCD", "John Smith", 5, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990");
        assertTrue(bus.isDriverExperienceAllowed(driver));
    }

    @Test
    public void test_2_4_02_DriverUnder5YearsCannotDriveElectric() {
        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");
        Driver driver = new Driver("56!@21ABCD", "John Smith", 3, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990");
        assertFalse(bus.isDriverExperienceAllowed(driver));
    }

    @Test
    public void test_2_4_03_DriverUnder5YearsCanDriveDiesel() {
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        Driver driver = new Driver("56!@21ABCD", "John Smith", 3, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990");
        assertTrue(bus.isDriverExperienceAllowed(driver));
    }

    // B5 - Driver Licence Restriction

    @Test
    public void test_2_5_01_HeavyLicenceCanDriveElectric() {
        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");
        Driver driver = new Driver("56!@21ABCD", "John Smith", 5, "Heavy",
                "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990");
        assertTrue(bus.isDriverLicenceAllowed(driver));
    }

    @Test
    public void test_2_5_02_PublicTransportLicenceCanDriveHybrid() {
        Bus bus = new Bus("12345678", 40, 80.0, "Hybrid");
        Driver driver = new Driver("56!@21ABCD", "John Smith", 5, "PublicTransport",
                "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990");
        assertTrue(bus.isDriverLicenceAllowed(driver));
    }

    @Test
    public void test_2_5_03_LightLicenceCannotDriveElectric() {
        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");
        Driver driver = new Driver("56!@21ABCD", "John Smith", 5, "Light",
                "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990");
        assertFalse(bus.isDriverLicenceAllowed(driver));
    }

    @Test
    public void test_2_5_04_LightLicenceCanDriveDiesel() {
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        Driver driver = new Driver("56!@21ABCD", "John Smith", 5, "Light",
                "124|La Trobe St|Melbourne|VIC|Australia", "01-01-1990");
        assertTrue(bus.isDriverLicenceAllowed(driver));
    }
}