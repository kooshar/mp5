package ca.ece.ubc.cpen221.mp5;

import org.json.simple.JSONObject;

public class Review {
    private String type;
    private String businessID;
    private int coolVotes;
    private int usefulVotes;
    private int funnyVotes;
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
            int coolVotes, 
            int usefulVotes, 
            int funnyVotes,
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
        this.coolVotes = coolVotes;
        this.usefulVotes = usefulVotes;
        this.funnyVotes = funnyVotes;
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
    public int getCoolVotes() {
        return coolVotes;

    }

    /**
     * 
     * @return the number of usefulVotes
     */
    public int getUsefulVotes() {
        return usefulVotes;

    }

    /**
     * 
     * @return the number of funnyVotes
     */
    public int getFunnyVotes() {
        return funnyVotes;
    }
    
    @SuppressWarnings("unchecked")
    public String getJSONString(){
        JSONObject review= new JSONObject();
        
        JSONObject vote=new JSONObject();
        vote.put("cool",coolVotes);
        vote.put("useful", usefulVotes);
        vote.put("funny", funnyVotes);
        
        review.put("vote", vote);
        review.put("review_id", reviewID);
        review.put("text", reviewText);
        review.put("stars", stars);
        review.put("user_id", userID);
        review.put("date", date);
        
        return review.toJSONString();
    }
}
