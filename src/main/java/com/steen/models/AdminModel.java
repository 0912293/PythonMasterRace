package com.steen.models;
import com.steen.Cryptr;
import com.steen.util.DateBuilder;
import com.steen.Main;
import com.steen.session.Filter;
import com.steen.session.Insert;
import com.steen.session.User;
import com.steen.util.SQLToJSON;
import com.steen.session.Search;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.steen.models.LoginModel.checkBlacklist;
import static com.steen.util.SQLToJSON.JsonListToString;
import static com.steen.util.SQLToJSON.getFormattedResult;

public class AdminModel implements Model {
    private String sql;
    public String username = "";
    public String name;
    public String surname;
    public String email;
    public String year;
    public String month;
    public String day;
    private Integer address_id;
    private String address_country;
    private String address_street;
    private String address_postalcode;
    private String address_number;
    private String address_city;
    public String birth_date;
    private Boolean admin;
    private ArrayList<User> users = new ArrayList<>();
    private Search search = new Search("SELECT * FROM users");
    private DateBuilder dbuilder = new DateBuilder();
    private ResultSet rs;
    private Connection connection = Main.connection;
    public AdminModel() {
        clear();
    }
    //---------------------------------userlist--------------------
    private void userlist(){
        try {
            Search csearch = new Search("SELECT * FROM users");
            rs = csearch.getResultSet();

            users.clear();
            while (rs.next()) {
                String username = rs.getString("username");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                User user = new User(username, name, surname);
                users.add(user);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<User> getUsers() {
        userlist();
        return users;
    }

    //-----------------------------------------checkAdmin-----------
    public boolean checkAdmin(){
        try {
            Search csearch = new Search("SELECT admin FROM users");
            csearch.addFilterParam("username",this.username, Filter.Operator.EQUAL);

            rs = csearch.getResultSet();
            while(rs.next()){
                admin = rs.getBoolean("admin");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return admin;
    }
//----------------------------------checkBlacklisted-------------------
    public boolean checkBlacklisted() {
        return checkBlacklist(this.username);
    }
//----------------------------------delete-resetPassword-blacklist-----

    public void blacklistUser(){
        try {
            ArrayList<String> list = new ArrayList<String >(Arrays.asList("username","blacklisted"));
            Insert insert =  new Insert("blacklist", list);
            insert.addRecord(new ArrayList<>(Arrays.asList("'" + this.username + "'","true")));

            int affected = insert.executeQuery();
            if(affected > 0) {
                System.out.println("blacklisted user");
            }
            else{
                System.out.print("Blacklisting went wrong :^)");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void undoBlackList(){
        try {
            //LOWER IS NOT SUPPORT BY SEARCH (YET)
            sql = "DELETE FROM blacklist WHERE LOWER(username) = LOWER('" + this.username + "');";
            PreparedStatement myStmt = connection.prepareStatement(sql);
            myStmt.executeUpdate();
            System.out.println("blacklisted user");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void insertDummyUser(){ // only for unit testing purposes
        try {
                ArrayList<String> list = new ArrayList<String >(Arrays.asList("username","name", "surname", "email", "birth_date"));
                Insert insert =  new Insert("users", list);
                insert.addRecord(new ArrayList<>(Arrays.asList("'" + this.username + "'", "'" + this.name + "'", "'" + this.surname + "'", "'" + this.email + "'", "'1996-07-05'")));
                insert.executeQuery();
                System.out.println("Inserted dummy user");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
        }
    }

    public void delete_user(){
        if(!checkAdmin()) {
            try {
                Search csearch = new Search("DELETE FROM users");
                csearch.addFilterParam("username",this.username, Filter.Operator.EQUAL);
                csearch.executeNonQuery();
                System.out.println("deleted user");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void resetPassword(){
        if(!checkAdmin()) {
            try {
                Search csearch = new Search("UPDATE users SET password = '" + Cryptr.getInstance("0000", Cryptr.Type.MD5).getEncryptedString());
                csearch.addFilterParam("username",this.username, Filter.Operator.EQUAL);

                csearch.executeNonQuery();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //---------update user
    public void setData(String name, String surname, String email, String year, String month, String day, String country, String street, String postal, String number, String city){
        if(!checkAdmin()) {
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.year = year;
            this.month = month;
            this.day = day;
            this.address_country = country;
            this.address_street = street;
            this.address_postalcode = postal;
            this.address_number = number;
            this.address_city = city;
            dbuilder.build(this.day, this.month, this.year);
            this.birth_date = dbuilder.getDate();
            updateAddress();
            updateUser();
        }
    }

    public void clear() {
        this.name = "";
        this.surname = "";
        this.email = "";
        this.year = "";
        this.month = "";
        this.day = "";
        this.address_country = "";
        this.address_street = "";
        this.address_postalcode = "";
        this.address_number = "";
        this.address_city = "";
        this.birth_date = "";
    }

    private void updateAddress() {
        try {
            Search csearch = new Search("UPDATE address SET address_country = '" +
                    this.address_country + "', address_postalcode = '" +
                    this.address_postalcode + "', address_city = '" +
                    this.address_city + "', address_street = '" +
                    this.address_street + "', address_number = '" +
                    this.address_number + "'");
            csearch.addFilterParam("address_id", this.address_id.toString(), Filter.Operator.EQUAL);

            csearch.executeNonQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUser() {
        try {
            Search csearch = new Search("UPDATE users SET name = '" +
                            this.name + "', surname = '" +
                            this.surname + "', email = '" +
                            this.email + "', birth_date = '" +
                            this.birth_date + "'");
            csearch.addFilterParam("username",this.username, Filter.Operator.EQUAL);

            csearch.executeNonQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //-----------------------------------------get user data
    public void searchUser(String user) {
        try {
            Search csearch = new Search("SELECT * FROM users");
            csearch.addFilterParam("username", user, Filter.Operator.EQUAL);

            rs = csearch.getResultSet();
            while (rs.next()) {
                username = rs.getString("username");
                name = rs.getString("name");
                surname = rs.getString("surname");
                email = rs.getString("email");
                address_id = rs.getInt("address_id");
                admin = rs.getBoolean("admin");
            }
            searchUserAddress();
            getDate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDate() {
        try {
            Search csearch = new Search("SELECT EXTRACT(YEAR FROM birth_date) AS birthyear , " +
                    "EXTRACT(MONTH FROM birth_date) AS birthmonth , " +
                    "EXTRACT(DAY FROM birth_date) AS birthday " +
                    "FROM users");
            csearch.addFilterParam("username",username, Filter.Operator.EQUAL);

            rs = csearch.getResultSet();
            while (rs.next()) {
                year = rs.getString("birthyear");
                month = rs.getString("birthmonth");
                day = rs.getString("birthday");
            }
            searchUserAddress();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchUserAddress() {
        try {
            Search csearch = new Search("SELECT * FROM address");
            csearch.addFilterParam("address_id", address_id.toString(), Filter.Operator.EQUAL);
            rs = csearch.getResultSet();
            while (rs.next()) {
                address_country = rs.getString("address_country");
                address_city = rs.getString("address_city");
                address_number = rs.getString("address_number");
                address_postalcode = rs.getString("address_postalcode");
                address_street = rs.getString("address_street");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public enum Data{USERNAME,NAME,SURNAME,EMAIL,COUNTRY,STREET,POSTAL,NUMBER,CITY,YEAR,MONTH,DAY}

    public String getData(Data data){
        switch(data){
            case NAME:
                return name;
            case SURNAME:
                return surname;
            case USERNAME:
                return username;
            case EMAIL:
                return email;
            case YEAR:
                return year;
            case MONTH:
                return month;
            case DAY:
                return day;
            case COUNTRY:
                return address_country;
            case STREET:
                return address_street;
            case POSTAL:
                return address_postalcode;
            case NUMBER:
                return address_number;
            case CITY:
                return address_city;
        }
        return null; //just in case :^)
    }

    public String getUsersJSON() {
        List<org.json.JSONObject> jsonList;
        try {
            jsonList = getFormattedResult(search.getResultSet());
            return JsonListToString(jsonList, SQLToJSON.Type.ARRAY);
        } catch (Exception e) {
        } return null;
    }
    public Search getSearch() {return search;}
}