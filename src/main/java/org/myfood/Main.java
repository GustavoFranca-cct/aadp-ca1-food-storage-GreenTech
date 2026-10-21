package org.myfood;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        boolean isRunning = true;

        System.out.println("----------------------------------");
        System.out.println("      Food Storage terminal");
        System.out.println("----------------------------------");


//        Food myFood = new Food("batata", 1.5f, "2023-12-31", 1);


        try {
            while (isRunning) {

                Scanner sc = new Scanner(System.in);
                int selector = sc.nextInt();


                // TODO: Main menu selection

                switch (selector) {
                    case 1:
                        // Handle option 1 like add new food
                        break;
                    case 2:
                        // Handle option 2 like view list of food
                        break;


                    case 9:
                        System.out.println("Exiting...");
                        isRunning = false;
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }

            }

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }

    }
}
