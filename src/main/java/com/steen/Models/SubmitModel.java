package com.steen.Models;

/**
 * Created by Jamal on 3-11-2016.
 */
public class SubmitModel implements Model {

    public static String SelectQueryColumn(String desc, String column) {
        if(desc != null && desc.equals("0")){
            return "games.games_"+column;
        }
        else {
            return "games.games_"+column+" DESC";
        }
    }
}
