package com.steen.models;
import com.steen.Cryptr;
import com.steen.Main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterModel implements Model {
    public String username;
    public String password;
    public String name;
    public String surname;
    public String country;
    public String city;
    public String street;
    public String postal;
    public String number;
    public String birth_date;
    public String email;
    public Boolean admin;
    int address_id;
    String sql;
    Connection connection = Main.connection;

    public RegisterModel() {}

    //----setters-----------------------------------
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) { this.password = Cryptr.getInstance(password, Cryptr.Type.MD5).getEncryptedString();}
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public void setPostal(String postal) {
        this.postal = postal;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public void setParameters(String username,String password,String name,String surname,String country,String city,String street,String postal,String number,String birth_date,String email) {
        this.username = username;
        this.password = Cryptr.getInstance(password, Cryptr.Type.MD5).getEncryptedString();
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.city = city;
        this.street = street;
        this.postal = postal;
        this.number = number;
        this.birth_date = birth_date;
        this.email = email;
        this.admin = false;
    }

    public void ParseReg() {
        try {
            sql = "INSERT INTO address (address_country, address_postalcode, address_city, address_street, address_number) VALUES ('" +
                    this.country + "','" +
                    this.postal + "','" +
                    this.city + "','" +
                    this.street + "','" +
                    this.number + "');";

            PreparedStatement myStmt = connection.prepareStatement(sql);
            myStmt.executeUpdate();

            ParseRegUser();
            ParseLinkAddressToUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ParseRegUser() {
        try {
            sql =
                    "INSERT INTO users (username, password, name, surname, email, birth_date) VALUES ('" +
                            this.username + "','" +
                            this.password + "','" +
                            this.name + "','" +
                            this.surname + "','" +
                            this.email + "','" +
                            this.birth_date + "');";

            PreparedStatement myStmt = connection.prepareStatement(sql);
            myStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int ParseAddress() {
        ResultSet rs = null;
        PreparedStatement myStmt;
        int i = 0;

        try {
            sql = "SELECT address_id FROM address a WHERE a.address_country = ? AND a.address_postalcode = ?" +
                    "AND a.address_city = ? AND a.address_street = ? AND a.address_number = ?;";

            myStmt = connection.prepareStatement(sql);
            myStmt.setString(1, this.country);
            myStmt.setString(2, this.postal);
            myStmt.setString(3, this.city);
            myStmt.setString(4, this.street);
            myStmt.setString(5, this.number);

            rs = myStmt.executeQuery();

            while (rs.next()) {
                i = rs.getInt("address_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    private void ParseLinkAddressToUser(){
        PreparedStatement myStmt;
        int id = ParseAddress();
        try {
            sql = "UPDATE users SET users.address_id = ? WHERE users.username = ?;";

            myStmt = connection.prepareStatement(sql);
            myStmt.setInt(1, id);
            myStmt.setString(2, this.username);

            myStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



