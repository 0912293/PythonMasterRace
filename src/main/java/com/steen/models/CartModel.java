package com.steen.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import static com.steen.Main.connection;

public class CartModel implements Model {
    private HashMap<Integer, Integer> products;

    public CartModel() {
        products = new HashMap<>();
    }

    public static <T> boolean checkForValue(T value, String table, String column) {
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + table + " t WHERE t." + column + " = " + value.toString();
        try {
            PreparedStatement myStmt = connection.prepareStatement(query);
            resultSet = myStmt.executeQuery(query);
            return resultSet.isBeforeFirst();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public void addToCart(int productID, int amount) {
        if (products.containsKey(productID)) {
            products.compute(productID, (key, val) -> val + amount);
        } else {
            products.put(productID, amount);
        }
    }

    public boolean removeFromCart(int productID, int amount) {
        if (products.containsKey(productID)) {
            if (products.get(productID) > amount) {
                products.compute(productID, (key, val) -> val - amount);
                return true;
            } else {
                products.remove(productID);
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Integer i : products.keySet()) {
            sb.append(i);
            sb.append(" : ");
            sb.append(products.get(i));
            sb.append("\n");
        }
        return sb.toString();
    }
}
