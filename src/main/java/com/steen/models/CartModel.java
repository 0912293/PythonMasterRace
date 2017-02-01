package com.steen.models;
import com.steen.session.Search;
import com.steen.util.JSONUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import static com.steen.util.SQLToJSON.Type;
import static com.steen.util.SQLToJSON.getFormattedResult;
import static com.steen.util.SQLToJSON.JsonListToString;

public class CartModel implements Model {
    private HashMap<Integer, Integer> products;

    public CartModel() {
        products = new HashMap<>();
    }

    public void addToCart(int productID, int amount) {
        if (products.containsKey(productID)) {
            products.compute(productID, (key, val) -> val + amount);
        } else {
            products.put(productID, amount);
        }
    }

    public int removeFromCart(List<Integer> toRemove, int amnt) {
        int removed = 0;
        for (int productID : toRemove) {
            if (products.containsKey(productID)) {
                int amount = products.get(productID);
                if (products.get(productID) > amount) {
                    products.compute(productID, (key, val) -> val - amount);
                    removed += amount;
                } else {
                    products.remove(productID);
                    removed += amount;
                }
            }
        }
        return removed;

    }

    public int getCount() {
        int total = 0;
        for (int k : products.keySet()) {
            total += products.get(k);
        }
        return total;
    }

    public HashMap<Integer, Integer> getProducts() {
        return products;
    }

    public void clearCart() {
        HashMap<Integer, Integer> temp = (HashMap<Integer, Integer>) products.clone();
        for (Integer key : temp.keySet()) {
            products.remove(key);
        }
    }

    public String getCartJSON() {
        // Get all products from id's
        String query = "SELECT * FROM games g WHERE ";
        String queryPlatform = "SELECT * FROM platforms p WHERE ";
        if (products.size() <= 0) {
            return "[]";
        }
        boolean first = true;
        for (int k : products.keySet()) {
            if (!first) {
                query += " OR ";
            } else first = false;
            query += "g.games_id = " + k + "";
        }

        boolean cheapsolutionLoL = true;
        for (int k : products.keySet()) {
            if (!cheapsolutionLoL) {
                queryPlatform += " OR ";
            } else cheapsolutionLoL = false;
            queryPlatform += "p.platform_id = " + k + "";
        }



        ResultSet rs = Search.getResultSet(query); // ResultSet containing all games with listed product ID
        List<JSONObject> jsonList = getFormattedResult(rs);
        Search search = new Search("SELECT * FROM PLATFORMS");
        ResultSet rs0 = search.getResultSet(queryPlatform);
        List<JSONObject> jsonList2 = getFormattedResult(rs0);
        for (JSONObject jsonObject : jsonList) {
            int id = jsonObject.getInt("games_id");
            jsonObject.put("amount", products.get(id));
        }
        for (JSONObject jsonObject: jsonList2){
            int id = jsonObject.getInt("platform_id");
            jsonObject.put("amount", products.get(id));
        }
        JSONArray jsonArray = new JSONArray(JsonListToString(jsonList, Type.ARRAY));
        JSONArray jsonArray1 = new JSONArray(JsonListToString(jsonList2, Type.ARRAY));
        JSONArray finalArray = JSONUtil.concat(jsonArray, jsonArray1);
        return finalArray.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Integer i : products.keySet()) {
            sb.append(i);
            sb.append(" : ");
            sb.append(products.get(i));
            sb.append("\n");
        }
        return sb.toString();
    }
}
