package com.steen.session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static com.steen.Main.connection;


public class Search {
//    private ProductModel productModel;
    private ArrayList<Object> objects = new ArrayList<>();
    private Filter filter;
    private OrderBy orderBy;
    private String sqlQuery;
    private String filteredQuery;

    public Search(String baseQuery) {
//        this.productModel = productModel;
        this.filter = new Filter();
        this.orderBy = new OrderBy();
        this.sqlQuery = baseQuery;
        getResultSet();
    }

    public Search() {
        this.filter = new Filter();
        this.orderBy = new OrderBy();
        this.sqlQuery = "SELECT * FROM games";
        getResultSet();
    }

    public ResultSet getResultSet() {
        updateQuery();
        ResultSet resultSet = null;
        try {
            PreparedStatement myStmt = connection.prepareStatement(filteredQuery);
            resultSet = myStmt.executeQuery(filteredQuery);
//            games.clear();
//            while (resultSet.next()) {
//                String gameName = resultSet.getString(2);
//                String gamePrice = resultSet.getString(3);
//                String gamePlatform = resultSet.getString(5);
//                String imageURL = resultSet.getString("games_image");
//                Game game = new Game(gameName, gamePlatform, gamePrice, "'" + imageURL + "'");
//                games.add(game);
//            }
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
        getResultSet();
    }

    public void addFilterParam(String column, String value, Filter.Operator operator) {
        filter.addParameter(column, value, operator);
        getResultSet();
    }

    public void removeFilterParam(String param) {
        filter.removeParameter(param);
        getResultSet();
    }

    public void removeFilterParam(int index) {
        filter.removeParameter(index);
        getResultSet();
    }

    public void addOrderParam(String column){
        orderBy.addParameter(column);
        getResultSet();
    }

    public void clearFilters() {
        filter.filters.clear();
    }

    public void clearOrderBy() {
        orderBy.orders.clear();
    }

//    public ProductModel getProductModel() {
//        return productModel;
//    }

    public ArrayList<Object> getObjects() {
        getResultSet();
        return objects;
    }

    public Filter getFilter() {
        return filter;
    }

    public boolean hasFilter() {
        return filter.filters.size() > 0;
    }
}
