package com.steen.controllers;

import com.steen.models.CartModel;
import com.steen.models.Model;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import java.util.HashMap;

import static com.steen.Main.p_layout;
import static com.steen.Main.sfp;
import static spark.Spark.modelAndView;
import static spark.Spark.post;
import static spark.Spark.get;

public class CheckoutController {
    public CheckoutController(final HashMap<String, Model> models) {
        CartModel cartModel = (CartModel) models.get("cart");

        get("/checkout/verify", (request,response) -> {
            HashMap<Integer, Integer> products = cartModel.getProducts();
            if (products.keySet().isEmpty()) {
                response.redirect("/");
            }
            HashMap<String, Object> model = new HashMap<>();

//            if (request.session().attribute("correctinfo") == null) {
//                response.redirect("/");
//            } else {
//            }
            model.put("admin", request.session().attribute("admin"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("username", request.session().attribute("username"));
            model.put("login_modal", sfp + "html/login_mod.vtl");
            model.put("template", sfp + "html/checkout.html");

            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());
    }
}
