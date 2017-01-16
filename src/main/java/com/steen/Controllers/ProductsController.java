package com.steen.Controllers;

import com.steen.Models.ProductModel;
import com.steen.session.Filter;
import com.steen.session.Search;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static com.steen.Main.p_layout;
import static spark.Spark.*;

public class ProductsController {
    public ProductsController(ProductModel productModel) {

        get("/games", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/games.html");
            model.put("login_modal", "templates/login_mod.vtl");
            model.put("username", request.session().attribute("username"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("admin", request.session().attribute("admin"));
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        get("/games.json", ((request, response) -> {
            productModel.clearSession();
            String search = request.queryParams("search");
            String order = request.queryParams("order");
            String filter = request.queryParams("filter");


            if (search != null && !search.equals("null") && !search.equals("")) {
                productModel.getSearch().addFilterParam("games_name", search, Filter.Operator.LIKE);
            }
            if (order != null && !order.equals("null") && !order.equals("")) {
                productModel.getSearch().addOrderParam(order);
            }
            if (filter != null && !filter.equals("null") && !filter.equals("")) {
                productModel.getSearch().addFilterParam(filter);
            }

            return productModel.getJSON();
        }
        ));

        get("/games_options.json", ((request, response) -> {
            productModel.clearSession();
            String query = "";
            String selector = request.queryParams("selector");
            if (selector.equals("0")) {
                query = "SELECT DISTINCT games_genre FROM games";
            } else if (selector.equals("1")) {
                query = "SELECT DISTINCT games_platform FROM games";
            } else {
                // RIP
            }
            return productModel.getJSON(query);
        }));

        get("/games/bekijken", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            model.put("login_modal", "templates/login_mod.vtl");
            model.put("template", "templates/games_bekijk.html");

            model.put("username", request.session().attribute("username"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("admin", request.session().attribute("admin"));

            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        post("/games/bekijken.json", ((request, response) -> {
            String id = request.queryParams("id");
            productModel.setSearch(new Search("SELECT * FROM games WHERE games_id= " + id + ";"));
            return productModel.getJSON();
        }
        ));
    }
}
