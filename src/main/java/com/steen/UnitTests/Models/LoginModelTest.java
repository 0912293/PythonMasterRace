package com.steen.UnitTests.Models;

import com.steen.Connector;
import com.steen.Cryptr;
import com.steen.Models.LoginModel;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * Created by Lennard Kras on 18-1-2017.
 */
@RunWith(Arquillian.class)
public class LoginModelTest {

    LoginModel Model;
    public static Connection connection;

    @Before
    public void setUp() throws Exception {
        this.Model = new LoginModel();
        this.Model.setCredentials("UnitTest", "1234"); //default username and password
        this.connection = Connector.connect();

    }

    @After
    public void tearDown() throws Exception {
        this.Model = null;
        this.connection = null;
        System.gc();

    }

    @Test
    public void setCredentials() throws Exception {
        try{
            String Username = "UnitTest";
            String Password = "1234";
            String EncryptedPassword = Cryptr.getInstance(Password, Cryptr.Type.MD5).getEncryptedString();
            this.Model.setCredentials(Username,Password);
            Assert.assertEquals(this.Model.getUsername(), Username);
            Assert.assertEquals(this.Model.getPassword(), EncryptedPassword);
        }
        catch(Exception e){
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void checkBlacklist() throws Exception {
        //Case 1: False
        String NotBlacklistedUsername = "UnitTest";
        Assert.assertFalse(this.Model.checkBlacklist(NotBlacklistedUsername));

        //Case 2: True
        String BlacklistedUsername = "UnitTestBlacklisted";
        Assert.assertTrue(this.Model.checkBlacklist(BlacklistedUsername));

    }

    @Test
    public void getUsername() throws Exception {
        Assert.assertEquals(this.Model.getUsername(), "UnitTest");
    }

    @Test
    public void getPassword() throws Exception {
        Assert.assertEquals(this.Model.getPassword(), Cryptr.getInstance("1234", Cryptr.Type.MD5).getEncryptedString());
    }

    @Test
    public void hasCorrectLoginInfo() throws Exception {
        //Case 1: User does not exist; Field remains false.
        this.Model.correctLoginInfo = false; //setting to default value. (In setup it turns automaticcally true, which defeats the purpose of this test.
        this.Model.setCredentials("UnitTestDoesNotExist rolf lol", "894328490");
        Assert.assertFalse(this.Model.hasCorrectLoginInfo());

        //Case 2: User does exists; Field turns true.
        this.Model.correctLoginInfo = false; //setting to default value. (In setup it turns automaticcally true, which defeats the purpose of this test.
        this.Model.setCredentials("UnitTest", "1234");
        Assert.assertTrue(this.Model.hasCorrectLoginInfo());

    }

    @Test
    public void isAdmin() throws Exception {
        //Case 1: false
        this.Model.admin = false;
        this.Model.setCredentials("UnitTest", "1234");
        Assert.assertFalse(this.Model.isAdmin());

        //Case 2: true
        this.Model.admin = false;
        this.Model.setCredentials("UnitTestAdmin", "1234");
        Assert.assertTrue(this.Model.isAdmin());
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(com.steen.Models.LoginModel.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}