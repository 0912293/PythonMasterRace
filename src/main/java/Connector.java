import java.sql.Connection;
import java.sql.DriverManager;

public final class Connector {
//  private static String url = "jdbc:mysql://localhost:3306/webshoptest";
//  private static String user = "root";
//  private static String password = "root";

    private static String url = "jdbc:mysql://g3project56.ddns.net:3306/webshoptest";
    private static String user = "projectuser";
    private static String password = "Hogeschool!1";

    public static Connection connect(){
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            System.out.println("Connection succesfull");
            return connection;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }
}
