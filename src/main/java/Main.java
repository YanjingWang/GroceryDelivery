import edu.gatech.cs6310.Controller;
import edu.gatech.cs6310.DeliveryService;
import edu.gatech.cs6310.Settings;

import edu.gatech.cs6310.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static edu.gatech.cs6310.Controller.hashPassword;


public class Main {

    private static Logger logger = LogManager.getLogger(Main.class);
    public static Controller controller = new Controller();

    public static void main(String[] args) {
        logger.info("Welcome to the Grocery Express Delivery Service!");
        System.out.println("Welcome to the Grocery Express Delivery Service!");
        logger.info("Please login");
        System.out.println("Please login");

        int retry = 0;
        User user = null;
        while (retry < Settings.AUTH_ATTEMPT_LIMIT && user == null) {
            user = login();
            if (user != null) {
                System.out.println("Login successfully as: " + user.getName() + " Role:" + user.getRole());
                logger.info("Login successfully as: " + user.getName() + " Role:" + user.getRole());
            } else {
                retry++;
                System.out.println("Login in failed! Try again. You have " + (Settings.AUTH_ATTEMPT_LIMIT - retry) + " time(s) left.");
                logger.info("Login in failed! Try again. You have " + (Settings.AUTH_ATTEMPT_LIMIT - retry) + " time(s) left.");
            }
        }

        if (user == null) {
            System.out.println("Login attempts exceed the limit of 5, simulation terminated");
            logger.error("Login attempts exceed the limit of 5, simulation terminated");
            return;
        }

        DeliveryService simulator = new DeliveryService(user);
        simulator.commandLoop();
        System.out.println("simulation terminated");
        logger.info("simulation terminated");
    }

    public static User login() {
        Console console = System.console();
        String inputUsername = console.readLine("Enter username: ");
        char[] passwordArr = console.readPassword("Enter password: ");
        String inputPassword = String.valueOf(passwordArr);
        String hashedPassword = hashPassword(inputPassword);
//        System.out.println("Username: " + inputUsername + " Password is: " + inputPassword + " Hashed: " + hashedPassword);

        return controller.validateUserLogin(inputUsername, hashedPassword);

    }


}
