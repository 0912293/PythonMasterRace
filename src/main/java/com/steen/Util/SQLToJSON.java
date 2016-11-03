package com.steen.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

public class SQLToJSON {

    public static List<JSONObject> getFormattedResult(ResultSet rs){
        List<JSONObject> resList = new ArrayList<JSONObject>();

        try{
            ResultSetMetaData rsMeta = rs.getMetaData();
            int columnCnt = rsMeta.getColumnCount();
            List<String> columnNames = new ArrayList<String>();

            for (int i = 1; i <= columnCnt; i++){
                columnNames.add(rsMeta.getColumnName(i));
            }

            while (rs.next()){
                JSONObject obj = new JSONObject();
                for (int i = 1; i <= columnCnt; i++){
                    String key = columnNames.get(i-1);
                    String value = rs.getString(i);
                    obj.put(key, value);
                }
                resList.add(obj);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resList;
    }
}