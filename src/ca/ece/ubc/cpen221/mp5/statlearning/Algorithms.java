package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.Set;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import ca.ece.ubc.cpen221.mp5.*;

public class Algorithms {

    /**
     * Use k-means clustering to compute k clusters for the restaurants in the
     * database.
     * 
     * @param k,
     *            number of clusters, the Restaurant database
     * @return List of Sets of Restaurant clusters
     */
    public static List<Set<Restaurant>> kMeansClustering(int k, RestaurantDB db) {
        ArrayList<Restaurant> restaurants = db.getRestaurants();
        ArrayList<Location> clusterLoc = randomClusterLocation(k, restaurants);

        ArrayList<Location> newClusterLoc = new ArrayList<>();
        newClusterLoc = Algorithms.updateClusterCenters(clusterLoc, restaurants);
        // keep updating clusters until cluster locations are stable
        while (!newClusterLoc.equals(clusterLoc)) {
            clusterLoc = newClusterLoc;
            newClusterLoc = Algorithms.updateClusterCenters(clusterLoc, restaurants);
        }
        System.out.println(convertClustersToJSON(findClusterRestaurant(clusterLoc, restaurants)));
        return findClusterRestaurant(clusterLoc, restaurants);
    }

    /**
     * 
     * @param List
     *            of old Cluster locations
     * @param List
     *            of restaurants
     * @return List of new Cluster locations
     * 
     *         Given old cluster centers, returns updated cluster center
     *         locations
     */

    private static ArrayList<Location> updateClusterCenters(ArrayList<Location> oldClusterCenters,
            ArrayList<Restaurant> restaurants) {
        ArrayList<Location> newClusterLoc = new ArrayList<>();

        ArrayList<Set<Restaurant>> clusterRestaurants = Algorithms.findClusterRestaurant(oldClusterCenters,
                restaurants);
        // Iterate through all sets of clusters
        for (int index = 0; index < clusterRestaurants.size(); index++) {
            Set<Restaurant> cluster = clusterRestaurants.get(index);

            double sumLongitude = 0;
            double sumLatitude = 0;
            int count = 0;
            // find sum of longitude and latitude
            for (Restaurant restaurant : cluster) {
                sumLongitude = sumLongitude + restaurant.getlongitude();
                sumLatitude = sumLatitude + restaurant.getlatitude();
                count++;
            }
            // if old cluster contains no restaurants, don't change the cluster
            // center
            if (count == 0) {
                newClusterLoc.add(oldClusterCenters.get(index));
            } else {
                double avLongitude = sumLongitude / count;
                double avLatitude = sumLatitude / count;
                // find center of restaurants in cluster, and update location of
                // cluster center
                newClusterLoc.add(new Location(avLongitude, avLatitude));
            }
        }

        return newClusterLoc;
    }

    /**
     * 
     * @param Location
     *            objects describing cluster centers
     * @param restaurants
     * @return Sets of Restaurants belonging to each cluster
     * 
     *         Given cluster locations, assigns restaurants to that cluster
     */

    private static ArrayList<Set<Restaurant>> findClusterRestaurant(ArrayList<Location> clusterCenters,
            ArrayList<Restaurant> restaurants) {
        ArrayList<Set<Restaurant>> clusterRestaurants = new ArrayList<>();
        // Initializes sets of Restaurant clusters
        for (int index = 0; index < clusterCenters.size(); index++) {
            clusterRestaurants.add(new HashSet<Restaurant>());
        }
        // finds restaurants closest to cluster point, and assigns them to that
        // cluster
        for (Restaurant restaurant : restaurants) {
            Location location = new Location(restaurant.getlongitude(), restaurant.getlatitude());
            int clusterindex = Location.closestLocation(location, clusterCenters);
            clusterRestaurants.get(clusterindex).add(restaurant);
        }

        return clusterRestaurants;
    }

    private static ArrayList<Location> randomClusterLocation(int k, ArrayList<Restaurant> restaurants) {
        ArrayList<Location> clusterLoc = new ArrayList<>();
        // initialize boundaries of longitude and latitude
        double longitudeMax = restaurants.get(0).getlongitude(), longitudeMin = longitudeMax;
        double latitudeMax = restaurants.get(0).getlatitude(), latitudeMin = latitudeMax;
        // find the max,min latitude and longitude of all restaurants
        for (Restaurant restaurant : restaurants) {
            longitudeMax = Math.max(longitudeMax, restaurant.getlongitude());
            longitudeMin = Math.min(longitudeMin, restaurant.getlongitude());
            latitudeMax = Math.max(latitudeMax, restaurant.getlatitude());
            latitudeMin = Math.min(latitudeMin, restaurant.getlatitude());
        }
        // assign 'k' clusters randomnly within the latitude and longitude
        // boundaries
        for (int index = 0; index < k; index++) {
            clusterLoc.add(Location.random(longitudeMax, longitudeMin, latitudeMax, latitudeMin));
        }
        return clusterLoc;
    }

