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
    private String username;
    private String item;
    public WishlistModel(String username, String item){
        this.username = username;
        this.item = item;
        insertItem();
        //getResult();
    }

    public ResultSet getResult(){
        ResultSet result = null;
        try {
            PreparedStatement myStmt = connection.prepareStatement(getquery);
            result = myStmt.executeQuery();
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    public void insertItem(){
        updateInsert();
        try{
            Statement myStmt = connection.createStatement();
            myStmt.execute(this.insertquery);

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public void updateInsert(){

        this.insertquery = "INSERT INTO wishlist " +
                "VALUES(" + this.username + "," +
                "" + this.item + ");";
    }

    public void updateGet(){

    }
}


