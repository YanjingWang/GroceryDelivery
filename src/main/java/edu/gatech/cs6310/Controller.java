package edu.gatech.cs6310;


import com.sun.source.tree.Tree;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Controller {
    private static DBManager manager = new DBManager();
    private static Logger logger = LogManager.getLogger(Controller.class);

    public User validateUserLogin(String username, String inputPassword) {
        User user = null;
        try (ResultSet rs = manager.get("select * from user where account_id='" + username + "'")) {
            if (rs != null) {
                rs.next();
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String phonenumber = rs.getString("phonenumber");
                String hashPassword = rs.getString("password");
                if (hashPassword.equals(inputPassword)) {
                    user = new User(firstname, lastname, phonenumber);
                }
            }
        } catch(SQLException e) {
            logger.error("Error find user by name: " + username + e);
        }

        return user;
    }

    public Store findStoreByName(String name) {
        Store store = null;
        try (ResultSet rs = manager.get("select * from store where store_name='" + name + "'")) {
            if (rs != null) {
                rs.next();
                String storeName = rs.getString("store_name");
                Long revenue = rs.getLong("revenue");
                store = new Store(storeName, revenue);
            }
        } catch(SQLException e) {
            logger.error("Error find store by name: " + name + e);
        }
        return store;
    }

    public List<Store> findAllStore() {
        List<Store> stores = new ArrayList<>();
        try (ResultSet rs = manager.get("select * from store where 1=1")) {
            if (rs != null) {
                while (rs.next()) {
                    String name = rs.getString("store_name");
                    Long revenue = rs.getLong("revenue");
                    stores.add(new Store(name, revenue));
                }
            }
        } catch(SQLException e) {
            System.out.println("Error find all store. " + e);
            logger.error("Error find all store. " + e);
        } finally {
            manager.closeConnection();
        }
        return stores;
    }

    public boolean createNewStore(Store store) {
        String name = store.getName();
        Long revenue = store.getRevenue();
        int rs = manager.insert("INSERT INTO delivery.store (store_name, revenue) VALUES ('" + name + "', " + revenue + ")");
        return rs == 1;
    }

    public Item findItemByName(String storeName, String itemName) {
        Item item = null;
        try (ResultSet rs = manager.get("SELECT * FROM item WHERE `store_name` = '"+ storeName +"' AND `item_name` = '" + itemName+ "'")) {
            if (rs != null) {
                rs.next();
                Long unitWeight = rs.getLong("unit_weight");
                item = new Item(storeName, unitWeight);
            }
        } catch(SQLException e) {
            logger.error("Error find item by name: " + itemName + e);
        }
        return item;
    }

    public boolean createNewItem(String storeName, Item item){
        String itemName = item.getItemName();
        Long weight = item.getWeight();
        int rs = manager.insert("INSERT INTO delivery.item(item_name, unit_weight, store_name) VALUES ('" + itemName + "', " + weight + ",'" + storeName + "')");
        return rs == 1;
    }

    public TreeMap<String,Item>  findAllItem(String storeName) {
        TreeMap<String,Item> items = new TreeMap<String,Item>();
        try (ResultSet rs = manager.get("SELECT * FROM item WHERE `store_name` = '"+ storeName +"'")) {
            if (rs != null) {
                while (rs.next()) {
                    String itemName = rs.getString("item_name");
                    Long itemWeight = rs.getLong("unit_weight");
                    items.put(itemName,new Item(itemName,itemWeight));
                }
            }
        } catch(SQLException e) {
            System.out.println("Error find all items. " + e);
            logger.error("Error find all items. " + e);
        } finally {
            manager.closeConnection();
        }
        return items;
    }

    public Pilot findPilotByID(String accountID) {
        Pilot pilot = null;
        User user =null;
        try (ResultSet rs = manager.get("SELECT * " +
                "FROM `pilot` AS s " +
                "INNER JOIN `user` AS u " +
                "ON s.pilot_id = u.account_id " +
                "WHERE s.pilot_id ='" + accountID + "'")) {
            if (rs != null) {
                rs.next();
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String phoneNo = rs.getString("phonenumber");
                user = new User(firstName,lastName,phoneNo);

                String accountId = rs.getString("pilot_id");
                String taxId = rs.getString("tax_id");
                String licenseId = rs.getString("license_id");
                Integer experience = rs.getInt("experience");
                pilot = new Pilot(accountId, user, taxId,licenseId,experience);
//                if this pilot drive a drone.
                String store_name = rs.getString("store_name");
                String droneid = rs.getString("drone");
                if(droneid != null & pilot.getAssignedDrone()==null){
                    Drone drone = null;
                    try (ResultSet rs_2 = manager.get("SELECT * FROM `drone` WHERE `store_name` = '"+ store_name +"' AND `drone_id` = '" + droneid+ "'")) {
                        if (rs_2 != null) {
                            rs.next();
                            String ID = rs_2.getString("drone_id");
                            Long totalCapacity = rs_2.getLong("total_capacity");
                            Integer maximumDeliveries = rs_2.getInt("max_deliveries");
                            drone = new Drone(ID, totalCapacity, maximumDeliveries);
                            // update drone current situiation?
                        }
                    } catch(SQLException e) {
                        logger.error("Error find store by account: " + droneid + e);
                    }
                    pilot.assignDrone(drone);
                }
            }
        } catch(SQLException e) {
            logger.error("Error find store by account: " + accountID + e);
        }
        return pilot;
    }

    public Set<String>  findAllLicense() {
        Set<String> licenses = new HashSet<String>();
        try (ResultSet rs = manager.get("SELECT `license_id` FROM `pilot`")) {
            if (rs != null) {
                while (rs.next()) {
                    String licenseID = rs.getString("license_id");
                    licenses.add(licenseID);
                }
            }
        } catch(SQLException e) {
            System.out.println("Error find all licenes. " + e);
            logger.error("Error find all licenes. " + e);
        } finally {
            manager.closeConnection();
        }
        return licenses;
    }

    public TreeMap<String,Pilot> findAllPilot(){
        TreeMap<String, Pilot> pilots = new TreeMap<>();
        try (ResultSet rs = manager.get("SELECT * FROM `pilot` AS s INNER JOIN `user` AS u ON s.pilot_id = u.account_id")) {
            if (rs != null) {
                while (rs.next()) {
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    String phoneNo = rs.getString("phonenumber");
                    String accountId = rs.getString("pilot_id");
                    String taxId = rs.getString("tax_id");
                    String licenseId = rs.getString("license_id");
                    Integer experience = rs.getInt("experience");
                    pilots.put(accountId, new Pilot(accountId, new User(firstName, lastName,phoneNo), taxId, licenseId, experience));
                }
            }
        } catch(SQLException e) {
            System.out.println("Error find all pilots. " + e);
            logger.error("Error find all pilots. " + e);
        } finally {
            manager.closeConnection();
        }
        return pilots;
    }

    public boolean createNewPilot(Pilot pilot) {
        String accountId = pilot.getAccountId();
        String firstName = pilot.getUser().getFirstName();
        String lastName = pilot.getUser().getLastName();
        String phoneNo = pilot.getUser().getPhoneNumber();
        String taxId = pilot.getTaxId();
        String licenseId = pilot.getLicenseId();
        Integer experience = pilot.getExperience();
        String password = "123";
        int rs_pilot = manager.insert("INSERT INTO delivery.pilot (`pilot_id`,`tax_id`,`license_id`, `experience`) VALUES ('" + accountId + "', '" + taxId + "', '" + licenseId + "', " + experience + ")");
        int rs_user = manager.insert("INSERT INTO delivery.user (`account_id`,`password`,`firstname`, `lastname`, `phonenumebr`) VALUES('" + accountId + "', '" + password + "', '" + firstName + "', '" + lastName + "','" + phoneNo + "',)");
        return rs_pilot == 1;
    }

    public Drone findDroneByID(String storeName, String id) {
        Drone drone = null;
        try (ResultSet rs = manager.get("SELECT * FROM drone WHERE `store_name` = '"+ storeName +"' AND `drone_id` = '" + id+ "'")) {
            if (rs != null) {
                rs.next();
                String ID = rs.getString("drone_id");
                Long totalCapacity = rs.getLong("total_capacity");
                Integer maximumDeliveries = rs.getInt("max_deliveries");
                drone = new Drone(ID, totalCapacity, maximumDeliveries);
            }
        } catch(SQLException e) {
            logger.error("Error find drone by id: " + id + e);
        }
        return drone;
    }


    public boolean createNewDrone(String storeName, Drone drone){
        //String droneID = drone.getId();
        //Long totalCapacity;
        //Long remainingCapacity;
        //Integer tripsCompleted;
        //Integer maximumDeliveries;
        //Long weight = drone.getWeight();
        int rs = manager.insert("INSERT INTO delivery.drone(drone_id, total_capacity, max_deliveries, trips_completed, remain_Capacity, store_name) VALUES ('" + drone.getId() + "', '" + drone.getTotalCapacity()
                + "','" + drone.getMaximumDeliveries() + "','" + drone.getTripsCompleted() + "','" + drone.getRemainingCapacity() + "','" + storeName + "')");
        return rs == 1;
    }

    public TreeMap<String, Drone> findAllDrone(String storeName){
        TreeMap<String, Drone> drones = new TreeMap<>();
        try (ResultSet rs = manager.get("SELECT * FROM `drone` WHERE `store_name` = '"+ storeName +"'")) {
            if (rs != null) {
                while (rs.next()) {
                    String droneID = rs.getString("drone_id");
                    Long totalCapacity = rs.getLong("total_capacity");
                    Integer maximumDeliveries = rs.getInt("max_deliveries");
                    drones.put(droneID, new Drone(droneID, totalCapacity, maximumDeliveries));
                }
            }
        } catch(SQLException e) {
            System.out.println("Error find all drones. " + e);
            logger.error("Error find all drones. " + e);
        } finally {
            manager.closeConnection();
        }
        return drones;
    };

    public Drone findDroneByID(String storeName,String droneid){
        Drone drone = null;
        try (ResultSet rs = manager.get("SELECT * FROM `drone` WHERE `store_name` = '"+ storeName +"' AND `drone_id` = '" + droneid+ "'")) {
            if (rs != null) {
                rs.next();
                String ID = rs.getString("drone_id");
                Long totalCapacity = rs.getLong("total_capacity");
                Integer maximumDeliveries = rs.getInt("max_deliveries");
                drone = new Drone(ID, totalCapacity, maximumDeliveries);

//              if this drone was assinged a pilot
                Integer trips_completed = rs.getInt("trips_completed");
                Long remain_capacity = rs.getLong("remain_Capacity");
                String pilot_id = rs.getString("pilot_id");
                if(pilot_id != null & drone.getPilot() == null){
                    Pilot pilot = null;
                    User user =null;
                    try (ResultSet rs_2 = manager.get("SELECT * " +
                            "FROM `pilot` AS s " +
                            "INNER JOIN `user` AS u " +
                            "ON s.pilot_id = u.account_id " +
                            "WHERE s.pilot_id ='" + pilot_id + "'")) {
                        if (rs_2 != null) {
                            rs.next();
                            String firstName = rs_2.getString("firstname");
                            String lastName = rs_2.getString("lastname");
                            String phoneNo = rs_2.getString("phonenumber");
                            user = new User(firstName,lastName,phoneNo);

                            String accountId = rs_2.getString("pilot_id");
                            String taxId = rs_2.getString("tax_id");
                            String licenseId = rs_2.getString("license_id");
                            Integer experience = rs_2.getInt("experience");
                            pilot = new Pilot(accountId, user, taxId,licenseId,experience);
                        }
                    } catch(SQLException e) {
                        logger.error("Error find store by account: " + pilot_id + e);
                    }
                    drone.setPilot(pilot);
                }
                drone.setRemainingCapacity(remain_capacity);
                drone.setTripsCompleted(trips_completed);

            }
        } catch(SQLException e) {
            logger.error("Error find store by account: " + droneid + e);
        }
        return drone;
    }

    public boolean driveDrone(String storeName, Drone drone, Pilot pilot){
        Drone preDrone = pilot.getAssignedDrone();
        Pilot prePilot = drone.getPilot();
        // clear previous record
        if (preDrone != null){
            // update pilot's previous drone'pilot as NULL in database
            int rs_preDrone = manager.update( "UPDATE `drone` SET `pilot_id` = NULL WHERE `store_name` = '" + storeName +"' AND `drone_id` = '" + preDrone.getId()+ "'");
            // update current pilot' drone as NULL in database
            int rs_pilot = manager.update( "UPDATE `pilot` SET `store_name` = NULL, `drone` = NULL WHERE `pilot_id` = '" +pilot.getAccountId()+ "'");
        }
        if(prePilot != null){
            //update prePilot's drone
            int rs_prePilot = manager.update( "UPDATE `pilot` SET `store_name` = NULL, `drone` = NULL WHERE `pilot_id` = '" +prePilot.getAccountId()+ "'");
            // update current current drone'pilot id
            int rs_Drone = manager.update( "UPDATE `drone` SET `pilot_id` = NULL WHERE `store_name` = '" + storeName +"' AND `drone_id` = '" + drone.getId()+ "'");
        }
        drone.unassignPilot();
        pilot.setAssignedDrone(null);
        int rs_curPilot = manager.update( "UPDATE `pilot` SET `store_name` ='" + storeName + "', `drone` = '" + drone.getId() + "' WHERE `pilot_id` = '" +pilot.getAccountId()+ "'");
        // update current current drone'pilot id
        int rs_curDrone = manager.update( "UPDATE `drone` SET `pilot_id` ='" + pilot.getAccountId() +"' WHERE `store_name` = '" + storeName +"' AND `drone_id` = '" + drone.getId()+ "'");
        return true;
    }

    public TreeMap<String,Order> findAllOrder(String storeName){
        TreeMap<String, Order> orders = new TreeMap<>();
        try (ResultSet rs = manager.get("SELECT * FROM `order` AS o LEFT JOIN `requested_item` AS r ON o.order_id = r.order_id AND o.store_name = r.store_name WHERE o.store_name = '"+ storeName +"'")) {
            if (rs != null) {
                while (rs.next()) {
                    String orderId = rs.getString("o.order_id");
                    String droneId = rs.getString("o.drone_id");
                    String requestedBy = rs.getString("o.customer_id");
                    orders.put(orderId, new Order(orderId, droneId, requestedBy));
                    String itemName = rs.getString("item_name");
                    Long weight = rs.getLong("weight");
                    Integer quantity = rs.getInt("quantity");
                    Integer unitPrice = rs.getInt("unit_price");
                    RequestedItem item = new RequestedItem(itemName,weight,quantity,unitPrice);
                    orders.get(orderId).addItem(item);
                }
            }
        } catch(SQLException e) {
            System.out.println("Error find all orders. " + e);
            logger.error("Error find all orders. " + e);
        } finally {
            manager.closeConnection();
        }
        return orders;
    }

    public Order findOrderByID(String storeName,String orderid){
        Order order = null;
        try (ResultSet rs = manager.get("SELECT * FROM `order` WHERE `store_name` = '"+ storeName +"' AND `order_id` = '" + orderid+ "'")) {
            if (rs != null) {
                rs.next();
                String orderId = rs.getString("order_id");
                String droneId = rs.getString("drone_id");
                String requestedBy = rs.getString("customer_id");
                order = new Order(orderId, droneId, requestedBy);
            }
        } catch(SQLException e) {
            logger.error("Error find store by account: " + orderid + e);
        }
        return order;
    }

    public boolean createNewOrder(String storeName,Order order) {
        String orderId = order.getOrderId();
        String droneId = order.getDroneId();
        String customerId = order.getCustomer();
        int rs_order = manager.insert("INSERT INTO delivery.order (`store_name`, `order_id`, `drone_id`,`customer_id`) VALUES ('" + storeName + "', '" + orderId + "', '" + droneId + "', '" + customerId + "')");
        return rs_order == 1;
    }

    public boolean deleteOrder(String storeName,Order order) {
        String orderId = order.getOrderId();
        String droneId = order.getDroneId();
        String customerId = order.getCustomer();
        int rs_order = manager.delete("DELETE FROM `order` WHERE `store_name` = '" +storeName+"' AND `order_id` = '" + orderId + "'");
        return rs_order == 1;
    }

    public  Order findOrderItem(Order order){
        try (ResultSet rs = manager.get("SELECT * FROM `order` AS o INNER JOIN `requested_item` AS r ON o.order_id = r.order_id AND o.store_name = r.store_name WHERE o.order_id = '" + order.getOrderId() + "'")) {
            if (rs != null) {
                rs.next();
                String itemName = rs.getString("item_name");
                Long weight = rs.getLong("weight");
                Integer quantity = rs.getInt("quantity");
                Integer unitPrice = rs.getInt("unit_price");
                RequestedItem item = new RequestedItem(itemName,weight,quantity,unitPrice);
                order.addItem(item);
            }
        } catch(SQLException e) {
            logger.error("Error find order items: " + order.getOrderId() + e);
        }
        return order;
    }

    public boolean createNewOrderItem(String storeName,Order order,RequestedItem item) {
        String orderId = order.getOrderId();
        String itemName = item.getItemName();
        Long weight = item.getWeight();
        Integer quantity = item.getQuantity();
        Integer unitPrice = item.getUnitPrice();
        Integer totalPrice = item.getTotalPrice();
        Long totalWeight = item.getTotalWeight();
        int rs_order = manager.insert("INSERT INTO delivery.requested_item (`item_name`, `store_name`, `order_id`,`unit_price`, `quantity`, `totalprice`, `totalweight`,`weight`) VALUES ('" + itemName + "', '" + storeName + "', '" + orderId + "', " + unitPrice + ", " + quantity + ", " + totalPrice + ", " + totalWeight + ", " + weight + "  )");
        return rs_order == 1;
    }



}
