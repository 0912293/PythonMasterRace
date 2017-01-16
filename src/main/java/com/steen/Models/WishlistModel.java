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

        getResult();
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
        try{
            Statement myStmt = connection.createStatement();
            myStmt.execute(insertquery);

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public void updateInsert(){

    }

    public void updateGet(){

    }
}


