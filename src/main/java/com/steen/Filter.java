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

    public void FilterData(String filter){
        this.sql = "SELECT g.games_name" +
                    " FROM games g" +
                    " ORDER BY " + filter;
    }

    public void LikeData(String search){
        this.sql = "SELECT g.games_name" +
                " FROM games g" +
                " WHERE g.games_name LIKE " + "'%" + search + "%'";
    }

    public void SearchData() {
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
