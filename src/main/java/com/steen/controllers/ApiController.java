package com.steen.controllers;
import com.steen.models.*;
import com.steen.session.Filter;
import com.steen.session.Search;
import com.steen.util.JSONUtil;
import com.steen.util.SQLToJSON;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import static spark.Spark.get;
import static spark.Spark.post;

public class ApiController {
    public ApiController(final HashMap<String, Model> models) {
        ApiModel apiModel = (ApiModel) models.get("api");
        AdminModel adminModel = (AdminModel) models.get("admin");
        ProductModel productModel = (ProductModel) models.get("product");
        WishlistModel wishlistModel = (WishlistModel) models.get("wishlist");
        CartModel cartModel = (CartModel) models.get("cart");

        post("/api/admin/users.json", (request, response) -> {
            String filter = request.queryParams("search");
            String order = request.queryParams("orders");

            if (filter != null && !filter.equals("")) {
                adminModel.getSearch().addFilterParam("games_name", filter, Filter.Operator.LIKE);
            }
            if (order != null && !order.equals("")) {
                adminModel.getSearch().addOrderParam(order);
            }

            return apiModel.getJSON(adminModel.getSearch());
        });

        post("/api/product/view.json", ((request, response) -> {
            String id = request.queryParams("id");
            productModel.setSearch(new Search("SELECT * FROM games WHERE games_id= " + id + ";"));
            return apiModel.getJSON(productModel.getSearch());
        }));

        post("/api/product/filtering.json", ((request, response) -> {
            productModel.clearSession();
            String query = "";
            String selector = request.queryParams("selector");

            final String genre = "0";
            final String platform = "1";

            switch (selector) {
                case genre:
                    query = "SELECT DISTINCT games_genre FROM games";
                    break;
                case platform:
                    query = "SELECT DISTINCT games_platform FROM games";
                    break;
                default:
                    // RIP
                    break;
            }
            return apiModel.getJSON(query);
        }));

        post("/api/product/games.json", ((request, response) -> {
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

            JSONArray jsonArray = new JSONArray(apiModel.getJSON(productModel.getSearch()));
            jsonArray = JSONUtil.replaceKeys(jsonArray,
                    new String[]{"games_name", "games_price", "games_id", "games_genre", "games_image"},
                    new String[]{"name", "price", "id", "genre", "image"});
            return jsonArray;
        }
        ));

        post("/api/wishlist.json", (request, response) -> {

            String username = request.session().attribute("username");
            return apiModel.getJSON(WishlistModel.getQuery(username));
        }
        );

        post("/api/cart.json", ((request, response) -> cartModel.getCartJSON()));

        post("/api/cart/count.json", ((request, response) -> {
            JSONObject jso = new JSONObject();
            jso.put("count", cartModel.getCount());
            return jso;
        }));

        post("/api/user/checkout_info.json", ((request, response) -> {
            String username = "Lucas"; //request.session().attribute("username");
            JSONObject userjson = new JSONObject(apiModel.getJSON("SELECT u.name, u.surname, u.email, " +
                    "a.address_country AS country, a.address_postalcode AS postalcode, " +
                    "a.address_city AS city, a.address_street AS street, a.address_number AS number " +
                    "FROM users u, address a WHERE u.username = '" + username + "' AND u.address_id = a.address_id",
                    SQLToJSON.Type.OBJECT));
            userjson = JSONUtil.replaceKeys(userjson,
                    new String[]{"address_city", "address_street", "address_number", "address_postalcode", "address_country"},
                    new String[] {"city","street","number","postalcode","country"});
            JSONArray productjson = new JSONArray(cartModel.getCartJSON());
            JSONObject json = new JSONObject("{\"userinfo\":"+userjson+",\"products\":" + productjson + "}");
            return json;
        }));
    }
}
