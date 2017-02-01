package com.steen.models;
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

    public String addUserWishlist(String username, String crypted_user){
        return "INSERT INTO user_wishlist VALUES(" + "'" + username + "'" + "," + "'" + crypted_user + "')";
    }

    public String insertItem(String crypted_user, int id) {
        return "INSERT INTO wishlist VALUES(" + "'" + crypted_user + "',"  + id + ")";
    }

    public ResultSet selectExecutor(String query){
        ResultSet resultSet = null;
        try{
            Statement myStmt = connection.createStatement();
            resultSet = myStmt.executeQuery(query);
            return resultSet;
        }
        catch (Exception e){
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
            e.printStackTrace();
        }
    }

    public void deleteItem(String cryptedUser, ArrayList<Integer> list){
        updateDelete(cryptedUser, list);
        try{
            Statement myStmt = connection.createStatement();
            for (String item : deletelist) {
                myStmt.execute(item);

            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getQuery(String crypt){
        return "SELECT * FROM " +
                "wishlist w, games g " +
                "WHERE w.crypted_user = " + "'" + crypt + "'" +
                "AND w.games_id = g.games_id";
    }

    public String getUserCrypt(String username){
        return "SELECT crypted_user FROM user_wishlist WHERE username="+ "'" + username + "'";
    }

    private void updateDelete(String cryptedUser, ArrayList<Integer> list) {
        String result = "";
        ArrayList<String> sql_list = new ArrayList<>();
        for (int item: list) {
            result = "DELETE FROM wishlist " +
                    "WHERE crypted_user = '" + cryptedUser + "' AND wishlist.games_id = " + item + ";";
            sql_list.add(result);
        }
        this.deletelist = sql_list;
    }
}


