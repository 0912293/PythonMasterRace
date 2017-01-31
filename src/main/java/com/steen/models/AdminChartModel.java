package com.steen.models;

import com.steen.Main;
import com.steen.session.Filter;
import com.steen.session.Search;
import com.steen.util.DateBuilder;
import com.steen.util.SQLToJSON;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import static com.steen.util.SQLToJSON.JsonListToString;
import static com.steen.util.SQLToJSON.getFormattedResult;
import static com.sun.deploy.config.JREInfo.clear;

public class AdminChartModel implements Model{
    public AdminChartModel() {
        clear();
    }
    private Search search = new Search("SELECT * FROM users");

    public String getAdmin(String user){
        Search csearch = new Search("SELECT admin FROM users");
        csearch.addFilterParam("username", user, Filter.Operator.LIKE);
        return getJSON(csearch);
    }
    public String getChart1JSON(){
        Search csearch = new Search("SELECT COUNT(o.order_id) as ord, o.order_pd FROM webshopdb.order o");
        csearch.addGroupParam("o.order_pd");
        return getJSON(csearch);
    }

    public String getChart2JSON(){
        Search csearch = new Search("SELECT COUNT(wishlist.games_id) AS added, games.games_name FROM wishlist, games WHERE " +
                "wishlist.games_id = games.games_id group by wishlist.games_id ORDER BY added DESC LIMIT 10");
        //No >.>
        return getJSON(csearch);
    }

    public String getChart3JSON(){
        Search csearch = new Search("SELECT SUM(ordered_game_info.og_info_games_price) AS total, " +
                "webshopdb.order.order_pd " +
                "FROM ordered_game_info, webshopdb.order, ordered_product " +
                "WHERE ordered_game_info.og_orderedproduct_id = ordered_product.orderedproduct_id " +
                "AND ordered_product.op_order_id = webshopdb.order.order_id " +
                "GROUP BY webshopdb.order.order_pd;");
        return getJSON(csearch);
    }

    public String getJSON(Search search) {
        List jsonList;
        try {
            jsonList = getFormattedResult(search.getResultSet());
            return JsonListToString(jsonList, SQLToJSON.Type.ARRAY);
        } catch (Exception e) {
            System.out.println("SQL >> Could not get JSON");
        }
        return null;
    }
    public Search getSearch() {
        return search;
    }
}
