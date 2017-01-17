package com.steen.Controllers;
import com.steen.Models.AdminModel;
import com.steen.Models.ApiModel;
import com.steen.Models.Model;
import com.steen.Models.ProductModel;
import com.steen.session.Filter;
import com.steen.session.Search;
import spark.Spark;
import java.util.HashMap;

import static spark.Spark.get;
import static spark.Spark.post;

public class ApiController {
    public ApiController(final HashMap<String, Model> models) {
        ApiModel apiModel = (ApiModel) models.get("api");
        AdminModel adminModel = (AdminModel) models.get("admin");
        ProductModel productModel = (ProductModel) models.get("product");

        get("/api/admin/users.json", (request, response) -> {
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

        get("/api/product/view.json", ((request, response) -> {
            String id = request.queryParams("id");
            productModel.setSearch(new Search("SELECT * FROM games WHERE games_id= " + id + ";"));
            return apiModel.getJSON(productModel.getSearch());
        }));

        get("/api/product/filtering.json", ((request, response) -> {
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
            return apiModel.getJSON(query);
        }));

        get("/api/product/games.json", ((request, response) -> {
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

            return apiModel.getJSON(productModel.getSearch());
        }
        ));
    }
}
