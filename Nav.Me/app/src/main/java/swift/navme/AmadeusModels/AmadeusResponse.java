package swift.navme.AmadeusModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ajayrahul on 8/10/17.
 */

public class AmadeusResponse {
    @SerializedName("city")
    CityModel cityModel;

    @SerializedName("airports")
    List<AirportsModel> airportsModelList;

    public CityModel getCityModel() {
        return cityModel;
    }

    public List<AirportsModel> getAirportsModelList() {
        return airportsModelList;
    }
}
