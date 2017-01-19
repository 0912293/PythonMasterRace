package com.steen.Controllers;

import com.steen.Models.Model;
import com.steen.Models.WishlistModel;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import java.sql.ResultSet;
import java.util.*;

import static com.steen.Main.p_layout;
import static com.steen.Main.sfp;
import static spark.Spark.get;
import static spark.Spark.post;

public class WishlistController {


    public WishlistController(final HashMap<String, Model> models) {

        WishlistModel wishlistModel = (WishlistModel) models.get("wishlist");
        get("/wishlist", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("login_modal", sfp + "html/login_mod.vtl");
            model.put("template", sfp + "html/wishlist.html");
            model.put("username", request.session().attribute("username"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("admin", request.session().attribute("admin"));


            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        post("/wishlist/add", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            ResultSet resultSet;
            String username = request.session().attribute("username");
            int id = Integer.parseInt(request.queryParams("id"));
            int wishlistID;
            try {
                if (username == null || username.equals("")){
                    throw new Exception();
                }
                String query = wishlistModel.getUserWishlist(username);
                resultSet = wishlistModel.selectExecutor(query);
                if(resultSet.next()){
                    wishlistID = resultSet.getInt(1);
                    wishlistModel.executeUpdate(wishlistModel.insertItem(wishlistID, id));
                }
                else{
                    wishlistModel.executeUpdate(wishlistModel.addUserWishlist(username));
                    resultSet = wishlistModel.selectExecutor(query);
                    wishlistID = resultSet.getInt(1);
                    wishlistModel.executeUpdate(wishlistModel.insertItem(wishlistID, id));
                }
                return "Item has been added to your wishlist.";
            } catch (Exception e) {
                return "Please, check that you are logged in.";
            }
        });

        post("/wishlist/delete", (request, response) -> {
            String username = request.session().attribute("username");
            List<String> toDelete = new ArrayList<String>();
            Integer i = 0;
            String key = i.toString();
            while(request.queryParams(key) != null){
                toDelete.add(request.queryParams(key));
                i++;
                key = i.toString();

            }
            ArrayList<Integer> toDelete2 = new ArrayList<Integer>();
            for (String a: toDelete){

                int b =Integer.parseInt(a);
                toDelete2.add(b);
            }

            wishlistModel.deleteItem(username, toDelete2);
            return null;
        });
    }
}

