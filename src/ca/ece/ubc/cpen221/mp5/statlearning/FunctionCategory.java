package ca.ece.ubc.cpen221.mp5.statlearning;

import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;

public class FunctionCategory implements MP5Function {

    @Override
    public double f(Restaurant yelpRestaurant, RestaurantDB db) {
        int hashCode = 0;

        // finds unique hashCode for each category
        for (char iter : yelpRestaurant.getcategories().get(0).toCharArray()) {
            hashCode += iter;
        }

        // return mapped hashcode to values between 0 - 10
        return Math.round(hashCode % 10);
    }

}
