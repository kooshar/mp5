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

        System.out.println("server got connected to the socket");

        DBqueryThread queryThread = new DBqueryThread(clientSocket, dataBase);
        new Thread(queryThread).start();

        // }
        System.out.println("a");
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
            
            //get the query
            System.out.println("1");
            try {
                inputQuery = new BufferedReader(new InputStreamReader(querySocket.getInputStream()));
                query = inputQuery.readLine();
                inputQuery.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            
            //find the restaurants if the query was properly received
            System.out.println("3");
            if (query.equals("")) {
                System.out.println("oh shit i didn't pull out");
            } else {
                getQuery = dblocal.query(query);
            }
            
            String OutputString="";
            for (Restaurant iterator : getQuery) {
                OutputString.concat(iterator.getJSONString());
            }
            
            try {
                PrintWriter outputQuery = new PrintWriter(new OutputStreamWriter(querySocket.getOutputStream()));
                outputQuery.write(OutputString);
                outputQuery.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}