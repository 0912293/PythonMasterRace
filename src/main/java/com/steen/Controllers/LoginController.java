package com.steen.Controllers;

import com.steen.Models.LoginModel;
import com.steen.Models.Model;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import static com.steen.Main.p_layout;
import static com.steen.Models.LoginModel.checkBlacklist;
import static spark.Spark.get;
import static spark.Spark.post;

import java.util.HashMap;
import java.util.Map;

public class LoginController {
    public LoginController(final HashMap<String, Model> models) {
        LoginModel loginModel = (LoginModel) models.get("login");

        post("/login", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String Username = req.queryParams("username");
            req.session().attribute("username", Username);
            Boolean userCheck = UserInputCheck(Username);
            String Password = req.queryParams("pass");
            req.session().attribute("pass", Password);
            Boolean passCheck = UserInputCheck(Password);

            String href = req.queryParams("url");
            System.out.println(href);
            System.out.println(Username);
            System.out.println(Password);
            loginModel.setCredentials(Username, Password);

            Boolean correctInfo = loginModel.hasCorrectLoginInfo();
            Boolean blacklisted = checkBlacklist(Username);
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
                    req.session().attribute("correctinfo", false);
                    req.session().attribute("username", null);
                    passCheck = null;
                    userCheck = null;
                }
                res.redirect(href);
            } else {
                res.redirect(href);
            }
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        post("/logout", (req, res) -> {
            req.session().attribute("username", "");
            req.session().attribute("pass", "");
            req.session().attribute("admin", false);
            req.session().attribute("correctinfo", false);

            String href = req.queryParams("url");
            res.redirect(href);
            return new ModelAndView(new HashMap<>(), p_layout);
        }, new VelocityTemplateEngine());


    }

    private static Boolean UserInputCheck(String input)
    {
        return (input != null) ? !input.contains(" ") || !input.contains("'") || !input.contains(";") : false;
    }
}
