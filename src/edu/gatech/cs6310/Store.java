package edu.gatech.cs6310;


import java.util.*;

public class Store {
    //Attributes
    private String storeName;
    private Long revenue;

    private SortedMap<String, Item> inventory = new TreeMap<>();
    private SortedMap<String, Item> soldItems = new TreeMap<>();
    private SortedMap<String, Drone> drones = new TreeMap<>();
    private SortedMap<String, Order> orders = new TreeMap<>();

    public Store(String inputName, Long inputNumber) {
        this.storeName = inputName;
        this.revenue = inputNumber;
    }
    //Methods
    public String getName() { return this.storeName; }
    public Long getRevenue() { return this.revenue; }

    public SortedMap<String, Item> getInventory() {
        return this.inventory;
    }

    public SortedMap<String, Drone> getDrones() {
        return this.drones;
    }

    public SortedMap<String, Order>  getOrders() {
        return this.orders;
    }

    public void addRevenue(Long revenue) {
        this.revenue += revenue;

    }

    public boolean addInventory(Item item) {
        Item invetoryItem = inventory.get(item.getItemName());
        if(invetoryItem != null) {
            return false;
        }
        this.inventory.put(item.getItemName(), item);
        return true;
    }

    public boolean addDrone(Drone drone) {
        Drone storeDrone =  drones.get(drone.getId());
        if(storeDrone != null) {
            return false;
        }
        this.drones.put(drone.getId(), drone);
        return true;
    }

    public void removeOrder(Order order) {
         this.orders.remove(order.getOrderId());
    }

    public boolean addOrder(Order order) {
        Order storeOrder = orders.get(order.getOrderId());
        if(storeOrder != null) {
            return false;
        }
        this.orders.put(order.getOrderId(), order);
        return true;
    }

    @Override
    public String toString() {
        return "name:" + getName() + ",revenue:"+getRevenue();
    }

}



