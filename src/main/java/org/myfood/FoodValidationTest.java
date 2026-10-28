package org.myfood;

import java.time.LocalDate;

public class FoodValidationTest {

    private static int passed = 0;
    private static int total = 0;

    // Records whether a condition is true.
    private static void check(boolean condition, String description) {
        total++;

        if (condition) {
            passed++;
            System.out.println("PASS: " + description);
        } else {
            System.out.println("FAIL: " + description);
        }
    }

    // Checks that an operation rejects an invalid value.
    private static void expectInvalid(Runnable action, String description) {
        total++;

        try {
            action.run();
            System.out.println("FAIL: " + description + " was accepted");
        } catch (IllegalArgumentException e) {
            passed++;
            System.out.println("PASS: " + description + " was rejected");
        }
    }

    public static void main(String[] args) {
        passed = 0;
        total = 0;

        LocalDate today = LocalDate.now();
        String bestBefore = today.plusDays(7).toString();

        // ---------------------------------------------
        // NAME VALIDATION: 11 checks
        // ---------------------------------------------

        String[] validNames = {
            "Burger", "pizza", "  FRIES  ", "Sandwich", "Hotdog"
        };

        String[] expectedNames = {
            "Burger", "Pizza", "Fries", "Sandwich", "Hotdog"
        };

        for (int i = 0; i < validNames.length; i++) {
            Food food = new Food(
                validNames[i], 250.0f, bestBefore, i + 1
            );

            check(
                expectedNames[i].equals(food.getName()),
                "Valid name stored correctly: " + validNames[i]
            );
        }

        String[] invalidNames = {
            "Sushi", "", "   ", null
        };

        for (String invalidName : invalidNames) {
            expectInvalid(
                () -> new Food("".equals(invalidName)
                    ? "" : invalidName, 250.0f, bestBefore, 10),
                "Invalid constructor name [" + invalidName + "]"
            );
        }

        Food namedFood = new Food(
            "Burger", 250.0f, bestBefore, 20
        );

        namedFood.setName("  pIzZa  ");

        check(
            "Pizza".equals(namedFood.getName()),
            "Setter normalises a valid name"
        );

        boolean nameRejected = false;

        try {
            namedFood.setName("Sushi");
        } catch (IllegalArgumentException e) {
            nameRejected = true;
        }

        check(
            nameRejected && "Pizza".equals(namedFood.getName()),
            "Invalid rename is rejected and previous name is preserved"
        );

        // ---------------------------------------------
        // WEIGHT VALIDATION: 16 checks
        // ---------------------------------------------

        Float[] validWeights = {
            0.5f, 250.0f, 1250.75f
        };

        for (Float validWeight : validWeights) {
            Food food = new Food(
                "Burger", validWeight, bestBefore, 30
            );

            check(
                validWeight.equals(food.getWeight()),
                "Valid weight stored correctly: " + validWeight
            );
        }

        Float[] invalidWeights = {
            null,
            0.0f,
            -250.0f,
            Float.NaN,
            Float.POSITIVE_INFINITY,
            Float.NEGATIVE_INFINITY
        };

        for (Float invalidWeight : invalidWeights) {
            expectInvalid(
                () -> new Food("Burger", invalidWeight, bestBefore, 31),
                "Invalid constructor weight [" + invalidWeight + "]"
            );
        }

        Food weightedFood = new Food(
            "Burger", 250.0f, bestBefore, 32
        );

        weightedFood.setWeight(300.5f);

        check(
            Float.valueOf(300.5f).equals(weightedFood.getWeight()),
            "Setter updates a valid weight"
        );

        for (Float invalidWeight : invalidWeights) {
            boolean rejected = false;

            try {
                weightedFood.setWeight(invalidWeight);
            } catch (IllegalArgumentException e) {
                rejected = true;
            }

            check(
                rejected
                    && Float.valueOf(300.5f).equals(weightedFood.getWeight()),
                "Invalid weight rejected and previous weight preserved: "
                    + invalidWeight
            );
        }

        // ---------------------------------------------
        // DATE VALIDATION: 21 checks
        // ---------------------------------------------

        String[] validDates = {
            today.toString(),
            today.plusDays(7).toString(),
            today.plusDays(14).toString(),
            "  " + today.plusDays(7) + "  "
        };

        String[] expectedDates = {
            today.toString(),
            today.plusDays(7).toString(),
            today.plusDays(14).toString(),
            today.plusDays(7).toString()
        };

        // Four checks: today, +7 days, +14 days and spaces.
        for (int i = 0; i < validDates.length; i++) {
            Food food = new Food(
                "Burger", 250.0f, validDates[i], 40
            );

            check(
                expectedDates[i].equals(food.getBBF()),
                "Valid date stored correctly: [" + validDates[i] + "]"
            );
        }

        String[] invalidDates = {
            null,
            "",
            "   ",
            "not-a-date",
            today.getYear() + "-02-30",
            "27/10/2026",
            today.minusDays(1).toString(),
            today.plusDays(15).toString()
        };

        // Eight checks: invalid constructor dates.
        for (String invalidDate : invalidDates) {
            expectInvalid(
                () -> new Food("Burger", 250.0f, invalidDate, 41),
                "Invalid constructor date [" + invalidDate + "]"
            );
        }

        Food datedFood = new Food(
            "Burger", 250.0f, bestBefore, 42
        );

        String updatedDate = today.plusDays(10).toString();

        datedFood.setBBF(updatedDate);

        // One check: valid date update.
        check(
            updatedDate.equals(datedFood.getBBF()),
            "Setter updates a valid best-before date"
        );

        // Eight checks: invalid updates preserve the previous date.
        for (String invalidDate : invalidDates) {
            boolean rejected = false;

            try {
                datedFood.setBBF(invalidDate);
            } catch (IllegalArgumentException e) {
                rejected = true;
            }

            check(
                rejected && updatedDate.equals(datedFood.getBBF()),
                "Invalid date rejected and previous date preserved: ["
                    + invalidDate + "]"
            );
        }

        // ---------------------------------------------
        // FINAL RESULTS
        // ---------------------------------------------

        System.out.println();
        System.out.println("Tests passed: " + passed + "/" + total);

        if (passed != total) {
            throw new AssertionError(
                "Some food-validation tests failed."
            );
        }
    }
}