package com.steen.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.steen.Main.connection;

public class dbUtil {
    public static <T> boolean checkForValue(T value, String table, String column) {
        ResultSet resultSet;
        String query = "SELECT * FROM " + table + " t WHERE t." + column + " = " + value.toString();
        try {
            PreparedStatement myStmt = connection.prepareStatement(query);
            resultSet = myStmt.executeQuery(query);
            return resultSet.isBeforeFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
