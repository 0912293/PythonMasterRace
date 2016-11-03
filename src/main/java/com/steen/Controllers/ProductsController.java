package com.steen.Controllers;

import com.steen.Models.SessionModel;
import com.steen.Util.SQLToJSON;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.steen.Main.connection;
import static com.steen.Main.p_layout;
import static com.steen.Util.SQLToJSON.JsonListToString;
import static com.steen.Util.SQLToJSON.getFormattedResult;
import static spark.Spark.*;

public class ProductsController {
    public ProductsController(SessionModel sessionModel) {

        get("/games", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/games.html");
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());

        get("/games.json", (req, res) ->{
            List jsonList = getFormattedResult(connection.prepareStatement("SELECT * FROM games").executeQuery());
//            String jsonstring = "{ 'products':[";
            return JsonListToString(jsonList, SQLToJSON.Type.ARRAY);
        });

    }
}
