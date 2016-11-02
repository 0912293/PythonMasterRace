package com.steen.session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class Search {
    private Session session;
    private ArrayList<Game> games = new ArrayList<>();
    private Filter filter;
    private OrderBy orderBy;
    private String sqlQuery;
    private String filteredQuery;

    public Search(Session session) {
        this.session = session;
        this.filter = new Filter();
        this.orderBy = new OrderBy();
        this.sqlQuery = "SELECT * FROM games";
        getResultset();
    }

    public ResultSet getResultset() {
        updateQuery();
        ResultSet resultSet = null;
        try {
            PreparedStatement myStmt = session.connection.prepareStatement(filteredQuery);
            resultSet = myStmt.executeQuery(filteredQuery);
            games.clear();
            while (resultSet.next()) {
                String gameName = resultSet.getString(2);
                String gamePrice = resultSet.getString(3);
                String gamePlatform = resultSet.getString(5);
                Game game = new Game(gameName, gamePlatform, gamePrice);
                games.add(game);


            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return resultSet;
    }

    private void updateQuery() {
        filteredQuery = sqlQuery + " " + filter.getWhereStatement(true) +  " " + orderBy.getOrderByStatement();
    }

    public void addFilterParam(String param) {
        filter.addParameter(param);
        getResultset();
    }

    public void addFilterParam(String column, String value, Filter.Operator operator) {
        filter.addParameter(column, value, operator);
        getResultset();
    }

    public void removeFilterParam(String param) {
        filter.removeParameter(param);
        getResultset();
    }

    public void removeFilterParam(int index) {
        filter.removeParameter(index);
        getResultset();
    }

    public void addOrderParam(String column){
        orderBy.addParameter(column);
        getResultset();
    }

    public void clearFilters() {
        filter.filters.clear();
    }

    public void clearOrderBy() {
        orderBy.orders.clear();
    }

    public Session getSession() {
        return session;
    }

    public ArrayList<Game> getGames() {
        getResultset();
        return games;
    }

    public Filter getFilter() {
        return filter;
    }

    public boolean hasFilter() {
        return filter.filters.size() > 0;
    }
}
