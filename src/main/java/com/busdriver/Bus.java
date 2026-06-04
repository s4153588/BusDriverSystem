package com.busdriver;

/**
 * Represents a bus in the Intelligent Bus Driver Guidance System.
 * Enforces all bus conditions B1 through B5.
 */
public class Bus {

    // Bus attributes
    private String busID;
    private int capacity;
    private double fuelLevel;
    private String fuelType; // Diesel, Hybrid, Electricity

    /**
     * Constructor to create a new Bus object.
     * Validates busID on creation.
     */
    public Bus(String busID, int capacity, double fuelLevel, String fuelType) {

        // B1: Validate bus ID format
        if (!isValidBusID(busID)) {
            throw new IllegalArgumentException("Invalid busID: " + busID);
        }

        this.busID = busID;
        this.capacity = capacity;
        this.fuelLevel = fuelLevel;
        this.fuelType = fuelType;
    }

    // ─── Getters ───────────────────────────────────────────────────────────────

    public String getBusID() { return busID; }
    public int getCapacity() { return capacity; }
    public double getFuelLevel() { return fuelLevel; }
    public String getFuelType() { return fuelType; }

    // ─── Setters with update restrictions ──────────────────────────────────────

    /**
     * B2: Capacity cannot increase during update operations.
     * It can only decrease or stay the same.
     */
    public void setCapacity(int newCapacity) {
        if (newCapacity > this.capacity) {
            throw new IllegalArgumentException(
                "Bus capacity cannot be increased during update.");
        }
        this.capacity = newCapacity;
    }

    /**
     * Updates the fuel level.
     */
    public void setFuelLevel(double fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    /**
     * Updates the fuel type.
     */
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    // ─── Validation Methods ────────────────────────────────────────────────────

    /**
     * B1: Validates bus ID rules.
     * - Must be exactly 8 characters long
     * - All characters must be digits
     */
    public static boolean isValidBusID(String id) {
        if (id == null || id.length() != 8) return false;
        for (char c : id.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    /**
     * B3: Checks if a driver is allowed to drive this bus based on age.
     * Drivers older than 50 cannot drive buses with capacity of 50 or more.
     */
    public boolean isDriverAgeAllowed(Driver driver) {
        if (driver.getAge() > 50 && this.capacity >= 50) {
            return false;
        }
        return true;
    }

    /**
     * B4: Checks if a driver has enough experience for an electric bus.
     * Only drivers with at least 5 years of experience can drive electric buses.
     */
    public boolean isDriverExperienceAllowed(Driver driver) {
        if (this.fuelType.equals("Electricity") && driver.getExperienceYears() < 5) {
            return false;
        }
        return true;
    }

    /**
     * B5: Checks if a driver holds the correct licence for this bus.
     * Only Heavy or PublicTransport licence holders can drive Electric or Hybrid buses.
     */
    public boolean isDriverLicenceAllowed(Driver driver) {
        if (this.fuelType.equals("Electricity") || this.fuelType.equals("Hybrid")) {
            String licence = driver.getLicenseType();
            if (!licence.equals("Heavy") && !licence.equals("PublicTransport")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a string representation for file storage.
     */
    @Override
    public String toString() {
        return busID + "," + capacity + "," + fuelLevel + "," + fuelType;
    }
}