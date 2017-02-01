package com.steen.controllers;

import com.steen.models.*;
import static com.steen.Main.p_layout;
import static com.steen.Main.sfp;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;
import java.util.HashMap;
import java.util.Map;
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


    }
}
