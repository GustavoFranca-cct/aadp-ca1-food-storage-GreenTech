package org.myfood;

public class Food {

    private String name;
    private Float weight;
    private String BBF;
    private Integer ID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getBBF() {
        return BBF;
    }

    public void setBBF(String BBF) {
        this.BBF = BBF;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

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
