package com.steen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Admin {
    String sql;
    String username;
    String name;
    String surname;
    String email;
    String birtdate;
    int address_id;
    String address_country;
    String address_street;
    String address_postalcode;
    String address_number;
    String address_city;
    ResultSet rs;
    StringBuilder sbuilder;


    Connection connection = Main.connection;

    public void searchUser(String user) {
        sbuilder = new StringBuilder();
        try {
            sql = "SELECT * FROM users WHERE username = '" + user + "'";

            PreparedStatement myStmt = connection.prepareStatement(sql);
            myStmt.executeUpdate();

            rs = myStmt.executeQuery();


            while (rs.next()) {
                username = rs.getString("username");
                name = rs.getString("name");
                surname = rs.getString("surname");
                email = rs.getString("email");
                birtdate = rs.getString("birtdate");
                address_id = rs.getInt("address_id");
            }

            searchUserAddress();



        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void searchUserAddress() {
        try {
            sql = "SELECT * FROM address WHERE address_id = '" + address_id + "'";

            PreparedStatement myStmt = connection.prepareStatement(sql);
            myStmt.executeUpdate();

            rs = myStmt.executeQuery();


            while (rs.next()) {
                address_country = rs.getString("address_country");
                address_city = rs.getString("address_city");
                address_number = rs.getString("address_number");
                address_postalcode = rs.getString("address_postalcode");
                address_street = rs.getString("address_street");
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}