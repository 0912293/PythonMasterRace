package com.steen.Controllers;

import com.steen.Models.SessionModel;
import com.steen.session.Filter;
import com.steen.session.Game;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.steen.Main.p_layout;
import static spark.Spark.get;

public class RootController {
    public RootController(final SessionModel session) {

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("admin", req.session().attribute("admin"));
            model.put("login_modal", "templates/login_mod.vtl");
            model.put("template", "templates/p_home.vtl");
            model.put("correctinfo", req.session().attribute("correctinfo"));
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        get("/do_something",(req,res)->{
            Map<String, Object> model = new HashMap<>();
//            Games games = new Games();
//            //games.ParseQuery();
//            List jsonList = getFormattedResult(games.ParseQuery());
//            ArrayList<Game> gameArrayList = games.getGamesList();
//
//            for (int i = 0; i < jsonList.size(); i++){
//
//                System.out.println(jsonList.get(i++));
//            }
            String product = req.queryParams("search");
            String alpha = req.queryParams("alpha");
            String price = req.queryParams("pricesort");
            String alphaOrder = "games.games_name";
            String productFilter = "games.games_price";
            if(price != null &&price.equals("0")){
                productFilter = "games.games_price";
            }
            else{
                productFilter = "games.games_price" + " DESC";
            }
            if(alpha != null && alpha.equals("0")){
                alphaOrder = ",games.games_name";
            }
            else{
                alphaOrder = ",games.games_name" + " DESC";
            }

            session.getSearch().clearFilters();
            session.getSearch().clearOrderBy();
            if (!product.equals("")) {
                session.getSearch().addFilterParam("LOWER(games_name)", "'%" + product + "%'", Filter.Operator.LIKE);
            }
            session.getSearch().addOrderParam("" + productFilter + alphaOrder);
            req.session().attribute("search", product);

            ArrayList<Game> gameArrayList = session.getSearch().getGames();


            req.session().attribute("alpha", alpha);
            req.session().attribute("pricesort", price);
            model.put("alpha", alpha);
            model.put("pricesort", price);
//
//            Filter filter = new Filter();
//            filter.LikeData(product);

            model.put("games", gameArrayList);
            model.put("search",product);
            model.put("template","templates/p_products.vtl");
            model.put("login_modal","templates/login_mod.vtl");
            model.put("admin",req.session().attribute("admin"));
            model.put("filtered", session.getSearch().hasFilter());
            model.put("username", req.session().attribute("username"));

            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());
    }
}
