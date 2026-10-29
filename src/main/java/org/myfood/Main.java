package org.myfood;

import java.util.Scanner;
import org.myfood.util.FoodStorage;

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
                System.out.print("Choose an option: ");

                // Exit safely if the input stream closes.
                if (!sc.hasNextLine()) {
                    break;
                }

                String input = sc.nextLine().trim();
                int selector;

                try {
                    selector = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid menu number.");
                    continue;
                }

                switch (selector) {
                    case 1:
                        try {
                            System.out.print("Enter food name (Burger, Pizza, Fries, Sandwich, Hotdog): ");
                            if (!sc.hasNextLine()) {
                                isRunning = false;
                                break;
                            }
                            String name = sc.nextLine();

                            System.out.print("Enter weight in grams: ");
                            if (!sc.hasNextLine()) {
                                isRunning = false;
                                break;
                            }
                            float weight = Float.parseFloat(sc.nextLine().trim());

                            System.out.print("Enter best-before date (yyyy-MM-dd): ");
                            if (!sc.hasNextLine()) {
                                isRunning = false;
                                break;
                            }
                            String bbf = sc.nextLine().trim();

                            System.out.print("Enter food ID: ");
                            if (!sc.hasNextLine()) {
                                isRunning = false;
                                break;
                            }
                            int id = Integer.parseInt(sc.nextLine().trim());

                            Food food = new Food(name, weight, bbf, id);
                            storage.addFood(food);
                            System.out.println("Food added successfully!");

                        } catch (IllegalArgumentException | IllegalStateException e) {
                            System.out.println("Could not add food: " + e.getMessage());
                        }
                        break;

                    case 2:
                        storage.displayAll();
                        break;

                    case 3:
                        System.out.print("Enter the food name to search: ");

                        if (!sc.hasNextLine()) {
                            isRunning = false;
                            break;
                        }

                        String searchName = sc.nextLine();

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
