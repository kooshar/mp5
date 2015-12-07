package ca.ece.ubc.cpen221.mp5.statlearning;

import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;

public class FunctionLongitude implements MP5Function{

    @SuppressWarnings("null")
    @Override
    public double f(Restaurant yelpRestaurant, RestaurantDB db){
        
        if(db.getRestaurant(yelpRestaurant.getbusiness_id())!=null){
            return yelpRestaurant.getlongitude();
        }
        else
            return (Double) null;
        
        
    }

}