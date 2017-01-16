package com.steen.Controllers;

import com.steen.Models.WishlistModel;
import com.steen.velocity.VelocityTemplateEngine;
import org.apache.velocity.VelocityContext;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static com.steen.Main.p_layout;
import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by jesse on 16-1-2017.
 */
public class WishlistController {

    public WishlistController(WishlistModel wishlistModel) {
        get("/wishlist", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("login_modal", "templates/login_mod.vtl");
            model.put("wishlist", "templates/wishlist.html");
            model.put("username", request.session().attribute("username"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("admin", request.session().attribute("admin"));
            String item = "hello";


            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

//        post("/wishlist", (request, response) -> {
//
//
//                return new ModelAndView(model, p_layout))
//            }, new VelocityTemplateEngine());
    }


}
