package com.steen.models;

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
    String newpass2;

    String sql;
    Connection connection = Main.connection;

    public void setUsername(String Username) { this.username = Username;}
    public void setOldpass(String Oldpass) { this.oldpass = Cryptr.getInstance(Oldpass, Cryptr.Type.MD5).getEncryptedString();}
    public void setNewpass(String Newpass) { this.newpass = Cryptr.getInstance(Newpass, Cryptr.Type.MD5).getEncryptedString();}
    public void setNewpass2(String Newpass2) { this.newpass2 = Cryptr.getInstance(Newpass2, Cryptr.Type.MD5).getEncryptedString();}

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
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean UpdatePassword(){
        PreparedStatement myStmt;
        if(newpass.equals(newpass2)) {
            if (password.equals(oldpass)) {
                try {
                    sql = "UPDATE users SET users.password = ? WHERE users.username = ?;";

                    myStmt = connection.prepareStatement(sql);
                    myStmt.setString(1, newpass);
                    myStmt.setString(2, this.username);

                    myStmt.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }
}
