package org.myfood.util;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * utilities for reading console inputs.
 */
public final class InputUtils {

    private InputUtils() {
        // Prevent instantiation
    }


//     Prompts for and reads a valid food name matching the foodName enum.
    public static String readFoodName(Scanner sc, String prompt) {
        System.out.print(prompt);
        return readFoodName(sc);
    }


//     Reads and validates a food name matching the foodName enum.
//     Re-prompts the user until a valid food name is entered or stream ends.
    public static String readFoodName(Scanner sc) {
        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();
            try {
                foodName fn = foodName.valueOf(input.toUpperCase());
                return fn.name().charAt(0) + fn.name().substring(1).toLowerCase();
            } catch (IllegalArgumentException e) {
                System.out.print("Invalid food name. Choose Burger, Pizza, Fries, Sandwich, or Hotdog: ");
            }
        }
        return "";
    }


//     Prompts for and reads a positive floating-point number.

    public static float readFloat(Scanner sc, String prompt) {
        System.out.print(prompt);
        return readFloat(sc);
    }


//     Reads and validates a positive floating-point number.
    public static float readFloat(Scanner sc) {
        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();
            try {
                float value = Float.parseFloat(input);
                if (value <= 0 || !Float.isFinite(value)) {
                    System.out.print("Weight must be greater than 0. Please enter a valid weight: ");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Please enter a valid weight in grams: ");
            }
        }
        return -1f;
    }

//      Prompts for and reads an integer.
    public static int readInt(Scanner sc, String prompt) {
        System.out.print(prompt);
        return readInt(sc);
    }


//     Reads and validates an integer.
    public static int readInt(Scanner sc) {
        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
        return -1;
    }



//     Prompts for and reads a food ID, using defaultId if the user presses enter with blank input.
    public static int readFoodId(Scanner sc, String prompt, int defaultId) {
        System.out.print(prompt);
        return readFoodId(sc, defaultId);
    }


//     Reads a food ID, using defaultId if the user presses enter with blank input.
    public static int readFoodId(Scanner sc, int defaultId) {
        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                return defaultId;
            }
            try {
                int id = Integer.parseInt(input);
                if (id <= 0) {
                    System.out.print("ID must be positive. Please enter a valid food ID (or press Enter for default " + defaultId + "): ");
                    continue;
                }
                return id;
            } catch (NumberFormatException e) {
                System.out.print("Invalid ID. Please enter a valid whole number (or press Enter for default " + defaultId + "): ");
            }
        }
        return -1;
    }

    // Prompts for and reads a positive food ID.
    public static int readFoodId(Scanner sc, String prompt) {
        System.out.print(prompt);
        return readFoodId(sc);
    }

    // Reads and validates a positive food ID.
    public static int readFoodId(Scanner sc) {
        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();
            try {
                int id = Integer.parseInt(input);
                if (id <= 0) {
                    System.out.print("ID must be positive. Please enter a valid food ID: ");
                    continue;
                }
                return id;
            } catch (NumberFormatException e) {
                System.out.print("Invalid ID. Please enter a valid whole number: ");
            }
        }
        return -1;
    }

    public static String readBestBefore(Scanner sc, String prompt) {
        System.out.print(prompt);
        return readBestBefore(sc);
    }


    /**
     * Reads the best-before date string.
     * Supports either format (yyyy-MM-dd) or shorthands like "2w" (2 weeks) or "14d" (14 days).
     */
    public static String readBestBefore(Scanner sc) {
        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                if (input.matches("(?i)^[0-9]+\\s*[dw]$")) {
                    char unit = Character.toLowerCase(input.charAt(input.length() - 1));
                    String numPart = input.substring(0, input.length() - 1).trim();
                    try {
                        int amount = Integer.parseInt(numPart);
                        LocalDate calculated = (unit == 'w') ? LocalDate.now().plusWeeks(amount) : LocalDate.now().plusDays(amount);
                        return calculated.toString();
                    } catch (NumberFormatException ignored) {
                    }
                }
                return input;
            }
            System.out.print("Date cannot be empty. Please enter best-before date (yyyy-MM-dd or e.g. 14d, 2w): ");
        }
        return "";
    }

    /**
     * Prompts for and reads a non-empty string.
     */
    public static String readString(Scanner sc, String prompt) {
        System.out.print(prompt);
        return readString(sc);
    }

    public static String readString(Scanner sc) {
        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.print("Input cannot be empty. Please try again: ");
        }
        return "";
    }
}
