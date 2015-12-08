package ca.ece.ubc.cpen221.mp5.statlearning;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;
import ca.ece.ubc.cpen221.mp5.RestaurantDBServer;

public class Testing {

    public static void main(String args[]) throws NumberFormatException, IOException {

        RestaurantDB db = new RestaurantDB("data/restaurants.json", "data/reviews.json", "data/users.json");
        
        Socket input=new Socket("Boom",1234);
        RestaurantDBServer newServer= new RestaurantDBServer(Integer.valueOf(args[0]).intValue(),args[1],args[2],args[3]);
        
        System.out.println("please enter query");
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        String query= br.readLine();
        PrintWriter toPort  = new PrintWriter(input);
        
        
        String Json = Algorithms.convertClustersToJSON(Algorithms.kMeansClustering(10, db));

        PrintWriter writer = new PrintWriter("visualize\\voronoi.json");
        writer.print(Json);
        writer.close();
        
        
    }
}
