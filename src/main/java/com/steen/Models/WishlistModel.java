package com.steen.Models;

import com.steen.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by jesse on 16-1-2017.
 */
public class WishlistModel {
    Connection connection = Main.connection;

    private String getquery;
    private String insertquery;

    public WishlistModel(){

    }

    public ResultSet getResult(String username){
        ResultSet result = null;
        updateGet(username);
        try {
            PreparedStatement myStmt = connection.prepareStatement(getquery);
            result = myStmt.executeQuery();
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    public void insertItem(String username, int id){
        updateInsert(username, id);
        try{
            Statement myStmt = connection.createStatement();
            myStmt.execute(this.insertquery);

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    private void updateInsert(String username, int id){

        this.insertquery = "INSERT INTO wishlist (username, games_id)" +
                "VALUES('" + username + "'," +
                "" + id + ");";

        System.out.print(this.insertquery);
    }

    private void updateGet(String username){
        this.getquery = "SELECT games_name, games_platform FROM games g, wishlist w" +
                "WHERE username = '" + username + "'" +
                "AND w.games_id = g.games_id ;";
    }
}


