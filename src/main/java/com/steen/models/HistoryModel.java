package com.steen.models;
import com.steen.util.JSONUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.steen.Main.connection;
import static com.steen.util.SQLToJSON.getFormattedResult;

public class HistoryModel implements Model {

    public HistoryModel(){}


    public String getQuery(String username){
        return "SELECT order.order_id, order.order_pd, orderstatus.orderstatus_descr, order.users_username " +
                "FROM webshopdb.order, webshopdb.orderstatus " +
                "WHERE order.users_username = " + "'" + username + "' AND order.order_osc = orderstatus.orderstatus_code ;";
    }

    public String getJSON(String username) {
        String query = getQuery(username);
        String JSON = null;
        try {
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery(query);

            JSONArray allOrders = new JSONArray();

            List<JSONObject> jsonObjects = getFormattedResult(rs);

            JSONObject order = new JSONObject();
            JSONArray order_products = new JSONArray();
            for (JSONObject jsonObject : jsonObjects) {
                String ord_id = jsonObject.getString("order_id");

                List<JSONObject> games_for_order = getFormattedResult(stmnt.executeQuery(
                        "SELECT op.op_games_id, op.op_platform_id, ogi.og_info_games_name, ogi.og_info_games_price, ogi.og_orderedproduct_id " +
                        "FROM ordered_game_info ogi, ordered_product op " +
                        "WHERE op.op_order_id = '"+ord_id+"' AND op.orderedproduct_id = ogi.og_orderedproduct_id"));

                games_for_order = JSONUtil.replaceKeys(games_for_order,
                        new String[] {"op_games_id", "op_platform_id", "og_info_games_name", "og_info_games_price", "og_orderedproduct_id"},
                        new String[] {"g_id", "p_id", "product_name", "product_price", "id"});

                List<JSONObject> platforms_for_order = getFormattedResult(stmnt.executeQuery(
                        "SELECT op.op_games_id, op.op_platform_id, opi.opl_info_name, opi.opl_info_price, opi.opl_orderedproduct_id " +
                        "FROM ordered_platform_info opi, ordered_product op " +
                        "WHERE op.op_order_id = '"+ord_id+"' AND op.orderedproduct_id = opi.opl_orderedproduct_id"));
                platforms_for_order = JSONUtil.replaceKeys(platforms_for_order,
                        new String[] {"op_games_id", "op_platform_id", "opl_info_name", "opl_info_price", "opl_orderedproduct_id"},
                        new String[] {"g_id", "p_id", "product_name", "product_price", "id"});


                if (games_for_order.size() > 0) {
                    for (JSONObject jso: games_for_order) {
                        order_products.put(jso);
                    }
                }
                if (platforms_for_order.size() > 0) {
                    for (JSONObject jso: platforms_for_order) {
                        order_products.put(jso);
                    }
                }

                order.put("info", jsonObject);
                order.put("products", order_products);
                allOrders.put(order);

                order = new JSONObject();
                order_products = new JSONArray();
            }
            JSON = allOrders.toString();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return JSON;
    }

}
