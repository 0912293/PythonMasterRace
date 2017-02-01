package com.steen.models;

import com.steen.Main;

import com.steen.util.SQLToJSON;
import com.steen.session.Search;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static com.steen.util.SQLToJSON.JsonListToString;
import static com.steen.util.SQLToJSON.getFormattedResult;

public class AdminProductModel implements Model{
    private Search search = new Search("SELECT * FROM users");
    String sql;
    Connection connection = Main.connection;

    public void AddGame(String name, float price, String genre, String platform, String publisher, int stock, String image) {
        PreparedStatement myStmt;
        try {
            sql = "INSERT INTO webshopdb.games (games_name,games_price,games_genre,games_platform,games_publisher,games_stock,games_image) VALUES " +
                    "('" + name + "'," +
                    "" + price + "," +
                    "'" + genre + "'," +
                    "'" + platform + "'," +
                    "'" + publisher + "'," +
                    "" + stock + "," +
                    "'" + image + "');";

            myStmt = connection.prepareStatement(sql);

            myStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateGame(String name, float price, String genre, String platform, String publisher, int stock, String image, int id) {
        PreparedStatement myStmt;
        try {
            sql = "UPDATE webshopdb.games SET " +
                    "games.games_name ='" + name + "'," +
                    "games.games_price =" + price + "," +
                    "games.games_genre ='" + genre + "'," +
                    "games.games_platform ='" + platform + "'," +
                    "games.games_publisher ='" + publisher + "'," +
                    "games.games_stock =" + stock + "," +
                    "games.games_image ='" + image + "' WHERE games.games_id ="+id;

            myStmt = connection.prepareStatement(sql);

            myStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void AddPlatform(String name, float price, String color, String manufact, String memory, int stock){
        PreparedStatement myStmt;
        try {
            sql = "INSERT INTO webshopdb.platforms (platform_name,platform_colour,platform_manufacturer,platform_price,platform_memory,platform_stock) VALUES " +
                    "('" + name + "'," +
                    "'" + color + "'," +
                    "'" + manufact + "'," +
                    "" + price + "," +
                    "'" + memory + "'," +
                    "" + stock + ");";

            myStmt = connection.prepareStatement(sql);

            myStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getGame(String id){return getJSON("SELECT * FROM games WHERE games_id = '"+id+"'");}


    public String getJSON(String query) {
        List jsonList;
        try {
            jsonList = getFormattedResult(Search.getResultSet(query));
            return JsonListToString(jsonList, SQLToJSON.Type.ARRAY);
        } catch (Exception e) {
        }
        return null;
    }
}
