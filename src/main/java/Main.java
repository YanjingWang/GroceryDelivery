import edu.gatech.cs6310.DeliveryService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Welcome to the Grocery Express Delivery Service!");
        System.out.println("Welcome to the Grocery Express Delivery Service!");

        DeliveryService simulator = new DeliveryService();
        simulator.commandLoop();
        System.out.println("simulation terminated");
        logger.info("simulation terminated");
    }
}
