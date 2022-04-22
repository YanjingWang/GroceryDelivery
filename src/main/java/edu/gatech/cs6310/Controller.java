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
                "FROM `pilot` AS s" +
                "INNER JOIN `user` AS u" +
                "ON s.pilot_id = u.account_id" +
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

    public Customer findCustomerByID(String accountID) {
        Customer customer = null;
        User user =null;
        try (ResultSet rs = manager.get("SELECT * " +
                "FROM `customer` AS c " +
                "INNER JOIN `user` AS u " +
                "ON c.customer_id = u.account_id " +
                "WHERE c.customer_id ='" + accountID + "'")) {
            if (rs != null) {
                rs.next();
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String phoneNo = rs.getString("phonenumber");
                user = new User(firstName,lastName,phoneNo);
                String accountId = rs.getString("customer_id");
                String rating = rs.getString("rating");
                String credits = rs.getString("credit");

                customer = new Customer(accountId, user, Integer.valueOf(rating), Long.valueOf(credits));
            }
        } catch(SQLException e) {
            logger.error("Error find store by account: " + accountID + e);
        }
        return customer;
    }

    public TreeMap<String,Customer> findAllCustomer(){
        TreeMap<String, Customer> customers = new TreeMap<>();
        try (ResultSet rs = manager.get("SELECT * FROM `customer` AS c INNER JOIN `user` AS u ON c.customer_id = u.account_id")) {
            if (rs != null) {
                while (rs.next()) {
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    String phoneNo = rs.getString("phonenumber");
                    String accountId = rs.getString("customer_id");
                    String rating = rs.getString("rating");
                    String credits = rs.getString("credit");
                    customers.put(accountId, new Customer(accountId, new User(firstName, lastName,phoneNo), Integer.valueOf(rating), Long.valueOf(credits)));
                }
            }
        } catch(SQLException e) {
            System.out.println("Error find all pilots. " + e);
            logger.error("Error find all pilots. " + e);
        } finally {
            manager.closeConnection();
        }
        return customers;
    }

    public boolean createNewCustomer(Customer customer) {
        String accountId = customer.getAccountId();
        String firstName = customer.getUser().getFirstName();
        String lastName = customer.getUser().getLastName();
        String phoneNo = customer.getUser().getPhoneNumber();
        Integer rating = customer.getRating();
        Long credits = customer.getCredits();
        String password = "123";
        int rs_customer = manager.insert("INSERT INTO delivery.customer (`customer_id`,`rating`,`credit`) VALUES ('" + accountId + "', " + rating + ", " + credits + ")");
        int rs_user = manager.insert("INSERT INTO delivery.user(`account_id`,`password`,`firstname`, `lastname`, `phonenumber`) VALUES('" + accountId + "', '" + password + "', '" + firstName + "', '" + lastName + "','" + phoneNo + "')");
        return rs_customer == 1;
    }


    public boolean createNewDrone(String storeName, Drone drone){
        //String droneID = drone.getId();
        //Long totalCapacity;
        //Long remainingCapacity;
        //Integer tripsCompleted;
        //Integer maximumDeliveries;
        //Long weight = drone.getWeight();
        int rs = manager.insert("INSERT INTO delivery.drone(drone_id, total_capacity, max_deliveries, trips_completed, remain_Capacity, pilot_id, store_name) VALUES ('" + drone.getId() + "', '" + drone.getTotalCapacity()
                + "','" + drone.getMaximumDeliveries() + "','" + drone.getTripsCompleted() + "','" + drone.getRemainingCapacity() + "','" + drone.getPilot() + "','" + storeName + "')");
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
            }
        } catch(SQLException e) {
            logger.error("Error find store by account: " + droneid + e);
        }
        return drone;
    }

    public TreeMap<String,Order> findAllOrder(String storeName){
        TreeMap<String, Order> orders = new TreeMap<>();
        try (ResultSet rs = manager.get("SELECT * FROM `order` AS o INNER JOIN `requested_item` AS r ON o.order_id = r.order_id WHERE o.store_name = '"+ storeName +"'")) {
            if (rs != null) {
                while (rs.next()) {
                    String orderId = rs.getString("order_id");
                    String droneId = rs.getString("drone_id");
                    String requestedBy = rs.getString("customer_id");
                    if (!orders.containsKey(orderId)){
                        orders.put(orderId, new Order(orderId, droneId, requestedBy));
                    }
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


}
