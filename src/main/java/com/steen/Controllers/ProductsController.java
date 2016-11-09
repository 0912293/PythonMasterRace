package com.steen.Controllers;

import com.steen.Models.ProductModel;
import com.steen.session.Filter;
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

        post("/games.json", ((request, response) -> {
            productModel.clearSession();
            String filter = request.queryParams("search");
            String order = request.queryParams("order");

            if (filter != null && !filter.equals("")) {
                productModel.getSearch().addFilterParam("games_name", filter, Filter.Operator.LIKE);
            }
            if (order != null && !order.equals("")) {
                productModel.getSearch().addOrderParam(order);
            }

            return productModel.getJSON();
        }
        ));

    }


}
