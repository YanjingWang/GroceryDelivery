package edu.gatech.cs6310;

public class Item {
    private String itemName;
    private Long weight;

    public Item(String itemName, Long weight) {
        this.itemName = itemName;
        this.weight = weight;
    }

    public String getItemName() {
       return this.itemName;
    }

    public Long getWeight() { return this.weight; }

    @Override
    public String toString() {
        return itemName + "," + weight;
    }
}