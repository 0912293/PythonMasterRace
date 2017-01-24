package com.steen.models;

import com.steen.Main;
import com.sun.istack.internal.Nullable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by jesse on 23-1-2017.
 */
public class FavoritesModel implements Model {
    private Connection connection = Main.connection;


    private String insertquery;
    public FavoritesModel(){}



    public static String getQuery(String username){
        return "SELECT * FROM " +
                "favorites f, games g " +
                "WHERE f.username = " + "'" + username + "'" +
                "AND f.games_id = g.games_id";
    }

    public void insertItem(String username, int id) {
        updateInsert(username, id);
        try {
            Statement myStmt = connection.createStatement();
            myStmt.execute(this.insertquery);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
    private void updateInsert(String username, int id) {

        this.insertquery = "INSERT INTO favorites (username, games_id)" +
                "VALUES('" + username + "'," +
                "" + id + ");";

        System.out.print(this.insertquery);
    }
}
