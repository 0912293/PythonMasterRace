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

    public void ParseQuery() {
        try {
            PreparedStatement myStmt = connection.prepareStatement(sql);
            ResultSet resultSet = myStmt.executeQuery(sql);
            while(resultSet.next()) {
                String gameName = resultSet.getString(2);
                String gamePrice = resultSet.getString(3);
                String gamePlatform = resultSet.getString(5);
                Game game = new Game(gameName, gamePlatform, gamePrice);
                arrayList.add(game);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<Game> getGamesList(){
        return this.arrayList;
    }
}
