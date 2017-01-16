package com.steen;

import static spark.Spark.*;

import com.steen.Controllers.*;
import com.steen.Models.*;
import org.apache.commons.collections.map.HashedMap;
import spark.Spark;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main {
    public static Connection connection = Connector.connect();
    public static final String p_layout = "templates/p_layout.vtl";
    public static Map<Integer, Map<String, Object>> sessions = new HashMap<>();


    public static void main(String[] args) {
//        Spark.port(80); //BIJ DEPLOYEN NAAR SERVER <-- DEZE PORT GEBRUIKEN EN JAR UITVOEREN ALS ROOT. ( $cd builds $sudo java -cp Blabla.jar com.steen.Main)
        Spark.port(4567);

        staticFileLocation("/public"); // sets folder for non java files

        ProductModel productModel = new ProductModel();

        new RootController(productModel);
        new RegisterController(new RegisterModel());
        new AdminController(new AdminModel());
        new LoginController(new LoginModel());
        new ProductsController(productModel);
        new WishlistController(new WishlistModel());

// voorbeeld voor maken van een json  file
//        get("/games", (req, res) ->{
//            List jsonList = getFormattedResult(productModel.getSearch().getResultSet());
//            String jsonstring = "[";
//            for (int i = 0; i < jsonList.size(); i++){
//                jsonstring = jsonstring + jsonList.get(i) + ",";
//
//            }
//            jsonstring = jsonstring + "]";
//            return jsonstring;
//        });
    }
}
