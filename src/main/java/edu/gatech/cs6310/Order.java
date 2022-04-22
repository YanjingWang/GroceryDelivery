package edu.gatech.cs6310;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class Order {

    private String orderId;
    private String requestedBy;
    private String droneId;
    private SortedSet<RequestedItem> items = new TreeSet<>(new Comparator<RequestedItem>() {
        @Override
        public int compare(RequestedItem o1, RequestedItem o2) {
            return o1.getItemName().compareTo(o2.getItemName());
        }
    });

    public Order(String orderId, String droneId, String requestedBy) {
        this.orderId = orderId;
        this.droneId = droneId;
        this.requestedBy = requestedBy;
    }

    public SortedSet<RequestedItem> getItems() {
        return this.items;
    }

    public boolean addItem(RequestedItem item) {
        if(item.getItemName() == null){
            return false;
        }
        if(!this.items.add(item)) {
            return false;
        }
        return true;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public String getCustomer() {
        return this.requestedBy;
    }

    public String getDroneId() {
        return this.droneId;
    }

    @Override
    public String toString() {
        return "orderID:" + orderId;
    }
}
