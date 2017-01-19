package com.steen.UnitTests.Models;

import com.steen.Connector;
import com.steen.Models.AdminModel;
import com.steen.User;
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
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Lennard Kras on 18-1-2017.
 */
@RunWith(Arquillian.class)
public class AdminModelTest {

    AdminModel Model;
    public static Connection connection;


    @Before
    public void setUp() throws Exception {
        this.connection = Connector.connect();
        this.Model = new AdminModel();
        this.Model.username = "UnitTestDummyUser";
        this.Model.name = "dummy";
        this.Model.surname = "dummy";
        this.Model.email = "dummy";
    }

    @After
    public void tearDown() throws Exception {
        this.Model = null;
        System.gc();
    }

    @Test
    public void getUsers() throws Exception {
        try {
            ArrayList<User> users = this.Model.getUsers();
            //Not quite testable. Output is always the same beceause there is no input and
            // is not affected by its own state.
            // (it fetches the userlist from the database.)
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }

    }

    @Test
    public void checkAdmin() throws Exception {
        try {
            //Case 1: No admin -> false
            this.Model.username = "UnitTest";
            Assert.assertFalse(this.Model.checkAdmin());

            //Case 2: Is admin -> True
            this.Model.username = "UnitTestAdmin";
            Assert.assertTrue(this.Model.checkAdmin());

        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void checkBlacklisted() throws Exception {
        try {
            //Case 1: Not blacklisted --> False
            this.Model.username = "UnitTest";
            Assert.assertFalse(this.Model.checkBlacklisted());
            //Case 2: Blacklisted --> True
            this.Model.username = "UnitTestBlacklisted";
            Assert.assertTrue(this.Model.checkBlacklisted());

        } catch(Exception e) {
            System.out.println(e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void blacklistUser() throws Exception {
        try {
            this.Model.username = "UnitTestBlacklisted";
            if(this.Model.checkBlacklisted()){
                System.out.println("Already Blacklisted.\nFirst unblacklisting before blacklisting...");
                this.Model.undoBlackList();
            }
            this.Model.blacklistUser();
            Assert.assertTrue(this.Model.checkBlacklisted());
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void undoBlackList() throws Exception {
        try {
            this.Model.username = "UnitTestBlacklisted";
            if(!this.Model.checkBlacklisted()) { // mind the !
                System.out.println("Not blacklisted. First blacklisting before undoing blacklist.");
                this.Model.blacklistUser();
            }
            this.Model.undoBlackList();
            Assert.assertFalse(this.Model.checkBlacklisted());

        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void CreatingAndDeletingUsers() throws Exception {
        //
        try {
            this.Model.username = "UnitTestDummyUser";
            this.Model.name = "dummy";
            this.Model.surname = "dummy";
            this.Model.email = "dummy";

            //stage 1: Creating user
            boolean found = false;
            this.Model.insertDummyUser();
            ArrayList<User> userlist = this.Model.getUsers();
            for(User user : userlist) {
                if(user.usernameName.equals(this.Model.username)) {
                    found = true;
                }
            }
            Assert.assertTrue(found);

            //Stage 2: Deleting user
            found = false;
            this.Model.delete_user();
            userlist = this.Model.getUsers();
            for(User user : userlist) {
                if(user.usernameName == this.Model.username) {
                    found = true;
                }
            }
            Assert.assertFalse(found);

        } catch(Exception e) {
            System.out.println(e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void resetPassword() throws Exception {
        // not testable in a good manner. There is not a method to retrieve the user password, so it can't
        // be checked if it has changed. This test only catches exceptions.
        try {
            this.Model.username = "UnitTestDummyUser";
            this.Model.name = "dummy";
            this.Model.surname = "dummy";
            this.Model.email = "dummy";

            this.Model.insertDummyUser();
            this.Model.resetPassword();
            this.Model.delete_user();
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void setData() throws Exception {
        try {

        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void clear() throws Exception {
        try {

        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void searchUser() throws Exception {
        try {

        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getData() throws Exception {
        try {

        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getUsersJSON() throws Exception {
        try {

        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getChart1JSON() throws Exception {
        try {

        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getChart2JSON() throws Exception {
        try {

        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getChart3JSON() throws Exception {
        try {

        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getJSON() throws Exception {
        try {

        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getSearch() throws Exception {
        try {

        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(com.steen.Models.AdminModel.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}
