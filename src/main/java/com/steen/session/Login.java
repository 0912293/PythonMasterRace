package com.steen.session;

import com.steen.Cryptr;
import com.steen.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {
    private Session session;
    private String username;
    private String password;
    private String sql;
    Boolean admin = false;
    Boolean correctLoginInfo = false;

    Login(Session session) {
        this.session = session;
    }

    void setCredentials(String username, String password){
        this.username = username;
        this.password = Cryptr.getInstance(password, Cryptr.Type.MD5).getEncryptedString();
        this.sql = "SELECT users.admin" +
                " FROM users" +
                " WHERE username = '" + (this.username) + "' AND password = '" + (this.password) + "'";
        parseLogin();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private void parseLogin() {
        try {
            PreparedStatement myStmt = session.connection.prepareStatement(sql);
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
}
