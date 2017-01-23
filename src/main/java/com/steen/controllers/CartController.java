package com.steen.controllers;

import com.steen.models.CartModel;
import com.steen.models.Model;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.steen.Main.p_layout;
import static com.steen.Main.sfp;
import static spark.Spark.get;
import static spark.Spark.post;

public class CartController {
    private CartModel cartModel;

    private final int ADD = 0;
    private final int DELETE = 1;

    public CartController(final HashMap<String, Model> models) {
        cartModel = (CartModel) models.get("cart");

        post("/cart/act", (request, response) -> {
            try {
                int action = Integer.parseInt(request.queryParams("action"));
                switch (action) {
                    case ADD:
                        int p_id = Integer.parseInt(request.queryParams("productId"));
                        int amount = Integer.parseInt(request.queryParams("amount"));
                        cartModel.addToCart(p_id, amount);
                        return "Product toegevoegd aan winkelwagen.";
                    case DELETE:
                        List<Integer> idsToDelete = new ArrayList<>();
                        for (int i = 0; true; i++) {
                            String p_idString = request.queryParams("p_id" + i);
                            if (p_idString == null) break;
                            int productID = Integer.parseInt(p_idString);
                            idsToDelete.add(productID);
                        }
                        int itemsdeleted = cartModel.removeFromCart(idsToDelete, -1);
                        if (itemsdeleted > 0){
                            if (itemsdeleted == 1)
                                return "1 product verwijderd.";
                            return itemsdeleted + " producten verwijderd.";
                        } else {
//                            return "Kon de geselecteerde producten niet verwijderen. " +
//                                    "Neem aub. contact op met de sitebeheerder als dit vaak voor komt..";
                            return "0 producten verwijderd.";
                        }
                    default: throw new Exception();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Er ging iets mis aan onze kant, " +
                        "neem aub. contact op met de sitebeheerder als dit vaak voor komt. ";
            }
        });

        get("/cart", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("login_modal", sfp + "html/login_mod.vtl");
            model.put("template", sfp + "html/cart.html");
            model.put("admin", request.session().attribute("admin"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("username", request.session().attribute("username"));



            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());
    }
}
