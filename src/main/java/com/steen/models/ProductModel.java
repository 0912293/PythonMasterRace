package com.steen.models;

import com.steen.Main;
import com.steen.session.Search;

import java.sql.Connection;

public class ProductModel implements Model {
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
}
