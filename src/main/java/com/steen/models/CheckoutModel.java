package com.steen.models;
import com.steen.util.DateBuilder;
import com.steen.util.JSONUtil;
import com.steen.util.SQLToJSON;
import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import static com.steen.Main.connection;
import static com.steen.util.SQLToJSON.JsonListToString;
import static com.steen.util.SQLToJSON.getFormattedResult;

public class CheckoutModel implements Model {
    private String OrderQuery;
    private String OrderedProductQuery;
    private String OrderedPlatformQuery = null;
    private String OrderedGameQuery = null;
    private String order_id;
    private String order_pd;
    private String users_username;
    private int order_osc;
    private int op_platform_id;
    private int op_games_id;
    private int orderedproduct_id = 0;

    public void insertInvoice(String uid, String username, JSONArray products) {
        updateOrderInsert(uid, username);
        try {
            Statement myStmt = connection.createStatement();
            myStmt.execute(this.OrderQuery);
            for (int i = 0; i < products.length(); i++) {
                JSONObject product = products.getJSONObject(i);
                buildOrderedProductQuery(product);
                myStmt.executeUpdate(this.OrderedProductQuery, Statement.RETURN_GENERATED_KEYS);

                ResultSet rs = myStmt.getGeneratedKeys();
                if (rs.next()){
                    orderedproduct_id=rs.getInt(1);
                }

                if (op_platform_id != -1) {
                    buildOrderedPlatformQuery(product);
                    myStmt.execute(OrderedPlatformQuery);
                } else if (op_games_id != -1) {
                    buildOrderedGamesQuery(product);
                    myStmt.execute(OrderedGameQuery);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateOrderInsert(String uid, String username) {
        //order_id
        this.order_id = uid;

        //order_pd
        DateBuilder dab = new DateBuilder();
        Calendar cal = new GregorianCalendar();
        dab.build("" + cal.get(Calendar.DAY_OF_MONTH), "" + (cal.get(Calendar.MONTH)+1), "" + cal.get(Calendar.YEAR));
        this.order_pd = dab.getDate();

        //users_username
        this.users_username = username;

        //order_osc
        this.order_osc = 1;

        this.OrderQuery = "INSERT INTO `order` (order_id, order_pd, users_username, order_osc) " +
                "VALUES( \"" + order_id + "\"," +
                        "\"" + order_pd + "\"," +
                        "\"" + users_username + "\"," +
                        "\"" + order_osc + "\");";
    }

    private void buildOrderedProductQuery(JSONObject product) {
        op_platform_id = -1;
        op_games_id = -1;
        try {
            op_platform_id = Integer.parseInt(product.getString("platform_id"));
        } catch (Exception e) {}
        try {
            op_games_id = Integer.parseInt(product.getString("games_id"));
        } catch (Exception e) {}

        int op_quantity = product.getInt("amount");
        this.OrderedProductQuery = "INSERT INTO `ordered_product` (op_platform_id, op_games_id, op_quantity, op_order_id) " +
                "VALUES( \"" + op_platform_id + "\"," +
                "\"" + op_games_id + "\"," +
                "\"" + op_quantity + "\"," +
                "\"" + order_id + "\");";
    }

    private void buildOrderedPlatformQuery(JSONObject platform) {
        OrderedPlatformQuery = "INSERT INTO `ordered_platform_info` (opl_info_name, opl_info_colour, " +
                "opl_info_memory, opl_info_manufacturer, opl_info_price, opl_orderedproduct_id) " +
                "VALUES ( \"" + platform.getString("platform_name") + "\"," +
                "\"" + platform.get("platform_colour") + "\"," +
                "\"" + platform.get("platform_memory") + "\"," +
                "\"" + platform.get("platform_manufacturer") + "\"," +
                "\"" + platform.get("platform_price") + "\"," +
                "\"" + this.orderedproduct_id + "\");";
    }

    private void buildOrderedGamesQuery(JSONObject game) {
        OrderedGameQuery= "INSERT INTO `ordered_game_info` (og_info_games_name, og_info_games_price, " +
                "og_info_games_genre, og_info_games_platform, og_info_games_publisher, og_orderedproduct_id) " +
                "VALUES ( \"" + game.get("games_name") + "\"," +
                "\"" + game.get("games_price") + "\"," +
                "\"" + game.get("games_genre") + "\"," +
                "\"" + game.get("games_platform") + "\"," +
                "\"" + game.get("games_publisher") + "\"," +
                "\"" + this.orderedproduct_id + "\");";
    }

    public static String getInvoiceJSON(String uid) {
        ResultSet gamesResultSet = null;
        ResultSet platformsResultSet = null;
        String json = "";
        try {
            Statement stmnt = connection.createStatement();
            String gamesQuery = "SELECT op.op_quantity, ogi.og_info_games_name, ogi.og_info_games_price " +
                    "FROM `order` o, ordered_product op, ordered_game_info ogi " +
                    "WHERE o.order_id = \""+uid+"\" AND o.order_id = op.op_order_id AND " +
                    "op.orderedproduct_id = ogi.og_orderedproduct_id";
            String platformsQuery = "SELECT op.op_quantity, opi.opl_info_name, opi.opl_info_price " +
                    "FROM `order` o, ordered_product op, ordered_platform_info opi " +
                    "WHERE o.order_id = \""+uid+"\" AND o.order_id = op.op_order_id AND " +
                    "op.orderedproduct_id = opi.opl_orderedproduct_id";
            gamesResultSet = stmnt.executeQuery(gamesQuery);
            JSONArray gamesJson = new JSONArray(JsonListToString(getFormattedResult(gamesResultSet), SQLToJSON.Type.ARRAY));
            gamesJson = JSONUtil.replaceKeys(gamesJson, new String[] {"op_quantity","og_info_games_name", "og_info_games_price"}, new String[] {"amount","product_name", "product_price"});
            platformsResultSet = stmnt.executeQuery(platformsQuery);
            JSONArray platformsJson = new JSONArray(JsonListToString(getFormattedResult(platformsResultSet), SQLToJSON.Type.ARRAY));
            platformsJson = JSONUtil.replaceKeys(platformsJson, new String[] {"op_quantity","opl_info_name", "opl_info_price"}, new String[] {"amount","product_name", "product_price"});
            json = JSONUtil.concat(gamesJson, platformsJson).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
