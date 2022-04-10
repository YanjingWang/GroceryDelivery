import edu.gatech.cs6310.DeliveryService;

import static edu.gatech.cs6310.DBManager.showData;

import edu.gatech.cs6310.DeliveryServiceUtility;

public class Main {


    public static void main(String[] args) {
        System.out.println("Welcome to the Grocery Express Delivery Service!");
//        showData();


        DeliveryService simulator = new DeliveryService();
        simulator.commandLoop();
        System.out.println("simulation terminated");
    }
}
