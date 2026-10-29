package org.myfood.util;

import org.myfood.Food;

public class FoodStorage {

    private static final int CAPACITY = 8;

    private final Food[] items;
    private int front;
    private int rear;
    private int size;

    // Constructor: creates an empty queue.
    public FoodStorage() {
        items = new Food[CAPACITY];
        front = 0;
        rear = -1;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == CAPACITY;
    }

    public int getSize() {
        return size;
    }

    public boolean containsId(int id) {
        for (int i = 0; i < size; i++) {
            int index = (front + i) % CAPACITY;

            if (items[index].getID() == id) {
                return true;
            }
        }

        return false;
    }

    public int getNextDefaultId() {
        int candidate = 1;

        while (containsId(candidate)) {
            candidate++;
        }

        return candidate;
    }

    // Add to the rear of the queue.
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

        if (containsId(food.getID())) {
            throw new IllegalArgumentException(
                "Food ID " + food.getID() + " is already in use."
            );
        }

        food.markStoredNow();

        rear = (rear + 1) % CAPACITY;
        items[rear] = food;
        size++;
    }

    // Remove from the front: oldest food first.
    public Food removeFood() {
        if (isEmpty()) {
            throw new IllegalStateException(
                "Storage is empty. No food to remove."
            );
        }

        Food removed = items[front];
        items[front] = null;

        front = (front + 1) % CAPACITY;
        size--;

        if (isEmpty()) {
            front = 0;
            rear = -1;
        }

        return removed;
    }

    public Food peekFront() {
        if (isEmpty()) {
            throw new IllegalStateException(
                "Storage is empty. No food to display."
            );
        }

        return items[front];
    }

    public void displayAll() {
        if (isEmpty()) {
            System.out.println("Storage is empty.");
            return;
        }

        System.out.println(
            "Stored trays: " + size + "/" + CAPACITY
        );

        for (int i = 0; i < size; i++) {
            int index = (front + i) % CAPACITY;
            displayFood(items[index]);
        }
    }

    public static void displayFood(Food food) {
        System.out.println("--------------------");
        System.out.println("ID: " + food.getID());
        System.out.println("Name: " + food.getName());
        System.out.println("Weight: " + food.getWeight() + " g");
        System.out.println("Best-before: " + food.getBBF());
        System.out.println("Stored at: " + food.getStoredAt());
    }

    public Food[] searchByName(String searchName) {
        if (searchName == null || searchName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Search name cannot be empty."
            );
        }

        String cleanedName = searchName.trim();
        Food[] matches = new Food[size];
        int matchCount = 0;

        // Search in FIFO order, including after array wraparound.
        for (int i = 0; i < size; i++) {
            int index = (front + i) % CAPACITY;
            Food food = items[index];

            if (cleanedName.equalsIgnoreCase(food.getName())) {
                matches[matchCount] = food;
                matchCount++;
            }
        }

        Food[] results = new Food[matchCount];

        for (int i = 0; i < matchCount; i++) {
            results[i] = matches[i];
        }

        return results;
    }
}