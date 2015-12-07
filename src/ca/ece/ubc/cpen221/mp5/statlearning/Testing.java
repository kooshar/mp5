package ca.ece.ubc.cpen221.mp5.statlearning;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;

public class Testing {

    public static void main(String args[]) throws FileNotFoundException, UnsupportedEncodingException {

        RestaurantDB db = new RestaurantDB("data/restaurants.json", "data/reviews.json", "data/users.json");
        String Json = Algorithms.convertClustersToJSON(Algorithms.kMeansClustering(10, db));

        PrintWriter writer = new PrintWriter("visualize\\voronoi.json");
        writer.print(Json);
        writer.close();
        
        
    }
}
