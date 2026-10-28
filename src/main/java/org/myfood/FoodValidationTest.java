package org.myfood;

import java.time.LocalDate;

public class FoodValidationTest {

    public static void main(String[] args) {

        String bestBefore = LocalDate.now().plusDays(7).toString();

        int passed = 0;
        int total = 0;

        // -------------------------------------------------
        // NAME VALIDATION: 11 checks
        // -------------------------------------------------

        String[] validInputs = {
            "Burger", "pizza", "  FRIES  ", "Sandwich", "Hotdog"
        };

        String[] expectedNames = {
            "Burger", "Pizza", "Fries", "Sandwich", "Hotdog"
        };

        // Five checks: valid names through the constructor.
        for (int i = 0; i < validInputs.length; i++) {
            total++;

            Food food = new Food(
                validInputs[i], 250.0f, bestBefore, i + 1
            );

            if (expectedNames[i].equals(food.getName())) {
                System.out.println(
                    "PASS: Valid name test " + (i + 1)
                );
                passed++;
            } else {
                System.out.println(
                    "FAIL: Valid name test " + (i + 1)
                );
            }
        }

        String[] invalidInputs = {
            "Sushi", "", "   ", null
        };

        // Four checks: invalid names through the constructor.
        for (int i = 0; i < invalidInputs.length; i++) {
            total++;

            try {
                new Food(
                    invalidInputs[i], 250.0f, bestBefore, 10
                );

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

        // One check: valid name change through the setter.
        Food food = new Food(
            "Burger", 250.0f, bestBefore, 20
        );

        total++;
        food.setName("  pIzZa  ");

        if ("Pizza".equals(food.getName())) {
            System.out.println(
                "PASS: Setter normalises a valid name"
            );
            passed++;
        } else {
            System.out.println(
                "FAIL: Setter stores the wrong name"
            );
        }

        // One check: rejected name change preserves the previous name.
        total++;

        try {
            food.setName("Sushi");

            System.out.println(
                "FAIL: Setter accepted an invalid name"
            );

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

        // -------------------------------------------------
        // WEIGHT VALIDATION: 16 checks
        // -------------------------------------------------

        Float[] validWeights = {
            0.5f, 250.0f, 1250.75f
        };

        // Three checks: valid weights through the constructor.
        for (Float validWeight : validWeights) {
            total++;

            Food weightedFood = new Food(
                "Burger", validWeight, bestBefore, 30
            );

            if (validWeight.equals(weightedFood.getWeight())) {
                System.out.println(
                    "PASS: Valid weight accepted: " + validWeight
                );
                passed++;
            } else {
                System.out.println(
                    "FAIL: Incorrect weight stored"
                );
            }
        }

        Float[] invalidWeights = {
            null,
            0.0f,
            -250.0f,
            Float.NaN,
            Float.POSITIVE_INFINITY,
            Float.NEGATIVE_INFINITY
        };

        // Six checks: invalid weights through the constructor.
        for (Float invalidWeight : invalidWeights) {
            total++;

            try {
                new Food(
                    "Burger", invalidWeight, bestBefore, 31
                );

                System.out.println(
                    "FAIL: Invalid weight accepted: " + invalidWeight
                );

            } catch (IllegalArgumentException e) {
                System.out.println(
                    "PASS: Invalid weight rejected: " + invalidWeight
                );
                passed++;
            }
        }

        // One check: valid weight change through the setter.
        Food weightedFood = new Food(
            "Burger", 250.0f, bestBefore, 32
        );

        total++;
        weightedFood.setWeight(300.5f);

        if (Float.valueOf(300.5f).equals(weightedFood.getWeight())) {
            System.out.println(
                "PASS: Setter updates weight"
            );
            passed++;
        } else {
            System.out.println(
                "FAIL: Setter stores incorrect weight"
            );
        }

        // Six checks: invalid setter values preserve the previous weight.
        for (Float invalidWeight : invalidWeights) {
            total++;

            try {
                weightedFood.setWeight(invalidWeight);

                System.out.println(
                    "FAIL: Setter accepted invalid weight: " + invalidWeight
                );

            } catch (IllegalArgumentException e) {
                if (Float.valueOf(300.5f).equals(weightedFood.getWeight())) {
                    System.out.println(
                        "PASS: Invalid weight change rejected; "
                        + "previous weight preserved"
                    );
                    passed++;
                } else {
                    System.out.println(
                        "FAIL: Rejected change altered weight"
                    );
                }
            }
        }

        // -------------------------------------------------
        // FINAL RESULTS
        // -------------------------------------------------

        System.out.println();
        System.out.println(
            "Tests passed: " + passed + "/" + total
        );

        if (passed != total) {
            throw new AssertionError(
                "Some food-validation tests failed."
            );
        }
    }
}