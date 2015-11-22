package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
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
        Set<Restaurant> a=query("price(1..2)&&price(1..3) || (rating(1..5))"
                + "|| name(\"Koojjjgh\")");
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
    public static void RestaurantDBs(
            String restaurantJSONfilename, 
            String reviewsJSONfilename,
            String usersJSONfilename)
    {
        // TODO: Implement this method
        
        //Read the Users data
        JSONParser userParser = new JSONParser();
        try {
            BufferedReader usersBuffer = new BufferedReader(
                    new FileReader(usersJSONfilename));
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
            BufferedReader reviewsBuffer = new BufferedReader(
                    new FileReader(reviewsJSONfilename));
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
    
    /**
     * 
     * @param queryString
     * @return
     */
    public static Set<Restaurant> query(String queryString) {
        // TODO: Implement this method
        // Write specs, etc.
        
        CharStream stream = new ANTLRInputStream(queryString);
        QueryLexer lexer = new QueryLexer(stream);
        lexer.reportErrorsAsExceptions();
        TokenStream tokens = new CommonTokenStream(lexer);
        
        // Feed the tokens into the parser.
        QueryParser parser = new QueryParser(tokens);
        parser.reportErrorsAsExceptions();
        
        // Generate the parse tree using the starter rule.
        ParseTree tree = parser.orExpr();
        ((RuleContext)tree).inspect(parser);
        
        // Finally, construct a Document value by walking over the parse tree.
        ParseTreeWalker walker = new ParseTreeWalker();
        QueryListener_QueryFinder listener = new QueryListener_QueryFinder();
        walker.walk(listener, tree);
        
        return null;
    }
    
    private static class QueryListener_QueryFinder extends QueryBaseListener {
        
    }
       
    
    /**
     * Returns a random review for a given restaurant name.
     * Returns "NO REVIEWS FOUND!" if no reviews were available for the 
     * restaurant or if the restaurant doesn't exist.
     * 
     * @param String restaurantName
     */
    @SuppressWarnings({ "unchecked" })
    public String randomReview(String restaurantName){
        String businessID="";
        ArrayList<Review> restaurantReviews=new ArrayList<>();
                
        for(Restaurant restaurantFinder: restaurants){
            if(restaurantFinder.getname().equals(restaurantName)){
                businessID=restaurantFinder.getBusinessID();
                break;
            }
        }
        
        for(Review reviewFinder:reviews){
            if (reviewFinder.getBusinessID().equals(businessID)){
                restaurantReviews.add(reviewFinder);
            }
        }
        
        if(restaurantReviews.size()!=0){
            Review randomReview=restaurantReviews.get(
                    (int) Math.random()*restaurantReviews.size());  
            return randomReview.getJSONString();
        }else{
            return "NO REVIEWS FOUND!";
        }
    }

    /**
     * Adds a user to the data set.
     * 
     * @param JSONUser user information in JSON format
     */
    public void addUser(String JSONUser){
        JSONParser userParser = new JSONParser();
        try{
            Object obj = userParser.parse(JSONUser);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject vote= (JSONObject) jsonObject.get("votes");
             
          //check if the user id exists
            boolean userIDExist=false;
            for(User user:users){
                if(user.getUserID().equals((String) jsonObject.get("user_id"))){
                    userIDExist=true;
                    break;
                }
            }
            
            //adds the user if the use id doesn't already exist
            if(!userIDExist){
                User newUser=new User(
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
                
                users.add(newUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Adds a review to the data set.
     * 
     * @param JSONReview user information in JSON format
     */
    public void addReview(String JSONReview){
        JSONParser reviewParser = new JSONParser();
        try{
            Object obj = reviewParser.parse(JSONReview);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject vote= (JSONObject) jsonObject.get("votes");
            
            //check if the review id exists
            boolean reviewIDExist=false;
            for(Review review:reviews){
                if(review.getReviewID().equals((String) jsonObject.get("review_id"))){
                    reviewIDExist=true;
                    break;
                }
            }
            
            //adds the review if the review id doesn't already exist
            if(!reviewIDExist){
                Review newReview=new Review(
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
                
                reviews.add(newReview);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}