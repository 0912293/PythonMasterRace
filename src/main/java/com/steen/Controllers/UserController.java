package com.steen.Controllers;

import com.steen.Models.Model;
import com.steen.Models.UserModel;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import com.steen.User;

import java.util.HashMap;
import java.util.Map;

import static com.steen.Main.p_layout;
import static spark.Spark.get;
import static spark.Spark.post;

public class UserController {

    public UserController(final HashMap<String, Model> models) {
        UserModel userModel = (UserModel) models.get("user");

        get("/user/me",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("login_modal", "templates/login_mod.vtl");
            model.put("template", "templates/user.html");
            model.put("admin", request.session().attribute("admin"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("username", request.session().attribute("username"));

            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        get("/user/orders",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("login_modal", "templates/login_mod.vtl");
            model.put("template", "templates/user.html");
            model.put("admin", request.session().attribute("admin"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("username", request.session().attribute("username"));

            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        get("/user/favorieten",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("login_modal", "templates/login_mod.vtl");
            model.put("template", "templates/user.html");
            model.put("admin", request.session().attribute("admin"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("username", request.session().attribute("username"));

            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        get("/user/wenslijst",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("login_modal", "templates/login_mod.vtl");
            model.put("template", "templates/user.html");
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
            System.out.println(oldpass);
            System.out.println(newpass);
            System.out.println(username);
            userModel.setUsername(username);
            userModel.GetPassword();
            if(userModel.UpdatePassword()){
                System.out.println("changed");
            }else{
                System.out.println("wrong password");
            }
            model.put("login_modal", "templates/login_mod.vtl");
            model.put("template", "templates/user.html");
            model.put("admin", request.session().attribute("admin"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("username", request.session().attribute("username"));

            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());
    }
}
