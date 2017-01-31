package com.steen.controllers;

import com.steen.models.AdminChartModel;
import com.steen.models.AdminModel;
import com.steen.models.AdminProductModel;
import com.steen.models.Model;
import com.steen.session.Filter;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;

import java.util.HashMap;
import java.util.Map;

import static com.steen.Main.p_layout;
import static com.steen.Main.sfp;
import static spark.Spark.*;

public class AdminController {
    int id;
    public AdminController(final HashMap<String, Model> models) {
        AdminModel adminModel = (AdminModel) models.get("admin");
        AdminProductModel adminProductModel = (AdminProductModel) models.get("admin_product");
        AdminChartModel adminChartModel = (AdminChartModel) models.get("admin_chart");

        before("/admin/*", (req,res) -> {
            if (!isAdmin(req)) {halt("401 - not an admin");}
        });

        before("/admin", (req,res) -> {
            if (!isAdmin(req))halt("401 - not an admin");
        });

        //--------------------------------AdminModel--------
        get("/admin", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", sfp + "html/admin.vtl");
            model.put("username", req.session().attribute("username"));
            model.put("admin", req.session().attribute("admin"));
            model.put("correctinfo", req.session().attribute("correctinfo"));
            model.put("userblacklisted", adminModel.checkBlacklisted());
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        post("/admin/delete_user", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", sfp + "html/admin.vtl");
            model.put("admin", req.session().attribute("admin"));
            model.put("correctinfo", req.queryParams("correctinfo"));
            model.put("userblacklisted", adminModel.checkBlacklisted());
            adminModel.delete_user();
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        post("/admin/reset_pass", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            adminModel.resetPassword();
            model.put("template", sfp + "html/admin.vtl");
            model.put("admin", req.session().attribute("admin"));
            model.put("correctinfo", req.queryParams("correctinfo"));
            model.put("userblacklisted", adminModel.checkBlacklisted());
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        post("/admin/update_user", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", sfp + "html/admin.vtl");
            model.put("admin", req.session().attribute("admin"));
            model.put("correctinfo", req.queryParams("correctinfo"));
            model.put("userblacklisted", adminModel.checkBlacklisted());

            adminModel.setData(req.queryParams("name"), req.queryParams("sur"), req.queryParams("email"), req.queryParams("year"), req.queryParams("month"), req.queryParams("day"), req.queryParams("country"),
                    req.queryParams("street"), req.queryParams("postal"), req.queryParams("number"), req.queryParams("city"));

            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

//---------------------Blacklist user-------------------
        post("/admin/blacklist_user", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", sfp + "html/admin.vtl");
            model.put("admin", req.session().attribute("admin"));
            model.put("correctinfo", req.queryParams("correctinfo"));
            model.put("username", req.session().attribute("username"));
            model.put("userblacklisted", adminModel.checkBlacklisted());
            adminModel.blacklistUser();
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

//---------------------UN-Blacklist user---------------------
        post("/admin/blacklist_undo", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", sfp + "html/admin.vtl");
            model.put("admin", req.session().attribute("admin"));
            model.put("correctinfo", req.queryParams("correctinfo"));
            model.put("username", req.session().attribute("username"));
            model.put("userblacklisted", adminModel.checkBlacklisted());
            adminModel.undoBlackList();
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

//--------------Admin user list----------------------------
        get("/admin/users", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", sfp + "html/admin_users.html");
            model.put("admin", req.session().attribute("admin"));
            model.put("username", req.session().attribute("username"));
            model.put("correctinfo", req.session().attribute("correctinfo"));
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        post("/admin/users.json", (request, response) -> {
            String filter = request.queryParams("search");
            String order = request.queryParams("order");
            if (filter != null && !filter.equals("")) {
                adminModel.getSearch().addFilterParam("games_name", filter, Filter.Operator.LIKE);
            }
            if (order != null && !order.equals("")) {
                adminModel.getSearch().addOrderParam(order);
            }

            return adminModel.getUsersJSON();
        });

        post("/api/admin/chart1.json", (request, response) -> {
            adminModel.getSearch();
            return adminChartModel.getChart1JSON();
        });

        post("/api/admin/chart2.json", (request, response) -> {
            adminModel.getSearch();
            return adminChartModel.getChart2JSON();
        });

        post("/api/admin/chart3.json", (request, response) -> {
            adminModel.getSearch();
            return adminChartModel.getChart3JSON();
        });

        get("/admin/getUserData",(req,res)->{
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", sfp + "html/admin.vtl");
            model.put("admin", req.session().attribute("admin"));
            model.put("correctinfo", req.session().attribute("correctinfo"));
            model.put("username", req.session().attribute("username"));

            String Username = req.queryParams("user");
            adminModel.searchUser(Username);
            model.put("result", adminModel.getData(AdminModel.Data.USERNAME));
            if (adminModel.getData(AdminModel.Data.USERNAME).length() > 0) {

                model.put("user", adminModel.getData(AdminModel.Data.USERNAME));
                model.put("name", adminModel.getData(AdminModel.Data.NAME));
                model.put("surname", adminModel.getData(AdminModel.Data.SURNAME));
                model.put("email", adminModel.getData(AdminModel.Data.EMAIL));
                model.put("street", adminModel.getData(AdminModel.Data.STREET));
                model.put("country", adminModel.getData(AdminModel.Data.COUNTRY));
                model.put("year", adminModel.getData(AdminModel.Data.YEAR));
                model.put("day", adminModel.getData(AdminModel.Data.DAY));
                model.put("month", adminModel.getData(AdminModel.Data.MONTH));
                model.put("city", adminModel.getData(AdminModel.Data.CITY));
                model.put("postal", adminModel.getData(AdminModel.Data.POSTAL));
                model.put("userblacklisted", adminModel.checkBlacklisted());
                try {
                    model.put("number", adminModel.getData(AdminModel.Data.NUMBER));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    System.out.println("malformed 'number' attribute for selected user");
                    res.redirect("/admin");
                }
                adminModel.clear();
            } else {
                model.put("result", "");
                res.redirect("/admin");
            }
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        get("/admin/chart", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", sfp + "html/admin_chart.html");
            model.put("admin", req.session().attribute("admin"));
            model.put("username", req.session().attribute("username"));
            model.put("correctinfo", req.session().attribute("correctinfo"));
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        get("/admin/product", (req, res) -> { Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", sfp + "html/admin_products.html");
            model.put("admin", req.session().attribute("admin"));
            model.put("username", req.session().attribute("username"));
            model.put("correctinfo", req.session().attribute("correctinfo"));
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        get("/admin/product/search", (req, res) -> { Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", sfp + "html/admin_products_edit.html");
            model.put("admin", req.session().attribute("admin"));
            model.put("username", req.session().attribute("username"));
            model.put("correctinfo", req.session().attribute("correctinfo"));
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        post("/admin/product/add/game", (req, res) -> {
            String gname = req.queryParams("gname");
            String gprice = req.queryParams("gprice");
            String ggenre = req.queryParams("ggenre");
            String gplatform = req.queryParams("gplatform");
            String gpublisher = req.queryParams("gpublisher");
            String gstock = req.queryParams("gstock");
            String gimage = req.queryParams("gimage");
            adminProductModel.AddGame(gname,Float.parseFloat(gprice),ggenre,gplatform,gpublisher,Integer.parseInt(gstock),gimage);
            return "success";
        });

        post("/admin/product/add/platform", (req, res) -> {
            String pname = req.queryParams("pname");
            String pcolor = req.queryParams("pcolor");
            String pmemory = req.queryParams("pmemory");
            String pmanufact = req.queryParams("pmanufact");
            String pprice = req.queryParams("pprice");
            String pstock = req.queryParams("pstock");
            adminProductModel.AddPlatform(pname,Float.parseFloat(pprice),pcolor,pmanufact,pmemory,Integer.parseInt(pstock));
            return "success";
        });

        post("/api/product/id", (req, res) -> {
            try{
                id = Integer.parseInt(req.queryParams("id"));
            }
            catch(Exception e){
            }
            return "oh boi";
        });

        post("api/product/info", (req, res) ->{
            return adminProductModel.getGame(Integer.toString(id));
        });

        get("/admin/product/edit", (req, res) -> { Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", sfp + "html/admin_products_edit.html");
            try{
                model.put("gid", id);
            }
            catch(Exception e){
            }
            model.put("admin", req.session().attribute("admin"));
            model.put("username", req.session().attribute("username"));
            model.put("correctinfo", req.session().attribute("correctinfo"));
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        post("/admin/product/edit/game", (req, res) -> {
            String gname = req.queryParams("gname");
            String gprice = req.queryParams("gprice");
            String ggenre = req.queryParams("ggenre");
            String gplatform = req.queryParams("gplatform");
            String gpublisher = req.queryParams("gpublisher");
            String gstock = req.queryParams("gstock");
            String gimage = req.queryParams("gimage");
            adminProductModel.updateGame(gname,Float.parseFloat(gprice),ggenre,gplatform,gpublisher,Integer.parseInt(gstock),gimage,id);
            return "success";
        });

    }
    private Boolean isAdmin(Request req) {
        if (req.session().attribute("admin") != null) {
            return req.session().attribute("admin");
        }
        return false;
    }
}