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
                System.out.println("5. Remove newest food (LIFO / Stack)");
                System.out.println("6. Display food at the top");
                System.out.println("7. Display oldest food");
                System.out.println("9. Exit");

                try {
                    int choice = InputUtils.readInt(
                        sc, "Choose an option: "
                    );

                    switch (choice) {
                        case 1:
                            storage.addFoodFromInput(sc);
                            break;

                        case 2:
                            storage.displayAll();
                            break;

                        case 3:
                            search.searchFood(sc, storage);
                            break;

                        case 4:
                            Food removedFifo = storage.removeFood();

                            System.out.println(
                                "Removed oldest food: "
                                + removedFifo.getName()
                                + " (ID: " + removedFifo.getID() + ")"
                            );
                            break;

                        case 5:
                            Food removedLifo = storage.removeFoodStack();

                            System.out.println(
                                "Removed newest food: "
                                + removedLifo.getName()
                                + " (ID: " + removedLifo.getID() + ")"
                            );
                            break;

                        case 6:
                            System.out.println("Food at the top:");
                            FoodStorage.displayFood(
                                storage.peekTop()
                            );
                            break;

                        case 7:
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
                                "Invalid option. Choose 1, 2, 3, 4, 5, 6, 7 or 9."
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