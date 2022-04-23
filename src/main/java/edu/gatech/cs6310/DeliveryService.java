package edu.gatech.cs6310;

import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeliveryService {

    private static final String DELIMITER = ",";
    private static Logger logger = LogManager.getLogger(DeliveryService.class);
    private User user;

    public DeliveryService(User user) {
        this.user = user;
    }


    DeliveryServiceUtility deliveryServiceUtility = new DeliveryServiceUtility(this.user);

    public void commandLoop() {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;

        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println("> " + wholeInputLine);
                logger.info("> " + wholeInputLine);
                if (tokens[0].equals("make_store")) {
                    if (user.getRole().equals(User.Role.ADMIN) || user.getRole().equals(User.Role.MANAGER)) {
                        deliveryServiceUtility.makeStore(tokens[1], tokens[2]);
                    } else {
                        System.out.println("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                        logger.info("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                    }
               } else if (tokens[0].equals("display_stores")) {
                    if (user.getRole().equals(User.Role.ADMIN) || user.getRole().equals(User.Role.MANAGER)) {
                        deliveryServiceUtility.displayStores();
                    } else {
                        System.out.println("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                        logger.info("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                    }
                }else if (tokens[0].equals("sell_item")) {
                    if (user.getRole().equals(User.Role.ADMIN) || user.getRole().equals(User.Role.MANAGER)) {
                        deliveryServiceUtility.sellItem(tokens[1], tokens[2], tokens[3]);
                    } else {
                        System.out.println("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                        logger.info("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                    }
                }else if (tokens[0].equals("display_items")) {
                    deliveryServiceUtility.displayItems(tokens[1]);
                }else if (tokens[0].equals("make_pilot")) {
                    if (user.getRole().equals(User.Role.ADMIN) || user.getRole().equals(User.Role.MANAGER)) {
                        deliveryServiceUtility.makePilot(tokens[1],tokens[2],tokens[3],tokens[4],tokens[5],tokens[6],tokens[7]);
                    } else {
                        System.out.println("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                        logger.info("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                    }
                }else if (tokens[0].equals("display_pilots")) {
                    if (user.getRole().equals(User.Role.ADMIN) || user.getRole().equals(User.Role.MANAGER)) {
                        deliveryServiceUtility.displayPilots();
                    } else {
                        System.out.println("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                        logger.info("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                    }
                }else if (tokens[0].equals("make_drone")) {
                    if (user.getRole().equals(User.Role.ADMIN) || user.getRole().equals(User.Role.MANAGER)) {
                        deliveryServiceUtility.makeDrone(tokens[1],tokens[2],tokens[3],tokens[4]);
                    } else {
                        System.out.println("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                        logger.info("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                    }
                }else if (tokens[0].equals("display_drones")) {
                    if (user.getRole().equals(User.Role.ADMIN) || user.getRole().equals(User.Role.MANAGER)) {
                        deliveryServiceUtility.displayDrones(tokens[1]);
                    } else {
                        System.out.println("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                        logger.info("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                    }
                }else if (tokens[0].equals("fly_drone")) {
                    if (user.getRole().equals(User.Role.ADMIN) || user.getRole().equals(User.Role.MANAGER)) {
                        deliveryServiceUtility.flyDrone(tokens[1],tokens[2],tokens[3]);
                    } else {
                        System.out.println("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                        logger.info("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                    }

                }else if (tokens[0].equals("make_customer")) {
                    if (user.getRole().equals(User.Role.ADMIN) || user.getRole().equals(User.Role.MANAGER)) {
                        deliveryServiceUtility.makeCustomer(tokens[1],tokens[2],tokens[3],tokens[4],tokens[5],tokens[6],tokens[7]);
                    } else {
                        System.out.println("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                        logger.info("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                    }
                }else if (tokens[0].equals("display_customers")) {
                    if (user.getRole().equals(User.Role.ADMIN) || user.getRole().equals(User.Role.MANAGER)) {
                        deliveryServiceUtility.displayCustomers();
                    } else {
                        System.out.println("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                        logger.info("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                    }
                }else if (tokens[0].equals("start_order")) {
                    if (user.getRole().equals(User.Role.ADMIN) || user.getRole().equals(User.Role.CUSTOMER)) {
                        deliveryServiceUtility.startOrder(tokens[1],tokens[2],tokens[3],tokens[4]);
                    } else {
                        System.out.println("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                        logger.info("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                    }
                }else if (tokens[0].equals("display_orders")) {
                    if (user.getRole().equals(User.Role.ADMIN) || user.getRole().equals(User.Role.MANAGER)) {
                        deliveryServiceUtility.displayOrders(tokens[1]);
                    } else {
                        System.out.println("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                        logger.info("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                    }
                }else if (tokens[0].equals("request_item")) {
                    if (user.getRole().equals(User.Role.ADMIN) || user.getRole().equals(User.Role.CUSTOMER)) {
                        deliveryServiceUtility.requestItem(tokens[1],tokens[2],tokens[3],tokens[4],tokens[5]);
                    } else {
                        System.out.println("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                        logger.info("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                    }
                }else if (tokens[0].equals("purchase_order")) {
                    if (user.getRole().equals(User.Role.ADMIN) || user.getRole().equals(User.Role.CUSTOMER)) {
                        deliveryServiceUtility.purchaseOrder(tokens[1],tokens[2]);
                    } else {
                        System.out.println("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                        logger.info("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                    }
                }else if(tokens[0].equals("cancel_order")) {
                    deliveryServiceUtility.cancelOrder(tokens[1],tokens[2]);
                }else if(tokens[0].equals("make_user")) {
                    if (user.getRole().equals(User.Role.ADMIN)) {
                        deliveryServiceUtility.makeUser(tokens[1],tokens[2],tokens[3],tokens[4],tokens[5],tokens[6]);
                    } else {
                        System.out.println("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                        logger.info("User " + user.getName() + " Role: " + user.getRole() + " has no privilege to run command " + tokens[0]);
                    }
                }else if (wholeInputLine.equals("stop")) {
                    System.out.println("stop acknowledged");
                    break;
                } else if (!wholeInputLine.startsWith("//")) {
                    System.out.println("command " + tokens[0] + " NOT acknowledged");
                    logger.info("command " + tokens[0] + " NOT acknowledged");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }
        commandLineInput.close();
    }
}
