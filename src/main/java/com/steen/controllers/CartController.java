package com.steen.controllers;

import com.steen.models.CartModel;
import com.steen.models.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static spark.Spark.post;

public class CartController {
    private CartModel cartModel;

    private final int ADD = 0;
    private final int DELETE = 1;

    public CartController(final HashMap<String, Model> models) {
        cartModel = (CartModel) models.get("cart");

        post("/cart/act", (request, response) -> {
            System.out.println("test");
            try {
                int p_id = Integer.parseInt(request.queryParams("productId"));
                int amount = Integer.parseInt(request.queryParams("amount"));
                int action = Integer.parseInt(request.queryParams("action"));
                switch (action) {
                    case ADD:
                        cartModel.addToCart(p_id, amount);
                        break;
                    case DELETE:
                        cartModel.removeFromCart(p_id, amount);
                        break;
                }
                System.out.println(cartModel.toString());
                return "{ 'code' : 200 , 'message' : 'OK' }";
            } catch (Exception e) {
                e.printStackTrace();
                return "{ 'code' : 500 , 'message' : 'Internal Server Error' }";
            }
        });
    }
}
