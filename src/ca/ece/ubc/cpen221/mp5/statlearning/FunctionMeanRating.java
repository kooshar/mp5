package ca.ece.ubc.cpen221.mp5.statlearning;
import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;

public class FunctionMeanRating implements MP5Function {

    @SuppressWarnings("null")
    @Override
    public double f(Restaurant yelpRestaurant, RestaurantDB db){
        if(db.getRestaurant(yelpRestaurant.getbusiness_id())!=null){
            return yelpRestaurant.getstars();
        }
        else
            return (Double) null;
        
        
    }

}
