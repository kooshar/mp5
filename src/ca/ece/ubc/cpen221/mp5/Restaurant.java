package ca.ece.ubc.cpen221.mp5;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Constructor
 */
public class Restaurant {
    private boolean open;
    private String url;
    private double longitude;
    private double latitude;
    private ArrayList<String> neighborhoods;
    private String business_id;
    private String name;
    private ArrayList<String> categories;
    private String state;
    private String type;
    private double stars;
    private String city;
    private String full_address;
    private int review_count;
    private String photo_url;
    private ArrayList<String> schools;
    private int price;

    public Restaurant(boolean open, String url, double longitude, double latitude, JSONArray neighborhoods,
            String business_id, String name, JSONArray categories, String state, String type, double stars, String city,
            String full_address, int review_count, String photo_url, JSONArray schools, int price) {

        this.url = url;
        this.open = open;
        this.business_id = business_id;
        this.city = city;

        this.categories = new ArrayList<String>();
        for (Object iter : categories) {
            this.categories.add(iter.toString());

        }

        this.full_address = full_address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;

        this.neighborhoods = new ArrayList<String>();
        for (Object iter : neighborhoods) {
            this.neighborhoods.add(iter.toString());
        }

        this.photo_url = photo_url;
        this.price = price;
        this.stars = stars;
        this.review_count = review_count;
        this.state = state;

        this.schools = new ArrayList<String>();
        for (Object iter : schools) {
            this.schools.add(iter.toString());
        }
    }

    public String getbusiness_id() {
        return business_id;
    }

    public String getname() {
        return name;
    }

    public String gettype() {
        return type;
    }

    public String getphoto_url() {
        return photo_url;
    }

    public int getprice() {
        return price;
    }

    public boolean getopen() {
        return open;
    }

    public String getUrl() {
        return url;
    }

    public String getcity() {
        return city;
    }

    public ArrayList<String> getcategories() {
        return new ArrayList<String>(categories);
    }

    public String getfull_address() {
        return full_address;
    }

    public ArrayList<String> getneighborhoods() {
        return new ArrayList<String>(neighborhoods);
    }

    public double getlatitude() {
        return latitude;
    }

    public double getlongitude() {
        return longitude;
    }

    public String geturl() {
        return url;
    }

    public double getstars() {
        return stars;

    }

    public int review_count() {
        return review_count;
    }

    public ArrayList<String> getschools() {
        return new ArrayList<String>(schools);
    }

    public String getstate() {
        return state;
    }

    @SuppressWarnings("unchecked")
    public String getJSONString() {
        JSONObject restaurant = new JSONObject();

        restaurant.put("open", open);
        restaurant.put("url", url);
        restaurant.put("longitude", longitude);
        restaurant.put("neighborhoods", neighborhoods);
        restaurant.put("business_id", business_id);
        restaurant.put("name", name);
        restaurant.put("categories", categories);
        restaurant.put("state", state);
        restaurant.put("type", type);
        restaurant.put("stars", stars);
        restaurant.put("city", city);
        restaurant.put("full_address", full_address);
        restaurant.put("review_count", review_count);
        restaurant.put("photo_url", photo_url);
        restaurant.put("schools", schools);
        restaurant.put("latitude", latitude);
        restaurant.put("price", price);

        return restaurant.toJSONString();
    }

}
