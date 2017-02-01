package com.steen.util;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JSONUtil {
    public static JSONObject replaceKeys(JSONObject jsonObject, String[] oldKeys, String[] newKeys) {
        String[][] toReplace = new String[][] { oldKeys, newKeys};
        for (int i = 0; i < toReplace[0].length; i++) {
            try {
                Object value = jsonObject.get(toReplace[0][i]);
                jsonObject.remove(toReplace[0][i]);
                jsonObject.put(toReplace[1][i], value);
            } catch (Exception e) {continue;}
        }
        return jsonObject;
    }

    public static JSONArray replaceKeys(JSONArray jsonArray, String[] oldKeys, String[] newKeys) {
        for (int j = 0; j < jsonArray.length(); j++) {
            JSONObject json = (JSONObject) jsonArray.get(j);
            replaceKeys(json, oldKeys, newKeys);
        }
        return jsonArray;
    }

    public static java.util.List<org.json.JSONObject> replaceKeys(List<JSONObject> jsonObjectList, String[] oldKeys, String[] newKeys) {
        List<JSONObject> res = new ArrayList<>();

        for (JSONObject jso : jsonObjectList) {
            res.add(replaceKeys(jso, oldKeys, newKeys));
        }
        return res;
    }

    public static JSONArray concat(JSONArray fst, JSONArray snd) {
        JSONArray res = new JSONArray();
        for (int i = 0; i < fst.length(); i++) {
            res.put(fst.get(i));
        }
        for (int i = 0; i < snd.length(); i++) {
            res.put(snd.get(i));
        }
        return res;
    }
}
