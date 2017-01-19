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
    public static final String staticFilePath = "public/";
    public static final String sfp = staticFilePath;
    public static final String p_layout = sfp + "html/p_layout.vtl";
    public static Map<Integer, Map<String, Object>> sessions = new HashMap<>();


    public static void main(String[] args) {
        Spark.port(4567);

        staticFileLocation("/public"); // sets folder for non java files

        HashMap<String, Model> models = new HashMap<>();
        models.put("api", new ApiModel());
        models.put("admin", new AdminModel());
        models.put("register", new RegisterModel());
        models.put("login", new LoginModel());
        models.put("product", new ProductModel());
        models.put("user", new UserModel());
        models.put("wishlist", new WishlistModel());


        new RootController(models);
        new ApiController(models);
        new AdminController(models);
        new RegisterController(models);
        new LoginController(models);
        new ProductsController(models);
        new UserController(models);
        new WishlistController(models);


    }
}
