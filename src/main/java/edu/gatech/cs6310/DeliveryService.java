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
                    deliveryServiceUtility.makeStore(tokens[1], tokens[2]);
               } else if (tokens[0].equals("display_stores")) {
                    deliveryServiceUtility.displayStores();
                }else if (tokens[0].equals("sell_item")) {
                    deliveryServiceUtility.sellItem(tokens[1], tokens[2], tokens[3]);
                }else if (tokens[0].equals("display_items")) {
                    deliveryServiceUtility.displayItems(tokens[1]);
                }else if (tokens[0].equals("make_pilot")) {
                    deliveryServiceUtility.makePilot(tokens[1],tokens[2],tokens[3],tokens[4],tokens[5],tokens[6],tokens[7]);
                }else if (tokens[0].equals("display_pilots")) {
                    deliveryServiceUtility.displayPilots();
                }else if (tokens[0].equals("make_drone")) {
                    deliveryServiceUtility.makeDrone(tokens[1],tokens[2],tokens[3],tokens[4]);
                }else if (tokens[0].equals("display_drones")) {
                    deliveryServiceUtility.displayDrones(tokens[1]);
                }else if (tokens[0].equals("fly_drone")) {
                    deliveryServiceUtility.flyDrone(tokens[1],tokens[2],tokens[3]);
                }else if (tokens[0].equals("make_customer")) {
                    deliveryServiceUtility.makeCustomer(tokens[1],tokens[2],tokens[3],tokens[4],tokens[5],tokens[6]);
                }else if (tokens[0].equals("display_customers")) {
                    deliveryServiceUtility.displayCustomers();
                }else if (tokens[0].equals("start_order")) {
                    deliveryServiceUtility.startOrder(tokens[1],tokens[2],tokens[3],tokens[4]);
                }else if (tokens[0].equals("display_orders")) {
                    deliveryServiceUtility.displayOrders(tokens[1]);
                }else if (tokens[0].equals("request_item")) {
                    deliveryServiceUtility.requestItem(tokens[1],tokens[2],tokens[3],tokens[4],tokens[5]);
                }else if (tokens[0].equals("purchase_order")) {
                    deliveryServiceUtility.purchaseOrder(tokens[1],tokens[2]);
                }else if(tokens[0].equals("cancel_order")) {
                    deliveryServiceUtility.cancelOrder(tokens[1],tokens[2]);
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
