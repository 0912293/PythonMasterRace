import com.steen.Cryptr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {
    String username;
    String password;
    String sql;
    Connection connection = Main.connection;
    Boolean correctLoginInfo = false;
    Boolean admin = false;

    public Login(String username, String password){
        this.username = username;
        this.password = Cryptr.getInstance(password, Cryptr.Type.MD5).getEncryptedString();
        this.sql = "SELECT users.admin" +
                " FROM users" +
                " WHERE username = '" + (this.username) + "' AND password = '" + (this.password) + "'";
    }

    public void ParseLogin() {
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

    public Boolean isAdmin() {
        return admin;
    }
}
