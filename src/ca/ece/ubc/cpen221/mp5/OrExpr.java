package ca.ece.ubc.cpen221.mp5;

import java.util.ArrayList;
import java.util.Stack;

public class OrExpr extends Atom implements QueryExpression {
    
    ArrayList<AndExpr> andExpressions;
    
    public OrExpr(ArrayList<AndExpr> andExpressions){
        this.andExpressions=andExpressions;
    }
    
    @Override
    public ArrayList<Restaurant> search() {
        Stack<ArrayList<Restaurant>> searchResult=new Stack<>();
        
        for(AndExpr andExpr:andExpressions){
            searchResult.push(andExpr.search());
        }
        
        ArrayList<Restaurant> leftSide=new ArrayList<>();
        ArrayList<Restaurant> rightSide=new ArrayList<>();
        
        while(!searchResult.isEmpty()){
            leftSide=searchResult.pop();
            if(!searchResult.isEmpty()){
                break;
            }else{
                rightSide=searchResult.pop();
                searchResult.push(orArray(leftSide,rightSide));
            }
        }
        
        return leftSide;
    }
    
    /**
     * Gets two arrays as an input. Returns the array of restaurants
     * that are in either on of the arrays
     * 
     * @param leftSide
     * @param RightSide
     * @return an array of restaurants that are in either on of the arrays
     */
    private ArrayList<Restaurant> orArray(
            ArrayList<Restaurant>leftSide,
            ArrayList<Restaurant>rightSide)
    {
        ArrayList<Restaurant> answer=new ArrayList<>();
        
        for(Restaurant restaurant:leftSide){
            answer.add(restaurant);
        }
        
        for(Restaurant newrestaurant:rightSide){
            boolean newRestaurantExist=false;
            for(Restaurant restaurant:leftSide){
                if (newrestaurant.getbusiness_id().equals(restaurant.getbusiness_id())){
                    newRestaurantExist=true;
                }
            }
            
            if(!newRestaurantExist){
                answer.add(newrestaurant);
            }
        }
        
        return answer;
        
    }
}
