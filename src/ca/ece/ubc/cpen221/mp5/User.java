package ca.ece.ubc.cpen221.mp5;

// TODO: Use this class to represent a Yelp user.

public class User {
    private String url;
    private int funnyVotes;
    private int usefulVotes;
    private int coolVotes;
    private int reviewCount;
    private String type;
    private String userID;
    private String name;
    private double averageStars;
    
    /**
     * Constructor
     * 
     * @param url
     * @param funnyVotes
     * @param usefulVotes
     * @param coolVotes
     * @param reviewCount
     * @param type
     * @param userID
     * @param name
     * @param averageStars
     */
    public User(String url,
            int funnyVotes, 
            int usefulVotes, 
            int coolVotes, 
            int reviewCount,
            String type, 
            String userID,
            String name,
            double averageStars)
    {
         this.url=url;
         this.funnyVotes=funnyVotes;
         this.usefulVotes=usefulVotes;
         this.coolVotes=coolVotes;
         this.reviewCount=reviewCount;
         this.type=type;
         this.userID=userID;
         this.name=name;
         this.averageStars=averageStars;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the number of funnyVotes
     */
    public int getFunnyVotes() {
        return funnyVotes;
    }

    /**
     * @return the number of usefulVotes
     */
    public int getUsefulVotes() {
        return usefulVotes;
    }

    /**
     * @return the number of reviewCount
     */
    public int getReviewCount() {
        return reviewCount;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @return the double averageStars
     */
    public double getAverageStars() {
        return averageStars;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the number of coolVotes
     */
    public int getCoolVotes() {
        return coolVotes;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

}
