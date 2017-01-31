package com.steen.UnitTests.integration.Models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Lennard Kras on 31-1-2017.
 */
import com.steen.Cryptr;
import com.steen.Main;
import com.steen.db.Connector;
import com.steen.models.RegisterModel;
import com.steen.util.DateBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by jesse on 31-1-2017.
 */
public class RegisterModelTest {

    RegisterModel model;
    Connection connection = Main.connection;
    DateBuilder dbuilder;

    @Before
    public void setUp() throws Exception {
        this.connection = Connector.connect();
        this.model = new RegisterModel();
        this.dbuilder = new DateBuilder();

    }

    @After
    public void tearDown() throws Exception {
        this.model = null;
        this.connection = null;
        System.gc();
    }

    public void setUsername() throws Exception {
        String username = "Bassie";
        this.model.setUsername(username);
        Assert.assertEquals(this.model.username, username);
    }

    @Test
    public void setPassword() throws Exception {
        String password = "123";
        String e_password = Cryptr.getInstance(password, Cryptr.Type.MD5).getEncryptedString();
        this.model.setPassword(password);
        Assert.assertEquals(this.model.password, e_password);
    }

    @Test
    public void setName() throws Exception {
        String name = "Bassie";
        this.model.setName(name);
        Assert.assertEquals(this.model.name, name);
    }

    @Test
    public void setSurname() throws Exception {
        String surname = "Clown";
        this.model.setSurname(surname);
        Assert.assertEquals(this.model.surname, surname);
    }

    @Test
    public void setCountry() throws Exception {
        String country = "The Netherlands";
        this.model.setCountry(country);
        Assert.assertEquals(this.model.country, country);
    }

    @Test
    public void setCity() throws Exception {
        String city = "Rotterdam";
        this.model.setCity(city);
        Assert.assertEquals(this.model.city, city);
    }

    @Test
    public void setStreet() throws Exception {
        String street = "Clownstraat";
        this.model.setStreet(street);
        Assert.assertEquals(this.model.street, street);
    }

    @Test
    public void setPostal() throws Exception {
        String postal = "3063BA";
        this.model.setPostal(postal);
        Assert.assertEquals(this.model.postal, postal);
    }

    @Test
    public void setNumber() throws Exception {
        String number = "10";
        this.model.setNumber(number);
        Assert.assertEquals(this.model.number, number);
    }

    @Test
    public void setBirth_date() throws Exception {
        String db = "1995-11-28";
        this.dbuilder.build("28", "11", "1995");
        Assert.assertEquals(dbuilder.getDate(), db);

    }

    @Test
    public void setEmail() throws Exception {
        String email = "bassiedeclown@circus.nl";
        this.model.setEmail(email);
        Assert.assertEquals(this.model.email, email);
    }

    @Test
    public void setAdmin() throws Exception {
        Boolean admin = true;
        this.model.setAdmin(admin);
        Assert.assertEquals(this.model.admin, admin);
    }

    @Test
    public void setParameters() throws Exception {
        // model.setParameters();
    }

    @Test
    public void parseReg() throws Exception {

        this.dbuilder.build("28", "12", "1940");
        this.model.setParameters("Karel", "122", "Bassiee", "Clown", "Nederland" +
                "", "Rotterdam", "Hoogstraat", "3073YD", "52", dbuilder.getDate(), "hallo@gmail.com");
        this.model.ParseReg();

        ResultSet rs = null;
        String query = "SELECT username, name, surname, email, birth_date FROM users " +
                "WHERE username = 'Karel';";
        try {
            Statement myStmt = connection.createStatement();
            rs = myStmt.executeQuery(query);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        ArrayList<String> list = new ArrayList<>();

        //System.out.print(rs.next());
        while (rs.next()) {
            int i = 1;
            while (i <= 5){
                list.add(rs.getString(i++));
            }
        }
        assertTrue(list.contains("Karel"));

        try {
            Statement myStmt = connection.createStatement();
            myStmt.execute("DELETE FROM users WHERE username = 'Karel';");
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
}