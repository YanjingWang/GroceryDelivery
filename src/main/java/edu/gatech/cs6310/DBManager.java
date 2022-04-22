package edu.gatech.cs6310;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DBManager {
    private static Logger logger = LogManager.getLogger(DBManager.class);


    private Connection con = null;
    private Statement state = null;
    private ResultSet result;
    private PreparedStatement pstate;

    public void mysqlConnect(){
        try{
            Class.forName(Settings.DB_DRIVER);
            con = DriverManager.getConnection(Settings.DB_CONNECTION, Settings.DB_USER, Settings.DB_PASSWORD);
        }
        catch(ClassNotFoundException e){
            System.err.println("Couldn't load driver.");
            logger.error("Couldn't load driver.");
        }
        catch(SQLException e){
            System.err.println("Couldn't connect to database.");
            logger.error("Couldn't connect to database.");
        }
    }

    public ResultSet get(String query){
        this.mysqlConnect();
        try{
            state = con.createStatement();
            result = state.executeQuery(query);
        }
        catch(SQLException e){
            System.err.println("Query error: " + query);
            logger.error("Query error: " + query);
        }
        catch(NullPointerException e){
            System.err.println("Element not found." + e);
            logger.error("Element not found." + e);
        }

        return result;
    }

    public int insert(String query){
        this.mysqlConnect();
        try{
            state = con.createStatement();
            return state.executeUpdate(query);
        }
        catch(SQLException e){
            System.err.println("Query error: " + query);
            logger.error("Query error: " + query);
        }
        catch(NullPointerException e){
            System.err.println("Element not found." + e);
            logger.error("Element not found." + e);
        }

        return -1;
    }
    public int delete(String query){
        this.mysqlConnect();
        try{
            state = con.createStatement();
            return state.executeUpdate(query);
        }
        catch(SQLException e){
            System.err.println("Query error: " + query);
            logger.error("Query error: " + query);
        }
        catch(NullPointerException e){
            System.err.println("Element not found." + e);
            logger.error("Element not found." + e);
        }

        return -1;
    }

    public int update(String query){
        this.mysqlConnect();
        try{
            state = con.createStatement();
            return state.executeUpdate(query);
        }
        catch(SQLException e){
            System.err.println("Query error: " + query);
            logger.error("Query error: " + query);
        }
        catch(NullPointerException e){
            System.err.println("Element not found." + e);
            logger.error("Element not found." + e);
        }

        return -1;
    }

    public void closeConnection(){
        try{
            if(!con.isClosed()){
                con.close();
            }
        }
        catch(NullPointerException e){
            System.err.println("Couldn't load driver.");
            logger.error("Couldn't load driver.");
        }
        catch(SQLException e){
            System.err.println("Couldn't close database.");
            logger.error("Couldn't close database.");
        }
    }

}
