package com.steen.Models;

import com.steen.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * Created by jesse on 16-1-2017.
 */
public class WishlistModel implements Model {
    Connection connection = Main.connection;

    private String getquery;
    private String insertquery;

    public WishlistModel() {

    }

//    public ResultSet getResult(String username){
//        ResultSet result = null;
//        try {
//            PreparedStatement myStmt = connection.prepareStatement(getQuery(username));
//            result = myStmt.executeQuery();
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//
//        return result;
//    }

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

        this.insertquery = "INSERT INTO wishlist (username, games_id)" +
                "VALUES('" + username + "'," +
                "" + id + ");";

        System.out.print(this.insertquery);
    }

    public static String getQuery(String username) {
        //System.out.println("Get query gets executed.");
        return "SELECT * FROM wishlist, games WHERE username = '" + username + "' AND wishlist.games_id = games.games_id;";


    }

    public static String deleteQuery(String username, List<Integer> list) {
        String result = "";
        String allItems;
        for (int item: list) {
            result += "DELETE FROM wishlist WHERE username = '" + username + "' AND wishlist.games_id in(" + item + ";";
        }

        return result;

    }
}


