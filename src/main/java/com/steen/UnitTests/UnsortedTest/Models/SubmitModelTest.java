//package com.steen.UnitTests.UnsortedTest.Models;
//
//import com.steen.models.SubmitModel;
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.JavaArchive;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
///**
// * Created by Lennard Kras on 18-1-2017.
// */
//@RunWith(Arquillian.class)
//public class SubmitModelTest {
//
//
//    @Before
//    public void setUp() throws Exception {
//        //Not used. selectQueryColumn method is static.
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        //Not used. selectQueryColumn method is static.
//    }
//
//    @Test
//    public void selectQueryColumn() throws Exception {
//        Assert.assertEquals(SubmitModel.SelectQueryColumn("0", "name"), "games.games_name"); //possibility 1
//        Assert.assertEquals(SubmitModel.SelectQueryColumn("1", "name"), "games.games_name DESC"); //possibility 2
//    }
//
//    @Deployment
//    public static JavaArchive createDeployment() {
//        return ShrinkWrap.create(JavaArchive.class)
//                .addClass(com.steen.models.SubmitModel.class)
//                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//    }
//
//}
