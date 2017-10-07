package swift.navme;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.kontakt.sdk.android.common.profile.IEddystoneDevice;

import java.util.ArrayList;

import swift.navme.BeaconService.BeaconFinderService;
import swift.navme.Hardcode.hardcode;

public class BaseActivity extends AppCompatActivity {

    private final static int REQUEST_ENABLE_BT = 1;
    private final static int REQUEST_ENABLE_LOCATION = 2;
    private final static int REQUEST_ENABLE_FINE_LOCATION = 3;
    public final static String BEACON = "UID";
    public  static int seconds = 30;
    public  static int minutes = 30;
    public  static int hour = 02;


    BroadcastReceiver broadcastReceiver;
    ArrayList<IEddystoneDevice> beaconsArray;
    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeReceiver();
        checkPermissions();
        makeSnackbar();
        addStaticData();
    }

    private void makeSnackbar() {

        if(isServiceRunning(BeaconFinderService.class))
        {
            Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content), "Scanning For Beacons", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Stop", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(BaseActivity.this,BeaconFinderService.class);
                            BaseActivity.this.stopService(i);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    makeSnackbar();
                                }
                            }, 1000);

                        }
                    });
            snackbar.show();
        }

        else
        {
            Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content), "Scan For Beacons", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Start", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(BaseActivity.this,BeaconFinderService.class);
                            BaseActivity.this.startService(i);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    makeSnackbar();
                                }
                            }, 1000);
                        }
                    });
            snackbar.show();
        }
    }

    private void makeReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                beaconsArray = intent.getParcelableArrayListExtra(BeaconFinderService.beacons_array);
                Toast.makeText(BaseActivity.this, "Beacon Found! \n" + beaconsArray.get(0).getUrl() + "\n" +
                        beaconsArray.get(0).getUniqueId(), Toast.LENGTH_LONG).show();



            }
        };
    }

    private void startService() {
        Intent i = new Intent(this, BeaconFinderService.class);
        startService(i);
    }

    @Override
    protected void onStart() {

        //Register the receiver
        LocalBroadcastManager.getInstance(this).registerReceiver((broadcastReceiver),
                new IntentFilter(BeaconFinderService.intent_filter)
        );

        super.onStart();
    }

    @Override
    protected void onStop() {

        //Unregister the receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onStop();

    }

    private void checkPermissions() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!bluetoothAdapter.isEnabled())
        {
            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(i,REQUEST_ENABLE_BT);
        }

        //Turn Location Settings On
        if(!isLocationEnabled(getApplicationContext()))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Settings").setMessage("Please Turn on Location Settings to proceed further.").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent ii = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(ii,REQUEST_ENABLE_LOCATION);

                }
            }).show();
        }

        //Runtime Permission
        if(Build.VERSION.SDK_INT >= 23 && (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED))
        {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_ENABLE_FINE_LOCATION);
        }

        else
        {
            startService();
        }

    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {

        if(requestCode == REQUEST_ENABLE_FINE_LOCATION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            startService();
        }

        else
        {
            Toast.makeText(this,"Please accept the Runtime Permission", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){

            case REQUEST_ENABLE_LOCATION:
            {
                if(!isLocationEnabled(getApplicationContext()))
                    Toast.makeText(this, "Location is required!", Toast.LENGTH_SHORT).show();

            }

            case REQUEST_ENABLE_BT:{

                if(resultCode != RESULT_OK)
                    Toast.makeText(this, "Bluetooth is Required.",Toast.LENGTH_SHORT).show();
                break;

            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void addStaticData(){
        hardcode.entryPoints = new ArrayList<>();
        hardcode.entryPoints.add(new LatLng(37.617809,-122.386687));
        hardcode.entryPoints.add(new LatLng(37.616845,-122.384237));
        hardcode.entryPoints.add(new LatLng(37.614802,-122.385497));

        hardcode.pathPoints = new ArrayList<>();
        hardcode.pathPoints.add(new LatLng(37.614802,-122.385497));
        hardcode.pathPoints.add(new LatLng(37.61584137416485,-122.38449577242135 ));
        hardcode.pathPoints.add(new LatLng(37.61740747841049,-122.38484378904106 ));
        hardcode.pathPoints.add(new LatLng(37.61779548048554,-122.38618958741426 ));
        hardcode.pathPoints.add(new LatLng(37.617700671130514,-122.38750655204058));


        hardcode.pathNames = new ArrayList<>();
        hardcode.pathNames.add("Your Location");
        hardcode.pathNames.add("McDonalds");
        hardcode.pathNames.add("WashRoom");
        hardcode.pathNames.add("Book Store");
        hardcode.pathNames.add("Gate No .2");


        hardcode.pathSnippets = new ArrayList<>();
        hardcode.pathSnippets.add("Current Location");
        hardcode.pathSnippets.add("Restaurant | Burgers&Fries | 4.5/5");
        hardcode.pathSnippets.add(" ");
        hardcode.pathSnippets.add("Bibliophile Corner | Free Reading | 4.8/5");
        hardcode.pathSnippets.add("Boarding Gate | Time Remaining to board : 2hrs 30min");


    }



}
