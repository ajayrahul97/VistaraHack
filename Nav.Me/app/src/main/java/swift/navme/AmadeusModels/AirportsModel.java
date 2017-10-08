package swift.navme.AmadeusModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajayrahul on 8/10/17.
 */

public class AirportsModel {
    @SerializedName("code")
    String code;

    @SerializedName("name")
    String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
