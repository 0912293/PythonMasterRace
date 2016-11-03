package com.steen.Controllers;

import com.steen.Models.AdminModel;
import com.steen.User;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.steen.Main.p_layout;
import static spark.Spark.get;
import static spark.Spark.post;

public class AdminController {

    public AdminController(final AdminModel adminModel) {

        //--------------------------------AdminModel--------
        get("/admin", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/admin.vtl");
            model.put("username", req.session().attribute("username"));
            model.put("admin", req.session().attribute("admin"));
            model.put("correctinfo", req.session().attribute("correctinfo"));
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        post("/delete_user", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/admin.vtl");
            model.put("admin", req.session().attribute("admin"));
            model.put("correctinfo", req.queryParams("correctinfo"));
            adminModel.delete_user();
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        post("/reset_pass", (req, res) -> {
            adminModel.reset();
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/admin.vtl");
            model.put("admin", req.session().attribute("admin"));
            model.put("correctinfo", req.queryParams("correctinfo"));
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        post("/update_user", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/admin.vtl");
            model.put("admin", req.session().attribute("admin"));
            model.put("correctinfo", req.queryParams("correctinfo"));

            adminModel.setData(req.queryParams("name"), req.queryParams("sur"), req.queryParams("email"), req.queryParams("year"), req.queryParams("month"), req.queryParams("day"), req.queryParams("country"),
                    req.queryParams("street"), req.queryParams("postal"), req.queryParams("number"), req.queryParams("city"));

            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

//---------------------Blacklist user-------------------
        post("/blacklist_user", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/admin.vtl");
            model.put("admin", req.session().attribute("admin"));
            model.put("correctinfo", req.queryParams("correctinfo"));
            model.put("username", req.session().attribute("username"));
            adminModel.blacklistUser();
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

//--------------Admin user list----------------------------
        get("/view_users", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            ArrayList<User> userArrayList = adminModel.getUsers();

            model.put("users", userArrayList);

            model.put("template", "templates/view_users.vtl");
            model.put("admin", req.session().attribute("admin"));
            model.put("username", req.queryParams("correctinfo"));
            model.put("correctinfo", req.session().attribute("username"));
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        get("/getUserData",(req,res)->{
            adminModel.ResetOldContent(); // oude info verwijderen
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template","templates/admin.vtl");
            model.put("username", req.session().attribute("username"));
            model.put("admin", req.session().attribute("admin"));
            model.put("correctinfo", req.session().attribute("correctinfo"));

            adminModel.searchUser(req.queryParams("user"));

            model.put("result",adminModel.getData(AdminModel.Data.USERNAME));
            model.put("usern",adminModel.getData(AdminModel.Data.USERNAME)); //username != de gebruiker die is ingelogd
            model.put("name",adminModel.getData(AdminModel.Data.NAME));
            model.put("surname",adminModel.getData(AdminModel.Data.SURNAME));
            model.put("email",adminModel.getData(AdminModel.Data.EMAIL));
            model.put("street",adminModel.getData(AdminModel.Data.STREET));
            model.put("country",adminModel.getData(AdminModel.Data.COUNTRY));
            model.put("year",adminModel.getData(AdminModel.Data.YEAR));
            model.put("day",adminModel.getData(AdminModel.Data.DAY));
            model.put("month",adminModel.getData(AdminModel.Data.MONTH));
            model.put("city",adminModel.getData(AdminModel.Data.CITY));
            try {
                model.put("number",Integer.parseInt(adminModel.getData(AdminModel.Data.NUMBER)));
            }
            catch (NumberFormatException e){
                e.printStackTrace();
                System.out.println("Deze user bestaat niet");
                res.redirect("/admin");
            }

            model.put("postal",adminModel.getData(AdminModel.Data.POSTAL));
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());
    }
}
