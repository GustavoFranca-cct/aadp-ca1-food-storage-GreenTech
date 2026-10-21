package org.myfood;

public class Food {

    private String name;
    private Float weight;
    private String BBF;
    private Integer ID;

    /**
     * Constructor for the Food class
     *
     * @param name           - The name of the food item
     * @param weight         - The weight of the food item
     * @param bestBeforeDate - The best before date of the food item
     * @param ID             - The ID of the food item
     */
    public Food(String name, Float weight, String bestBeforeDate, Integer ID) {
        this.name = name;
        this.weight = weight;
        this.BBF = bestBeforeDate;
        this.ID = ID;

    }
}
