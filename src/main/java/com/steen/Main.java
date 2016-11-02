package com.steen;

import static com.steen.Util.SQLToJSON.getFormattedResult;
import static spark.Spark.*;

import com.steen.Controllers.AdminController;
import com.steen.Controllers.LoginController;
import com.steen.Controllers.RegisterController;
import com.steen.Controllers.RootController;
import com.steen.Models.AdminModel;
import com.steen.Models.LoginModel;
import com.steen.Models.RegisterModel;
import com.steen.Models.SessionModel;

import java.sql.Connection;
import java.util.*;

public class Main {
    public static Connection connection = Connector.connect();
    public static final String p_layout = "templates/p_layout.vtl";

    public static void main(String[] args) {
//        Spark.port(80); //BIJ DEPLOYEN NAAR SERVER <-- DEZE PORT GEBRUIKEN EN JAR UITVOEREN ALS ROOT. ( $cd builds $sudo java -cp Blabla.jar com.steen.Main)
        Spark.port(4567);

        staticFileLocation("/public");              // sets folder for non java files

        new RootController(new SessionModel());
        new RegisterController(new RegisterModel());
        new AdminController(new AdminModel());
        new LoginController(new LoginModel());

        //--------------------------------------------------------project



//        get("/games", (req, res) ->{
//            List jsonList = getFormattedResult(sessionModel.getSearch().getResultset());
//            String jsonstring = "[";
//            for (int i = 0; i < jsonList.size(); i++){
//                jsonstring = jsonstring + jsonList.get(i) + ",";
//
//            }
//            jsonstring = jsonstring + "]";
//            return jsonstring;
//        });
    }
}
