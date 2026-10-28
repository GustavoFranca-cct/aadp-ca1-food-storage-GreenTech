package org.myfood;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Food {

    private String name;
    private Float weight;
    private String BBF;
    private Integer ID;

    // CONSTRUCTOR: validates the supplied values.
    public Food(String name, Float weight, String bestBeforeDate, Integer ID) {
        setName(name);
        setWeight(weight);
        setBBF(bestBeforeDate);
        this.ID = ID;
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

        String cleanedName = name.trim();

        String[] allowedNames = {
            "Burger", "Pizza", "Fries", "Sandwich", "Hotdog"
        };

        for (String allowedName : allowedNames) {
            if (allowedName.equalsIgnoreCase(cleanedName)) {
                this.name = allowedName;
                return;
            }
        }

        throw new IllegalArgumentException(
            "Invalid food name. Choose Burger, Pizza, Fries, Sandwich or Hotdog."
        );
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        if (weight == null || !Float.isFinite(weight) || weight <= 0) {
            throw new IllegalArgumentException(
                "Weight must be a positive, finite number in grams."
            );
        }

        this.weight = weight;
    }

    public String getBBF() {
        return BBF;
    }

    public void setBBF(String BBF) {
        if (BBF == null || BBF.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Best-before date cannot be empty."
            );
        }

        String cleanedDate = BBF.trim();

        if (!cleanedDate.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
            throw new IllegalArgumentException(
                "Use the date format yyyy-MM-dd."
            );
        }

        LocalDate bestBeforeDate;

        try {
            bestBeforeDate = LocalDate.parse(cleanedDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                "Best-before date must be a real calendar date.", e
            );
        }

        LocalDate today = LocalDate.now();
        LocalDate latestAllowed = today.plusDays(14);

        if (bestBeforeDate.isBefore(today)) {
            throw new IllegalArgumentException(
                "Best-before date cannot be in the past."
            );
        }

        if (bestBeforeDate.isAfter(latestAllowed)) {
            throw new IllegalArgumentException(
                "Best-before date cannot be more than 14 days from today."
            );
        }

        this.BBF = bestBeforeDate.toString();
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
}