package com.steen.controllers;

import com.steen.models.Model;
import com.steen.models.ProductModel;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static com.steen.Main.p_layout;
import static com.steen.Main.sfp;
import static spark.Spark.*;

public class ProductsController {
    public ProductsController(final HashMap<String, Model> models) {
        ProductModel productModel = (ProductModel) models.get("product");

        get("/games", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", sfp + "html/games.html");
            model.put("login_modal", sfp + "html/login_mod.vtl");
            model.put("username", request.session().attribute("username"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("admin", request.session().attribute("admin"));
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        get("/games/bekijken", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            model.put("login_modal", sfp + "html/login_mod.vtl");
            model.put("template", sfp + "html/games_bekijk.html");
            model.put("username", request.session().attribute("username"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("admin", request.session().attribute("admin"));

            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());
    }
}
