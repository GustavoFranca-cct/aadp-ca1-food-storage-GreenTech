package org.myfood;

import java.time.LocalDate;

public class FoodValidationTest {

    public static void main(String[] args) {

        String bestBefore = LocalDate.now().plusDays(7).toString();

        String[] validInputs = {
            "Burger", "pizza", "  FRIES  ", "Sandwich", "Hotdog"
        };

        String[] expectedNames = {
            "Burger", "Pizza", "Fries", "Sandwich", "Hotdog"
        };

        int passed = 0;
        int total = 0;

        // Test valid names through the constructor
        for (int i = 0; i < validInputs.length; i++) {
            total++;

            Food food = new Food(
                validInputs[i], 250.0f, bestBefore, i + 1
            );

            if (expectedNames[i].equals(food.getName())) {
                System.out.println("PASS: Valid name test " + (i + 1));
                passed++;
            } else {
                System.out.println("FAIL: Valid name test " + (i + 1));
            }
        }

        String[] invalidInputs = {"Sushi", "", "   ", null};

        // Test invalid names through the constructor
        for (int i = 0; i < invalidInputs.length; i++) {
            total++;

            try {
                new Food(invalidInputs[i], 250.0f, bestBefore, 10);

                System.out.println(
                    "FAIL: Invalid name was accepted: " + invalidInputs[i]
                );

            } catch (IllegalArgumentException e) {
                System.out.println(
                    "PASS: Invalid name rejected: " + invalidInputs[i]
                );
                passed++;
            }
        }

        // Test a valid name change through the setter
        Food food = new Food("Burger", 250.0f, bestBefore, 20);

        total++;
        food.setName("  pIzZa  ");

        if ("Pizza".equals(food.getName())) {
            System.out.println("PASS: Setter normalises a valid name");
            passed++;
        } else {
            System.out.println("FAIL: Setter stores the wrong name");
        }

        // Test that a rejected change preserves the previous name
        total++;

        try {
            food.setName("Sushi");
            System.out.println("FAIL: Setter accepted an invalid name");

        } catch (IllegalArgumentException e) {
            if ("Pizza".equals(food.getName())) {
                System.out.println(
                    "PASS: Setter rejects invalid name and preserves Pizza"
                );
                passed++;
            } else {
                System.out.println(
                    "FAIL: Rejected change altered the stored name"
                );
            }
        }

        System.out.println();
        System.out.println("Tests passed: " + passed + "/" + total);

        if (passed != total) {
            throw new AssertionError("Some name-validation tests failed.");
        }
    }
}