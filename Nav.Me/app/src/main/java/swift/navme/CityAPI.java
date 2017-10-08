package swift.navme;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import swift.navme.AmadeusModels.AmadeusResponse;
import swift.navme.AmadeusModels.CityModel;

/**
 * Created by ajayrahul on 8/10/17.
 */

public interface CityAPI {

    @GET("location/{code}/")
    Call<AmadeusResponse>getCityInfo(
        @Path("code") String code,
        @Query("apikey") String apiKey

    );


}
