package swift.navme;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajayrahul on 8/10/17.
 */

public class CityModel {
    @SerializedName("name")
    String name;
    @SerializedName("state")
    String state;
    @SerializedName("country")
    String country;
}
