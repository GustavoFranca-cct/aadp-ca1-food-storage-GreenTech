package org.myfood.util;

import org.myfood.Food;

/**
 * Food storage with a maximum capacity of eight trays.
 *
 * @author marlon_k
 */
public class FoodStorage {

    private static final int CAPACITY = 8;

    private Food[] items;
    private int front;
    private int rear;
    private int size;

    // Constructor
    public FoodStorage() {
        items = new Food[CAPACITY];
        front = 0;
        rear = -1;
        size = 0;
    }

    // Checks if the storage is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Checks if the storage is full
    public boolean isFull() {
        return size == CAPACITY;
    }

    // Returns the number of stored items
    public int getSize() {
        return size;
    }

    /**
     * Finds all stored foods with the requested name.
     * The search does not remove or reorder any food.
     *
     * @param searchName the food name to find
     * @return an array containing all matching foods,
     *         or an empty array when no matches exist
     * @throws IllegalArgumentException if the search name is blank or null
     */
    public Food[] searchByName(String searchName) {
        if (searchName == null || searchName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Search name cannot be empty."
            );
        }

        String cleanedName = searchName.trim();

        Food[] matches = new Food[CAPACITY];
        int matchCount = 0;

        // Check every array position without modifying storage.
        for (int i = 0; i < items.length; i++) {
            Food food = items[i];

            if (food != null
                    && cleanedName.equalsIgnoreCase(food.getName())) {
                matches[matchCount] = food;
                matchCount++;
            }
        }

        // Return only the matching entries, with no unused positions.
        Food[] results = new Food[matchCount];

        for (int i = 0; i < matchCount; i++) {
            results[i] = matches[i];
        }

        return results;
    }
}