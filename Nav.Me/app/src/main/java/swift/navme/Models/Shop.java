package swift.navme.Models;

public class Shop {
    private String shopName, description, avgTime;
    private int imageId;

    public Shop() {
    }

    public Shop(String shopName, String description, String avgTime, int imageId) {
        this.shopName = shopName;
        this.description = description;
        this.avgTime = avgTime;
        this.imageId = imageId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String name) {
        this.shopName = name;
    }

    public String getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(String avgTime) {
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
}
