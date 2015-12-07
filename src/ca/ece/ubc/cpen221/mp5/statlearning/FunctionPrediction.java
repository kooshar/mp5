package ca.ece.ubc.cpen221.mp5.statlearning;

import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;

public class FunctionPrediction implements MP5Function{
    double a; 
    double b;
    double r2;
    double predictX;
    MP5Function featureFunction;
    //Constructor
    public FunctionPrediction(double a, double b, double r2, MP5Function featureFunction ){
        this.a=a;
        this.b=b;
        this.r2=r2;
        this.featureFunction = featureFunction;    
    }
    
    
    @Override   
    /**returns rating prediction by linear regression function
     * 
     * @param yelpRestaurant
     * @param RestaurantDB db
     * @return predicted rating
     */
    public double f(Restaurant yelpRestaurant, RestaurantDB db) {
        predictX = featureFunction.f(yelpRestaurant, db);
        return (a*predictX + b);
    }
   
    
    //returns R-squared value
    public double getR2(){
        return r2;
    }
    
    public double geta(){
        return a;
    }
    public double getb(){
        return b;
    }
    
   

}
