package com.steen.Controllers;

import com.steen.Models.LoginModel;
import com.steen.Models.Model;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import com.steen.User;

import static com.steen.Main.p_layout;
import static com.steen.Models.LoginModel.checkBlacklist;
import static spark.Spark.get;
import static spark.Spark.post;

import java.util.HashMap;
import java.util.Map;

public class LoginController {
    public User user = new User();
    public LoginController(final HashMap<String, Model> models) {
        LoginModel loginModel = (LoginModel) models.get("login");

        post("/login", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String Username = req.queryParams("username");
            req.session().attribute("username", Username);
            user.setUserName(Username);
            Boolean userCheck = UserInputCheck(Username);
            String Password = req.queryParams("pass");
            req.session().attribute("pass", Password);
            Boolean passCheck = UserInputCheck(Password);

            String href = req.queryParams("url");
            loginModel.setCredentials(Username, Password);

            Boolean correctInfo = loginModel.hasCorrectLoginInfo();
            Boolean blacklisted = checkBlacklist(Username);
            if (!blacklisted) {
                if (correctInfo) {
                    req.session().attribute("username", Username);
                    req.session().attribute("admin", loginModel.isAdmin());
                    req.session().attribute("correctinfo", true);
                    model.put("correctinfo", true);
                    model.put("username", Username);
                    model.put("pass", Password);
                    model.put("userCheck", userCheck);
                    model.put("passCheck", passCheck);
                } else {
                    req.session().removeAttribute("username");
                    req.session().removeAttribute("admin");
                    req.session().removeAttribute("correctinfo");
                    Username = null;
                    Password = null;
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
            req.session().removeAttribute("username");
            req.session().removeAttribute("admin");
            req.session().removeAttribute("correctinfo");

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
