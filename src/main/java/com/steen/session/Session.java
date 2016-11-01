package com.steen.session;

import com.steen.Main;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;

public class Session {
    Connection connection = Main.connection;
    private Login login;
    private Search search;
//    private Cart cart;

    public Session() {
        this.login = new Login(this);
        this.search = new Search(this);
    }

    public void clearSession() {
        login = new Login(this);
        search = new Search(this);
    }

//    protected void setConnector(Connector connector) {
//        this.connector = connector;
//    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(String username, String password) {
        this.login.setCredentials(username, password);
    }

    public boolean hasCorrectLogin() {
        return login.correctLoginInfo;
    }

    public boolean isAdmin() {
        return login.admin;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public String toJSON() {
        StringBuilder sb = new StringBuilder();

        throw new NotImplementedException();
    }
}
