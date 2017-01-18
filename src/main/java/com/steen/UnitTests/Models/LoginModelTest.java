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
public class LoginModelTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void setCredentials() throws Exception {

    }

    @Test
    public void checkBlacklist() throws Exception {

    }

    @Test
    public void getUsername() throws Exception {

    }

    @Test
    public void getPassword() throws Exception {

    }

    @Test
    public void hasCorrectLoginInfo() throws Exception {

    }

    @Test
    public void isAdmin() throws Exception {

    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(com.steen.Models.LoginModel.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}
