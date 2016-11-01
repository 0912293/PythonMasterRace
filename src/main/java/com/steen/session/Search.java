package com.steen.session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Search {
    private Session session;
    private ArrayList<Game> games = new ArrayList<>();
    private Filter filter;

    private String sqlQuery;
    private String filteredQuery;

    public Search(Session session) {
        this.session = session;
        this.filter = new Filter();
        this.sqlQuery = "SELECT * FROM games";
        updateGames();
    }

    private void updateGames() {
        updateQuery();
        try {
            PreparedStatement myStmt = session.connection.prepareStatement(filteredQuery);
            ResultSet resultSet = myStmt.executeQuery(filteredQuery);
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
    }

    private void updateQuery() {
        filteredQuery = sqlQuery + " " + filter.getWhereStatement(true);
    }

    public void addFilterParam(String param) {
        filter.addParameter(param);
        updateGames();
    }

    public void addFilterParam(String column, String value, Filter.Operator operator) {
        filter.addParameter(column, value, operator);
        updateGames();
    }

    public void removeFilterParam(String param) {
        filter.removeParameter(param);
        updateGames();
    }

    public void removeFilterParam(int index) {
        filter.removeParameter(index);
        updateGames();
    }

    public void clearFilters() {
        filter.filters.clear();
    }

    public Session getSession() {
        return session;
    }

    public ArrayList<Game> getGames() {
        updateGames();
        return games;
    }

    public Filter getFilter() {
        return filter;
    }

    public boolean hasFilter() {
        return filter.filters.size() > 0;
    }
}
