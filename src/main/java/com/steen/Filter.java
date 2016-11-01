package com.steen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Jamal on 31-10-2016.
 */
public class Filter {
    String sql;
    Connection connection = Main.connection;

    public void filterData(String filter){
        this.sql = "SELECT g.game_name" +
                    " FROM games" +
                    " ORDER BY " + filter;
    }

    public void ParseLogin() {
        try {
            PreparedStatement myStmt = connection.prepareStatement(sql);
            ResultSet resultSet = myStmt.executeQuery(sql);
            if (resultSet.next()) {

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
