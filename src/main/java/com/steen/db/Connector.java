package com.steen.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Driver.*;

public final class Connector {


    private static String url = "jdbc:mysql://projectsteen.ddns.net:3306/webshopdb";
    private static String user = "projectapp";
    private static String password = "HRO!1";

    public static Connection connect(){
        try {

            Connection connection = DriverManager.getConnection(url, user, password);

            return connection;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
