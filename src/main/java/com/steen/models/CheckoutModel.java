package com.steen.models;

import com.steen.util.DateBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import static com.steen.Main.connection;

public class CheckoutModel implements Model {
    private String OrderQuery;
    private String OrderedProductQuery;
    private String order_id;
    private String order_pd;
    private String users_username;
    private int order_osc;

    public void insertInvoice(String uid, String username, JSONArray products) {
        updateOrderInsert(uid, username);
        try {
            Statement myStmt = connection.createStatement();
            myStmt.execute(this.OrderQuery);
            for (int i = 0; i < products.length(); i++) {
                buildOrderedProductQuery(products.getJSONObject(i));
                myStmt.execute(this.OrderedProductQuery);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateOrderInsert(String uid, String username) {
        //order_id
        this.order_id = uid;

        //order_pd
        DateBuilder dab = new DateBuilder();
        Calendar cal = new GregorianCalendar();
        dab.build("" + cal.get(Calendar.DAY_OF_YEAR), "" + (cal.get(Calendar.MONTH)+1), "" + cal.get(Calendar.YEAR));
        this.order_pd = dab.getDate();

        //users_username
        this.users_username = username;

        //order_osc
        this.order_osc = 1;

        this.OrderQuery = "INSERT INTO `order` (order_id, order_pd, users_username, order_osc) " +
                "VALUES( '" + order_id + "'," +
                        "'" + order_pd + "'," +
                        "'" + users_username + "'," +
                        "'" + order_osc + "');";
    }

    private void buildOrderedProductQuery(JSONObject product) {
        String op_platform_id = product.getString("platformId");
        String op_games_id = product.getString("gameId");
        String op_quantity = product.getString("amount");
        this.OrderedProductQuery = "INSERT INTO `order` (op_platform_id, op_games_id, op_quantity, op_order_id) " +
                "VALUES( '" + op_platform_id + "'," +
                "'" + op_games_id + "'," +
                "'" + op_quantity + "'," +
                "'" + order_id + "');";
        if (op_platform_id != null) {
            buildOrderedPlatformQuery(product);
        } else if (op_games_id != null) {
            buildOrderedGamesQuery(product);
        }
    }

    private void buildOrderedPlatformQuery(JSONObject platform) {
        //To be implemented
    }

    private void buildOrderedGamesQuery(JSONObject game) {
        //To be implemented
    }
}
