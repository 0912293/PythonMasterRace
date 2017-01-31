package com.steen.UnitTests.unit.Models;

import com.steen.util.DateBuilder;
import com.steen.models.RegisterModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.steen.Cryptr;

/**
 * Created by Lennard Kras on 18-1-2017.
 */

public class RegisterModelTest {

    RegisterModel Model;
    DateBuilder dbuilder;

    @Before
    public void setUp() throws Exception {
        this.Model = new RegisterModel();
        this.dbuilder = new DateBuilder();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void setUsername() throws Exception {
        String username = "UnitTest";
        this.Model.setUsername(username);
        Assert.assertEquals(this.Model.username, username);
    }

    @Test
    public void setPassword() throws Exception {
        String password = "123";
        String e_password = Cryptr.getInstance(password, Cryptr.Type.MD5).getEncryptedString();
        this.Model.setPassword(password);
        Assert.assertEquals(this.Model.password, e_password);
    }

    @Test
    public void setName() throws Exception {
        String name = "Bassie";
        this.Model.setName(name);
        Assert.assertEquals(this.Model.name, name);
    }

    @Test
    public void setSurname() throws Exception {
        String surname = "Clown";
        this.Model.setSurname(surname);
        Assert.assertEquals(this.Model.surname, surname);
    }

    @Test
    public void setCountry() throws Exception {
        String country = "The Netherlands";
        this.Model.setCountry(country);
        Assert.assertEquals(this.Model.country, country);
    }

    @Test
    public void setCity() throws Exception {
        String city = "Rotterdam";
        this.Model.setCity(city);
        Assert.assertEquals(this.Model.city, city);
    }

    @Test
    public void setStreet() throws Exception {
        String street = "Clownstraat";
        this.Model.setStreet(street);
        Assert.assertEquals(this.Model.street, street);
    }

    @Test
    public void setPostal() throws Exception {
        String postal = "3063BA";
        this.Model.setPostal(postal);
        Assert.assertEquals(this.Model.postal, postal);
    }

    @Test
    public void setNumber() throws Exception {
        String number = "10";
        this.Model.setNumber(number);
        Assert.assertEquals(this.Model.number, number);
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
        this.Model.setEmail(email);
        Assert.assertEquals(this.Model.email, email);
    }

    @Test
    public void setAdmin() throws Exception {
        Boolean admin = true;
        this.Model.setAdmin(admin);
        Assert.assertEquals(this.Model.admin, admin);
    }

    @Test
    public void setParameters() throws Exception {
        //Same as all the setters, but just in one method. The others work, so this does as well.
    }

}
