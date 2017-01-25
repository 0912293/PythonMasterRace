//package com.steen.UnitTests.UnsortedTest.Models;
//
//import com.steen.models.ProductModel;
//import com.steen.session.Search;
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
//public class ProductModelTest {
//
//    ProductModel Model;
//
//    @Before
//    public void setUp() throws Exception {
//        this.Model = new ProductModel();
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        this.Model = null;
//        System.gc();
//    }
//
//    @Test
//    public void clearSession() throws Exception {
//        try{
//            Search oldSearch = this.Model.getSearch();
//            this.Model.clearSession();
//            Assert.assertNotEquals(this.Model.getSearch(), oldSearch);
//        }
//        catch(Exception e){
//            Assert.fail(e.getMessage());
//        }
//
//    }
//
//    @Test
//    public void getSearch() throws Exception {
//        try{
//            Assert.assertNotNull(this.Model.getSearch());
//        }
//        catch(Exception e){
//            Assert.fail(e.getMessage());
//        }
//    }
//
//    @Test
//    public void setSearch() throws Exception {
//        try{
//            Search newSearch = new Search();
//            this.Model.setSearch(newSearch);
//            Assert.assertEquals(this.Model.getSearch(), newSearch);
//        }
//        catch(Exception e){
//            Assert.fail(e.getMessage());
//        }
//
//    }
//
//    @Deployment
//    public static JavaArchive createDeployment() {
//        return ShrinkWrap.create(JavaArchive.class)
//                .addClass(com.steen.models.ProductModel.class)
//                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//    }
//
//}
