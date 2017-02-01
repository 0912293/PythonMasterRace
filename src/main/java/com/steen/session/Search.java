package com.steen.session;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import static com.steen.Main.connection;


public class Search {
    private ArrayList<Object> objects = new ArrayList<>();
    private Filter filter;
    private OrderBy orderBy;
    private GroupBy groupBy;
    private String sqlQuery;
    private String filteredQuery;

    public Search(String baseQuery) {
        this.groupBy = new GroupBy();
        this.filter = new Filter();
        this.orderBy = new OrderBy();
        this.sqlQuery = baseQuery;
    }

    public Search() {
        this.groupBy = new GroupBy();
        this.filter = new Filter();
        this.orderBy = new OrderBy();
        this.sqlQuery = "SELECT * FROM games";
    }

    public ResultSet getResultSet() {
        updateQuery();
        return getResultSet(filteredQuery);
    }

    public int executeNonQuery() {
        updateQuery();
        int affectedRows = 0;
        try {
            PreparedStatement myStmt = connection.prepareStatement(filteredQuery);
            affectedRows = myStmt.executeUpdate(filteredQuery);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    public static ResultSet getResultSet(String query) {
        ResultSet resultSet = null;
        try {
            PreparedStatement myStmt = connection.prepareStatement(query);
            resultSet = myStmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    private void updateQuery() {
        filteredQuery = sqlQuery;
        if (hasFilter()) {
            filteredQuery += " " + filter.getWhereStatement(true);
        }
        if (orderBy.orders.size() > 0) {
            filteredQuery += " " + orderBy.getOrderByStatement();
        }
        if (groupBy.group.size() > 0) {
            filteredQuery += " " + groupBy.getGroupByStatement();
        }
    }

    public String getFilteredQuery() {
        updateQuery();
        return filteredQuery;
    }

    public void addFilterParam(String param) {
        filter.addParameter(param);
    }

    public void addFilterParam(String column, String value, Filter.Operator operator) {
        filter.addParameter(column, value, operator);
    }

    public void removeFilterParam(String param) {
        filter.removeParameter(param);
    }

    public void removeFilterParam(int index) {
        filter.removeParameter(index);
    }

    public void addOrderParam(String column){
        orderBy.addParameter(column);
    }

    public void addGroupParam(String column){
        groupBy.addParameter(column);
    }

    public void clearFilters() {
        filter.filters.clear();
    }

    public void clearOrderBy() {
        orderBy.orders.clear();
    }

    public Filter getFilter() {
        return filter;
    }

    public boolean hasFilter() {
        return filter.filters.size() > 0;
    }
}
