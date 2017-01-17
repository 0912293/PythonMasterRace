package com.steen.Models;

import com.steen.Util.SQLToJSON;
import com.steen.session.Search;

import java.util.HashMap;
import java.util.List;

import static com.steen.Util.SQLToJSON.JsonListToString;
import static com.steen.Util.SQLToJSON.getFormattedResult;

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
}
