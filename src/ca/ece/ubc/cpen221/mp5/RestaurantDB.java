package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

// TODO: This class represents the Restaurant Database.
// Define the internal representation and 
// state the rep invariant and the abstraction function.

public class RestaurantDB {
    private static ArrayList<Restaurant> restaurants=new ArrayList<>();
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Review> reviews = new ArrayList<>();

    public static void main(String args[]){
        RestaurantDBs("","data/reviews.json","data/users.json");
    }
    /**
     * Create a database from the Yelp dataset given the names of three files:
     * <ul>
     * <li>One that contains data about the restaurants;</li>
     * <li>One that contains reviews of the restaurants;</li>
     * <li>One that contains information about the users that submitted reviews.
     * </li>
     * </ul>
     * The files contain data in JSON format.
     * 
     * @param restaurantJSONfilename
     *            the filename for the restaurant data
     * @param reviewsJSONfilename
     *            the filename for the reviews
     * @param usersJSONfilename
     *            the filename for the users
     */
    @SuppressWarnings("unchecked")
    public static void RestaurantDBs(String restaurantJSONfilename, String reviewsJSONfilename, String usersJSONfilename) {
        // TODO: Implement this method
        
        //Read the Users data
        JSONParser userParser = new JSONParser();
        try {
            BufferedReader usersBuffer = new BufferedReader(new FileReader(usersJSONfilename));
            String line;

            while ((line = usersBuffer.readLine()) != null) {
                Object obj = userParser.parse(line);
                JSONObject jsonObject = (JSONObject) obj;
                JSONObject vote= (JSONObject) jsonObject.get("votes");
                                
                User nextUser=new User(
                        (String) jsonObject.get("url"),
                        ((Long) vote.get("cool")).intValue(),
                        ((Long) vote.get("useful")).intValue(),
                        ((Long) vote.get("funny")).intValue(),
                        ((Long) jsonObject.get("review_count")).intValue(),
                        (String) jsonObject.get("type"),
                        (String) jsonObject.get("user_id"),
                        (String) jsonObject.get("name"),
                        (Double) jsonObject.get("average_stars")
                        );
                
                users.add(nextUser);
            }
            usersBuffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //Read the Review data
        JSONParser reviewParser = new JSONParser();
        try {
            BufferedReader reviewsBuffer = new BufferedReader(new FileReader(reviewsJSONfilename));
            String line;

            while ((line = reviewsBuffer.readLine()) != null) {
                Object obj = reviewParser.parse(line);
                JSONObject jsonObject = (JSONObject) obj;
                JSONObject vote= (JSONObject) jsonObject.get("votes");
                                
                Review nextReview=new Review(
                        (String) jsonObject.get("type"),
                        (String) jsonObject.get("business_id"),
                        ((Long) vote.get("cool")).intValue(),
                        ((Long) vote.get("useful")).intValue(),
                        ((Long) vote.get("funny")).intValue(),
                        (String) jsonObject.get("review_id"),
                        (String) jsonObject.get("text"),
                        ((Long) jsonObject.get("stars")).intValue(),
                        (String) jsonObject.get("user_id"),
                        (String) jsonObject.get("date")
                        );
                
                reviews.add(nextReview);
            }
            reviewsBuffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<Restaurant> query(String queryString) {
        // TODO: Implement this method
        // Write specs, etc.
        return null;
    }

}
