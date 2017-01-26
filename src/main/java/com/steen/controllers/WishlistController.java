package com.steen.controllers;

import com.steen.Cryptr;
import com.steen.models.Model;
import com.steen.models.WishlistModel;
import com.steen.models.*;
import static com.steen.Main.p_layout;
import static com.steen.Main.sfp;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import java.sql.ResultSet;
import java.util.*;
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
            model.put("wishlistID", wishlistModel.getUserWishlist(request.session().attribute("username")));



            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        post("/wishlist/add", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            ResultSet resultSet;
            String username = request.session().attribute("username");
            Cryptr encrypter = Cryptr.getInstance(username, Cryptr.Type.MD5);
            String encrypted_user = encrypter.getEncryptedString();
            System.out.println(encrypted_user);
            int id = Integer.parseInt(request.queryParams("id"));
            System.out.println("De id is " + id);
            try {
                if (username == null || username.equals("")){
                    throw new Exception();
                }
                String query = wishlistModel.getUserWishlist(username);
                resultSet = wishlistModel.selectExecutor(query);
                if(resultSet.next()){
                    wishlistModel.executeUpdate(wishlistModel.insertItem(encrypted_user, id));
                }
                else{
                    wishlistModel.executeUpdate(wishlistModel.addUserWishlist(username, encrypted_user));
                    resultSet = wishlistModel.selectExecutor(query);
                    if(resultSet.next()){
                        wishlistModel.executeUpdate(wishlistModel.insertItem(encrypted_user, id));
                    }
                }
                return "Item has been added to your wishlist.";
            } catch (Exception e) {
                System.out.println(e);
                return "Please, check that you are logged in.";
            }
        });

        post("/wishlist/delete", (request, response) -> {
            String username = request.session().attribute("username");
            String cryptedUser;
            try {
                String query = wishlistModel.getUserWishlist(username);
                ResultSet resultSet = wishlistModel.selectExecutor(query);
                if(resultSet.next()){
                    cryptedUser = resultSet.getString(2);
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

                        int b = Integer.parseInt(a);
                        toDelete2.add(b);
                    }

                    wishlistModel.deleteItem(cryptedUser, toDelete2);
                }

            }
            catch (Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            return "Items have been deleted!";
        });
    }
}

