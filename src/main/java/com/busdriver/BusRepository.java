package com.busdriver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BusRepository {

    private String filePath;
    private List<Bus> buses;

    public BusRepository(String filePath) {
        this.filePath = filePath;
        this.buses = new ArrayList<>();
        loadFromFile();
    }

    public boolean add(Bus bus) {
        for (Bus b : buses) {
            if (b.getBusID().equals(bus.getBusID())) {
                return false;
            }
        }
        buses.add(bus);
        saveToFile();
        return true;
    }

    public Bus retrieve(String busID) {
        for (Bus b : buses) {
            if (b.getBusID().equals(busID)) {
                return b;
            }
        }
        return null;
    }

    public boolean update(String busID, int newCapacity, double newFuelLevel, String newFuelType) {
        Bus bus = retrieve(busID);
        if (bus == null) return false;

        if (newCapacity >= 0) {
            bus.setCapacity(newCapacity);
        }
        bus.setFuelLevel(newFuelLevel);
        if (newFuelType != null && !newFuelType.isEmpty()) {
            bus.setFuelType(newFuelType);
        }
        saveToFile();
        return true;
    }

    public int count() {
        return buses.size();
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Bus b : buses) {
                writer.write(b.toString());
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
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Bus b = new Bus(parts[0], Integer.parseInt(parts[1]),
                            Double.parseDouble(parts[2]), parts[3]);
                    buses.add(b);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}