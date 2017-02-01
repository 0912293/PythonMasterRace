package com.steen.controllers;
import com.steen.models.FavoritesModel;
import com.steen.models.Model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;
import static com.steen.Main.p_layout;
import static com.steen.Main.sfp;
import static spark.Spark.get;
import static spark.Spark.post;


public class FavoritesController {


    public FavoritesController(final HashMap<String, Model> models) {
        FavoritesModel favoritesModel = (FavoritesModel) models.get("favorites");


        get("/favorites", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("login_modal", sfp + "html/login_mod.vtl");
            model.put("template", sfp + "html/favorites.html");
            model.put("username", request.session().attribute("username"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("admin", request.session().attribute("admin"));

            return new ModelAndView(model, p_layout);

        }, new VelocityTemplateEngine());

        post("/favorites/add", (request, response) -> {
            String username = request.session().attribute("username");
            int id;
            id = Integer.parseInt(request.queryParams("id"));
            int isGame;
            isGame = Integer.parseInt(request.queryParams("isGame"));

            try {
                if (username == null || username.equals("")){
                    throw new Exception();
                }
                if (favoritesModel.checkInDatabase(username, id, isGame)) {
                    return "Product staat al in je favorieten.";

                }else{
                    favoritesModel.insertItem(username, id, isGame);
                    return "Product is toegevoegd aan je favorieten.";
                }
            } catch (Exception e){
                return "Kijk of u bent ingelogd en probeer het nogmaals.";
            }
        });

        post("/favorites/delete", (request, response) -> {
            String username = request.session().attribute("username");
            List<String> toDelete = new ArrayList<String>();
            Integer i = 0;
            String key = i.toString();
            while(request.queryParams(key) != null){
                toDelete.add(request.queryParams(key));
                i++;
                key = i.toString();

            }
            ArrayList<Integer> games = new ArrayList<>();
            ArrayList<Integer> platforms = new ArrayList<>();
            for (String a: toDelete){

                int b = Integer.parseInt(a);
                if (b < 1000)
                    games.add(b);
                else
                    platforms.add(b);
            }

            favoritesModel.deleteItems(username, games, platforms);
            //return "Producten zijn uit favorieten verwijderd!";
            return null;
        });
    }
}
