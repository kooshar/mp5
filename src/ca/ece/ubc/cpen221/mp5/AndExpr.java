package ca.ece.ubc.cpen221.mp5;

import java.util.ArrayList;
import java.util.Stack;

public class AndExpr implements QueryExpresssion {
    ArrayList<Atom> atoms;
    
    public AndExpr(ArrayList<Atom> atoms){
        this.atoms=atoms;
    }
    
    @Override
    public ArrayList<Restaurant> search() {
        Stack<ArrayList<Restaurant>> searchResult=new Stack<>();
        
        for(Atom atom:atoms){
            searchResult.push(atom.search());
        }
        
        ArrayList<Restaurant> leftSide=new ArrayList<>();
        ArrayList<Restaurant> rightSide=new ArrayList<>();
        
        while(!searchResult.isEmpty()){
            leftSide=searchResult.pop();
            if(!searchResult.isEmpty()){
                break;
            }else{
                rightSide=searchResult.pop();
                searchResult.push(andArray(leftSide,rightSide));
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
    private ArrayList<Restaurant> andArray(
            ArrayList<Restaurant>leftSide,
            ArrayList<Restaurant>rightSide)
    {
        ArrayList<Restaurant> answer=new ArrayList<>();
        
        
        for(Restaurant newrestaurant:rightSide){
            boolean newRestaurantExist=false;
            
            for(Restaurant restaurant:leftSide){
                if (newrestaurant.getBusinessID().equals(restaurant.getBusinessID())){
                    newRestaurantExist=true;
                }
            }
            
            if(newRestaurantExist){
                answer.add(newrestaurant);
            }
        }
        
        return answer;
        
    }

}
