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

    public Login(String username, String password){
        this.username = username;
        this.password = Cryptr.getInstance(password, Cryptr.Type.MD5).getEncryptedString();
    }

    public void ParseLogin() {
        try {
            sql = "SELECT users.username, users.password" +
                    " FROM users" +
                    " WHERE username = '" + (this.username) + "' and password = '" + (this.password) + "'";
            PreparedStatement myStmt = connection.prepareStatement(sql);
            ResultSet resultSet = myStmt.executeQuery(sql);
            if (resultSet.next()) {
                correctLoginInfo = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
