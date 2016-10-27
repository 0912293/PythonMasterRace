import static spark.Spark.*;

import com.steen.Cryptr;
import org.eclipse.jetty.util.PathWatcher;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


public class Main {
    static Connection connection = Connector.connect();
    private static String Password;
    private static String Username;
    private static Login login;
    private static Map<String, Object> homeModel = new HashMap<String, Object>();

    public static void main(String[] args) {
        staticFileLocation("/public");              // sets folder for non java files

        //--------------------------------------------------------project
        String h_layout = "templates/p_home.vtl";
        String p_layout = "templates/p_layout.vtl";
        get("/", (req, res) -> {

            homeModel.put("username", req.session().attribute("username"));
            homeModel.put("pass", req.session().attribute("pass"));
            homeModel.put("template","templates/p_home.vtl");
            return new ModelAndView(homeModel, p_layout);
        }, new VelocityTemplateEngine());

        post("/login", (req, res) -> {
            Username = req.queryParams("username");
            req.session().attribute("username", Username);
            if(Username.contains(" ")){
                System.out.println("Your username contains spaces");
            }
            Password = req.queryParams("pass");
            req.session().attribute("pass", Password);
            homeModel.put("username", Username);
            homeModel.put("pass", Password);
            login = new Login(Username, Password);
            login.ParseLogin();
            Boolean correctInfo = login.correctLoginInfo;
            homeModel.put("correctinfo", correctInfo);

            return new ModelAndView(homeModel, h_layout);
        }, new VelocityTemplateEngine());


        get("/do_something",(req,res)->{
            Map<String, Object> model = new HashMap<String, Object>();
            String product = req.queryParams("search");
            req.session().attribute("search", product);
            model.put("search",product);
            model.put("template","templates/p_products.vtl");

            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());
    }
}
