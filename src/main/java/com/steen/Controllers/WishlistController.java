package com.steen.Controllers;

import com.steen.Models.Model;
import com.steen.Models.ProductModel;
import com.steen.Models.WishlistModel;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;
import java.util.HashMap;
import java.util.Map;
import static com.steen.Main.p_layout;
import static spark.Spark.get;
import static spark.Spark.post;

public class WishlistController {


    public WishlistController(final HashMap<String, Model> models) {

        WishlistModel wishlistModel = (WishlistModel) models.get("wishlist");
        //hello
        get("/wishlist", (request, response) -> {
            System.out.println("Java Check2");
            Map<String, Object> model = new HashMap<>();
            model.put("login_modal", "templates/login_mod.vtl");
            model.put("template", "templates/wishlist.html");
            model.put("username", request.session().attribute("username"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("admin", request.session().attribute("admin"));


            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        post("/wishlist", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            System.out.println("Java Check");

            String username = request.session().attribute("username");
            int id;
            try {
                if (username == null || username.equals("")){
                    throw new Exception();
                }
                id = Integer.parseInt(request.queryParams("id"));
                wishlistModel.insertItem(username, id);
                return "Item has been added to your wishlist.";
            } catch (Exception e) {
                return "Please, check that you are logged in.";
            }
        });
    }

}

