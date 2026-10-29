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

    // Checks if the storage is empty.
    public boolean isEmpty() {
        return size == 0;
    }

    // Checks if the storage is full.
    public boolean isFull() {
        return size == CAPACITY;
    }

    // Returns the number of stored items.
    public int getSize() {
        return size;
    }

    // Adds a food item to the rear of the queue.
    public void addFood(Food food) {
        if (food == null) {
            throw new IllegalArgumentException(
                "Food cannot be null."
            );
        }

        if (isFull()) {
            throw new IllegalStateException(
                "Storage is full. Maximum capacity is 8."
            );
        }

        rear = (rear + 1) % CAPACITY;
        items[rear] = food;
        size++;
    }

    // Removes and returns the oldest food item.
    public Food removeFood() {
        if (isEmpty()) {
            throw new IllegalStateException(
                "Storage is empty. No food to remove."
            );
        }

        Food removedFood = items[front];

        // Clears the position so removed food is not found in searches.
        items[front] = null;

        front = (front + 1) % CAPACITY;
        size--;

        if (isEmpty()) {
            front = 0;
            rear = -1;
        }

        return removedFood;
    }

    // Returns the oldest food item without removing it.
    public Food peekFront() {
        if (isEmpty()) {
            throw new IllegalStateException(
                "Storage is empty. No food to display."
            );
        }

        return items[front];
    }

    // Displays all food items from oldest to newest.
    public void displayAll() {
        if (isEmpty()) {
            System.out.println("Storage is empty.");
            return;
        }

        System.out.println("Food items in storage:");

        for (int i = 0; i < size; i++) {
            int index = (front + i) % CAPACITY;
            Food food = items[index];

            System.out.println("--------------------");
            System.out.println("ID: " + food.getID());
            System.out.println("Name: " + food.getName());
            System.out.println("Weight: " + food.getWeight() + " g");
            System.out.println("Best-before: " + food.getBBF());
        }
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
