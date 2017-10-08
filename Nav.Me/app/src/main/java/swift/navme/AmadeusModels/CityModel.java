package swift.navme.AmadeusModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajayrahul on 8/10/17.
 */

public class CityModel {
    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    @SerializedName("name")
    String name;
    @SerializedName("state")
    String state;
    @SerializedName("country")
    String country;
}
