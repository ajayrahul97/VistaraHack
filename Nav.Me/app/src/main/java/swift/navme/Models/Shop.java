package swift.navme.Models;

public class Shop {
    private String shopName, description;
    private int imageId, avgTime, timeToReach, distance;
    private double rating;
    private double lat, longi;

    public Shop() {
    }

    public Shop(String shopName, String description, int avgTime, int imageId, int timeToReach, int distance, double rating, double lat, double longi) {
        this.shopName = shopName;
        this.description = description;
        this.avgTime = avgTime;
        this.imageId = imageId;

        this.timeToReach = timeToReach;
        this.distance = distance;
        this.rating = rating;

        this.lat = lat;
        this.longi = longi;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String name) {
        this.shopName = name;
    }

    public int getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(int avgTime) {
        this.avgTime = avgTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getTimeToReach() {
        return timeToReach;
    }

    public void setTimeToReach(int timeToReach) {
        this.timeToReach = timeToReach;
    }
}
