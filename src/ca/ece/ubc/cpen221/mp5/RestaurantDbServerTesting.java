package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RestaurantDbServerTesting {
    public static Socket toServer;
    static int port = 1546;

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

        System.out.println(JSONanswer);
        out.close();
        toServer.close();
    }

    private static class Servermaina implements Runnable {

        @Override
        public void run() {
            try {
                new RestaurantDBServer(port, "data/restaurants.json", "data/reviews.json", "data/users.json");
            } catch (IOException e) {

            }
        }

    }
}
