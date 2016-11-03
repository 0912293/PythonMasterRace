package com.steen.Models;

import com.steen.Cryptr;
import com.steen.DateBuilder;
import com.steen.Main;
import com.steen.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AdminModel {
    String sql;
    String username = "";
    String name;
    String surname;
    String email;
    String year;
    String month;
    String day;
    int address_id;
    String address_country;
    String address_street;
    String address_postalcode;
    String address_number;
    String address_city;
    String birth_date;
    Boolean admin;
    private ArrayList<User> users = new ArrayList<>();
    DateBuilder dbuilder = new DateBuilder();
    ResultSet rs;

    Connection connection = Main.connection;

    //---------------------------------userlist--------------------
    private void userlist(){
        try {
            sql = "SELECT * FROM users;";

            PreparedStatement myStmt = connection.prepareStatement(sql);
            rs = myStmt.executeQuery(sql);

            users.clear();
            while (rs.next()) {
                String username = rs.getString("username");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                User user = new User(username, name, surname);
                users.add(user);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<User> getUsers() {
        userlist();
        return users;
    }

    //-----------------------------------------check-----------
    public boolean check(){
        try {
            sql = "SELECT admin FROM users WHERE username = '"+ this.username +"';";

            PreparedStatement myStmt = connection.prepareStatement(sql);
            rs = myStmt.executeQuery(sql);

            while(rs.next()){
                admin = rs.getBoolean("admin");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return admin;
    }
//----------------------------------delete-resetPassword-blacklist-----

    public void blacklistUser(){
        try {
            sql = "INSERT INTO blacklist (username,blacklisted) VALUES ('" + this.username + "',true);";

            PreparedStatement myStmt = connection.prepareStatement(sql);
            myStmt.executeUpdate();
            System.out.println("blacklisted user");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete_user(){
        if(!check()) {
            try {
                sql = "DELETE FROM users WHERE username = '" + this.username + "';";

                PreparedStatement myStmt = connection.prepareStatement(sql);
                myStmt.executeUpdate();
                System.out.println("deleted user");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void resetPassword(){
        if(!check()) {
            try {
                sql = "UPDATE users SET password = '" + Cryptr.getInstance("0000", Cryptr.Type.MD5).getEncryptedString() + "' WHERE username = '" + this.username + "';";

                PreparedStatement myStmt = connection.prepareStatement(sql);
                myStmt.executeUpdate();
                System.out.println("reseted password");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //---------update user
    public void setData(String name, String surname, String email, String year, String month, String day, String country, String street, String postal, String number, String city){
        if(!check()) {
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.year = year;
            this.month = month;
            this.day = day;
            this.address_country = country;
            this.address_street = street;
            this.address_postalcode = postal;
            this.address_number = number;
            this.address_city = city;
            dbuilder.build(this.day, this.month, this.year);
            this.birth_date = dbuilder.getDate();

            updateAddress();
            updateUser();
        }
    }

    public void clear() {
        this.name = null;
        this.surname = null;
        this.email = null;
        this.year = null;
        this.month = null;
        this.day = null;
        this.address_country = null;
        this.address_street = null;
        this.address_postalcode = null;
        this.address_number = null;
        this.address_city = null;
        this.birth_date = null;
        updateAddress();
        updateUser();
    }

    private void updateAddress() {
        try {
            sql = "UPDATE address SET address_country = '" +
                    this.address_country + "', address_postalcode = '" +
                    this.address_postalcode + "', address_city = '" +
                    this.address_city + "', address_street = '" +
                    this.address_street + "', address_number = '" +
                    this.address_number + "' WHERE address_id = '"+ this.address_id +"';";

            PreparedStatement myStmt = connection.prepareStatement(sql);
            myStmt.executeUpdate();
            System.out.println("updated address");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateUser() {
        try {
            sql = "UPDATE users SET name = '" +
                            this.name + "', surname = '" +
                            this.surname + "', email = '" +
                            this.email + "', birth_date = '" +
                            this.birth_date + "' WHERE username = '"+ this.username +"';";

            PreparedStatement myStmt = connection.prepareStatement(sql);
            myStmt.executeUpdate();
            System.out.println("updated user");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    //-----------------------------------------get user data
    public void searchUser(String user) {
        try {
            sql = "SELECT * FROM users WHERE username = '" + user + "'";

            PreparedStatement myStmt = connection.prepareStatement(sql);
            myStmt.executeQuery(sql);

            rs = myStmt.executeQuery();

            while (rs.next()) {
                username = rs.getString("username");
                name = rs.getString("name");
                surname = rs.getString("surname");
                email = rs.getString("email");
                address_id = rs.getInt("address_id");
                admin = rs.getBoolean("admin");
            }
            searchUserAddress();
            getDate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void getDate() {
        try {
            sql = "SELECT EXTRACT(YEAR FROM birth_date) AS birthyear , " +
                    "EXTRACT(MONTH FROM birth_date) AS birthmonth , " +
                    "EXTRACT(DAY FROM birth_date) AS birthday " +
                    "FROM users WHERE username = '" + username + "'";

            PreparedStatement myStmt = connection.prepareStatement(sql);
            myStmt.executeQuery(sql);

            rs = myStmt.executeQuery();


            while (rs.next()) {
                year = rs.getString("birthyear");
                month = rs.getString("birthmonth");
                day = rs.getString("birthday");
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
            myStmt.executeQuery(sql);

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

    public enum Data{
        USERNAME,NAME,SURNAME,EMAIL,COUNTRY,STREET,POSTAL,NUMBER,CITY,YEAR,MONTH,DAY
    }

    public String getData(Data data){
        switch(data){
            case NAME:
                return name;
            case SURNAME:
                return surname;
            case USERNAME:
                return username;
            case EMAIL:
                return email;
            case YEAR:
                return year;
            case MONTH:
                return month;
            case DAY:
                return day;
            case COUNTRY:
                return address_country;
            case STREET:
                return address_street;
            case POSTAL:
                return address_postalcode;
            case NUMBER:
                return address_number;
            case CITY:
                return address_city;
        }
        return null; //just in case :^)
    }
}