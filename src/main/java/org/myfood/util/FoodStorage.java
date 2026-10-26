/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.myfood.util;
import org.myfood.Food;
/**
 *
 * @author marlon_k
 */

public class FoodStorage {
    private static final int CAPACITY = 8;

private Food[] items;
private int front;
private int rear;
private int size;

public FoodStorage() {
    items = new Food[CAPACITY];
    front = 0;
    rear = -1;
    size = 0;
}

// Checks if the storage is empty
public boolean isEmpty() {
    return size == 0;
}

// Checks if the storage is full
public boolean isFull() {
    return size == CAPACITY;
}

// Returns the number of stored items
public int getSize() {
    return size;
}
    
}
