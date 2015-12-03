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
     * @param k, number of clusters, the Restaurant database
     * @return List of Sets of Restaurant clusters
     */
    public static List<Set<Restaurant>> kMeansClustering(int k, RestaurantDB db) {
        // TODO: Implement this method
        ArrayList<Restaurant> restaurants = db.getRestaurants();
        ArrayList<Location> clusterLoc=randomClusterLocation(k,restaurants);

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
     * @param List of old Cluster locations
     * @param List of restaurants
     * @return List of new Cluster locations
     * 
     * Given old cluster centers, returns updated cluster center locations
     */

    private static ArrayList<Location> updateClusterCenters(ArrayList<Location> oldClusterCenters,
            ArrayList<Restaurant> restaurants) {
        ArrayList<Location> newClusterLoc = new ArrayList<>();

        ArrayList<Set<Restaurant>> clusterRestaurants = Algorithms.findClusterRestaurant(oldClusterCenters,
                restaurants);
        //Iterate through all sets of clusters
        for (int index = 0; index < clusterRestaurants.size(); index++) {
            Set<Restaurant> cluster = clusterRestaurants.get(index);

            double sumLongitude = 0;
            double sumLatitude = 0;
            int count = 0;
            //find sum of longitude and latitude
            for (Restaurant restaurant : cluster) {
                sumLongitude = sumLongitude + restaurant.getlongitude();
                sumLatitude = sumLatitude + restaurant.getlatitude();
                count++;
            }
            //if old cluster contains no restaurants, don't change the cluster center
            if (count == 0) {
                newClusterLoc.add(oldClusterCenters.get(index));
            } else {
                double avLongitude = sumLongitude / count;
                double avLatitude = sumLatitude / count;
                //find center of restaurants in cluster, and update location of cluster center
                newClusterLoc.add(new Location(avLongitude, avLatitude));
            }
        }

        return newClusterLoc;
    }
    /**
     * 
     * @param Location objects describing cluster centers
     * @param restaurants
     * @return Sets of Restaurants belonging to each cluster
     * 
     * Given cluster locations, assigns restaurants to that cluster
     */

    private static ArrayList<Set<Restaurant>> findClusterRestaurant(ArrayList<Location> clusterCenters,
            ArrayList<Restaurant> restaurants) {
        ArrayList<Set<Restaurant>> clusterRestaurants = new ArrayList<>();
        //Initializes sets of Restaurant clusters 
        for (int index = 0; index < clusterCenters.size(); index++) {
            clusterRestaurants.add(new HashSet<Restaurant>());
        }
        //finds restaurants closest to cluster point, and assigns them to that cluster
        for (Restaurant restaurant : restaurants) {
            Location location = new Location(restaurant.getlongitude(), restaurant.getlatitude());
            int clusterindex = Location.closestLocation(location, clusterCenters);
            clusterRestaurants.get(clusterindex).add(restaurant);
        }

        return clusterRestaurants;
    }
    
    private static ArrayList<Location> randomClusterLocation(int k,ArrayList<Restaurant> restaurants){
        ArrayList<Location> clusterLoc = new ArrayList<>();
        //initialize boundaries of longitude and latitude
        double longitudeMax = restaurants.get(0).getlongitude(), longitudeMin = longitudeMax;
        double latitudeMax = restaurants.get(0).getlatitude(), latitudeMin = latitudeMax;
        //find the max,min latitude and longitude of all restaurants
        for (Restaurant restaurant : restaurants) {
            longitudeMax = Math.max(longitudeMax, restaurant.getlongitude());
            longitudeMin = Math.min(longitudeMin, restaurant.getlongitude());
            latitudeMax = Math.max(latitudeMax, restaurant.getlatitude());
            latitudeMin = Math.min(latitudeMin, restaurant.getlatitude());
        }
        //assign 'k' clusters randomnly within the latitude and longitude boundaries 
        for (int index = 0; index < k; index++) {
            clusterLoc.add(Location.random(longitudeMax, longitudeMin, latitudeMax, latitudeMin));
        }
        return clusterLoc;
    }
    /**
     * 
     * 
     * 
     * @param Sets of Restaurant clusters
     * @return String with location information about restaurants
     */

    @SuppressWarnings("unchecked")
    public static String convertClustersToJSON(List<Set<Restaurant>> clusters) {
        ArrayList<JSONObject> allInfo = new ArrayList<>();
        int clusterIndex = 0;
        
        for (Set<Restaurant> cluster : clusters) {
            for (Restaurant restaurant : cluster) {
                JSONObject restaurantInfo = new JSONObject();
                restaurantInfo.put("x", restaurant.getlongitude());
                restaurantInfo.put("y", restaurant.getlatitude());
                restaurantInfo.put("name", restaurant.getname());
                restaurantInfo.put("cluster", clusterIndex);
                restaurantInfo.put("wieght", 1.0);

                allInfo.add(restaurantInfo);
            }
            clusterIndex++;
        }

        return allInfo.toString();
    }

    public static MP5Function getPredictor(User u, RestaurantDB db, MP5Function featureFunction) {
        // TODO: Implement this method
        return null;
    }

    public static MP5Function getBestPredictor(User u, RestaurantDB db, List<MP5Function> featureFunctionList) {
        // TODO: Implement this method
        return null;
    }
}