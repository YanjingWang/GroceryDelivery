package edu.gatech.cs6310;

public class RequestedItem extends Item{

    private Integer unitPrice;
    private Integer quantity;
    private Integer totalPrice;
    private Long totalWeight;

    public RequestedItem(String itemName, Long weight, Integer quantity, Integer unitPrice) {
        super(itemName, weight);
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = unitPrice * quantity;
        this.totalWeight = quantity * weight;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getTotalPrice() {
        return this.totalPrice;
    }

    public Long getTotalWeight() {
        return this.totalWeight;
    }

    @Override
    public String toString() {
        return "item_name:" + getItemName() + ",total_quantity:" + this.quantity + ",total_cost:" + this.totalPrice
                + ",total_weight:" + this.totalWeight;
    }
}
