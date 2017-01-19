package com.steen.session;

public class User {
    private String usernameName;
    private String userName;
    private String userSurname;

    public User(){

    }

    public User(String usernameName, String userName, String userSurname){
        this.usernameName = usernameName;
        this.userName = userName;
        this.userSurname = userSurname;
    }

    public String getUsernameName() {
        return usernameName;
    }

    public void setUsernameName(String usernameName) {
        this.usernameName = usernameName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }
}
