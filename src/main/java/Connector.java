import java.sql.Connection;
import java.sql.DriverManager;

public final class Connector {

    public static Connection connect(){

        try {
            String url = "jdbc:mysql://localhost:3306/webshoptest";
            String user = "root";
            String password = "Test123";

            Connection connection = DriverManager.getConnection(url, user, password);

            System.out.println("Connection succesfull");
            return connection;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }
}
