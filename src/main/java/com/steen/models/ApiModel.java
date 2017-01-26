package com.steen.models;

import com.steen.util.SQLToJSON;
import com.steen.session.Search;
import java.util.List;
import static com.steen.util.SQLToJSON.getFormattedResult;
import static com.steen.util.SQLToJSON.JsonListToString;



public class ApiModel implements Model {
    public String getJSON(Search search) {
        List jsonList;
        try {
            jsonList = getFormattedResult(search.getResultSet());
            return JsonListToString(jsonList, SQLToJSON.Type.ARRAY);
        } catch (Exception e) {
            System.out.println("SQL >> Could not get JSON");
        }
        return null;
    }

    public String getJSON(String query) {
        List jsonList;
        try {
            jsonList = getFormattedResult(Search.getResultSet(query));
            return JsonListToString(jsonList, SQLToJSON.Type.ARRAY);
        } catch (Exception e) {
            System.out.println("SQL >> Could not get JSON");
        }
        return null;
    }

    public String getJSON(String query, SQLToJSON.Type type) {
        List jsonList;
        try {
            jsonList = getFormattedResult(Search.getResultSet(query));
            return JsonListToString(jsonList, type);
        } catch (Exception e) {
            System.out.println("SQL >> Could not get JSON");
        }
        return null;
    }
}
