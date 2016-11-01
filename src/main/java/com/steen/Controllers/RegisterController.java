package com.steen.Controllers;

import spark.ModelAndView;
import spark.Route;

import java.util.HashMap;
import java.util.Map;
import static com.steen.Util.ViewUtil.strictVelocityEngine;
import static spark.Spark.get;

/**
 * Created by jesse on 31-10-2016.
 */


public class
RegisterController {
    static String layout = "templates/p_layout.vtl";

    public RegisterController() {
    }
    public static Route serveRegisterPage = (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("username", req.session().attribute("username"));
            model.put("pass", req.session().attribute("pass"));
            model.put("name", req.session().attribute("name"));
            model.put("sur", req.session().attribute("sur"));
            model.put("country", req.session().attribute("country"));
            model.put("city", req.session().attribute("city"));
            model.put("number", req.session().attribute("number"));
            model.put("street", req.session().attribute("street"));
            model.put("postal", req.session().attribute("postal"));
            model.put("day", req.session().attribute("day"));
            model.put("month", req.session().attribute("month"));
            model.put("year", req.session().attribute("year"));
            model.put("email", req.session().attribute("email"));

            model.put("template","templates/p_reg.vtl");

            model.put("login_modal","templates/login_mod.vtl");
        return strictVelocityEngine().render(new ModelAndView(model, layout));
    };



}


