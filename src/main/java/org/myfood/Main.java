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

                System.out.println();
                System.out.println("--- Main Menu ---");
                System.out.println("1. Add food (not implemented yet)");
                System.out.println("2. Display food (not implemented yet)");
                System.out.println("3. Search food by name");
                System.out.println("9. Exit");
                System.out.print("Enter your choice: ");

                // Exit safely if the input stream closes.
                if (!sc.hasNextLine()) {
                    break;
                }

                String input = sc.nextLine().trim();
                int selector;

                try {
                    selector = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println(
                        "Invalid input. Please enter a menu number."
                    );
                    continue;
                }

                switch (selector) {

                    case 1:
                        System.out.println(
                            "Adding food is not implemented yet."
                        );
                        break;

                    case 2:
                        System.out.println(
                            "Displaying storage is not implemented yet."
                        );
                        break;

                    case 3:
                        System.out.print("Enter food name to search: ");

                        if (!sc.hasNextLine()) {
                            isRunning = false;
                            break;
                        }

                        String searchName = sc.nextLine();

                        try {
                            Food[] results =
                                storage.searchByName(searchName);

                            if (results.length == 0) {
                                System.out.println(
                                    "No matching food found."
                                );
                            } else {
                                System.out.println(
                                    "Matching trays: " + results.length
                                );

                                for (Food food : results) {
                                    System.out.println("--------------------");
                                    System.out.println(
                                        "ID: " + food.getID()
                                    );
                                    System.out.println(
                                        "Name: " + food.getName()
                                    );
                                    System.out.println(
                                        "Weight: " + food.getWeight() + " g"
                                    );
                                    System.out.println(
                                        "Best-before: " + food.getBBF()
                                    );
                                }
                            }

                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }

                        break;

                    case 9:
                        isRunning = false;
                        break;

                    default:
                        System.out.println(
                            "Invalid option. Choose 1, 2, 3 or 9."
                        );
                }
            }
        }

        System.out.println("Exiting...");
    }
}