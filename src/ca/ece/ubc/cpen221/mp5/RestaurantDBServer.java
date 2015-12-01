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

   private RestaurantDB dataBase;
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
   //boolean ServerOn = true;
    public RestaurantDBServer(int port, String restaurantsFile, String reviewsFile, String usersFile) throws IOException {
        dataBase = new RestaurantDB(restaurantsFile, reviewsFile, usersFile);
        try 
        { 
            myServerSocket = new ServerSocket(port); 
        }catch(IOException ioe) 
        { 
            System.out.println("Could not create server socket on port:" + port +  "Quitting"); 
            System.exit(-1); 
        }  
        Socket clientSocket = myServerSocket.accept(); 
        while(myServerSocket.accept() != null){
            clientSocket = myServerSocket.accept();
            DBqueryThread queryThread = new DBqueryThread(clientSocket,dataBase);
            queryThread.run();
                        
        }
        clientSocket.close();
        
          
        

    }
    
    
/**
    
    public static void main(String[] args) throws Exception {
        Thread[] queries = new Thread[args.length];
        for (int i = 0; i<queries.length;i++){
            queries[i] = new Thread(new DBquery(args[i],dataBase));
            
            
            
        }
        
        
        
        
    }
    
     **/
   
    
    
    

    private class DBqueryThread implements Runnable{
        String query;
        RestaurantDB dblocal;
        Set<Restaurant> getQuery  = new HashSet<Restaurant>();
        Socket querySocket;
        
        public DBqueryThread(Socket querySocket , RestaurantDB db) throws IOException{
            
            this.querySocket = querySocket;
            dblocal = db;
            BufferedReader inputQuery = new BufferedReader(new InputStreamReader(querySocket.getInputStream()));
            query = inputQuery.readLine();
            inputQuery.close();
            
               
            
        }
        

        public void run() {
            getQuery = dblocal.query(query);
            try {
                PrintWriter outputQuery = new PrintWriter(new OutputStreamWriter(querySocket.getOutputStream()));
                
                for(Restaurant iterator: getQuery){
                    outputQuery.println(iterator.getname());
                }
                outputQuery.close();
                
            } catch (IOException e) {
                
                e.printStackTrace();
            }
            
        }
    

    }
    
}