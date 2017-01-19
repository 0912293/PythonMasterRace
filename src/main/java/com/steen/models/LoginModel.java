package com.steen.models;

import com.steen.Cryptr;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.steen.Main.connection;

public class LoginModel implements Model {
    private ProductModel productModel;
    private String username;
    private String password;
    private String sql;
    Boolean admin = false;
    Boolean correctLoginInfo = false;
    Boolean blacklisted = false;
    ResultSet rs;

    public LoginModel() {}

    public void setCredentials(String username, String password){
        this.username = username;
        if(!checkBlacklist(this.username)) {
            this.password = Cryptr.getInstance(password, Cryptr.Type.MD5).getEncryptedString();
            this.sql = "SELECT users.admin" +
                    " FROM users" +
                    " WHERE username = '" + (this.username) + "' AND password = '" + (this.password) + "'";
            parseLogin();
        }
    }

    public static boolean checkBlacklist(String username){
        boolean blacklisted = false;
        try {
            String sql = "SELECT blacklisted FROM blacklist WHERE LOWER(username) = LOWER('"+ username +"');";
            PreparedStatement myStmt = connection.prepareStatement(sql);
            ResultSet rs = myStmt.executeQuery(sql);
            while(rs.next()){
                blacklisted = rs.getBoolean("blacklisted");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return blacklisted;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private void parseLogin() {
        try {
            PreparedStatement myStmt = connection.prepareStatement(sql);
            ResultSet resultSet = myStmt.executeQuery(sql);
            if (resultSet.next()) {
                correctLoginInfo = true;
                admin = resultSet.getBoolean("admin");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Boolean hasCorrectLoginInfo() {
        return correctLoginInfo;
    }

    public Boolean isAdmin() {
        return admin;
    }
}
