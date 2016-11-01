package com.steen;

import com.steen.Game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Jamal on 31-10-2016.
 */
public class Games {
    String sql;
    Connection connection = Main.connection;
    private ArrayList<Game> arrayList = new ArrayList();

    public Games(){
        this.sql = "SELECT * FROM games g";
    }

    public ResultSet ParseQuery() {
        ResultSet rs = null;

        try {
            PreparedStatement myStmt = connection.prepareStatement(sql);
            rs = myStmt.executeQuery(sql);
//            while(rs.next()) {
//                String gameName = rs.getString(2);
//                String gamePrice = rs.getString(3);
//                String gamePlatform = rs.getString(5);
//                Game game = new Game(gameName, gamePlatform, gamePrice);
//                arrayList.add(game);
//            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return rs;
    }

    public ArrayList<Game> getGamesList(){
        return this.arrayList;
    }
}
