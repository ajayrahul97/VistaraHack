package swift.navme;

/**
 * Created by ajayrahul on 8/10/17.
 */

import android.content.Context;

import com.readystatesoftware.chuck.ChuckInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class NetworkingFactory {

    private CityAPI cityAPI;
    private Retrofit retrofit;

    private static NetworkingFactory cityInstance;

    public static void init(Context context) {
        cityInstance = new NetworkingFactory(context, "https://api.sandbox.amadeus.com/v1.2/", true);
    }

    public static NetworkingFactory getCityInstance() {
        return cityInstance;
    }


    private NetworkingFactory(Context context, String url, boolean json) {
        OkHttpClient.Builder httpclient = new OkHttpClient.Builder().addInterceptor(new ChuckInterceptor(context));
        if (json) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpclient.build())
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .client(httpclient.build())
                    .build();
        }
    }

    public CityAPI getCityApi() {
        cityAPI = retrofit.create(CityAPI.class);
        return cityAPI;
    }

}
