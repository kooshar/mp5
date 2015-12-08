package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

// TODO: Implement a server that will instantiate a database, 
// process queries concurrently, etc.

public class RestaurantDBServer {

    public RestaurantDB dataBase;
    /**
     * Constructor
     * 
     * @param port
     * @param filename1
     * @param filename2
     * @param filename3
     * 
     */

    ServerSocket myServerSocket;

    // boolean ServerOn = true;
    public RestaurantDBServer(int port, String restaurantsFile, String reviewsFile, String usersFile)
            throws IOException {
        dataBase = new RestaurantDB(restaurantsFile, reviewsFile, usersFile);
        try {
            myServerSocket = new ServerSocket(port);
        } catch (IOException ioe) {
            System.out.println("Could not create server socket on port:" + port + "a  Quitting");
            System.exit(-1);
        }

        Socket clientSocket;
        while ((clientSocket = myServerSocket.accept()) != null) {
            DBqueryThread queryThread = new DBqueryThread(clientSocket, dataBase);
            new Thread(queryThread).start();
        }

    }

    private class DBqueryThread implements Runnable {

        private final String randomReview = "randomReview";
        private final String getRestaurant = "getRestaurant";
        private final String addRestaurant = "addRestaurant";
        private final String addUser = "addUser";
        private final String addReview = "addReview";

        private RestaurantDB dblocal;
        private Socket querySocket;

        public DBqueryThread(Socket querySocket, RestaurantDB db) throws IOException {
            this.querySocket = querySocket;
            dblocal = db;

        }

        public void run() {
            StringBuffer outputString = new StringBuffer();
            String query = "";

            // get the query
            try {
                BufferedReader inputQuery = new BufferedReader(new InputStreamReader(querySocket.getInputStream()));
                query = inputQuery.readLine();

                if (query.contains(randomReview)) {
                    outputString = new StringBuffer(
                            dblocal.randomReview(query.substring(randomReview.length() + 2, query.length() - 2)));
                } else if (query.contains(getRestaurant)) {
                    outputString = new StringBuffer(
                            dblocal.getRestaurant(query.substring(getRestaurant.length() + 2, query.length() - 2)));
                } else if (query.contains(addRestaurant)) {
                    outputString = new StringBuffer(
                            dblocal.addRestaurant(query.substring(getRestaurant.length() + 2, query.length() - 2)));
               } else if (query.contains(addUser)) {
                    outputString = new StringBuffer(
                            dblocal.addUser(query.substring(addUser.length() + 2, query.length() - 2)));
               } else if (query.contains(addReview)) {
                    outputString = new StringBuffer(
                            dblocal.addReview(query.substring(addReview.length() + 2, query.length() - 2)));
                } else {
                    Set<Restaurant> getQuery = new HashSet<Restaurant>();
                    getQuery = dblocal.query(query);

                    for (Restaurant restaurant : getQuery) {
                        outputString.append(restaurant.getJSONString());
                    }
                }

                // find the restaurants if the query was properly received

                try {
                    PrintWriter outputQuery = new PrintWriter(new OutputStreamWriter(querySocket.getOutputStream()));
                    outputQuery.println(outputString);
                    outputQuery.flush();
                    outputQuery.close();
                    inputQuery.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
}