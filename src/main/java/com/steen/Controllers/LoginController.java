package com.steen.Controllers;

import com.steen.Models.LoginModel;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import static com.steen.Main.p_layout;
import static spark.Spark.get;
import static spark.Spark.post;

import java.util.HashMap;
import java.util.Map;

public class LoginController {
    public LoginController(final LoginModel loginModel) {

        get("/p_log", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("admin", req.session().attribute("admin"));
            model.put("username", req.session().attribute("username"));
            model.put("pass", req.session().attribute("pass"));
            model.put("login_modal", "templates/login_mod.vtl");
            model.put("template", "templates/p_login.vtl");
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());


        post("/login", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String Username = req.queryParams("username");
            req.session().attribute("username", Username);
            Boolean userCheck = UserInputCheck(Username);
            String Password = req.queryParams("pass");
            req.session().attribute("pass", Password);
            Boolean passCheck = UserInputCheck(Password);

            loginModel.setCredentials(Username, Password);

            Boolean correctInfo = loginModel.hasCorrectLoginInfo();
            Boolean blacklisted = loginModel.checkBlacklist();
            if (!blacklisted) {
                if (correctInfo) {
                    req.session().attribute("admin", loginModel.isAdmin());
                    req.session().attribute("correctinfo", true);
                    model.put("correctinfo", true);
                    model.put("username", Username);
                    model.put("pass", Password);
                    model.put("userCheck", userCheck);
                    model.put("passCheck", passCheck);
                } else {
                    Username = null;
                    Password = null;
                    correctInfo = false;
                    req.session().attribute("correctinfo", correctInfo);
                    req.session().attribute("username", null);
                    passCheck = null;
                    userCheck = null;
                }
                model.put("login_modal", "templates/login_mod.vtl");
                model.put("template", "templates/p_home.vtl");
                res.redirect("/");
            } else {
                model.put("login_modal", "templates/login_mod.vtl");
                model.put("template", "templates/blacklisted.vtl");
                res.redirect("/");
            }
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        get("/logout", (req, res) -> {
            req.session().attribute("username", "");
            req.session().attribute("pass", "");
            req.session().attribute("admin", false);
            req.session().attribute("correctinfo", false);
            res.redirect("/"); // stuurt de gebruiker naar de home page, dus hierna word de http request voor home uitgevoerd in de client.
            return new ModelAndView(new HashMap<>(), p_layout);
        }, new VelocityTemplateEngine());
    }

    private static Boolean UserInputCheck(String input)
    {
        return (input != null) ? !input.contains(" ") || !input.contains("'") || !input.contains(";") : false;
    }
}
