package com.steen.controllers;
import com.steen.models.*;
import com.steen.session.Filter;
import com.steen.session.Search;
import com.steen.util.JSONUtil;
import com.steen.util.SQLToJSON;
import org.json.JSONArray;
import org.json.JSONObject;
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
        CheckoutModel checkoutModel = (CheckoutModel) models.get("checkout");
        AdminChartModel adminChartModel = (AdminChartModel) models.get("admin_chart");

        ProductModel platformModel = (ProductModel) models.get("platform");

        HistoryModel historyModel = (HistoryModel) models.get("history");
        FavoritesModel favoritesModel = (FavoritesModel) models.get("favorites");

        post("/api/user.ses", ((request, response) -> {
            return new JSONObject("{ username :" + request.session().attribute("username")+" }");
        }));

        get("/api/admincheck.ses", (req, res) -> {
            return adminChartModel.getAdmin(req.session().attribute("username"));
        });

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

        post("/api/product/platforms.json", (request, response) -> {
            platformModel.clearSession();
            platformModel.setSearch(new Search("SELECT * FROM platforms"));

            String search = request.queryParams("search");
            String order = request.queryParams("order");
            String filter = request.queryParams("filter");

            if (search != null && !search.equals("null") && !search.equals("")) {
                platformModel.getSearch().addFilterParam("platform_name", search, Filter.Operator.LIKE);
            }
            if (order != null && !order.equals("null") && !order.equals("")) {
                platformModel.getSearch().addOrderParam(order);
            }
            if (filter != null && !filter.equals("null") && !filter.equals("")) {
                platformModel.getSearch().addFilterParam(filter);
            }
            String json = "{}";
            try {
                json = apiModel.getJSON(platformModel.getSearch());
            } catch (Exception e) {}
            return json;
        });

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
                    new String[]{"name", "price", "gameId", "genre", "image"});
            return jsonArray;
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
            JSONArray array1 = new JSONArray(apiModel.getJSON(FavoritesModel.getQuery(username)));
            JSONArray array2 = new JSONArray(apiModel.getJSON(FavoritesModel.getQuery2(username)));
            return JSONUtil.concat(array1, array2);

        }
        );

        post("/api/cart.json", ((request, response) -> cartModel.getCartJSON()));

        post("/api/cart/count.json", ((request, response) -> {
            JSONObject jso = new JSONObject();
            jso.put("count", cartModel.getCount());
            return jso;
        }));

        post("/api/user/checkout_info.json", ((request, response) -> {
            String username = request.session().attribute("username");
            JSONObject userjson = new JSONObject(apiModel.getJSON("SELECT u.name, u.surname, u.email, " +
                    "a.address_country AS country, a.address_postalcode AS postalcode, " +
                    "a.address_city AS city, a.address_street AS street, a.address_number AS number " +
                    "FROM users u, address a WHERE u.username = '" + username + "' AND u.address_id = a.address_id",
                    SQLToJSON.Type.OBJECT));
            userjson = JSONUtil.replaceKeys(userjson,
                    new String[]{"address_city", "address_street", "address_number", "address_postalcode", "address_country"},
                    new String[] {"city","street","number","postalcode","country"});
            JSONArray productjson = new JSONArray(cartModel.getCartJSON());
            JSONObject json = new JSONObject("{'userinfo':"+userjson+",'products':" + productjson + "}");
            return json;
        }));

        post("/invoice.json", (request, response) ->  {
            String uid = request.queryParams("uid");
            JSONObject invoiceJson = new JSONObject();
            JSONObject userJson = new JSONObject(apiModel.getJSON("SELECT o.order_id, o.order_pd, u.name, u.surname, " +
                            "a.address_postalcode, a.address_country, a.address_city, a.address_street, a.address_number " +
                            "FROM `order` o, users u, address a  " +
                            "WHERE o.order_id = '"+uid+"' AND o.users_username = u.username AND u.address_id = a.address_id",
                    SQLToJSON.Type.OBJECT));
            JSONArray productJson = new JSONArray(CheckoutModel.getInvoiceJSON(uid));
            invoiceJson.put("userinfo", userJson);
            invoiceJson.put("products", productJson);
            return invoiceJson;
        });

        post("/api/order_history.json", (request, response) -> {
            String username = request.session().attribute("username");
            return historyModel.getJSON(username);
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
    }
}
