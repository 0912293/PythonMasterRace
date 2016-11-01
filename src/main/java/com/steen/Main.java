package com.steen;

import static com.steen.Util.SQLToJSON.getFormattedResult;
import static spark.Spark.*;

import com.steen.Models.RegisterModel;
import spark.ModelAndView;
import com.steen.velocity.VelocityTemplateEngine;
import java.sql.Connection;
import java.util.*;

public class Main {
    public static Connection connection = Connector.connect();
    static String Password;
    static String Username;
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
    static Boolean isAdmin;
    static Boolean correctInfo;
    static Login login;
    static RegisterModel regist;
    static DateBuilder dbuilder = new DateBuilder();
    static Map<String, Object> homeModel = new HashMap<String, Object>();
    static Map<String, Object> afterLoginModel = new HashMap<String, Object>();

    static Admin admQ = new Admin();

    public static void main(String[] args) {


        staticFileLocation("/public");              // sets folder for non java files

        //--------------------------------------------------------project
        String p_layout = "templates/p_layout.vtl";
        get("/", (req, res) -> {

            homeModel.put("admin",isAdmin);
            homeModel.put("login_modal", "templates/login_mod.vtl");
            homeModel.put("template","templates/p_home.vtl");
            homeModel.put("correctinfo", correctInfo);
            homeModel.put("username", req.session().attribute("username"));
            return new ModelAndView(homeModel, p_layout);
        }, new VelocityTemplateEngine());

        get("/p_log", (req, res) -> {
            homeModel.put("admin",isAdmin);
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
            correctInfo = login.correctLoginInfo;
            if (correctInfo) {
                isAdmin = login.isAdmin();
                req.session().attribute("admin", isAdmin);
                homeModel.put("correctinfo", correctInfo);
                homeModel.put("username", Username);
                homeModel.put("pass", Password);
                homeModel.put("userCheck", userCheck);
                homeModel.put("passCheck", passCheck);
            }
            else{
                Username = null;
                Password = null;
                login = null;
                correctInfo = null;
                passCheck = null;
                userCheck = null;
            }

            homeModel.put("login_modal", "templates/login_mod.vtl");
            homeModel.put("template","templates/p_home.vtl");
            res.redirect("/");
            return new ModelAndView(homeModel, p_layout);
        }, new VelocityTemplateEngine());

        get("/logout", (req, res) -> {
            req.session().attribute("username", null);
            req.session().attribute("pass", null);
            req.session().attribute("admin", false);
            Username = null;
            login = null;
            name = null;
            correctInfo = false;
            isAdmin = false;
            regist = null;
            dbuilder =  new DateBuilder();
            afterLoginModel = null;
            homeModel = new HashMap<>();
            afterLoginModel = new HashMap<>();

            res.redirect("/"); // stuurt de gebruiker naar de home page, dus hierna word de http request voor home uitgevoerd in de client.
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
            regist = new RegisterModel(Username,Password,name,surname,country,city,street,postal,number,dbuilder.getDate(),email, isAdmin);
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
            Games games = new Games();
            //games.ParseQuery();
            List jsonList = getFormattedResult(games.ParseQuery());
            ArrayList<Game> gameArrayList = games.getGamesList();

            for (int i = 0; i < jsonList.size(); i++){

                System.out.println(jsonList.get(i++));
            }



            String product = req.queryParams("search");
            req.session().attribute("search", product);

            String pricesort = req.queryParams("pricesort");
            String alpha = req.queryParams("alpha");
            req.session().attribute("alpha", alpha);
            req.session().attribute("pricesort", pricesort);
            model.put("alpha", alpha);
            model.put("pricesort", pricesort);

            Filter filter = new Filter();
            filter.LikeData(product);


            model.put("games", gameArrayList);
            model.put("search",product);
            model.put("template","templates/p_products.vtl");
            model.put("login_modal","templates/login_mod.vtl");
            model.put("admin",isAdmin);
            model.put("username", req.session().attribute("username"));

            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());


        //--------------------------------Admin--------
        get("/admin",(req,res)->{
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template","templates/admin.vtl");
            model.put("admin", isAdmin);
            model.put("username", req.session().attribute("username"));
            model.put("correctinfo", correctInfo);
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        post("/delete_user" ,(req,res)->{
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template","templates/admin.vtl");
            model.put("admin", isAdmin);
            model.put("correctinfo", correctInfo);
            admQ.delete_user();
            return new ModelAndView(model, p_layout);
        },new VelocityTemplateEngine());

        post("/reset_pass" ,(req,res)->{
            admQ.reset();
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template","templates/admin.vtl");
            model.put("admin", isAdmin);
            model.put("correctinfo", correctInfo);

            return new ModelAndView(model, p_layout);
        },new VelocityTemplateEngine());

        post("/update_user" ,(req,res)->{
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template","templates/admin.vtl");
            model.put("admin", isAdmin);
            model.put("correctinfo", correctInfo);

            admQ.setData(req.queryParams("name"),req.queryParams("sur"),req.queryParams("email"),req.queryParams("year"),req.queryParams("month"),req.queryParams("day"),req.queryParams("country"),
                    req.queryParams("street"),req.queryParams("postal"),req.queryParams("number"),req.queryParams("city"));

            return new ModelAndView(model, p_layout);
        },new VelocityTemplateEngine());

        get("/getUserData",(req,res)->{
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("admin", isAdmin);
            model.put("template","templates/admin.vtl");
            model.put("correctinfo", correctInfo);

            Username = req.queryParams("user");
            admQ.searchUser(Username);
            model.put("result",admQ.getData(Admin.Data.USERNAME));

            model.put("username",admQ.getData(Admin.Data.USERNAME));
            model.put("name",admQ.getData(Admin.Data.NAME));
            model.put("surname",admQ.getData(Admin.Data.SURNAME));
            model.put("email",admQ.getData(Admin.Data.EMAIL));
            model.put("street",admQ.getData(Admin.Data.STREET));
            model.put("country",admQ.getData(Admin.Data.COUNTRY));
            model.put("year",admQ.getData(Admin.Data.YEAR));
            model.put("day",admQ.getData(Admin.Data.DAY));
            model.put("month",admQ.getData(Admin.Data.MONTH));
            model.put("city",admQ.getData(Admin.Data.CITY));
            model.put("number",Integer.parseInt(admQ.getData(Admin.Data.NUMBER)));
            model.put("postal",admQ.getData(Admin.Data.POSTAL));
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        get("/api",(req,res) -> { //Voorbeeld van hoe een json aan de browser geleverd word. *bekijk via de browser de broncode*
            Games games = new Games();
            //games.ParseQuery();
            List jsonList = getFormattedResult(games.ParseQuery());
            ArrayList<Game> gameArrayList = games.getGamesList();
            String ret = "";
            for (int i = 0; i < jsonList.size(); i++){
                ret = ret + jsonList.get(i++);
            }
            return ret;
        });
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
