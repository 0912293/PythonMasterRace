package com.steen.Controllers;

import com.steen.Models.ProductModel;
import com.steen.Models.WishlistModel;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static com.steen.Main.p_layout;
import static spark.Spark.get;

/**
 * Created by Jamal on 16-1-2017.
 */
public class WishListController {

    public WishListController(final WishlistModel wishlistModel) {

        get("/wishlist", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("login_modal", "templates/login_mod.vtl");
            model.put("template", "templates/p_home.vtl");
            model.put("admin", req.session().attribute("admin"));
            model.put("correctinfo", req.session().attribute("correctinfo"));
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());
    }
}
