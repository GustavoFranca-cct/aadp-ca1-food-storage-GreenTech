package org.myfood;

import java.util.NoSuchElementException;
import java.util.Scanner;
import org.myfood.util.FoodStorage;
import org.myfood.util.InputUtils;
import org.myfood.util.search;

public class Main {

    public static void main(String[] args) {
        FoodStorage storage = new FoodStorage();
        boolean isRunning = true;

        System.out.println("----------------------------------");
        System.out.println("      Food Storage terminal");
        System.out.println("----------------------------------");

        try (Scanner sc = new Scanner(System.in)) {
            while (isRunning) {
                System.out.println("\n--- Main Menu ---");
                System.out.println("1. Add food");
                System.out.println("2. Display all food");
                System.out.println("3. Search food by name");
                System.out.println("4. Remove oldest food (FIFO)");
                System.out.println("5. Display oldest food");
                System.out.println("9. Exit");

                try {
                    int choice = InputUtils.readInt(
                        sc, "Choose an option: "
                    );

                    switch (choice) {
                        case 1:
                            Food.addFood(sc, storage);
                            break;

                        case 2:
                            storage.displayAll();
                            break;

                        case 3:
                            search.searchFood(sc, storage);
                            break;

                        case 4:
                            Food removed = storage.removeFood();

                            System.out.println(
                                "Removed oldest food: "
                                + removed.getName()
                                + " (ID: " + removed.getID() + ")"
                            );
                            break;

                        case 5:
                            System.out.println("Oldest food:");
                            FoodStorage.displayFood(
                                storage.peekFront()
                            );
                            break;

                        case 9:
                            isRunning = false;
                            break;

                        default:
                            System.out.println(
                                "Invalid option. Choose 1, 2, 3, 4, 5 or 9."
                            );
                    }

                } catch (NoSuchElementException e) {
                    System.out.println("\nInput closed.");
                    isRunning = false;

                } catch (IllegalArgumentException
                         | IllegalStateException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("Exiting...");
    }
}