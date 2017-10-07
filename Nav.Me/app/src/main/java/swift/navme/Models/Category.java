package swift.navme.Models;

public class Category {

    String title;
    int preference;

    public Category(String title, int preference) {
        this.title = title;
        this.preference = preference;
    }

    public int getPreference() {
        return preference;
    }

    public String getTitle() {
        return title;
    }
    
}
