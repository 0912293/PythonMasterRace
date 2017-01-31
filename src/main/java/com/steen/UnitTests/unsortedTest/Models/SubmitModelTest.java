package com.steen.UnitTests.unsortedTest.Models;

import com.steen.models.SubmitModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Lennard Kras on 18-1-2017.
 */
public class SubmitModelTest {


    @Before
    public void setUp() throws Exception {
        //Not used. selectQueryColumn method is static.
    }

    @After
    public void tearDown() throws Exception {
        //Not used. selectQueryColumn method is static.
    }

    @Test
    public void selectQueryColumn() throws Exception {
        Assert.assertEquals(SubmitModel.SelectQueryColumn("0", "name"), "games.games_name"); //possibility 1
        Assert.assertEquals(SubmitModel.SelectQueryColumn("1", "name"), "games.games_name DESC"); //possibility 2
    }

}
