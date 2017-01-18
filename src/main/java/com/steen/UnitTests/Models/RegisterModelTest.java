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
public class RegisterModelTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void setUsername() throws Exception {

    }

    @Test
    public void setPassword() throws Exception {

    }

    @Test
    public void setName() throws Exception {

    }

    @Test
    public void setSurname() throws Exception {

    }

    @Test
    public void setCountry() throws Exception {

    }

    @Test
    public void setCity() throws Exception {

    }

    @Test
    public void setStreet() throws Exception {

    }

    @Test
    public void setPostal() throws Exception {

    }

    @Test
    public void setNumber() throws Exception {

    }

    @Test
    public void setBirth_date() throws Exception {

    }

    @Test
    public void setEmail() throws Exception {

    }

    @Test
    public void setAdmin() throws Exception {

    }

    @Test
    public void setParameters() throws Exception {

    }

    @Test
    public void parseReg() throws Exception {

    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(com.steen.Models.RegisterModel.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}
