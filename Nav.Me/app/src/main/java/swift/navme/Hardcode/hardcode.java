package swift.navme.Hardcode;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by yash on 7/10/17.
 */

public class hardcode {

    public static ArrayList<LatLng> entryPoints;
    public static ArrayList<LatLng> pathPoints;
    public static ArrayList<String> pathNames;

    public static int generateRandom(int Min, int Max){
        return Min + (int)(Math.random() * ((Max - Min) + 1));
    }

}
