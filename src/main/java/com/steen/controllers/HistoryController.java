package com.steen.controllers;
import com.steen.models.HistoryModel;
import com.steen.models.Model;
import java.util.HashMap;
import java.util.Map;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;
import static com.steen.Main.p_layout;
import static com.steen.Main.sfp;
import static spark.Spark.get;

public class HistoryController {

    public HistoryController(final HashMap<String, Model> models){

        HistoryModel historyModel = (HistoryModel) models.get("history");

        get("/order_history", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", sfp + "html/order_history.html");
            model.put("login_modal", sfp + "html/login_mod.vtl");
            model.put("username", request.session().attribute("username"));
            model.put("correctinfo", request.session().attribute("correctinfo"));
            model.put("admin", request.session().attribute("admin"));

            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());


    }
}
