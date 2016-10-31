import static spark.Spark.*;

import org.apache.commons.lang.ObjectUtils;
import org.eclipse.jetty.server.Authentication;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.sql.Array;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    static Connection connection = Connector.connect();
    static String Password;
    static String Username;
    static Login login;
    static String name;
    static String surname;
    static String country;
    static String city;
    static String street;
    static String postal;
    static String number;
    static String day;
    static String month;
    static String year;
    static String email;
    static Register regist;
    static DateBuilder dbuilder = new DateBuilder();
    static Boolean admin;
    static Map<String, Object> homeModel = new HashMap<String, Object>();
    static Map<String, Object> afterLoginModel = new HashMap<String, Object>();

    public static void main(String[] args) {
        staticFileLocation("/public");              // sets folder for non java files

        //--------------------------------------------------------project
        String p_layout = "templates/p_layout.vtl";
        get("/", (req, res) -> {

            homeModel.put("login_modal", "templates/login_mod.vtl");
            homeModel.put("template","templates/p_home.vtl");
            return new ModelAndView(homeModel, p_layout);
        }, new VelocityTemplateEngine());

        get("/p_log", (req, res) -> {
            homeModel.put("username", req.session().attribute("username"));
            homeModel.put("pass", req.session().attribute("pass"));
            homeModel.put("login_modal", "templates/login_mod.vtl");
            homeModel.put("template","templates/p_login.vtl");
            return new ModelAndView(homeModel, p_layout);
        }, new VelocityTemplateEngine());


        post("/login", (req, res) -> {
            Username = req.queryParams("username");
            req.session().attribute("username", Username);
            Boolean userCheck = UserInputCheck(Username);
            Password = req.queryParams("pass");
            req.session().attribute("pass", Password);
            Boolean passCheck = UserInputCheck(Password);

            login = new Login(Username, Password);
            login.ParseLogin();
            Boolean correctInfo = login.correctLoginInfo;

            homeModel.put("correctinfo", correctInfo);
            homeModel.put("username", Username);
            homeModel.put("pass", Password);
            homeModel.put("userCheck", userCheck);
            homeModel.put("passCheck", passCheck);

            homeModel.put("login_modal", "templates/login_mod.vtl");
            homeModel.put("template","templates/p_home.vtl");
            return new ModelAndView(homeModel, p_layout);
        }, new VelocityTemplateEngine());


        get("/p_reg", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("username", req.session().attribute("username"));
            model.put("pass", req.session().attribute("pass"));
            model.put("name", req.session().attribute("name"));
            model.put("sur", req.session().attribute("sur"));
            model.put("country", req.session().attribute("country"));
            model.put("city", req.session().attribute("city"));
            model.put("number", req.session().attribute("number"));
            model.put("street", req.session().attribute("street"));
            model.put("postal", req.session().attribute("postal"));
            model.put("day", req.session().attribute("day"));
            model.put("month", req.session().attribute("month"));
            model.put("year", req.session().attribute("year"));
            model.put("email", req.session().attribute("email"));

            model.put("template","templates/p_reg.vtl");

            model.put("login_modal","templates/login_mod.vtl");
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        post("/regist",(req,res)->{
            Map<String, Object> model = new HashMap<String, Object>();
            ArrayList<String> userDetails = new ArrayList<String>();
            Username = req.queryParams("username");
            Password = req.queryParams("pass");
            name = req.queryParams("name");
            surname = req.queryParams("sur");
            country = req.queryParams("country");
            city = req.queryParams("city");
            number = req.queryParams("number");
            street = req.queryParams("street");
            postal = req.queryParams("postal");
            day = req.queryParams("day");
            month = req.queryParams("month");
            year = req.queryParams("year");
            email = req.queryParams("email");

            userDetails.addAll(Arrays.asList(Username, Password, name, surname, country, city, number, street, postal, day, month, year, email));

            Boolean nullCheck = NullCheck(userDetails);

            dbuilder.build(day,month,year);

            model.put("login_modal","templates/login_mod.vtl");
            regist = new Register(Username,Password,name,surname,country,city,street,postal,number,dbuilder.getDate(),email, admin);
            if(nullCheck){
                regist.ParseReg();
                model.put("template","templates/p_after_reg.vtl");
            }
            else{
                model.put("template", "templates/p_reg.vtl");
            }
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        get("/do_something",(req,res)->{
            Map<String, Object> model = new HashMap<>();
            String product = req.queryParams("search");
            req.session().attribute("search", product);
            model.put("search",product);
            model.put("template","templates/p_products.vtl");
            model.put("login_modal","templates/login_mod.vtl");

            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());
    }

    private static Boolean UserInputCheck(String input)
    {
        return (input != null) ? !input.contains(" ") || !input.contains("'") || !input.contains(";") : false;
    }

    private static Boolean NullCheck(ArrayList<String> list){
        for(int i = 0; i < list.size(); i++)
        {
            if(list.get(i) == null || list.get(i).equals("")){
                System.out.println(("You need to fill in all fields"));
                return false;
            }
        }
        return true;
    }
}
