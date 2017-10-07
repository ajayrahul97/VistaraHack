/* Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package swift.navme;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.IndoorLevel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A demo activity showing how to use indoor.
 */
public class MapsActivity3 extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng point1, point2, origin;


    private boolean showLevelPicker = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        origin = new LatLng(37.614631, -122.385153);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 18));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                point1 = latLng;
            }
        });
    }

    /**
     * Called when the toggle level picker button is clicked.
     */
    public void onToggleLevelPicker(View view) {
        showLevelPicker = !showLevelPicker;
        mMap.getUiSettings().setIndoorLevelPickerEnabled(showLevelPicker);
    }

    /**
     * Called when the focused building info is clicked.
     */
    public void onFocusedBuildingInfo(View view) {
        IndoorBuilding building = mMap.getFocusedBuilding();
        if (building != null) {
            StringBuilder s = new StringBuilder();
            for (IndoorLevel level : building.getLevels()) {
                s.append(level.getName()).append(" ");
            }
            if (building.isUnderground()) {
                s.append("is underground");
            }
            setText(s.toString());
        } else {
            setText("No visible building");
        }
    }

    /**
     * Called when the focused level info is clicked.
     */
    public void onVisibleLevelInfo(View view) {
        IndoorBuilding building = mMap.getFocusedBuilding();
        if (building != null) {
            IndoorLevel level =
                    building.getLevels().get(building.getActiveLevelIndex());
            if (level != null) {
                setText(level.getName());
            } else {
                setText("No visible level");
            }
        } else {
            setText("No visible building");
        }
    }

    /**
     * Called when the activate higher level is clicked.
     */
    public void onHigherLevel(View view) {
        IndoorBuilding building = mMap.getFocusedBuilding();
        if (building != null) {
            List<IndoorLevel> levels = building.getLevels();
            if (!levels.isEmpty()) {
                int currentLevel = building.getActiveLevelIndex();
                // The levels are in 'display order' from top to bottom,
                // i.e. higher levels in the building are lower numbered in the array.
                int newLevel = currentLevel - 1;
                if (newLevel == -1) {
                    newLevel = levels.size() - 1;
                }
                IndoorLevel level = levels.get(newLevel);
                setText("Activating level " + level.getName());
                level.activate();
            } else {
                setText("No levels in building");
            }
        } else {
            setText("No visible building");
        }
    }

    public void onAddMarker(View view) {
        if (point1 != null) {
            addMarkers(point1, "Here");
        }
        Toast.makeText(MapsActivity3.this, "Cdnt:" + String.valueOf(point1.latitude) + ".." + String.valueOf(point1.longitude), Toast.LENGTH_LONG
        );
        Log.e("cdnt", String.valueOf(point1.latitude) + ".." + String.valueOf(point1.longitude));


    }

    public void onAddPath(View view) {
        drawPath(point1, origin);
    }

    private void setText(String message) {
        TextView text = (TextView) findViewById(R.id.top_text);
        text.setText(message);
    }

    public void addMarkers(LatLng pos, String text) {
        mMap.addMarker(new MarkerOptions().position(pos).title(text));

    }

    public void drawPath(LatLng pos1, LatLng pos2) {

//        Polyline polyline  = mMap.addPolyline(new PolylineOptions()
//        .add(pos1,pos2)
//        .width(4)
//        .color(Color.parseColor("#05b1fb"))//Google maps blue color
//        .geodesic(true));

        String urlPass = makeURL(origin.latitude, origin.longitude, point1.latitude, point1.longitude);
        ConnectAsyncTask asyncTask = new ConnectAsyncTask(urlPass);
        asyncTask.execute();

    }

    public String makeURL(double sourcelat, double sourcelog, double destlat, double destlog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString
                .append(Double.toString(sourcelog));
        urlString.append("&destination=");// to
        urlString
                .append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=walking&alternatives=true");
        urlString.append("&key=AIzaSyCzMYoH0gJDstUbcWsicITDcIURScd42Y0");
        Log.i("TAG", "makeURL: " + urlString.toString());
        return urlString.toString();

    }

    public void drawPath(String result) {

        try {
            //Tranform the string into a json object
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);

            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .addAll(list)
                    .width(12)
                    .color(Color.parseColor("#05b1fb"))//Google maps blue color
                    .geodesic(true)
            );


            /*
           for(int z = 0; z<list.size()-1;z++){
                LatLng src= list.get(z);
                LatLng dest= list.get(z+1);
                Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude,   dest.longitude))
                .width(2)
                .color(Color.BLUE).geodesic(true));
            }
           */
        } catch (JSONException e) {

        }
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }


    private class ConnectAsyncTask extends AsyncTask<Void, Void, String> {
        String url;

        ConnectAsyncTask(String urlPass) {
            url = urlPass;
        }

        @Override
        protected String doInBackground(Void... params) {
            JSONParser jParser = new JSONParser();
            String json = jParser.getJSONFromUrl(url);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                Log.i("TAG", "onPostExecute: Path Available");
                drawPath(result);
            } else {
                Log.i("TAG", "onPostExecute: Path Not Available");
            }

            //Make the SnackBar after the request is executed.

        }

    }


}