    /**
     * 
     * 
     * 
     * @param Sets
     *            of Restaurant clusters
     * @return String with location information about restaurants
     */

    @SuppressWarnings("unchecked")
    public static String convertClustersToJSON(List<Set<Restaurant>> clusters) {
        ArrayList<JSONObject> allInfo = new ArrayList<>();
        int clusterIndex = 0;

        for (Set<Restaurant> cluster : clusters) {
            for (Restaurant restaurant : cluster) {
                JSONObject restaurantInfo = new JSONObject();
                restaurantInfo.put("x", restaurant.getlatitude());
                restaurantInfo.put("y", restaurant.getlongitude());
                restaurantInfo.put("name", restaurant.getname());
                restaurantInfo.put("cluster", clusterIndex);
                restaurantInfo.put("weight", 4.0);

                allInfo.add(restaurantInfo);
            }
            clusterIndex++;
        }

        return allInfo.toString();
    }

    /**
     * 
     * @param User
     *            whose rating is to be predicted
     * @param RestaurantDB
     *            database
     * @param featureFunction
     * @return MP5Function used for prediction of next rating
     */

    public static MP5Function getPredictor(User u, RestaurantDB db, MP5Function featureFunction) {
        String userID = u.getUserID();
        double x;
        double y;
        double sxx = 0;
        double syy = 0;
        double sxy = 0;
        double mean_X;
        double mean_Y;
        double b;
        double a;
        double r2;
        ArrayList<Double> X = new ArrayList<Double>();
        ArrayList<Double> Y = new ArrayList<Double>();
        // find all X and Y values on previous reviews of user
        for (Review reviews : db.getReviews()) {
            if (reviews.getUserID().equals(userID)) {
                x = featureFunction.f(db.getRestaurantObject(reviews.getBusinessID()), db);
                y = reviews.getStars();
                X.add(x);
                Y.add(y);

            }
        }

        // to fit a linear model through X and Y, calculate all variables
        mean_X = getMean(X);
        mean_Y = getMean(Y);

        for (int i = 0; i < X.size(); i++) {
            sxx += sxx + Math.pow(mean_X - X.get(i), 2);
            syy += syy + Math.pow(mean_Y - Y.get(i), 2);
            sxy += sxy + (mean_X - X.get(i)) * (mean_Y - Y.get(i));
        }

        // if only one rating is given, linear function has 0 slope
        if (Y.size() == 0) {
            b = 0;
        } else
            b = sxy / sxx;

        a = mean_Y - b * mean_X;
        r2 = Math.pow(sxy, 2) / (sxx * syy);

        return new FunctionPrediction(a, b, r2, featureFunction);

    }

    /**
     * Returns function with most accurate(highest R-squared value) linear
     * regression model
     * 
     * @param User
     *            whose rating is to be predicted
     * @param RestaurantDB
     *            database
     * @param featureFunctionList
     * @return MP5Function with highest r-squared value
     */
    public static MP5Function getBestPredictor(User u, RestaurantDB db, List<MP5Function> featureFunctionList) {
        // double tempR2;
        FunctionPrediction bestFunction = (FunctionPrediction) getPredictor(u, db, featureFunctionList.get(0));
        FunctionPrediction tempFunction;
        // iterate through all feature functions to find function that gives
        // best R-squared value
        for (MP5Function iterFunction : featureFunctionList) {
            tempFunction = (FunctionPrediction) getPredictor(u, db, iterFunction);

            if (tempFunction.getR2() > bestFunction.getR2()) {
                bestFunction = tempFunction;
            }

        }

        return new FunctionPrediction(bestFunction.geta(), bestFunction.getb(), bestFunction.getR2(),
                bestFunction.featureFunction);
    }

    /**
     * Computes mean of ArrayList
     * 
     * @param ArrayList
     *            of doubles
     * @return mean of the list
     */
    public static double getMean(ArrayList<Double> list) {
        double sum = 0;
        for (Double iter : list) {
            sum += iter;
        }
        return sum / list.size();
    }
}