package edu.gatech.cs6310;

import java.sql.*;

public class DBManager {
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String connection = "jdbc:mysql://localhost:3306/delivery";
    private static String user = "admin";
    private static String password = "password";


    private static Connection con = null;
    private static Statement state = null;
    private static ResultSet result;
    private static PreparedStatement pstate;

    public static void mysqlConnect(){
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(connection, user, password);
            System.out.println("Successfully connected to database.");
        }
        catch(ClassNotFoundException e){
            System.err.println("Couldn't load driver.");
        }
        catch(SQLException e){
            System.err.println("Couldn't connect to database.");
        }
    }

    public static void showData(){
        mysqlConnect();
        try{
            state = con.createStatement();
            result = state.executeQuery("select * from user where 1=1");
            while(result.next()){
                String user = result.getString("user_name");
                String password = result.getString("password");
                System.out.println("Username: " + user + " Password: " + password);
            }
        }
        catch(SQLException e){
            System.err.println("Query error.");
        }
        catch(NullPointerException e){
            System.err.println("Element not found.");
        }
        closeConnection();
    }



    public static void closeConnection(){
        try{
            if(!con.isClosed()){
                con.close();
                System.out.println("Database closed successfully.");
            }
        }
        catch(NullPointerException e){
            System.err.println("Couldn't load driver.");
        }
        catch(SQLException e){
            System.err.println("Couldn't close database.");
        }
    }

}
