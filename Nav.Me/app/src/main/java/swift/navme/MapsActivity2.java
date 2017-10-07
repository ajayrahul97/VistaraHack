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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.IndoorLevel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import swift.navme.Hardcode.hardcode;

    /**
     * A demo activity showing how to use indoor.
     */
public class MapsActivity2 extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng point1, point2, origin;
    private ArrayList<Circle> drawnCircle;
    int i = 2;


    private boolean showLevelPicker = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        drawnCircle = new ArrayList<>();

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

    public void onAddMarker(View view){
        if(point1!=null){
            addMarkers(point1,"Here");
        }


    }

    public void onAddPath(View view){
        drawPath(point1,origin);
    }

    private void setText(String message) {
        TextView text = (TextView) findViewById(R.id.top_text);
        text.setText(message);
    }

    public void addMarkers(LatLng pos,String text){
        mMap.addMarker(new MarkerOptions().position(pos).title(text));

    }

    public void drawPath(LatLng pos1, LatLng pos2){

        Polyline polyline  = mMap.addPolyline(new PolylineOptions()
        .add(pos1,pos2)
        .width(4)
        .color(Color.parseColor("#05b1fb"))//Google maps blue color
        .geodesic(true));
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    private void drawCircle(LatLng center, float r){
        Circle circle = mMap.addCircle(new CircleOptions()
                .center(center)
                .radius(r)
                .strokeColor(Color.BLACK)
                .strokeWidth((float) 1)
                .fillColor(Color.argb(70,0,0,255)));
        drawnCircle.add(circle);
    }


    public void onLocateMeClicked(View view) {
        int point = (i++)%3;
        drawCircle(hardcode.entryPoints.get(point),8);
    }

    public void onStubClicked(View view) {
        for(int i = 0; i < drawnCircle.size(); i++){
            drawnCircle.get(i).setVisible(false);
        }
    }
}