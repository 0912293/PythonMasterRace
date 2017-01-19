package com.steen.controllers;

import com.steen.models.Model;
import com.steen.models.UserModel;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static com.steen.Main.p_layout;
import static com.steen.Main.sfp;
import static spark.Spark.get;
import static spark.Spark.post;

public class UserController {

    public UserController(final HashMap<String, Model> models) {
        UserModel userModel = (UserModel) models.get("user");

        get("/user/me",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            if (request.session().attribute("correctinfo") == null) {
                response.redirect("/");
            } else {
                model.put("admin", request.session().attribute("admin"));
                model.put("correctinfo", request.session().attribute("correctinfo"));
                model.put("username", request.session().attribute("username"));
            }
            model.put("login_modal", sfp + "html/login_mod.vtl");
            model.put("template", sfp + "html/user.html");

            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        get("/user/orders",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("login_modal", sfp + "html/login_mod.vtl");
            model.put("template", sfp + "html/user.html");
            model.put("admin", request.session().attribute("admin"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("username", request.session().attribute("username"));

            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        get("/user/favorieten",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("login_modal", sfp + "html/login_mod.vtl");
            model.put("template", sfp + "html/user.html");
            model.put("admin", request.session().attribute("admin"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("username", request.session().attribute("username"));

            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        get("/user/wenslijst",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("login_modal", sfp + "html/login_mod.vtl");
            model.put("template", sfp + "html/user.html");
            model.put("admin", request.session().attribute("admin"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("username", request.session().attribute("username"));

            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        post("/user/wachtwoord/veranderen",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String username = request.session().attribute("username");
            String oldpass = request.queryParams("opass");
            String newpass = request.queryParams("npass");
            userModel.setOldpass(oldpass);
            userModel.setNewpass(newpass);
            userModel.setUsername(username);
            userModel.GetPassword();
            model.put("login_modal", sfp + "html/login_mod.vtl");
            model.put("template", sfp + "html/user.html");
            model.put("admin", request.session().attribute("admin"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("username", request.session().attribute("username"));

            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());
    }
}
