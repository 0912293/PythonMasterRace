package com.steen.UnitTests.integration.Models;
import com.steen.db.Connector;
import com.steen.models.ApiModel;
import com.steen.session.Filter;
import com.steen.session.Search;
import org.json.JSONArray;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.sql.Connection;

public class ApiModelTest {

    private String Query;
    private Search Search;
    private ApiModel Model;
    public static Connection connection;

    @Before
    public void setUp() throws Exception {
        this.connection = Connector.connect();
        this.Model = new ApiModel();
        this.Query = "SELECT * FROM webshopdb.games G WHERE G.games_name LIKE '%Mario%';";
        this.Search = new Search();
        this.Search.addFilterParam("games_name", "Mario", Filter.Operator.LIKE);
    }

    @After
    public void tearDown() throws Exception {
        this.Query = null;
        this.Search = null;
        this.Model = null;
        System.gc();
    }

    @Test
    public void getJSON() throws Exception {
        try{
            String JSONString = this.Model.getJSON(this.Search);
            System.out.println(JSONString);
            Assert.assertNotNull(JSONString);
            JSONArray map = new JSONArray(JSONString);
            Assert.assertEquals(3, map.length());
        }
        catch(Exception e){
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getJSON1() throws Exception {
        try{
            String JSONString = this.Model.getJSON(this.Query); //should result in a array containing 85 games
            System.out.println(JSONString);
            Assert.assertNotNull(JSONString);
            JSONArray map = new JSONArray(JSONString);
            Assert.assertEquals(3, map.length());
        }
        catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }

}
