package org.myfood;

public class Food {

    // Fields: information stored in each Food object
    private String name;
    private Float weight;
    private String BBF;
    private Integer ID;

    // CONSTRUCTOR: runs when you create a new Food object
    public Food(String name, Float weight, String bestBeforeDate, Integer ID) {
        setName(name); // Validate the name before storing it
        this.weight = weight;
        this.BBF = bestBeforeDate;
        this.ID = ID;
    }

    // Return the food name
    public String getName() {
        return name;
    }

    // Validate and store the food name
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
        this.weight = weight;
    }

    public String getBBF() {
        return BBF;
    }

    public void setBBF(String BBF) {
        this.BBF = BBF;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
}