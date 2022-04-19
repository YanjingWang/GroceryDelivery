package edu.gatech.cs6310;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private static DBManager manager = new DBManager();
    private static Logger logger = LogManager.getLogger(Controller.class);


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

}
