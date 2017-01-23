package com.steen.models;

import com.steen.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AdminProductModel implements Model{
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
