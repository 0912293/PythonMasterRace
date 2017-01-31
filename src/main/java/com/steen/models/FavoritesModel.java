package com.steen.models;

import com.steen.Main;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by jesse on 23-1-2017.
 */


public class FavoritesModel implements Model {
    private Connection connection = Main.connection;


    private String insertquery;
    private ArrayList<String> deleteList = new ArrayList<>();
    public FavoritesModel(){}



    public static String getQuery(String username){
        return "SELECT * FROM " +
                "favorites f, games g " +
                "WHERE f.username = " + "'" + username + "'" +
                "AND f.og_id = g.games_id";
    }

    public Boolean checkInDatabase(String username, int id){
        ResultSet rs = null;
        String query = "SELECT * FROM " +
                "favorites f, games g " +
                "WHERE f.username = " + "'" + username + "'" +
                "AND f.og_id = " + id + ";";
        try{
            Statement myStmt = connection.createStatement();
            rs = myStmt.executeQuery(query);
            return rs.isBeforeFirst();
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }

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

        this.insertquery = "INSERT INTO favorites (username, og_id)" +
                "VALUES('" + username + "'," +
                "" + id + ");";

        System.out.print(this.insertquery);
    }

    public void deleteItem(String username, ArrayList<Integer> list){
        updateDelete(username, list);
        try{
            Statement myStmt = connection.createStatement();
            for (String item : deleteList) {
                myStmt.execute(item);

            }
        } catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateDelete(String username, ArrayList<Integer> list){
        String result = "";
        ArrayList<String> sql_list = new ArrayList<>();
        for(int item: list){
            result = "DELETE FROM favorites " +
                    "WHERE favorites.username = '" + username + "' " +
                    "AND favorites.og_id = " + item + ";";
            sql_list.add(result);
        }
        this.deleteList = sql_list;

    }
}
