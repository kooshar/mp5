package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class RestaurantDbServerTesting {

    static int port = 4698;

    public static void main(String args[]) throws NumberFormatException, IOException {
        ArrayList<String> queries = new ArrayList<>();
        queries.add("in(\"Telegraph Ave\")");
        queries.add("in(\"Telegraph Ave\") && (category(\"Chinese\") || category(\"Italian\")) && price(1..2)");
        queries.add("randomReview(\"Cafe 3\")");
        queries.add("getRestaurant(\"gclB3ED6uk6viWlolSb_uA\")");
        queries.add("addRestaurant(\"{\"open\": true, \"url\": \"http://www.yelp.com/biz/cafe-3-berkeley\", \"long"
                + "itude\": -122.260408, \"neighborhoods\": [\"Telegraph Ave\", \"UC Campus Area\"], \"business_i"
                + "d\": \"gclB3ED6uk6viWlolSb_uA\", \"name\": \"Cafe 3\", \"categories\": [\"Cafes\", \"Restaurant"
                + "s\"], \"state\": \"CA\", \"type\": \"business\", \"stars\": 2.0, \"city\": \"Berkeley\", \"full_add"
                + "ress\": \"2400 Durant Ave\nTelegraph Ave\nBerkeley, CA 94701\", \"review_count\": 9, \"photo_ur"
                + "l\": \"http://s3-media1.ak.yelpcdn.com/bphoto/AaHq1UzXiT6zDBUYrJ2NKA/ms.jpg\", \"schools\": [\"Uni"
                + "versity of California at Berkeley\"], \"latitude\": 37.867417, \"price\": 1}\")");
        queries.add("addRestaurant(\"{\"open\": true, \"url\": \"http://www.yelp.com/biz/cafe-3-berkeley\", \"long"
                + "itude\": -122.260408, \"neighborhoods\": [\"Telegraph Ave\", \"UC Campus Area\"], \"business_i"
                + "d\": \"newID\", \"name\": \"Cafe 3\", \"categories\": [\"Cafes\", \"Restaurant"
                + "s\"], \"state\": \"CA\", \"type\": \"business\", \"stars\": 2.0, \"city\": \"Berkeley\", \"full_add"
                + "ress\": \"2400 Durant Ave\nTelegraph Ave\nBerkeley, CA 94701\", \"review_count\": 9, \"photo_ur"
                + "l\": \"http://s3-media1.ak.yelpcdn.com/bphoto/AaHq1UzXiT6zDBUYrJ2NKA/ms.jpg\", \"schools\": [\"Uni"
                + "versity of California at Berkeley\"], \"latitude\": 37.867417, \"price\": 1}\")");
        queries.add("addReview(\"{\"type\": \"review\", \"business_id\": \"1CBs84C-a-cuA3vncXVSAw\", \"votes\": {\"c"
                + "ool\": 0, \"useful\": 0, \"funny\": 0}, \"review_id\": \"0a-pCW4guXIlWNpVeBHChg\", \"text\": \"The pi"
                + "zza is terrible, but if you need a place to watch a game or just down some pitchers, this place wo"
                + "rks.\n\nOh, and the pasta is even worse than the pizza.\", \"stars\": 2, \"user_id\": \"90wm_01FAI"
                + "qhcgV_mPON9Q\", \"date\": \"2006-07-26\"}\")");
        queries.add("addReview(\"{\"type\": \"review\", \"business_id\": \"1CBs84C-a-cuA3vncXVSAw\", \"votes\": {\"c"
                + "ool\": 0, \"useful\": 0, \"funny\": 0}, \"newid\": \"0a-pCW4guXIlWNpVeBHChg\", \"text\": \"The pi"
                + "zza is terrible, but if you need a place to watch a game or just down some pitchers, this place wo"
                + "rks.\n\nOh, and the pasta is even worse than the pizza.\", \"stars\": 2, \"user_id\": \"90wm_01FAI"
                + "qhcgV_mPON9Q\", \"date\": \"2006-07-26\"}\")");
        queries.add("addUser(\"{\"url\": \"http://www.yelp.com/user_details?userid=CO8LSMeF1cyEno-9vCOipQ\", \"votes\""
                + ": {\"funny\": 1, \"useful\": 5, \"cool\": 1}, \"review_count\": 12, \"type\": \"user\", \"user_id\":"
                + " \"CO8LSMeF1cyEno-9vCOipQ\", \"name\": \"EastBayResident S.\", \"average_stars\": 4.16666666666667}\")");
        queries.add("addUser(\"{\"url\": \"http://www.yelp.com/user_details?userid=CO8LSMeF1cyEno-9vCOipQ\", \"votes\""
                + ": {\"funny\": 1, \"useful\": 5, \"cool\": 1}, \"review_count\": 12, \"type\": \"user\", \"user_id\":"
                + " \"newID\", \"name\": \"EastBayResident S.\", \"average_stars\": 4.16666666666667}\")");
        

        Server server = new Server(port);
        new Thread(server).start();

        for (int i = 0; i < queries.size(); i++) {
            QuerySender querySender = new QuerySender(queries.get(i), port);
            new Thread(querySender).start();
        }

    }
}

class QuerySender implements Runnable {
    private final String query;
    private int port;

    public QuerySender(String query, int port) {
        this.query = query;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            @SuppressWarnings("resource")
            Socket toServer = new Socket("localhost", port);

            PrintWriter out = new PrintWriter(toServer.getOutputStream());
            out.println(query);
            out.flush();

            BufferedReader answerBuffer = new BufferedReader(new InputStreamReader(toServer.getInputStream()));
            
            String JSONanswer = answerBuffer.readLine();
            while(answerBuffer.ready()){
                JSONanswer+="\n";
                JSONanswer+=answerBuffer.readLine();
            }
            
            System.out.println(JSONanswer);
            
            answerBuffer.close();
        } catch (IOException ioe) {
            System.exit(-1);
        }
    }

}

class Server implements Runnable {
    private int port;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            new RestaurantDBServer(port, "data/restaurants.json", "data/reviews.json", "data/users.json");
        } catch (IOException e) {

        }
    }

}
