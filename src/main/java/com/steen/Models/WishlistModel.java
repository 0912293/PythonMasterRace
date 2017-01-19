package com.steen.Models;

import com.steen.Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class WishlistModel implements Model {
    Connection connection = Main.connection;

    private String getquery;
    private String insertquery;
    private String deletequery;
    private ArrayList<String> deletelist = new ArrayList<>();

    public WishlistModel() {
    }


    public String getUserWishlist(String username){
        return "SELECT * FROM user_wishlist u WHERE u.username =" + "'" + username + "'";
    }

    public String addUserWishlist(String username){
        return "INSERT INTO user_wishlist(username) values(" + "'" + username + "')";
    }

    public String insertItem(int wishlistID, int id) {
        return "INSERT INTO wishlist values("+ "'" + wishlistID + "'," + "'" + id + "')";
    }

    public ResultSet selectExecutor(String query){
        ResultSet resultSet = null;
        try{
            Statement myStmt = connection.createStatement();
            resultSet = myStmt.executeQuery(query);
            return resultSet;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return resultSet;
    }

    public void executeUpdate(String query){
        try{
            Statement myStmt = connection.createStatement();
            myStmt.executeUpdate(query);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }



    public void deleteItem(String username, ArrayList<Integer> list){
        updateDelete(username, list);
        try{
            Statement myStmt = connection.createStatement();
            for (String item : deletelist) {
                myStmt.execute(item);

            }
        } catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getQuery(String username) {
        //System.out.println("Get query gets executed.");
        return "SELECT * FROM wishlist w, games g, user_wishlist uw " +
                "WHERE uw.username = '" + username + "'" +
                " AND uw.wishlist_id = w.wishlist_id" +
                " AND w.games_id = g.games_id;";

    }

    private void updateDelete(String username, ArrayList<Integer> list) {
        String result = "";
        ArrayList<String> sql_list = new ArrayList<>();
        for (int item: list) {
            result = "DELETE FROM wishlist " +
                    "WHERE username = '" + username + "' AND wishlist.games_id = " + item + ";";
            sql_list.add(result);
        }
        this.deletelist = sql_list;
    }
}


