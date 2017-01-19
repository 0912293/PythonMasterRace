package com.steen.db;

import java.sql.Connection;
import java.sql.DriverManager;

public final class Connector {
//  private static String url = "jdbc:mysql://localhost:3306/webshoptest";
//  private static String user = "root";
//  private static String password = "root";

    private static String url = "jdbc:mysql://projectsteen.ddns.net:3306/webshopdb?&amp;allowMultiQueries=true";
//    private static String url = "jdbc:mysql://localhost:3306/webshopdb"; //gebruiken bij deployen
    private static String user = "projectapp";
    private static String password = "HRO!1";

    public static Connection connect(){
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            System.out.println("Connection succesfull");
            return connection;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }
}
