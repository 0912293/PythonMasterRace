package com.steen.models;

import com.steen.Main;

import java.sql.Connection;

/**
 * Created by jesse on 26-1-2017.
 */
public class HistoryModel implements Model {

    //private Connection connection = Main.connection;

    public HistoryModel(){}


    public String getQuery(String username){
        return "SELECT * " +
                "FROM webshopdb.order " +
                "WHERE order.users_username = " + "'" + username + "' ;";
    }

}
