package edu.gatech.cs6310;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class that knows what action to take based on appropriate input
 */
public class DeliveryServiceUtility {

    private SortedMap<String, Store> stores = new TreeMap<>();
    private SortedMap<String, Pilot> pilots = new TreeMap<>();
    private SortedMap<String, Customer> customers = new TreeMap<>();
    private Set<String> licenses = new HashSet<>();
    private static Logger logger = LogManager.getLogger(DeliveryServiceUtility.class);

    /**
     * Create a new store
     *
     * @param storeName
     * @param revenue
     */
    public void makeStore(String storeName, String revenue) {
        if(stores.get(storeName) != null) {
            System.out.println("ERROR: store_identifier_already_exists");
            logger.error("ERROR: store_identifier_already_exists");
            return;
        }
        stores.put(storeName, new Store(storeName, Long.valueOf(revenue)));
        printSuccessfulChange();
    }

    /**
     * Display the stores
     */
    public void displayStores() {
        for(Store store : stores.values()) {
            System.out.println(store);
            logger.info(store);
        }
        printSuccessfulDisplay();
    }

    /**
     * Add the inventory to a given store
     *
     * @param storeName
     * @param itemName
     * @param weight
     */
    public void sellItem(String storeName, String itemName, String weight) {
        Store store = stores.get(storeName);
        if(store == null) {
            System.out.println("ERROR: store_identifier_already_exists");
            logger.error("ERROR: store_identifier_already_exists");
            return;
        }
        if(!store.addInventory(new Item(itemName, Long.valueOf(weight)))){
            System.out.println("ERROR: item_identifier_already_exists");
            logger.error("ERROR: item_identifier_already_exists");
            return;
        }
        printSuccessfulChange();
    }

    /**
     * Display all the items for the given store
     *
     * @param storeName
     */
    public void displayItems(String storeName) {
        Store store = stores.get(storeName);
        if(store == null) {
            System.out.println("ERROR: store_identifier_does_not_exist");
            logger.error("ERROR: store_identifier_does_not_exist");
            return;
        }
        for(Item item : store.getInventory().values()) {
            System.out.println(item);
            logger.info(item);
        }
        printSuccessfulDisplay();
    }

    /**
     * Create a new pilot object
     *
     * @param accountId
     * @param firstName
     * @param lastName
     * @param phoneNo
     * @param taxId
     * @param licenseId
     * @param experience
     */
    public void makePilot(String accountId, String firstName, String lastName, String phoneNo, String taxId, String licenseId, String experience) {
        Pilot pilot = pilots.get(accountId);
        if(pilot != null) {
            System.out.println("ERROR: pilot_identifier_already_exists");
            logger.error("ERROR: pilot_identifier_already_exists");
            return;
        }
        if(licenses.contains(licenseId)) {
            System.out.println("ERROR: pilot_license_already_exists");
            logger.error("ERROR: pilot_identifier_already_exists");
            return;
        }

        pilots.put(accountId, new Pilot(accountId, new User(firstName, lastName,phoneNo), taxId, licenseId, Integer.valueOf(experience)));
        licenses.add(licenseId);
        printSuccessfulChange();
    }

    /**
     * Display the pilots
     */
    public void displayPilots() {
        for(Pilot pilot: pilots.values()) {
            System.out.println(pilot);
            logger.info(pilot);
        }
        printSuccessfulDisplay();
    }

    /**
     * Create a drone
     *
     * @param storeName
     * @param droneId
     * @param maxCapacity
     * @param maxDeliveries
     */
    public void makeDrone(String storeName, String droneId, String maxCapacity, String maxDeliveries) {
        Store store = stores.get(storeName);
        if(store == null) {
            System.out.println("ERROR: store_identifier_does_not_exist");
            logger.error("ERROR: store_identifier_does_not_exist");
            return;
        }
        if(!store.addDrone(new Drone(droneId, Long.valueOf(maxCapacity), Integer.valueOf(maxDeliveries)))){
            System.out.println("ERROR: drone_identifier_already_exists");
            logger.error("ERROR: drone_identifier_already_exists");
            return;
        }
        printSuccessfulChange();
    }

    /**
     * Display the drones associated to a store
     *
     * @param storeName
     */
    public void displayDrones(String storeName) {
        Store store = stores.get(storeName);
        if(store == null) {
            System.out.println("ERROR: store_identifier_does_not_exist");
            logger.error("ERROR: store_identifier_does_not_exist");
            return;
        }
        for(Drone drone : store.getDrones().values()) {
            System.out.println(drone);
            logger.info(drone);
        }
        printSuccessfulDisplay();
    }

