package com.steen.controllers;
import com.steen.models.MailModel;
import com.steen.models.Model;
import java.util.HashMap;
import static spark.Spark.post;

public class MailController {
    public MailController(final HashMap<String, Model> models) {
        MailModel mailModel = (MailModel) models.get("mail");

        post("/mail", (request, response) -> {
            String title = request.queryParams("title");
            String url = request.queryParams("url");
            String recipient = request.queryParams("to");
            mailModel.sendMail(recipient, title, url);
            return null;
        });
    }
}
