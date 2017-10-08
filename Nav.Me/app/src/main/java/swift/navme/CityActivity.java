package swift.navme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import swift.navme.Adapters.AirportAdapter;
import swift.navme.AmadeusModels.AirportsModel;
import swift.navme.AmadeusModels.AmadeusResponse;

public class CityActivity extends AppCompatActivity {

    TextView city, state, country;
    AirportAdapter airportAdapter;
    List<AirportsModel> airportsModelList = new ArrayList<>();
    RecyclerView localAirportlist;
    LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city2);
        city = (TextView) findViewById(R.id.city_name);
        state = (TextView) findViewById(R.id.state);
        country = (TextView) findViewById(R.id.country);
        localAirportlist = (RecyclerView) findViewById(R.id.airport_list) ;

        airportAdapter = new AirportAdapter(this, airportsModelList);

        layoutManager = new LinearLayoutManager(this);
        localAirportlist.setLayoutManager(layoutManager);
        localAirportlist.setAdapter(airportAdapter);


        Log.e("HHE", "HHEHEE");
        final CityAPI cityAPI = NetworkingFactory.getCityInstance().getCityApi();
        Call<AmadeusResponse> cityModelCall = cityAPI.getCityInfo("PAR", "jUvOnfAlDYbiG5mNQa9HtBHAXCNYZhzX");
        cityModelCall.enqueue(new Callback<AmadeusResponse>() {
            @Override
            public void onResponse(Call<AmadeusResponse> call, Response<AmadeusResponse> response) {
                if (response.body() != null) {
                    Log.i("Aaa", "Rsponse not null");
                    city.setText(response.body().getCityModel().getName());
                    state.setText(response.body().getCityModel().getState());
                    country.setText(response.body().getCityModel().getCountry());
                    List<AirportsModel> localairportsList = response.body().getAirportsModelList();
                    airportsModelList.addAll(localairportsList);
                    airportAdapter.notifyDataSetChanged();
                } else {
                    Log.i("Aaa", "Rwsponse IISSS null");
                }
            }

            @Override
            public void onFailure(Call<AmadeusResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "HEEE", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
