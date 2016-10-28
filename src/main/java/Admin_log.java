import com.steen.Cryptr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Admin_log {
    String username;
    String sql;
    Connection connection = Main.connection;
    Boolean correctLoginInfo = false;
    Boolean admin;

    public Admin_log(String username){
        this.username = username;
    }

    public void CheckAdmin() {
        try {
            sql = "SELECT users.admin" +
                    " FROM users" +
                    " WHERE username = '" + (this.username) + "'";
            PreparedStatement myStmt = connection.prepareStatement(sql);
            ResultSet resultSet = myStmt.executeQuery(sql);
            while (resultSet.next()) {
                admin= resultSet.getBoolean("admin");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean getAdmin(){
        return admin;
    }

}