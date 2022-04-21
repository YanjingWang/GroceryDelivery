package edu.gatech.cs6310;

/**
 * This is a global config file, all setting variables should be final.
 */
public class Settings {
    public static final String PASSWORD_SALT = "2022Spring-Team-60";
    public static final int AUTH_ATTEMPT_LIMIT = 5;

    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_CONNECTION = "jdbc:mysql://database:3306/delivery";
    public static final String DB_USER = "admin";
    public static final String DB_PASSWORD = "password";

}
