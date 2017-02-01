package com.steen.controllers;

import com.steen.util.DateBuilder;
import com.steen.models.*;
import static com.steen.util.ViewUtil.strictVelocityEngine;
import com.steen.velocity.VelocityTemplateEngine;
import spark.ModelAndView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import static com.steen.Main.p_layout;
import static com.steen.Main.sfp;
import static spark.Spark.get;
import static spark.Spark.post;

public class RegisterController {

    public RegisterController(final HashMap<String, Model> models) {
        RegisterModel registerModel = (RegisterModel) models.get("register");

        get("/p_reg", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            if (req.session().attribute("correctinfo") == null) {
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
            } else {
                res.redirect("/");
            }
            model.put("template", sfp + "html/p_reg.vtl");
            model.put("login_modal", sfp + "html/login_mod.vtl");
            return strictVelocityEngine().render(new ModelAndView(model, p_layout));
        });

        post("/regist",(req,res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            ArrayList<String> userDetails = new ArrayList<String>();
            String Username = req.queryParams("username");
            String Password = req.queryParams("pass");
            String name = req.queryParams("name");
            String surname = req.queryParams("sur");
            String country = req.queryParams("country");
            String city = req.queryParams("city");
            String number = req.queryParams("number");
            String street = req.queryParams("street");
            String postal = req.queryParams("postal");
            String day = req.queryParams("day");
            String month = req.queryParams("month");
            String year = req.queryParams("year");
            String email = req.queryParams("email");
            DateBuilder dbuilder = new DateBuilder();

            userDetails.addAll(Arrays.asList(Username, Password, name, surname, country, city, number, street, postal, day, month, year, email));

            Boolean nullCheck = NullCheck(userDetails);

            dbuilder.build(day,month,year);

            model.put("login_modal", sfp + "html/login_mod.vtl");
            registerModel.setParameters(Username,Password,name,surname,country,city,street,postal,number,dbuilder.getDate(),email);
            if(nullCheck){
                registerModel.ParseReg();
                res.redirect("/");
            }
            else{
                model.put("template", sfp + "html/p_reg.vtl");
            }
            return new ModelAndView(model, p_layout);
        }, new VelocityTemplateEngine());
    }

    private static Boolean NullCheck(ArrayList<String> list){
        for(int i = 0; i < list.size(); i++)
        {
            if(list.get(i) == null || list.get(i).equals("")){
                return false;
            }
        }
        return true;
    }

}


