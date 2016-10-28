import com.steen.Cryptr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Register {
    String username;
    String password;
    String name;
    String surname;
    String country;
    String city;
    String street;
    String postal;
    int number;
    String bday;
    String email;

    String sql;

    Connection connection = Main.connection;

    public Register(String username, String password,String name,String surname,String country,String city,String street,String postal,String number,String bday, String email){
        this.username = username;
        this.password = Cryptr.getInstance(password, Cryptr.Type.MD5).getEncryptedString();
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.city = city;
        this.street = street;
        this.postal = postal;
        this.number = Integer.parseInt(number);
        this.bday = bday;
        this.email = email;
    }

    public void ParseReg() {
        try {
            sql = "INSERT INTO users (username,password,name,surname,country,city,street,postal,bday,number,email) VALUES ('"+
                    this.username+"','"+
                    this.password+"','"+
                    this.name+"','"+
                    this.surname+"','"+
                    this.country+"','"+
                    this.city+"','"+
                    this.street+"','"+
                    this.postal+"','"+
                    this.bday+"',"+
                    this.number+",'"+
                    this.email+"')";
            PreparedStatement myStmt = connection.prepareStatement(sql);
            myStmt.executeUpdate();
            System.out.println("registered");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
