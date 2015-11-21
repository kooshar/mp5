package ca.ece.ubc.cpen221.mp5;

// TODO: Use this class to represent a Yelp review.

public class Review {
    private String type;
    private String businessID;
    private int coolVote;
    private int usefulVote;
    private int funnyVote;
    private String reviewID;
    private String reviewText;
    private int stars;
    private String userID;
    private String date;
    
    /**
     * Constructor
     * 
     * @param type
     * @param businessID
     * @param coolVote
     * @param usefulVote
     * @param funnyVote
     * @param reviewID
     * @param reviewText
     * @param stars
     * @param userID
     * @param date
     */
    public Review(
            String type,
            String businessID,
            int coolVote, 
            int usefulVote, 
            int funnyVote,
            String reviewID, 
            String reviewText, 
            int stars, 
            String userID, 
            String date
            )
    {
        this.type = type;
        this.businessID = businessID;
        this.reviewID = reviewID;
        this.reviewText = reviewText;
        this.stars = stars;
        this.userID = userID;
        this.date = date;
        this.coolVote = coolVote;
        this.usefulVote = usefulVote;
        this.funnyVote = funnyVote;
    }
    /**
     * 
     * @return the type
     */
    public String getType() {
        return type;
    }
    
    /**
     * 
     * @return the businessID
     */
    public String getBusinessID() {
        return businessID;
    }

    /**
     * 
     * @return the reviewID
     */
    public String getReviewID() {
        return reviewID;
    }

    /**
     * 
     * @return the reviewText
     */
    public String getReviewText() {
        return reviewText;
    }

    /**
     * 
     * @return the number of stars
     */
    public int getStars() {
        return stars;

    }

    /**
     * 
     * @return the userID
     */
    public String getUserID() {
        return userID;

    }
    
    /**
     * 
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @return the number of coolVotes
     */
    public int getCoolVote() {
        return coolVote;

    }

    /**
     * 
     * @return the number of usefulVotes
     */
    public int getUsefulVote() {
        return usefulVote;

    }

    /**
     * 
     * @return the number of funnyVotes
     */
    public int getFunnyVote() {
        return funnyVote;
    }
}
