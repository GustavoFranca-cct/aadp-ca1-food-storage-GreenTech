package org.myfood;

import java.util.Scanner;
import org.myfood.util.FoodStorage;
import org.myfood.util.InputUtils;

public class Main {

    public static void main(String[] args) {

        FoodStorage storage = new FoodStorage();
        boolean isRunning = true;

        System.out.println("----------------------------------");
        System.out.println("      Food Storage terminal");
        System.out.println("----------------------------------");

        // Create one Scanner for the entire session.
        try (Scanner sc = new Scanner(System.in)) {
            while (isRunning) {

                System.out.println("\n1. Add food");
                System.out.println("2. Display all food");
                System.out.println("3. Search food by name");
                System.out.println("4. Remove oldest food (FIFO)");
                System.out.println("5. Display oldest food");
                System.out.println("9. Exit");

                // Exit safely if the input stream closes.
                if (!sc.hasNextLine()) {
                    break;
                }

                int selector = InputUtils.readInt(sc, "Choose an option: ");

                switch (selector) {
                    case 1:
                        try {
                            String name = InputUtils.readFoodName(sc, "Enter food name (Burger, Pizza, Fries, Sandwich, Hotdog): ");
                            if (name.isEmpty()) {
                                isRunning = false;
                                break;
                            }

                            float weight = InputUtils.readFloat(sc, "Enter weight in grams: ");
                            if (weight <= 0) {
                                isRunning = false;
                                break;
                            }

                            String bbf = InputUtils.readBestBefore(sc, "Enter best-before date (yyyy-MM-dd or 14d, 2w): ");
                            if (bbf.isEmpty()) {
                                isRunning = false;
                                break;
                            }

                            int defaultId = storage.getNextDefaultId();
                            int id = InputUtils.readFoodId(sc, "Enter food ID (press Enter for default " + defaultId + "): ", defaultId);
                            if (id <= 0) {
                                isRunning = false;
                                break;
                            }

                            if (id != defaultId && storage.containsId(id)) {
                                System.out.println("ID " + id + " is already in use. Falling back to default ID: " + defaultId);
                                id = defaultId;
                            }

                            Food food = new Food(name, weight, bbf, id);
                            storage.addFood(food);
                            System.out.println("Food added successfully! (ID: " + id + ")");

                        } catch (IllegalArgumentException | IllegalStateException e) {
                            System.out.println("Could not add food: " + e.getMessage());
                        }
                        break;

                    case 2:
                        storage.displayAll();
                        break;

                    case 3:
                        String searchName = InputUtils.readString(sc, "Enter the food name to search: ");
                        if (searchName.isEmpty()) {
                            isRunning = false;
                            break;
                        }

                        try {
                            Food[] results = storage.searchByName(searchName);

                            if (results.length == 0) {
                                System.out.println("No matching food found.");
                            } else {
                                System.out.println("Search results:");
                                for (Food food : results) {
                                    System.out.println("ID: " + food.getID()
                                            + " | Name: " + food.getName()
                                            + " | Weight: " + food.getWeight() + " g"
                                            + " | Best-before: " + food.getBBF());
                                }
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Search error: " + e.getMessage());
                        }
                        break;

                    case 4:
                        try {
                            Food removedFood = storage.removeFood();
                            System.out.println("Removed oldest food: " + removedFood.getName()
                                    + " (ID: " + removedFood.getID() + ")");
                        } catch (IllegalStateException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 5:
                        try {
                            Food oldestFood = storage.peekFront();
                            System.out.println("Oldest food: " + oldestFood.getName()
                                    + " (ID: " + oldestFood.getID() + ")");
                        } catch (IllegalStateException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 9:
                        isRunning = false;
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }

        System.out.println("Exiting...");
    }
}