    /**
     * Assign a drone to a pilot and vice-versa
     *
     * @param storeName
     * @param droneId
     * @param pilotAccountId
     */
    public void flyDrone(String storeName, String droneId, String pilotAccountId) {
        Store store = stores.get(storeName);
        if(store == null) {
            System.out.println("ERROR: store_identifier_does_not_exist");
            logger.error("ERROR: store_identifier_does_not_exist");
            return;
        }
        Drone drone = store.getDrones().get(droneId);
        if(drone == null) {
            System.out.println("ERROR: drone_identifier_does_not_exist");
            logger.error("ERROR: drone_identifier_does_not_exist");
            return;
        }

        Pilot pilot = pilots.get(pilotAccountId);
        if(pilot == null) {
            System.out.println("ERROR: pilot_identifier_does_not_exist");
            logger.error("ERROR: pilot_identifier_does_not_exist");
            return;
        }
        if(pilot.getAssignedDrone() != null) {
            pilot.getAssignedDrone().unassignPilot();
        }
        drone.assignPilot(pilot);
        printSuccessfulChange();
    }

    /**
     * Create/Register a new customer
     *
     * @param customerId
     * @param firstName
     * @param lastName
     * @param phoneNo
     * @param rating
     * @param credits
     */
    public void makeCustomer(String customerId, String firstName, String lastName, String phoneNo, String rating, String credits) {
        Customer customer = customers.get(customerId);
        if(customer != null) {
            System.out.println("ERROR: customer_identifier_already_exists");
            logger.error("ERROR: customer_identifier_already_exists");
            return;
        }

        customers.put(customerId, new Customer(customerId, new User(firstName, lastName,phoneNo), Integer.valueOf(rating),Long.valueOf(credits)));
        printSuccessfulChange();
    }

    /**
     * Display customers
     */
    public void displayCustomers() {
        for(Customer customer: customers.values()) {
            System.out.println(customer);
            logger.info(customer);
        }
        printSuccessfulDisplay();
    }

    /**
     * Initiate an order
     *
     * @param storeName
     * @param orderId
     * @param droneId
     * @param customerId
     */
    public void startOrder(String storeName, String orderId, String droneId, String customerId) {
        Store store = stores.get(storeName);
        if(store == null) {
            System.out.println("ERROR: store_identifier_does_not_exist");
            logger.error("ERROR: store_identifier_does_not_exist");
            return;
        }
        Order newOrder = new Order(orderId, droneId, customerId);
        if(!store.addOrder(newOrder)){
            System.out.println("ERROR: order_identifier_already_exists");
            logger.error("ERROR: order_identifier_already_exists");
            return;
        }
        if(store.getDrones().get(droneId) == null) {
            System.out.println("ERROR: drone_identifier_does_not_exist");
            logger.error("ERROR: drone_identifier_does_not_exist");
            store.removeOrder(newOrder);
            return;
        }
        if(customers.get(customerId) == null) {
            System.out.println("ERROR: customer_identifier_does_not_exist");
            logger.error("ERROR: customer_identifier_does_not_exist");
            return;
        }
    printSuccessfulChange();
    }

    /**
     * Display the orders
     * @param storeName
     */
    public void displayOrders(String storeName) {
        Store store = stores.get(storeName);
        if(store == null) {
            System.out.println("ERROR: store_identifier_does_not_exist");
            logger.error("ERROR: store_identifier_does_not_exist");
            return;
        }

        for(Order order : store.getOrders().values()) {
            System.out.println(order);
            if(!order.getItems().isEmpty()) {
                order.getItems().forEach( item ->  {
                    System.out.println(item);
                    logger.info(item);
                });
            }
        }
        printSuccessfulDisplay();
    }

