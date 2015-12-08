package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

// TODO: This class represents the Restaurant Database.
// Define the internal representation and 
// state the rep invariant and the abstraction function.

public class RestaurantDB {
    private ArrayList<Restaurant> restaurants = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Review> reviews = new ArrayList<>();

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
    public RestaurantDB(String restaurantJSONfilename, String reviewsJSONfilename, String usersJSONfilename) {
        // Read the Restaurant data
        JSONParser restaurantParser = new JSONParser();
        try {
            BufferedReader restaurantReader = new BufferedReader(new FileReader(restaurantJSONfilename));
            String line;
            while ((line = restaurantReader.readLine()) != null) {
                Object obj = restaurantParser.parse(line);
                JSONObject jsonObject = (JSONObject) obj;

                boolean isopen = (Boolean) jsonObject.get("open");
                String name = (String) jsonObject.get("name");
                String city = (String) jsonObject.get("city");
                JSONArray categories = (JSONArray) jsonObject.get("categories");

                String url = (String) jsonObject.get("url");

                double longitude = (Double) jsonObject.get("longitude");
                double latitude = (Double) jsonObject.get("latitude");
                JSONArray neighborhoods = (JSONArray) jsonObject.get("neighborhoods");
                String business_id = (String) jsonObject.get("business_id");
                String state = (String) jsonObject.get("state");
                String type = (String) jsonObject.get("type");
                double stars = (Double) jsonObject.get("stars");
                String full_address = (String) jsonObject.get("full_address");

                Long review_count_long = (Long) jsonObject.get("review_count");
                int review_count = review_count_long.intValue();

                String photo_url = (String) jsonObject.get("photo_url");
                JSONArray schools = (JSONArray) jsonObject.get("schools");

                Long price_long = (Long) jsonObject.get("price");
                int price = price_long.intValue();

                Restaurant newRestaurant = new Restaurant(isopen, url, longitude, latitude, neighborhoods, business_id,
                        name, categories, state, type, stars, city, full_address, review_count, photo_url, schools,
                        price);
                restaurants.add(newRestaurant);

            }
            restaurantReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Read the Users data
        JSONParser userParser = new JSONParser();
        try {
            BufferedReader usersBuffer = new BufferedReader(new FileReader(usersJSONfilename));
            String line;

            while ((line = usersBuffer.readLine()) != null) {
                Object obj = userParser.parse(line);
                JSONObject jsonObject = (JSONObject) obj;
                JSONObject vote = (JSONObject) jsonObject.get("votes");

                User nextUser = new User((String) jsonObject.get("url"), ((Long) vote.get("cool")).intValue(),
                        ((Long) vote.get("useful")).intValue(), ((Long) vote.get("funny")).intValue(),
                        ((Long) jsonObject.get("review_count")).intValue(), (String) jsonObject.get("type"),
                        (String) jsonObject.get("user_id"), (String) jsonObject.get("name"),
                        (Double) jsonObject.get("average_stars"));

                users.add(nextUser);
            }
            usersBuffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Read the Review data
        JSONParser reviewParser = new JSONParser();
        try {
            BufferedReader reviewsBuffer = new BufferedReader(new FileReader(reviewsJSONfilename));
            String line;

            while ((line = reviewsBuffer.readLine()) != null) {
                Object obj = reviewParser.parse(line);
                JSONObject jsonObject = (JSONObject) obj;
                JSONObject vote = (JSONObject) jsonObject.get("votes");

                Review nextReview = new Review((String) jsonObject.get("type"), (String) jsonObject.get("business_id"),
                        ((Long) vote.get("cool")).intValue(), ((Long) vote.get("useful")).intValue(),
                        ((Long) vote.get("funny")).intValue(), (String) jsonObject.get("review_id"),
                        (String) jsonObject.get("text"), ((Long) jsonObject.get("stars")).intValue(),
                        (String) jsonObject.get("user_id"), (String) jsonObject.get("date"));

                reviews.add(nextReview);
            }
            reviewsBuffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a set of Restaurant that pass the search given a
     * query. The grammar of the query is specified in the file Query.g4.
     * 
     * @param queryString
     */
    public Set<Restaurant> query(String queryString) {
        CharStream stream = new ANTLRInputStream(queryString);
        QueryLexer lexer = new QueryLexer(stream);
        lexer.reportErrorsAsExceptions();
        TokenStream tokens = new CommonTokenStream(lexer);

        // Feed the tokens into the parser.
        QueryParser parser = new QueryParser(tokens);
        parser.reportErrorsAsExceptions();

        // Generate the parse tree using the starter rule.
        ParseTree tree = parser.orExpr();
        ((RuleContext) tree).inspect(parser);

        // Finally, construct a Document value by walking over the parse tree.
        ParseTreeWalker walker = new ParseTreeWalker();
        QueryListener_QueryFinder listener = new QueryListener_QueryFinder();
        walker.walk(listener, tree);

        Set<Restaurant> searchResult = new HashSet<>();
        ArrayList<Restaurant> results = listener.searchRestaurants();
        searchResult.addAll(results);

        return searchResult;
    }

    /**
     * Returns an ArrayList of all the restaurants in the database
     * 
     */
    public ArrayList<Restaurant> getRestaurants() {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        restaurants.addAll(this.restaurants);

        return restaurants;
    }
    
    /**
     * 
     * Returns an ArrayList of all the users in the database
     */
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        users.addAll(this.users);

        return users;
    }

    /**
     * 
     * Returns an ArrayList of all the users in the database
     */
    public ArrayList<Review> getReviews() {
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.addAll(this.reviews);

        return reviews;
    }

    /**
     * Returns the name of the restaurant with given businessID. returns
     * "NO RESTAURANT FOUND!"if a restaurant with given businessID isn't 
     * found
     * 
     * @param String
     *            businessId
     * @return String name of restaurant, "NO RESTAURANT FOUND!" if restaurant
     *         is not found
     */
    public String getRestaurant(String businessId) {

        for (Restaurant restaurantFinder : restaurants) {
            if (restaurantFinder.getbusiness_id().equals(businessId))
                return restaurantFinder.getname();
        }

        return "NO RESTAURANT FOUND!";

    }

    /**
     * Returns the restaurant with the businessID. returns NULL if 
     * the restaurant with given businessID isn't found
     * 
     * @param businessId
     * @return the restaurant object with that businessID
     */
    public Restaurant getRestaurantObject(String businessId) {
        for (Restaurant restaurantFinder : restaurants) {
            if (restaurantFinder.getbusiness_id().equals(businessId))
                return restaurantFinder;
        }

        return null;

    }

    /**
     * Adds a restaurant if the restaurants business id is not in the database.
     * Returns "RESTAURANT SUCCESSFULLY ADDED" if the restaurant was
     * successfully added. Returns "RESTAURANT ID ALREADY EXISTS" if the
     * business id is already in the database. Returns
     * "OPERATION ENDED WITH AN ERROR" if JSONrestaurant cannot be read.
     * 
     * @param JSONrestaurant
     *            restaurant full information in JSON format
     */
    public String addRestaurant(String JSONrestaurant) {
        JSONParser addrestaurantParser = new JSONParser();
        Object obj;
        try {
            obj = addrestaurantParser.parse(JSONrestaurant);

            JSONObject jsonObject = (JSONObject) obj;

            boolean restaurantIDExist = false;
            for (Restaurant restaurant : restaurants) {
                if (restaurant.getbusiness_id().equals((String) jsonObject.get("business_id"))) {
                    restaurantIDExist = true;
                    break;
                }
            }

            if (!restaurantIDExist) {
                boolean isopen = (Boolean) jsonObject.get("open");
                String name = (String) jsonObject.get("name");
                String city = (String) jsonObject.get("city");
                JSONArray categories = (JSONArray) jsonObject.get("categories");

                String url = (String) jsonObject.get("url");

                double longitude = (Double) jsonObject.get("longitude");
                double latitude = (Double) jsonObject.get("latitude");
                JSONArray neighborhoods = (JSONArray) jsonObject.get("neighborhoods");
                String business_id = (String) jsonObject.get("business_id");
                String state = (String) jsonObject.get("state");
                String type = (String) jsonObject.get("type");
                double stars = (Double) jsonObject.get("stars");
                String full_address = (String) jsonObject.get("full_address");

                Long review_count_long = (Long) jsonObject.get("review_count");
                int review_count = review_count_long.intValue();

                String photo_url = (String) jsonObject.get("photo_url");
                JSONArray schools = (JSONArray) jsonObject.get("schools");

                Long price_long = (Long) jsonObject.get("price");
                int price = price_long.intValue();

                Restaurant newRestaurant = new Restaurant(isopen, url, longitude, latitude,
                        neighborhoods, business_id, name, categories, state, type, stars,
                        city, full_address, review_count, photo_url, schools, price);
                
                restaurants.add(newRestaurant);
                return "RESTAURANT SUCCESSFULLY ADDED";
            } else {
                return "RESTAURANT ID ALREADY EXISTS";
            }
        } catch (Exception e) {
            return "OPERATION ENDED WITH AN ERROR";
        }

    }

    /**
     * Returns a random review in JSON format for a given restaurant name.
     * Returns "NO REVIEWS FOUND!" if no reviews were available for the
     * restaurant or if the restaurant doesn't exist.
     * 
     * @param String
     *            restaurantName
     */
    @SuppressWarnings({})
    public String randomReview(String restaurantName) {
        String businessID = "";
        ArrayList<Review> restaurantReviews = new ArrayList<>();

        for (Restaurant restaurantFinder : restaurants) {
            if (restaurantFinder.getname().equals(restaurantName)) {
                businessID = restaurantFinder.getbusiness_id();
                break;
            }
        }

        for (Review reviewFinder : reviews) {
            if (reviewFinder.getBusinessID().equals(businessID)) {
                restaurantReviews.add(reviewFinder);
            }
        }

        if (restaurantReviews.size() != 0) {
            Review randomReview = restaurantReviews.get(
                    (int) Math.random() * restaurantReviews.size());
            return randomReview.getJSONString();
        } else {
            return "NO REVIEWS FOUND!";
        }
    }

    /**
     * Adds a User if the user id is not in the database. Returns
     * "USER SUCCESSFULLY ADDED" if the user was successfully added. Returns
     * "USER ID ALREADY EXISTS" if the user id is already in the database.
     * Returns "OPERATION ENDED WITH AN ERROR" if JSONUser cannot be read.
     * 
     * @param JSONUser
     *            user full information in JSON format
     */
    public String addUser(String JSONUser) {
        JSONParser userParser = new JSONParser();
        try {
            Object obj = userParser.parse(JSONUser);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject vote = (JSONObject) jsonObject.get("votes");

            // check if the user id exists
            boolean userIDExist = false;
            for (User user : users) {
                if (user.getUserID().equals((String) jsonObject.get("user_id"))) {
                    userIDExist = true;
                    break;
                }
            }

            // adds the user if the use id doesn't already exist
            if (!userIDExist) {
                User newUser = new User((String) jsonObject.get("url"), 
                        ((Long) vote.get("cool")).intValue(),
                        ((Long) vote.get("useful")).intValue(),
                        ((Long) vote.get("funny")).intValue(),
                        ((Long) jsonObject.get("review_count")).intValue(), 
                        (String) jsonObject.get("type"),
                        (String) jsonObject.get("user_id"), 
                        (String) jsonObject.get("name"),
                        (Double) jsonObject.get("average_stars"));

                users.add(newUser);
                return "USER SUCCESSFULLY ADDED";
            } else {
                return "USER ID ALREADY EXISTS";
            }
        } catch (Exception e) {
            return "OPERATION ENDED WITH AN ERROR";
        }
    }

    /**
     * Adds a Review if the review id is not in the database. Returns
     * "REVIEW SUCCESSFULLY ADDED" if the review was successfully added. Returns
     * "REVIEW ID ALREADY EXISTS" if the review id is already in the database.
     * Returns "OPERATION ENDED WITH AN ERROR" if JSONReview cannot be read.
     * 
     * @param JSONReview
     *            review full information in JSON format
     */
    public String addReview(String JSONReview) {
        JSONParser reviewParser = new JSONParser();
        try {
            Object obj = reviewParser.parse(JSONReview);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject vote = (JSONObject) jsonObject.get("votes");

            // check if the review id exists
            boolean reviewIDExist = false;
            for (Review review : reviews) {
                if (review.getReviewID().equals((String) jsonObject.get("review_id"))) {
                    reviewIDExist = true;
                    break;
                }
            }

            // adds the review if the review id doesn't already exist
            if (!reviewIDExist) {
                Review newReview = new Review((String) jsonObject.get("type"),
                        (String) jsonObject.get("business_id"),
                        ((Long) vote.get("cool")).intValue(), 
                        ((Long) vote.get("useful")).intValue(),
                        ((Long) vote.get("funny")).intValue(), 
                        (String) jsonObject.get("review_id"),
                        (String) jsonObject.get("text"),
                        ((Long) jsonObject.get("stars")).intValue(),
                        (String) jsonObject.get("user_id"), 
                        (String) jsonObject.get("date"));

                reviews.add(newReview);
                return "REVIEW SUCCESSFULLY ADDED";
            } else {
                return "REVIEW ID ALREADY EXISTS";
            }
        } catch (Exception e) {
            return "OPERATION ENDED WITH AN ERROR";
        }
    }

    private class QueryListener_QueryFinder extends QueryBaseListener {
        private Stack<QueryExpression> stack = new Stack<>();

        @Override
        public void exitIn(@NotNull QueryParser.InContext ctx) {
            if (ctx.IN() != null) {
                String neighborhood = ctx.STRING().getText();
                neighborhood = neighborhood.substring(1, neighborhood.length() - 1);
                stack.push(new InAtom(neighborhood));
            }
        }

        @Override
        public void exitPrice(@NotNull QueryParser.PriceContext ctx) {
            if (ctx.PRICE() != null) {
                int leftRange = Integer.parseInt(ctx.range().leftNum().getText());
                int rightRange = Integer.parseInt(ctx.range().rightNum().getText());

                stack.push(new PriceAtom(leftRange, rightRange));
            }
        }

        @Override
        public void exitRating(@NotNull QueryParser.RatingContext ctx) {
            if (ctx.RATING() != null) {
                int leftRange = Integer.parseInt(ctx.range().leftNum().getText());
                int rightRange = Integer.parseInt(ctx.range().rightNum().getText());

                stack.push(new RatingAtom(leftRange, rightRange));
            }
        }

        @Override
        public void exitName(@NotNull QueryParser.NameContext ctx) {
            if (ctx.NAME() != null) {
                String name = ctx.STRING().getText();
                name = name.substring(1, name.length() - 1);
                stack.push(new NameAtom(name));
            }
        }

        @Override
        public void exitCategory(@NotNull QueryParser.CategoryContext ctx) {
            if (ctx.CATEGORY() != null) {
                String category = ctx.STRING().getText();
                category = category.substring(1, category.length() - 1);
                stack.push(new CategoryAtom(category));
            }
        }

        @Override
        public void exitOrExpr(@NotNull QueryParser.OrExprContext ctx) {
            if (ctx.OR() != null) {
                int size = ctx.OR().size() + 1;

                ArrayList<AndExpr> andExpressions = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    andExpressions.add((AndExpr) stack.pop());
                }

                stack.push(new OrExpr(andExpressions));
            }
        }

        @Override
        public void exitAndExpr(@NotNull QueryParser.AndExprContext ctx) {
            if (ctx.AND() != null) {
                int size = ctx.AND().size() + 1;

                ArrayList<Atom> atoms = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    QueryExpression a = stack.pop();
                    atoms.add((Atom) a);
                }

                stack.push(new AndExpr(atoms));
            }
        }

        public ArrayList<Restaurant> searchRestaurants() {
            OrExpr finalExpr = (OrExpr) stack.pop();
            return finalExpr.search();
        }
    }

    private class NameAtom extends Atom {
        private String name;

        public NameAtom(String name) {
            this.name = name;
        }

        @Override
        public ArrayList<Restaurant> search() {
            ArrayList<Restaurant> searchResult = new ArrayList<>();
            for (Restaurant restaurant : restaurants) {
                if (restaurant.getname().equals(name)) {
                    searchResult.add(restaurant);
                }
            }
            return searchResult;
        }

    }

    private class CategoryAtom extends Atom {
        private String category;

        public CategoryAtom(String category) {
            this.category = category;
        }

        @Override
        public ArrayList<Restaurant> search() {
            ArrayList<Restaurant> searchResult = new ArrayList<>();
            for (Restaurant restaurant : restaurants) {
                if (restaurant.getcategories().contains(category)) {
                    searchResult.add(restaurant);
                }
            }
            return searchResult;
        }

    }

    private class InAtom extends Atom {
        private String neighborhood;

        public InAtom(String neighborhood) {
            this.neighborhood = neighborhood;
        }

        @Override
        public ArrayList<Restaurant> search() {
            ArrayList<Restaurant> searchResult = new ArrayList<>();
            for (Restaurant restaurant : restaurants) {
                if (restaurant.getneighborhoods().contains(neighborhood)) {
                    searchResult.add(restaurant);
                }
            }
            return searchResult;
        }

    }

    private class PriceAtom extends Atom {
        private int leftRange;
        private int rightRange;

        public PriceAtom(int leftRange, int rightRange) {
            this.leftRange = leftRange;
            this.rightRange = rightRange;
        }

        @Override
        public ArrayList<Restaurant> search() {
            ArrayList<Restaurant> searchResult = new ArrayList<>();
            for (Restaurant restaurant : restaurants) {
                if (leftRange <= restaurant.getprice() && restaurant.getprice() <= rightRange) {
                    searchResult.add(restaurant);
                }
            }
            return searchResult;
        }

    }

    private class RatingAtom extends Atom {
        private int leftRange;
        private int rightRange;

        public RatingAtom(int leftRange, int rightRange) {
            this.leftRange = leftRange;
            this.rightRange = rightRange;
        }

        @Override
        public ArrayList<Restaurant> search() {
            ArrayList<Restaurant> searchResult = new ArrayList<>();
            for (Restaurant restaurant : restaurants) {
                if (leftRange <= restaurant.getstars() && restaurant.getstars() <= rightRange) {
                    searchResult.add(restaurant);
                }
            }
            return searchResult;
        }

    }

}