package com.steen.Models;

import com.steen.Cryptr;
import com.steen.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserModel implements Model {
    String password;
    String oldpass;
    String newpass;
    String username;

    String sql;
    Connection connection = Main.connection;

    public void setUsername(String Username) { this.username = Username;}
    public void setOldpass(String Oldpass) { this.oldpass = Cryptr.getInstance(Oldpass, Cryptr.Type.MD5).getEncryptedString();
        System.out.println(oldpass);}
    public void setNewpass(String Newpass) { this.newpass = Cryptr.getInstance(Newpass, Cryptr.Type.MD5).getEncryptedString();
        System.out.println(newpass);}

    public void GetPassword(){
        ResultSet rs;
        PreparedStatement myStmt;

        try {
            sql = "SELECT password FROM users u WHERE u.username = ?;";

            myStmt = connection.prepareStatement(sql);
            myStmt.setString(1, this.username);

            rs = myStmt.executeQuery();

            while (rs.next()) {
                password = rs.getString("password");
                System.out.println(password);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean UpdatePassword(){
        PreparedStatement myStmt;
        if(password.equals(oldpass)) {
            try {
                sql = "UPDATE users SET users.password = ? WHERE users.username = ?;";

                myStmt = connection.prepareStatement(sql);
                myStmt.setString(1, newpass);
                myStmt.setString(2, this.username);

                myStmt.executeUpdate();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            return true;
        }else {
            return false;
        }
    }
}
