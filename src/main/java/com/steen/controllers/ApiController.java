package com.steen.controllers;
import com.steen.Cryptr;
import com.steen.models.*;
import com.steen.session.Filter;
import com.steen.session.Search;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.util.HashMap;

import static spark.Spark.get;
import static spark.Spark.post;

public class ApiController {
    public ApiController(final HashMap<String, Model> models) {
        ApiModel apiModel = (ApiModel) models.get("api");
        AdminModel adminModel = (AdminModel) models.get("admin");
        ProductModel productModel = (ProductModel) models.get("product");
        WishlistModel wishlistModel = (WishlistModel) models.get("wishlist");
        CartModel cartModel = (CartModel) models.get("cart");

        FavoritesModel favoritesModel = (FavoritesModel) models.get("favorites");

        post("/api/admin/users.json", (request, response) -> {
            String filter = request.queryParams("search");
            String order = request.queryParams("order");

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

            return apiModel.getJSON(productModel.getSearch());
        }
        ));

        post("/api/getUserCrypt.json", (request, response) ->{
            String username = request.session().attribute("username");
            return apiModel.getJSON(wishlistModel.getUserCrypt(username));
        });

        post("/api/wishlist.json", (request, response) -> {
            String crypted_user  = request.queryParams("wishlist_id");
            return apiModel.getJSON(wishlistModel.getQuery(crypted_user));
        });

        post("/api/favorites.json", (request, response) -> {
            String username = request.session().attribute("username");
            return apiModel.getJSON(wishlistModel.getQuery(username));
        }
        );

        post("/api/cart.json", ((request, response) -> cartModel.getCartJSON()));

        post("/api/cart/count.json", ((request, response) -> {
            JSONObject jso = new JSONObject();
            jso.put("count", cartModel.getCount());
            return jso;
        }));
    }
}