    /**
     * Request a new item within an order
     *
     * @param storeName
     * @param orderId
     * @param itemName
     * @param quantity
     * @param unitPrice
     */
    public void requestItem(String storeName, String orderId, String itemName, String quantity, String unitPrice){
        Store store = stores.get(storeName);
        if(store == null) {
            System.out.println("ERROR: store_identifier_does_not_exist");
            logger.error("ERROR: store_identifier_does_not_exist");
            return;
        }
        Order order = store.getOrders().get(orderId);
        if(order == null) {
            System.out.println("ERROR: order_identifier_does_not_exist");
            logger.error("ERROR: order_identifier_does_not_exist");
            return;
        }
        Item item = store.getInventory().get(itemName);
        if(item == null) {
            System.out.println("ERROR: item_identifier_does_not_exist");
            logger.error("ERROR: item_identifier_does_not_exist");
            return;
        }
        if(order.getItems().contains(item)) {
            System.out.println("ERROR: item_already_ordered");
            logger.error("ERROR: item_already_ordered");
            return;
        }
        RequestedItem requestedItem = new RequestedItem(itemName, item.getWeight(), Integer.valueOf(quantity), Integer.valueOf(unitPrice));
        Customer customer = customers.get(order.getCustomer());

        Integer totalCostOfItemsCurrentlyInOrder = 0;
        Long totalWeightCurrentlyInOrder = 0L;
        for(RequestedItem items : order.getItems()) {
            totalCostOfItemsCurrentlyInOrder += items.getTotalPrice();
            totalWeightCurrentlyInOrder += items.getTotalWeight();
        }

        if(!customer.canBuy(Long.valueOf(totalCostOfItemsCurrentlyInOrder + requestedItem.getTotalPrice()))) {
            System.out.println("ERROR: customer_cant_afford_new_item");
            logger.error("ERROR: customer_cant_afford_new_item");
            return;
        }

        Drone drone = store.getDrones().get(order.getDroneId());
        if(!drone.canCarry(totalWeightCurrentlyInOrder + requestedItem.getTotalWeight())) {
            System.out.println("ERROR: drone_cant_carry_new_item");
            logger.error("ERROR: drone_cant_carry_new_item");
            return;
        }

        order.addItem(requestedItem);
        printSuccessfulChange();
    }

    /**
     * For purchase orders, 5 things should happen:
     *  [1] Deduct customer credits
     *  [2] Update revenues
     *  [3] Drone refueling should be reduced by 1
     *  [4] Pilot's experience must increment by 1
     *  [5] Order must be removed from system
     *
     * @param storeName
     * @param orderId
     */
    public void purchaseOrder(String storeName, String orderId) {
        Store store = stores.get(storeName);
        if(store == null) {
            System.out.println("ERROR: store_identifier_does_not_exist");
            logger.error("ERROR: store_identifier_does_not_exist");
            return;
        }

        Order order = store.getOrders().get(orderId);
        if(order == null) {
            System.out.println("ERROR: order_identifier_does_not_exist");
            logger.error("ERROR: order_identifier_does_not_exist");
            return;
        }

        Drone drone = store.getDrones().get(order.getDroneId());
        Pilot pilot = drone.getAssignedPilot();

        Customer customer = customers.get(order.getCustomer());

        order.getItems().forEach( requestedItem -> {
            customer.updateUsedCredits(Long.valueOf(requestedItem.getTotalPrice()));
            store.addRevenue(Long.valueOf(requestedItem.getTotalPrice()));
            drone.addPayload(requestedItem.getTotalWeight());
        });

        if (pilot == null) {
            System.out.println("ERROR:drone_needs_pilot");
            logger.error("ERROR:drone_needs_pilot");
            return;
        }
        if(drone.tripsLeft() <= 0) {
            System.out.println("ERROR:drone_needs_fuel");
            logger.error("ERROR:drone_needs_fuel");
            return;
        }

        pilot.incrementExperience();
        drone.incrementTripsCompleted();
        store.removeOrder(order);
        printSuccessfulChange();
    }

    /**
     * For cancel orders, remove the order without affecting any stats
     *
     * @param storeName
     * @param orderId
     */
    public void cancelOrder(String storeName, String orderId) {
        Store store = stores.get(storeName);
        if(store == null) {
            System.out.println("ERROR: store_identifier_does_not_exist");
            logger.error("ERROR: store_identifier_does_not_exist");
            return;
        }

        Order order = store.getOrders().get(orderId);
        if(order == null) {
            System.out.println("ERROR: order_identifier_does_not_exist");
            logger.error("ERROR: order_identifier_does_not_exist");
            return;
        }
        store.removeOrder(order);
        printSuccessfulChange();
    }

    private void printSuccessfulDisplay() {
        System.out.println("OK: display_completed");
        logger.info("OK: display_completed");
    }

    private void printSuccessfulChange() {
        System.out.println("OK: change_completed");
        logger.info("OK: change_completed");
    }
}
