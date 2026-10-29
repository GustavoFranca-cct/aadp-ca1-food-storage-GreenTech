package org.myfood.util;

import java.util.NoSuchElementException;
import java.util.Scanner;

public final class InputUtils {

    private InputUtils() {
        // Utility class: no objects are needed.
    }

    public static String readString(Scanner sc, String prompt) {
        System.out.print(prompt);

        if (!sc.hasNextLine()) {
            throw new NoSuchElementException("Input stream closed.");
        }

        return sc.nextLine().trim();
    }

    public static int readInt(Scanner sc, String prompt) {
        while (true) {
            String input = readString(sc, prompt);

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    public static float readFloat(Scanner sc, String prompt) {
        while (true) {
            String input = readString(sc, prompt);

            try {
                float value = Float.parseFloat(input);

                if (Float.isFinite(value) && value > 0) {
                    return value;
                }

                System.out.println(
                    "Weight must be a positive, finite number."
                );
            } catch (NumberFormatException e) {
                System.out.println(
                    "Please enter a number, for example 250 or 250.5."
                );
            }
        }
    }

    public static String readFoodName(Scanner sc, String prompt) {
        String[] allowedNames = {
            "Burger", "Pizza", "Fries", "Sandwich", "Hotdog"
        };

        while (true) {
            String input = readString(sc, prompt);

            for (String allowed : allowedNames) {
                if (allowed.equalsIgnoreCase(input)) {
                    return allowed;
                }
            }

            System.out.println(
                "Choose Burger, Pizza, Fries, Sandwich or Hotdog."
            );
        }
    }

    public static String readBestBefore(Scanner sc, String prompt) {
        // Food.setBBF performs the date validation.
        return readString(sc, prompt);
    }

    public static int readFoodId(
            Scanner sc, String prompt, int defaultId) {

        while (true) {
            String input = readString(sc, prompt);

            if (input.isEmpty()) {
                return defaultId;
            }

            try {
                int id = Integer.parseInt(input);

                if (id > 0) {
                    return id;
                }

                System.out.println("Food ID must be positive.");
            } catch (NumberFormatException e) {
                System.out.println(
                    "Enter a positive whole number or press Enter."
                );
            }
        }
    }
}