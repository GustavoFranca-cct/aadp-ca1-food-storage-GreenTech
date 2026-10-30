package org.myfood.util;

import org.myfood.Food;

import java.util.Scanner;

public class search {
    public static void searchFood(Scanner sc, FoodStorage storage) {

        String name = InputUtils.readString(
                sc, "Enter the food name to search: ");

        // Blank input is rejected by searchByName.
        // The main loop catches the error and shows the menu again.
        Food[] results = storage.searchByName(name);

        if (results.length == 0) {
            System.out.println("No matching food found.");
            return;
        }

        System.out.println("Matching trays: " + results.length);

        for (Food food : results) {
            FoodStorage.displayFood(food);
        }
    }
}
