package com.steen.controllers;

import com.steen.models.FavoritesModel;
import com.steen.models.Model;

import java.util.HashMap;
import java.util.Map;

import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import static com.steen.Main.p_layout;
import static com.steen.Main.sfp;
import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by jesse on 23-1-2017.
 */
public class FavoritesController {


    public FavoritesController(final HashMap<String, Model> models) {
        FavoritesModel favoritesModel = (FavoritesModel) models.get("favorites");


        get("/favorites", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("login_modal", sfp + "html/login_mod.vtl");
            model.put("template", sfp + "html/favorites.html");
            model.put("username", request.session().attribute("username"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("admin", request.session().attribute("admin"));

            return new ModelAndView(model, p_layout);

        }, new VelocityTemplateEngine());



    }
}
