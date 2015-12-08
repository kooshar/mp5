package ca.ece.ubc.cpen221.mp5.statlearning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.omg.CORBA.portable.OutputStream;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;
import ca.ece.ubc.cpen221.mp5.RestaurantDBServer;

public class Testing {
    public static Socket toServer;
    static int port = 7243;

    public static void main(String args[]) throws NumberFormatException, IOException {
        String query1 = "in(\"Telegraph Ave\")";

        Servermaina server = new Servermaina();
        new Thread(server).start();

        try {
            toServer = new Socket("localhost", port);
        } catch (IOException ioe) {
            System.exit(-1);
        }

        PrintWriter out = new PrintWriter(toServer.getOutputStream());
        out.println(query1);
        out.flush();

        BufferedReader answerBuffer = new BufferedReader(new InputStreamReader(toServer.getInputStream()));
        String JSONanswer = answerBuffer.readLine();
        answerBuffer.close();

        out.close();
        toServer.close();
    }

    private static class Servermaina implements Runnable {

        @Override
        public void run() {
            try {
                RestaurantDBServer dbserver = new RestaurantDBServer(port, "data/restaurants.json", "data/reviews.json",
                        "data/users.json");
            } catch (IOException e) {

            }
        }

    }
}
