package com.steen.controllers;

import com.steen.models.Model;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static com.steen.Main.p_layout;
import static com.steen.Main.sfp;
import static spark.Spark.before;
import static spark.Spark.get;

public class RootController {
    public RootController(final HashMap<String, Model> models) {

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("login_modal", sfp + "html/login_mod.vtl");
            model.put("template", sfp + "html/p_home.vtl");
            model.put("admin", req.session().attribute("admin"));
            model.put("correctinfo", req.session().attribute("correctinfo"));
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());


//        get("/do_something",(req,res)->{
//            Map<String, Object> model = new HashMap<>();
////            Games games = new Games();
////            //games.ParseQuery();
////            List jsonList = getFormattedResult(games.ParseQuery());
////            ArrayList<Game> gameArrayList = games.getGamesList();
////
////            for (int i = 0; i < jsonList.size(); i++){
////
////                System.out.println(jsonList.get(i++));
////            }
//
//            String product = req.queryParams("search");
//            String alpha = req.queryParams("alpha");
//            String price = req.queryParams("pricesort");
//            String alphaOrder = "games.games_name";
//            String productFilter = "games.games_price";
//
//            if(alpha != null) {
//                alphaOrder = SubmitModel.SelectQueryColumn(alpha, "name");
//
//            }
//            if(price != null){
//                productFilter = SubmitModel.SelectQueryColumn(price, "price");
//            }
//            session.getSearch().clearFilters();
//            session.getSearch().clearOrderBy();
//            if (!product.equals("")) {
//                session.getSearch().addFilterParam("LOWER(games_name)", "'%" + product + "%'", Filter.Operator.LIKE);
//            }
//            session.getSearch().addOrderParam(productFilter);
//            session.getSearch().addOrderParam(alphaOrder);
//            req.session().attribute("search", product);
//
//            ArrayList<Objects> gameArrayList = session.getSearch().getObjects();
//
//
//            req.session().attribute("alpha", alpha);
//            req.session().attribute("pricesort", price);
//            model.put("alpha", alpha);
//            model.put("pricesort", price);
//
////            Filter filter = new Filter();
////            filter.LikeData(product);
//
//            model.put("games", gameArrayList);
//            model.put("search", product);
//            model.put("template","html/p_products.vtl");
//            model.put("login_modal","html/login_mod.vtl");
//            model.put("admin",req.session().attribute("admin"));
//            model.put("filtered", session.getSearch().hasFilter());
//            model.put("username", req.session().attribute("username"));
//
//            return new ModelAndView(model, p_layout);
//        }, new VelocityTemplateEngine());
    }
}
