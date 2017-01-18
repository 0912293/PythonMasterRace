package com.steen.UnitTests.Models;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by Lennard Kras on 18-1-2017.
 */
@RunWith(Arquillian.class)
public class AdminModelTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getUsers() throws Exception {

    }

    @Test
    public void checkAdmin() throws Exception {

    }

    @Test
    public void checkBlacklisted() throws Exception {

    }

    @Test
    public void blacklistUser() throws Exception {

    }

    @Test
    public void undoBlackList() throws Exception {

    }

    @Test
    public void delete_user() throws Exception {

    }

    @Test
    public void resetPassword() throws Exception {

    }

    @Test
    public void setData() throws Exception {

    }

    @Test
    public void clear() throws Exception {

    }

    @Test
    public void searchUser() throws Exception {

    }

    @Test
    public void getData() throws Exception {

    }

    @Test
    public void getUsersJSON() throws Exception {

    }

    @Test
    public void getChart1JSON() throws Exception {

    }

    @Test
    public void getChart2JSON() throws Exception {

    }

    @Test
    public void getChart3JSON() throws Exception {

    }

    @Test
    public void getJSON() throws Exception {

    }

    @Test
    public void getSearch() throws Exception {

    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(com.steen.Models.AdminModel.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}
