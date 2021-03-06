package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.ArrayList;

public class Location {
    private final double accuracy = 0.000000001;
    private double longitude;
    private double latitude;

    public Location(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * returns the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * returns the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * returns a random location where longitudeMax > longitude > longitudeMin
     * and latitudeMax > latitude > latitudeMin
     * 
     * @param longitudeMax
     * @param longitudeMin
     * @param latitudeMax
     * @param latitudeMin
     */
    public static Location random(double longitudeMax, double longitudeMin, double latitudeMax, double latitudeMin) {
        double longitude = longitudeMin + (longitudeMax - longitudeMin) * Math.random();
        double latitude = latitudeMin + (latitudeMax - latitudeMin) * Math.random();

        return new Location(longitude, latitude);
    }

    /**
     * this method returns the index of the closest location in endPoints from
     * the startPoint
     * 
     * @param startPoint
     * @param Endpoints
     */
    public static int closestLocation(Location startPoint, ArrayList<Location> endPoints) {
        double closestDistance = Location.distance(startPoint, endPoints.get(0));
        int closestLocationIndex = 0;

        for (int index = 0; index < endPoints.size(); index++) {
            if (Location.distance(startPoint, endPoints.get(index)) < closestDistance) {
                closestDistance = Location.distance(startPoint, endPoints.get(index));
                closestLocationIndex = index;
            }
        }
        return closestLocationIndex;
    }

    /**
     * returns the distance of two location a and b
     * 
     * @param a
     * @param b
     */
    public static double distance(Location a, Location b) {
        return Math.sqrt(
                Math.pow(a.getLatitude() - b.getLatitude(), 2) + Math.pow(a.getLongitude() - b.getLongitude(), 2));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Location) {
            Location location = (Location) o;
            return location.latitude < this.latitude + accuracy && location.latitude > this.latitude - accuracy
                    && location.longitude > this.longitude - accuracy && location.longitude < this.longitude + accuracy;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (int) (this.longitude / 2 + this.latitude / 2);
    }

    @Override
    public String toString() {
        return "Location [longitude=" + longitude + ", latitude=" + latitude + "]";
    }

}
