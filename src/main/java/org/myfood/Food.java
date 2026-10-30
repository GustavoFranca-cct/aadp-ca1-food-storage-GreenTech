package org.myfood;

import org.myfood.util.FoodStorage;
import org.myfood.util.InputUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Food {

    private String name;
    private Float weight;
    private String BBF;
    private Integer ID;
    private LocalDateTime storedAt;

    // CONSTRUCTOR: runs when a new Food object is created.
    public Food(String name, Float weight,
                String bestBeforeDate, Integer ID) {
        setName(name);
        setWeight(weight);
        setBBF(bestBeforeDate);
        setID(ID);
    }

    public static void addFood(Scanner sc, FoodStorage storage) {

        // Check capacity before asking for food information.
        if (storage.isFull()) {
            System.out.println(
                    "Storage is full. Maximum capacity is 8."
            );
            return;
        }

        String name = InputUtils.readFoodName(
                sc,
                "Enter food name (Burger, Pizza, Fries, Sandwich, Hotdog): "
        );

        float weight = InputUtils.readFloat(
                sc, "Enter weight in grams: "
        );

        String bestBefore = InputUtils.readBestBefore(
                sc, "Enter best-before date (yyyy-MM-dd, 14d or 2w): "
        );

        int defaultId = storage.getNextDefaultId();

        int id = InputUtils.readFoodId(
                sc,
                "Enter food ID (Enter for default " + defaultId + "): ",
                defaultId
        );

        if (storage.containsId(id)) {
            System.out.println(
                    "ID " + id + " is already in use. Using " + defaultId + "."
            );

            id = defaultId;
        }

        // The constructor validates before storage is modified.
        Food food = new Food(name, weight, bestBefore, id);

        storage.addFood(food);

        System.out.println(
                "Food added successfully! (ID: " + food.getID() + ")"
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Food name cannot be empty."
            );
        }

        String[] allowedNames = {
            "Burger", "Pizza", "Fries", "Sandwich", "Hotdog"
        };

        for (String allowedName : allowedNames) {
            if (allowedName.equalsIgnoreCase(name.trim())) {
                this.name = allowedName;
                return;
            }
        }

        throw new IllegalArgumentException(
            "Choose Burger, Pizza, Fries, Sandwich or Hotdog."
        );
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        if (weight == null
                || !Float.isFinite(weight)
                || weight <= 0) {
            throw new IllegalArgumentException(
                "Weight must be a positive, finite number in grams."
            );
        }

        this.weight = weight;
    }

    public String getBBF() {
        return BBF;
    }

    public void setBBF(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Best-before date cannot be empty."
            );
        }

        String cleaned = value.trim();
        LocalDate today = LocalDate.now();
        LocalDate date;

        if (cleaned.matches("(?i)[0-9]+\\s*[dw]")) {
            char unit = Character.toLowerCase(
                cleaned.charAt(cleaned.length() - 1)
            );

            String number = cleaned.substring(
                0, cleaned.length() - 1
            ).trim();

            int amount;

            try {
                amount = Integer.parseInt(number);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                    "Date shorthand is too large.", e
                );
            }

            int maximum = unit == 'w' ? 2 : 14;

            if (amount > maximum) {
                throw new IllegalArgumentException(
                    "Best-before date cannot exceed 14 days from today."
                );
            }

            date = unit == 'w'
                ? today.plusWeeks(amount)
                : today.plusDays(amount);

        } else {
            if (!cleaned.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
                throw new IllegalArgumentException(
                    "Use yyyy-MM-dd or shorthand such as 14d or 2w."
                );
            }

            try {
                date = LocalDate.parse(cleaned);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(
                    "Best-before date must be a real calendar date.", e
                );
            }
        }

        if (date.isBefore(today)) {
            throw new IllegalArgumentException(
                "Best-before date cannot be in the past."
            );
        }

        if (date.isAfter(today.plusDays(14))) {
            throw new IllegalArgumentException(
                "Best-before date cannot exceed 14 days from today."
            );
        }

        this.BBF = date.toString();
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        if (ID == null || ID <= 0) {
            throw new IllegalArgumentException(
                "Food ID must be a positive integer."
            );
        }

        // Prevent changing a tray's ID after it has entered storage.
        if (storedAt != null) {
            throw new IllegalStateException(
                "The ID cannot be changed after storage."
            );
        }

        this.ID = ID;
    }

    public LocalDateTime getStoredAt() {
        return storedAt;
    }

    // Called by FoodStorage when the item is successfully added.
    public void markStoredNow() {
        storedAt = LocalDateTime.now();
    }
}