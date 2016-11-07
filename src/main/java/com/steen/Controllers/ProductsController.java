package com.steen.Controllers;

import com.steen.Models.ProductModel;
import com.steen.session.Filter;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import static com.steen.Main.p_layout;
import static com.steen.Util.SQLToJSON.getFormattedResult;
import static spark.Spark.*;

public class ProductsController {
    public ProductsController(ProductModel productModel) {

        get("/games", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/games.html");
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        get("/games.json", ((request, response) -> {
            productModel.clearSession();
            String filter = request.queryParams("search");
            String order = request.queryParams("order");

            if (filter != null && !filter.equals("")) {
                productModel.getSearch().addFilterParam("games_name", filter, Filter.Operator.LIKE);
            }
            if (order != null && !filter.equals("")) {
                productModel.getSearch().addOrderParam(order);
            }

            return getFormattedResult(productModel.getSearch().getResultset());
        }
        ));

    }


}
