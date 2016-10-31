import com.steen.Cryptr;
import org.apache.velocity.runtime.directive.Parse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Admin {
    String sql;
    String username;
    String name;
    String surname;
    String email;
    String birtdate;
    int address_id;
    String address_country;
    String address_street;
    String address_postalcode;
    String address_number;
    String address_city;
    ResultSet rs;
    StringBuilder sbuilder;


    Connection connection = Main.connection;

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
                birtdate = rs.getString("birth_date");
                address_id = rs.getInt("address_id");
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
        USERNAME,NAME,SURNAME,EMAIL,BIRTHDATE,COUNTRY,STREET,POSTAL,NUMBER,CITY
    }

    public String getData(Data data){
        String result="";
        switch(data){
            case NAME:
                result = name;
                break;
            case SURNAME:
                result = surname;
                break;
            case USERNAME:
                result = username;
                break;
            case EMAIL:
                result = email;
                break;
            case BIRTHDATE:
                result = birtdate;
                break;
            case COUNTRY:
                result = address_country;
                break;
            case STREET:
                result = address_street;
                break;
            case POSTAL:
                result = address_postalcode;
                break;
            case NUMBER:
                result = address_number;
                break;
            case CITY:
                result = address_city;
                break;
        }
        return result;
    }
}