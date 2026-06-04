package com.busdriver;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a bus driver in the Intelligent Bus Driver Guidance System.
 * Enforces all driver conditions D1 through D5.
 */
public class Driver {

    // Driver attributes
    private String driverID;
    private String name;
    private int experienceYears;
    private String licenseType; // Light, Medium, Heavy, PublicTransport
    private String address;
    private String birthdate; // Format: DD-MM-YYYY

    /**
     * Constructor to create a new Driver object.
     * Validates driverID, address, and birthdate on creation.
     */
    public Driver(String driverID, String name, int experienceYears,
                  String licenseType, String address, String birthdate) {

        // D1: Validate driver ID format
        if (!isValidDriverID(driverID)) {
            throw new IllegalArgumentException("Invalid driverID: " + driverID);
        }

        // D2: Validate address format
        if (!isValidAddress(address)) {
            throw new IllegalArgumentException("Invalid address format: " + address);
        }

        // D3: Validate birthdate format
        if (!isValidBirthdate(birthdate)) {
            throw new IllegalArgumentException("Invalid birthdate format: " + birthdate);
        }

        this.driverID = driverID;
        this.name = name;
        this.experienceYears = experienceYears;
        this.licenseType = licenseType;
        this.address = address;
        this.birthdate = birthdate;
    }

    // ─── Getters ───────────────────────────────────────────────────────────────

    public String getDriverID() { return driverID; }
    public String getName() { return name; }
    public int getExperienceYears() { return experienceYears; }
    public String getLicenseType() { return licenseType; }
    public String getAddress() { return address; }
    public String getBirthdate() { return birthdate; }

    // ─── Setters with update restrictions ──────────────────────────────────────

    /**
     * D5: driverID cannot be modified after creation.
     */
    public void setDriverID(String driverID) {
        throw new UnsupportedOperationException("driverID cannot be modified.");
    }

    /**
     * D5: name cannot be modified after creation.
     */
    public void setName(String name) {
        throw new UnsupportedOperationException("name cannot be modified.");
    }

    /**
     * Updates the license type.
     * D4: If driver has more than 10 years experience, licenseType cannot change.
     */
    public void setLicenseType(String licenseType) {
        if (this.experienceYears > 10) {
            throw new UnsupportedOperationException(
                "Cannot change licenseType for drivers with more than 10 years of experience.");
        }
        this.licenseType = licenseType;
    }

    /**
     * Updates the address.
     * D2: Must follow Street Number|Street Name|City|State|Country format.
     */
    public void setAddress(String address) {
        if (!isValidAddress(address)) {
            throw new IllegalArgumentException("Invalid address format: " + address);
        }
        this.address = address;
    }

    /**
     * Updates experience years.
     */
    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    // ─── Validation Methods ────────────────────────────────────────────────────

    /**
     * D1: Validates driver ID rules.
     * - Must be exactly 10 characters long
     * - First two characters must be digits between 2 and 9
     * - At least two special characters between characters 3 and 8 (index 2-7)
     * - Last two characters must be uppercase letters A-Z
     */
    public static boolean isValidDriverID(String id) {
        if (id == null || id.length() != 10) return false;

        // First two characters must be digits 2-9
        if (!Character.isDigit(id.charAt(0)) || !Character.isDigit(id.charAt(1))) return false;
        if (id.charAt(0) < '2' || id.charAt(0) > '9') return false;
        if (id.charAt(1) < '2' || id.charAt(1) > '9') return false;

        // Last two characters must be uppercase letters
        if (!Character.isUpperCase(id.charAt(8)) || !Character.isUpperCase(id.charAt(9))) return false;

        // At least two special characters between index 2 and 7 (inclusive)
        int specialCount = 0;
        for (int i = 2; i <= 7; i++) {
            char c = id.charAt(i);
            if (!Character.isLetterOrDigit(c)) {
                specialCount++;
            }
        }
        return specialCount >= 2;
    }

    /**
     * D2: Validates address format.
     * Must follow: Street Number|Street Name|City|State|Country
     */
    public static boolean isValidAddress(String address) {
        if (address == null) return false;
        String[] parts = address.split("\\|");
        return parts.length == 5 && 
               parts[0].trim().length() > 0 &&
               parts[1].trim().length() > 0 &&
               parts[2].trim().length() > 0 &&
               parts[3].trim().length() > 0 &&
               parts[4].trim().length() > 0;
    }

    /**
     * D3: Validates birthdate format DD-MM-YYYY.
     */
    public static boolean isValidBirthdate(String birthdate) {
        if (birthdate == null) return false;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate.parse(birthdate, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Calculates the driver's age from birthdate.
     */
    public int getAge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dob = LocalDate.parse(this.birthdate, formatter);
        return Period.between(dob, LocalDate.now()).getYears();
    }

    /**
     * Returns a string representation for file storage.
     */
    @Override
    public String toString() {
        return driverID + "," + name + "," + experienceYears + "," +
               licenseType + "," + address + "," + birthdate;
    }
}