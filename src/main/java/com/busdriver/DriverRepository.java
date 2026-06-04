package com.busdriver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DriverRepository {

    private String filePath;
    private List<Driver> drivers;

    public DriverRepository(String filePath) {
        this.filePath = filePath;
        this.drivers = new ArrayList<>();
        loadFromFile();
    }

    public boolean add(Driver driver) {
        for (Driver d : drivers) {
            if (d.getDriverID().equals(driver.getDriverID())) {
                return false;
            }
        }
        drivers.add(driver);
        saveToFile();
        return true;
    }

    public Driver retrieve(String driverID) {
        for (Driver d : drivers) {
            if (d.getDriverID().equals(driverID)) {
                return d;
            }
        }
        return null;
    }

    public boolean update(String driverID, String newAddress, String newLicenseType, int newExperienceYears) {
        Driver driver = retrieve(driverID);
        if (driver == null) return false;

        if (newAddress != null && !newAddress.isEmpty()) {
            driver.setAddress(newAddress);
        }
        if (newLicenseType != null && !newLicenseType.isEmpty()) {
            driver.setLicenseType(newLicenseType);
        }
        driver.setExperienceYears(newExperienceYears);
        saveToFile();
        return true;
    }

    public int count() {
        return drivers.size();
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Driver d : drivers) {
                writer.write(d.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 6);
                if (parts.length == 6) {
                    Driver d = new Driver(parts[0], parts[1],
                            Integer.parseInt(parts[2]), parts[3], parts[4], parts[5]);
                    drivers.add(d);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}