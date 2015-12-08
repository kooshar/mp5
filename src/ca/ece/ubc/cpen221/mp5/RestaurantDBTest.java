package ca.ece.ubc.cpen221.mp5;

import static org.junit.Assert.*;

import org.junit.Test;

public class RestaurantDBTest {

    @Test
    public void testAllGetRestaurantMethods() {
        RestaurantDB db = new RestaurantDB("data/restaurants.json", "data/reviews.json", "data/users.json");
        System.out.println(db.getRestaurant("gclB3ED6uk6viWlolSb_uA"));
        assertEquals(db.getRestaurant("gclB3ED6uk6viWlolSb_uA"),"Cafe 3");
        assertEquals(db.getRestaurantObject("gclB3ED6uk6viWlolSb_uA").getbusiness_id(),"gclB3ED6uk6viWlolSb_uA");
    }

}
