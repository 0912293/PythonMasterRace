import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");              // sets folder for non java files
        String layout = "templates/layout.vtl";

        get("/", (req, res) -> {                    // Using "/" brings user to this page when accessing the root url
            HashMap model = new HashMap();
            model.put("template","templates/hello.vtl");
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/cake", (req, res) -> {
            HashMap model = new HashMap();
            model.put("template","templates/cake.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/form", (req, res) -> {
            HashMap model = new HashMap();
            model.put("template","templates/form.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/greeting_card", (req, res) -> {
            HashMap model = new HashMap();
            String recipient = req.queryParams("recipient");
            String sender = req.queryParams("sender");

            System.out.println(req.params());

            model.put("recipient", recipient);
            model.put("sender", sender);
            model.put("template", "templates/greeting_card.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

//-----------------------------------------------------------------------------layout 2
        String layout2 = "templates/layout2.vtl";
        get("/ly", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/ly_home.vtl");
            return new ModelAndView(model, layout2);
        }, new VelocityTemplateEngine());

        get("/detector", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/detector.vtl");

            String year = request.queryParams("year");
            Integer integerYear = Integer.parseInt(year);
            Boolean isLeapYear = isLeapYear(integerYear);

            model.put("isLeapYear", isLeapYear);
            model.put("year", request.queryParams("year"));
            return new ModelAndView(model, layout2);
        }, new VelocityTemplateEngine());

        //------------------------------------------layout3
        String layout3 = "templates/layout3.vtl";
        get("/w_home", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("username", request.session().attribute("username"));
            model.put("template", "templates/welcome.vtl");
            return new ModelAndView(model, layout3);
        }, new VelocityTemplateEngine());


        post("/welcome", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String inputtedUsername = request.queryParams("username");
            request.session().attribute("username", inputtedUsername);
            model.put("username", inputtedUsername);
            model.put("template", "templates/welcome.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //--------------------------------------------------------project
        String p_layout = "templates/p_layout.vtl";
        get("/p_home", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("username", req.session().attribute("username"));
            model.put("pass", req.session().attribute("pass"));
            model.put("template","templates/p_home.vtl");
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        post("/login", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String Username = req.queryParams("username");
            req.session().attribute("username", Username);
            String Password = req.queryParams("pass");
            req.session().attribute("pass", Password);
            model.put("username", Username);
            model.put("pass", Password);
            model.put("template","templates/p_home.vtl");
            return new ModelAndView(model, p_layout);
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

    public static Boolean isLeapYear(Integer year) {
        if ( year % 400 == 0 ) {
            return true;
        } else if ( year % 100 == 0 ) {
            return false;
        } else {
            return year % 4 == 0;
        }
    }



}
