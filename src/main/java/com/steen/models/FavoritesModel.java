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
    private ArrayList<String> deleteGames = new ArrayList<>();
    private ArrayList<String> deletePlatforms = new ArrayList<>();
    public FavoritesModel(){}



    public static String getQuery(String username){
        return "SELECT * FROM " +
                "favorites f, games g " +
                "WHERE f.username = " + "'" + username + "'" +
                "AND f.og_id = g.games_id";
    }

    public static String getQuery2(String username){
        return "SELECT * FROM " +
                "favorites f, platforms p " +
                "WHERE f.username = " + "'" + username + "'" +
                "AND f.op_id = p.platform_id";
    }
    //public static String

    public Boolean checkInDatabase(String username, int id, int isGame){
        ResultSet rs = null;
        String query = null;
        if (isGame == 1) {
            query = "SELECT * FROM " +
                    "favorites f, games g " +
                    "WHERE f.username = " + "'" + username + "'" +
                    "AND f.og_id = " + id + ";";
        } else{
            query = "SELECT * FROM " +
                    "favorites f, platforms p " +
                    "WHERE f.username = " + "'" + username + "'" +
                    "AND f.op_id = " + id + ";";
        }
        try{
            Statement myStmt = connection.createStatement();
            rs = myStmt.executeQuery(query);
            return rs.isBeforeFirst();
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
    public void insertItem(String username, int id, int isGame) {
        updateInsert(username, id, isGame);
        try {
            Statement myStmt = connection.createStatement();
            myStmt.execute(this.insertquery);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void updateInsert(String username, int id, int isGame) {
        if (isGame == 1) {
            this.insertquery = "INSERT INTO favorites (username, og_id, op_id)" +
                    "VALUES('" + username + "'," +
                    "" + id + ", -1);";
        } else{
            this.insertquery = "INSERT INTO favorites (username, og_id, op_id)" +
                    "VALUES('" + username + "', -1," +
                    "" + id + ");";
        }
        System.out.print(this.insertquery);
    }

    public void deleteItems(String username, ArrayList<Integer> games, ArrayList<Integer> platforms){
        updateDelete(username, games, platforms);
        try{
            Statement myStmt = connection.createStatement();
            for (String item : deleteGames) {
                myStmt.execute(item);
            } for (String item : deletePlatforms) {
                myStmt.execute(item);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void updateDelete(String username, ArrayList<Integer> games, ArrayList<Integer> platforms){
        String result = "";
        ArrayList<String> sql_list = new ArrayList<>();
        for(int item: games){
            result = "DELETE FROM favorites " +
                    "WHERE favorites.username = '" + username + "' " +
                    "AND favorites.og_id = " + item + ";";
            deleteGames.add(result);
        }
        for(int item: platforms){
            result = "DELETE FROM favorites " +
                    "WHERE favorites.username = '" + username + "' " +
                    "AND favorites.op_id = " + item + ";";
            deletePlatforms.add(result);
        }

    }
}
