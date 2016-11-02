package com.steen.Models;

import com.steen.Connector;
import com.steen.Main;
import com.steen.Models.LoginModel;
import com.steen.session.Search;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;

public class SessionModel {
    Connection connection = Main.connection;
    private Search search;
//    private Cart cart;

    public SessionModel() {
        this.search = new Search(this);
    }

    public void clearSession() {
        search = new Search(this);
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

}
