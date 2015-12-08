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

        Socket clientSocket = myServerSocket.accept();
        // while(myServerSocket.accept() != null){
        // clientSocket = myServerSocket.accept();

        DBqueryThread queryThread = new DBqueryThread(clientSocket, dataBase);
        new Thread(queryThread).start();

        // }
        // clientSocket.close();

    }

    private class DBqueryThread implements Runnable {
        RestaurantDB dblocal;
        Set<Restaurant> getQuery = new HashSet<Restaurant>();
        Socket querySocket;

        public DBqueryThread(Socket querySocket, RestaurantDB db) throws IOException {
            this.querySocket = querySocket;
            dblocal = db;

        }

        public void run() {
            String query = "";
            BufferedReader inputQuery;

            // get the query
            try {
                inputQuery = new BufferedReader(new InputStreamReader(querySocket.getInputStream()));
                query = inputQuery.readLine();

                // find the restaurants if the query was properly received
                getQuery = dblocal.query(query);

                StringBuffer outputString = new StringBuffer();
                for (Restaurant restaurant : getQuery) {
                    outputString.append(restaurant.getJSONString());
                }

                try {
                    PrintWriter outputQuery = new PrintWriter(new OutputStreamWriter(querySocket.getOutputStream()));
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