package com.steen.session;

/**
 * Created by Jamal on 31-10-2016.
 */
public class Game {
    String gameName;
    String gamePlatform;
    String gamePrice;
    String imageURL;



    public Game(String gameName, String gamePlatform, String gamePrice, String imageURL){
        this.gameName = gameName;
        this.gamePlatform = gamePlatform;
        this.gamePrice = gamePrice;
        this.imageURL = imageURL;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGamePlatform() {
        return gamePlatform;
    }

    public void setGamePlatform(String gamePlatform) {
        this.gamePlatform = gamePlatform;
    }

    public String getGamePrice() {
        return gamePrice;
    }

    public void setGamePrice(String gamePrice) {
        this.gamePrice = gamePrice;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
