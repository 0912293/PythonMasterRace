package com.steen.Models;

import com.steen.Connector;
import com.steen.Main;
import com.steen.Models.LoginModel;
import com.steen.Util.SQLToJSON;
import com.steen.session.Search;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.steen.Main.connection;
import static com.steen.Util.SQLToJSON.JsonListToString;
import static com.steen.Util.SQLToJSON.getFormattedResult;

public class ProductModel {
    Connection connection = Main.connection;
    private Search search;
//    private Cart cart;

    public ProductModel() {
        this.search = new Search();
    }

    public void clearSession() {
        search = new Search();
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public String getJSON() {
        List jsonList;
        try {
            jsonList = getFormattedResult(search.getResultset());
            return JsonListToString(jsonList, SQLToJSON.Type.ARRAY);
        } catch (Exception e) {
            System.out.println("SQL >> Could not get JSON");
        }
        return null;
    }

}
